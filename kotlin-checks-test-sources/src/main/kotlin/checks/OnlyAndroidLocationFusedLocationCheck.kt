package checks
import android.location.Location;  // Noncompliant {{Use com.google.android.gms.location instead of android.location to maximize battery life.}}

class OnlyAndroidLocationFusedLocationCheck {
}