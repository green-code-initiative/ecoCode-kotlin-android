package checks
import android.bluetooth.BluetoothAdapter //NonCompliant{{You are using Bluetooth. Did you take a look at the Bluetooth Low Energy API?}}

class OnlyBcCheck{
    var toto : BluetoothAdapter? = null
}