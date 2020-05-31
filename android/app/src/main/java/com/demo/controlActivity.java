package com.dz365.ble;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class controlActivity extends AppCompatActivity {
    Button btnJDQOn,btnJDQOff,TimeSet20min,TimeSet40min,TimeSet60min;
    Button btnLEDOn,btnLEDOff;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    //显示结果 函数
    public void show_result(byte[] buffer,int count)
    {
        StringBuffer msg = new StringBuffer();
        TextView tvInfo = (TextView)findViewById(R.id.tvInfo);
        tvInfo.setText("实时温度");
        try {
            msg.append("\r\n");
            for (int i = 0; i < count; i++)
                msg.append(String.format("%c ", buffer[i]));

        }catch(Exception e) {

        }
        msg.append(" ℃");
        msg.append("\r\n");
//        try {
//            for (int i = 0; i < count; i++)
//                msg.append(String.format("%c ", buffer[i]));
//        }catch (Exception e) {
//
//        }
        tvInfo.setText(msg);      //将msg显示到界面上
    }



    BluetoothGattCharacteristic tempCharacteristic,rs232_out_characteristic,rs232_in_characteristic;

    void send_cmd(byte[] cmd,String tips) {
        if (rs232_out_characteristic == null) {
            Toast.makeText(getApplicationContext(), "无法发送", Toast.LENGTH_SHORT).show();
            return;
        }
        rs232_out_characteristic.setValue(cmd);     //将cmd内容发送给蓝牙下位机
        if (app.mBluetoothGatt.writeCharacteristic(rs232_out_characteristic)) {
            Toast.makeText(getApplicationContext(), tips + "发送成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), tips + "发送失败", Toast.LENGTH_SHORT).show();
        }
        app.vib.vibrate(100);            //震动100ms
    }

    @Override
    public void onBackPressed() {
        //释放资源
        if (app.mConnectionState != STATE_CONNECTED)
            return;
        if (app.mBluetoothGatt != null) {
            app.mBluetoothGatt.disconnect();
            app.mBluetoothGatt.close();
            app.mBluetoothGatt = null;
        }
        super.onBackPressed();//注释掉这行,back键不退出activity
    }

    public static final String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);




        app.bluetoothMessageHandle = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0x1235) {
                    show_result((byte [])msg.obj,msg.arg1);    //接收 数据，并显示到界面上
                }
            }
        };

        rs232_out_characteristic = null;
        rs232_in_characteristic = null;
        //显示传递过来的参数 address
        Intent intent = getIntent();
        final String strServiceUUID = intent.getStringExtra("ServiceUUID");
        TimeSet20min = (Button)findViewById(R.id.TimeSet20min);
        TimeSet40min = (Button)findViewById(R.id.TimeSet40min);
        TimeSet60min = (Button)findViewById(R.id.TimeSet60min);
//        btnJDQOn = (Button)findViewById(R.id.btnJDQOn);
        TimeSet20min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (app.mConnectionState != STATE_CONNECTED) {
                    Toast.makeText(getApplicationContext(), "蓝牙没有连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                byte []cmd = new byte[1];
                cmd[0] = (byte)'a';

                send_cmd(cmd,"间隔20分钟");
            }
        });
//        TimeSet40min = (Button) findViewById(R.id.TimeSet30min);
        TimeSet40min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (app.mConnectionState != STATE_CONNECTED) {
                    Toast.makeText(getApplicationContext(), "蓝牙没有连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                byte []cmd = new byte[1];
                cmd[0] = (byte)'b';
                send_cmd(cmd,"间隔40分钟");
            }
        });

        TimeSet60min = (Button)findViewById(R.id.TimeSet60min);
        TimeSet60min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (app.mConnectionState != STATE_CONNECTED) {
                    Toast.makeText(getApplicationContext(), "蓝牙没有连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                byte []cmd = new byte[1];
                cmd[0] = (byte)'c';

                send_cmd(cmd,"间隔60分钟");
            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //根据服务uuid分析出发送和接收charactor
                //strServiceUUID
                for (BluetoothGattService bs : app.bleServiceList) {
                    String strUuid = bs.getUuid().toString();
                    if (!strServiceUUID.equals(strUuid)) continue;
                    app.mConnectionState = STATE_CONNECTED;
                    List<BluetoothGattCharacteristic> list_chars = bs.getCharacteristics();
                    for (BluetoothGattCharacteristic achars : list_chars) {
                        String strCharUUid = achars.getUuid().toString();
                        tempCharacteristic = bs.getCharacteristic(achars.getUuid());
                        int property = tempCharacteristic.getProperties();
                        if ((property & BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) > 0) {
                            rs232_out_characteristic = bs.getCharacteristic(achars.getUuid());
                        }
                        if ((property & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0) {
                            rs232_out_characteristic = bs.getCharacteristic(achars.getUuid());
                        }
                        if ((property & BluetoothGattCharacteristic.PROPERTY_SIGNED_WRITE) > 0) {
                            rs232_out_characteristic = bs.getCharacteristic(achars.getUuid());
                        }
                        if (((property & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) || ((property & BluetoothGattCharacteristic.PROPERTY_READ) > 0)) {
                            rs232_in_characteristic = bs.getCharacteristic(achars.getUuid());
                            if (app.mBluetoothGatt.setCharacteristicNotification(rs232_in_characteristic, true)) {   //设置通知
                                List<BluetoothGattDescriptor> descriptorList = rs232_in_characteristic.getDescriptors();
                                if(descriptorList != null && descriptorList.size() > 0) {
                                    for(BluetoothGattDescriptor descriptor : descriptorList) {
                                        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                                        app.mBluetoothGatt.writeDescriptor(descriptor);
                                    }
                                } else {
                                    final BluetoothGattDescriptor descriptor = rs232_in_characteristic.getDescriptor(UUID.fromString(CLIENT_CHARACTERISTIC_CONFIG));
                                    if (descriptor != null) {
                                        descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
                                        app.mBluetoothGatt.writeDescriptor(descriptor);
                                    }
                                }
                            }
                        }
                    }
                    break;  //扫描完成
                }
            }
        },1000);
    }
}
