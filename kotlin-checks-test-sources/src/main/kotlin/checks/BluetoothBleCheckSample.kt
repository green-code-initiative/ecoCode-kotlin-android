package checks

import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.BluetoothLeScanner //Noncompliant {{Using android.bluetooth.le.* is a good practice.}}

class BluetoothBleCheckSample {
    var toto : BluetoothAdapter? = null
    var titi : BluetoothLeScanner? = null
}