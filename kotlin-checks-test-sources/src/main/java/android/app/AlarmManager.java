package android.app;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AlarmManager {
    @Nullable
    public static String TAG = "AlarmManager";

    public static final String RTC_WAKEUP = "RTC";

    public void set(@NotNull String rtcWakeup, long currentTime, @NotNull PendingIntent pendingIntent) {
        
    }
}
