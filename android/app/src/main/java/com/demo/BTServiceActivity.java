package com.dz365.ble;

import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BTServiceActivity extends Activity {
    ListView lvBTService;
    private List listServiceUUID,listUUIDDetail;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    private MyShowAdapter myAdapter2;
    private Handler msgHandler;









    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                app.mConnectionState = STATE_CONNECTED;
                app.mBluetoothGatt.discoverServices();        //蓝牙模块连接成功，开始发现服务操作

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                app.mConnectionState = STATE_DISCONNECTED;   //设置状态为  断开
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            boolean in_ok = false,out_ok = false;

            //淘宝通用的uuid, 如果买的是个别厂商定制开发的ble模块，可以借助于工具app，测出
            //   该模块的uuid值
            String strRs232_out_servier_Uuid = "0000ffe0-0000-1000-8000-00805f9b34fb";
            String strRs232_out_chars_uuid   = "0000ffe1-0000-1000-8000-00805f9b34fb";

            String strRs232_in_servier_Uuid = "0000ffe0-0000-1000-8000-00805f9b34fb";
            String strRs232_in_chars_uuid   = "0000ffe1-0000-1000-8000-00805f9b34fb";

            //NBee 新一信息  XY-MBD07A 蓝牙模块 (BroadCom双模芯片方案)
        	/*
        	String strRs232_out_servier_Uuid = "0000ffe1-0000-1000-8000-00805f9b34fb";
        	String strRs232_out_chars_uuid   = "0000ffe3-0000-1000-8000-00805f9b34fb";

        	String strRs232_in_servier_Uuid = "0000ffe1-0000-1000-8000-00805f9b34fb";
        	String strRs232_in_chars_uuid   = "0000ffe2-0000-1000-8000-00805f9b34fb";
        	*/

            if (status == BluetoothGatt.GATT_SUCCESS) {
                app.bleServiceList = gatt.getServices();

                for (BluetoothGattService bs : app.bleServiceList) {
                    String strUuid = bs.getUuid().toString();
                    listServiceUUID.add(strUuid);           //服务id
                    List<BluetoothGattCharacteristic>list_chars = bs.getCharacteristics();
                    String strTemp = "";
                    for (BluetoothGattCharacteristic achars : list_chars) {
                        String strCharUUid = achars.getUuid().toString();
                        strTemp += strCharUUid + " ";
                    }
                    listUUIDDetail.add(strTemp);

                    /*
                    if (strRs232_out_servier_Uuid.equals(strUuid)) {   //查找out service uuid
                        List<BluetoothGattCharacteristic>list_chars = bs.getCharacteristics();
                        for (BluetoothGattCharacteristic achars : list_chars) {
                            String strCharUUid = achars.getUuid().toString();
                            if (strRs232_out_chars_uuid.equals(strCharUUid)) {
                                rs232_out_characteristic = bs.getCharacteristic(achars.getUuid());
                                out_ok = true;
                            }
                        }
                    }

                    if (strRs232_in_servier_Uuid.equals(strUuid)) {   //查找in service uuid
                        List<BluetoothGattCharacteristic>list_chars = bs.getCharacteristics();
                        for (BluetoothGattCharacteristic achars : list_chars) {
                            String strCharUUid = achars.getUuid().toString();
                            if (strRs232_in_chars_uuid.equals(strCharUUid)) {
                                rs232_in_characteristic = bs.getCharacteristic(achars.getUuid());
                                try {
                                    Thread.sleep(150);                  //延时
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
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
                                            gatt.writeDescriptor(descriptor);
                                        }

                                    }
                                    in_ok = true;
                                }
                            }
                        }
                    }
                     */
                }
                //if (in_ok && out_ok) {
                //    app.vib.vibrate(100);
               // }
                //myAdapter2.notifyDataSetChanged();
                //lvBTService.invalidate();
                //app.vib.vibrate(100);
                Message msg = new Message();
                msg.what = 0x1234;
                msgHandler.sendMessage(msg);  //将收到的下位机发来的数据发送给界面
            } else {
                //Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {

        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            byte[] buf = new byte[24];
            Message msg = new Message();
            msg.what = 0x1235;
            buf = characteristic.getValue();
            msg.obj = buf;
            msg.arg1 = buf.length;
            app.bluetoothMessageHandle.sendMessage(msg);  //将收到的下位机发来的数据发送给界面

        }
        @Override
        public void onCharacteristicWrite (BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status){
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btservice);

        lvBTService = (ListView)findViewById(R.id.lvBTService);
        listServiceUUID = new ArrayList();
        listUUIDDetail = new ArrayList();

        myAdapter2 = new MyShowAdapter(this,listServiceUUID,listUUIDDetail);
        lvBTService.setAdapter(myAdapter2);

        myAdapter2.notifyDataSetChanged();

        //显示传递过来的参数 address
        Intent intent = getIntent();
        String strAddress = intent.getStringExtra("address");
        //Toast.makeText(getApplicationContext(),strAddress,Toast.LENGTH_SHORT).show();
        int position = intent.getIntExtra("position",0);
        app.mDevice = app.mLeDevices.get(position);

        msgHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0x1234) {
                    myAdapter2.notifyDataSetChanged();
                }
            }
        };

        lvBTService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listServiceUUID.size() < 1) return;
                //进入设备的控制activity
                Intent intent = new Intent(BTServiceActivity.this,controlActivity.class);
                intent.putExtra("ServiceUUID",(String)listServiceUUID.get(position));        //设备地址
                startActivity(intent);
            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (app.mDevice == null) {
                    Toast.makeText(getApplicationContext(), "蓝牙设备为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                app.mBluetoothGatt = app.mDevice.connectGatt(getApplicationContext(), false, mGattCallback);
            }
        },1000);


    }
}
