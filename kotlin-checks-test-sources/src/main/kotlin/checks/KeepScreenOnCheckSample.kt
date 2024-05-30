package checks

import android.view.Window
import android.view.WindowManager

class KeepScreenOnCheckSample {
    fun foo(window: Window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) //Noncompliant {{You are keeping the screen turned on, this will drain the battery very fast. Only use this feature if strictly necessary}}
    }
}
