package checks

import android.bluetooth.le.BluetoothLeScanner // Noncompliant {{Using android.bluetooth.le.* is a good practice.}}

class OnlyBleCheck{
    var titi : BluetoothLeScanner? = null
}
