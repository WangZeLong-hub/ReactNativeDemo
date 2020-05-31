package com.demo;

import android.content.Intent;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import android.Manifest;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.facebook.react.ReactActivity;

public class MainActivity extends AppCompatActivity {

    Button buttonRefresh;

    ListView lvShow;
    List listMaster,listDetail;
    private MyShowAdapter myAdapter;

    private static final int REQUEST_ENABLE_BT = 1;



    List<String> deviceNamelist,addressList;


    private static final long SCAN_PERIOD = 2000;

    private boolean mScanning;

    private Handler mHandler;                               //handler对象

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    final int finalRss = rssi;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //回调函数，扫描到的设备 都保存起来
                            if (addressList.contains(device.getAddress())) return;  //已经存在的就不用加到列表中了
                            String strName = device.getName();
                            if (strName == null) return;
                            if (strName.length() <= 1) return;             //没有名字的蓝牙，不加

                            app.mLeDevices.add(device);
                            deviceNamelist.add(device.getName());
                            addressList.add(device.getAddress());

                            listMaster.add(device.getName());
                            listDetail.add(String.format("%s rssi:%d",device.getAddress(),finalRss));
                        }
                    });
                }
            };

    ScanCallback scanCallback = new ScanCallback() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            BluetoothDevice device = result.getDevice();
            //回调函数，扫描到的设备 都保存起来
            if (addressList.contains(device.getAddress())) return;  //已经存在的就不用加到列表中了
            String strName = device.getName();
            if (strName == null) return;
            if (strName.length() <= 1) return;             //没有名字的蓝牙，不加
            app.mLeDevices.add(device);
            deviceNamelist.add(device.getName());
            addressList.add(device.getAddress());
            listMaster.add(device.getName());
            listDetail.add(String.format("%s rssi:%d",device.getAddress(),result.getRssi()));
        }
    };
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    try {
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                            app.mBluetoothAdapter.stopLeScan(mLeScanCallback);   //停止搜索
                        } else {
                            app.bleScanner.stopScan(scanCallback);
                        }
                        myAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "搜索完成", Toast.LENGTH_SHORT).show();
                        buttonRefresh.setEnabled(true);  //搜索按钮允许点了
                    }catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, SCAN_PERIOD);


            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                //安卓6.0及以下版本操作
                mScanning = true;
                app.mBluetoothAdapter.startLeScan(mLeScanCallback);   //开始搜索
            } else {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    // 申请定位授权
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                } else {
                    mScanning = true;
                    app.bleScanner.startScan(scanCallback);
                }
            }
        } else {
            mScanning = false;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                app.mBluetoothAdapter.stopLeScan(mLeScanCallback);    //停止搜索
            }else {
                app.bleScanner.stopScan(scanCallback);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();         //创建handler
        app.mLeDevices = new ArrayList<BluetoothDevice>();

        lvShow = (ListView)findViewById(R.id.btInfo);
        listMaster = new ArrayList();
        listDetail = new ArrayList();

        deviceNamelist = new ArrayList<String>();
        addressList = new ArrayList<String>();
        app.bleServiceList = new ArrayList<BluetoothGattService>();

        myAdapter = new MyShowAdapter(this,listMaster,listDetail);
        lvShow.setAdapter(myAdapter);

        app.vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);  //获取震动服务

        lvShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listMaster.size() < 1) return;
                //Toast.makeText(getApplicationContext(),String.format("%s",listMaster.get(position)),Toast.LENGTH_SHORT).show();
                //进入设备的服务选择activity
                Intent intent = new Intent(MainActivity.this,BTServiceActivity.class);
                intent.putExtra("address",addressList.get(position));        //设备地址
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

        //处理搜索按钮
        buttonRefresh = (Button)findViewById(R.id.buttonRefresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
                    Toast.makeText(getApplicationContext(), "本机不支持蓝牙ble功能", Toast.LENGTH_SHORT).show();
                    finish();
                }
                app.mBluetoothManager =
                        (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);  //获取蓝牙服务
                app.mBluetoothAdapter = app.mBluetoothManager.getAdapter();    //获取到蓝牙适配器

                if (app.mBluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(), "获取蓝牙适配器失败", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                if (!app.mBluetoothAdapter.isEnabled()) {           //如果蓝牙适配器没有打开，则打开
                    if (!app.mBluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    }
                }

                buttonRefresh.setEnabled(false);  //搜索按钮不允许点了，要搜索完成才允许点

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && app.bleScanner == null)
                    app.bleScanner = app.mBluetoothAdapter.getBluetoothLeScanner();  //安卓6.0以上版本

                //清空相关信息
                listMaster.clear();   listDetail.clear();
                myAdapter.notifyDataSetChanged();            //清空界面

                deviceNamelist.clear();
                app.mLeDevices.clear();
                addressList.clear();
                app.bleServiceList.clear();

                scanLeDevice(true);     //扫描周围的蓝牙ble设备
            }
        });

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RNActivity.class));
            }
        });

//        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,TestActivity.class));
//            }
//        });
    }
}