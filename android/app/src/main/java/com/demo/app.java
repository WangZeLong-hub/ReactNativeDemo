package com.demo;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.os.Handler;
import android.os.Vibrator;

import java.util.ArrayList;
import java.util.List;

public class app extends Application {
    public static BluetoothManager mBluetoothManager;
    public static BluetoothAdapter mBluetoothAdapter;
    public static ArrayList<BluetoothDevice> mLeDevices;
    public static List<BluetoothGattService> bleServiceList;
    public static BluetoothLeScanner bleScanner = null;
    public static BluetoothDevice mDevice;
    public static BluetoothGatt mBluetoothGatt;
    public static Vibrator vib;                                            //震动对象
    public static int mConnectionState;
    public static Handler bluetoothMessageHandle;
}
