package android.app

import android.content.Context
import android.content.SharedPreferences
import android.view.Window

class Activity : Context() {
    val window = Window()
    fun getPreferences(mode: Int): SharedPreferences {
        return SharedPreferences()
    }
}
