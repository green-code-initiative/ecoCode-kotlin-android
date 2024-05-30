package checks

import android.content.Context
import android.content.Intent
import android.app.PendingIntent
import android.app.Activity
import android.app.AlarmManager
import android.app.Application
import android.content.ContextWrapper
import android.content.IntentFilter


class JobCoalesceRuleSample :Activity() {

    fun test(){

        val context = Context() // OK
        val context2 = Application() // OK
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager // N-OK
        val intent = Intent(context, AlarmManager::class.java) // OK
        val currentTime: Long = System.currentTimeMillis()// N-OK
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        val test = AlarmManager() // N-OK
        //val alarmTag = "Alarm's tag"
        //val onAlarmListener: AlarmManager.OnAlarmListener = listener()  as AlarmManager.OnAlarmListener
        //val handler = Handler()
        //val alarmClockInfo = AlarmManager.AlarmClockInfo(System.currentTimeMillis(), pendingIntent)

        alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime, pendingIntent)// Noncompliant {{Avoid using AlarmManager or a SyncAdapter for an alarm. Instead use the JobScheduler because the alarm triggers are mutualized.}}
/*
        alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime, alarmTag, onAlarmListener, handler)// Noncompliant {{Avoid using AlarmManager or a SyncAdapter for an alarm. Instead use the JobScheduler because the alarm triggers are mutualized.}}

        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent)// Noncompliant {{Avoid using AlarmManager or a SyncAdapter for an alarm. Instead use the JobScheduler because the alarm triggers are mutualized.}}

        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, currentTime, pendingIntent) // Noncompliant {{Avoid using AlarmManager or a SyncAdapter for an alarm. Instead use the JobScheduler because the alarm triggers are mutualized.}}

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, currentTime, pendingIntent)// Noncompliant {{Avoid using AlarmManager or a SyncAdapter for an alarm. Instead use the JobScheduler because the alarm triggers are mutualized.}}

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, currentTime, alarmTag, onAlarmListener, handler)// Noncompliant {{Avoid using AlarmManager or a SyncAdapter for an alarm. Instead use the JobScheduler because the alarm triggers are mutualized.}}

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, currentTime, pendingIntent)// Noncompliant {{Avoid using AlarmManager or a SyncAdapter for an alarm. Instead use the JobScheduler because the alarm triggers are mutualized.}}

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, currentTime, 1000, pendingIntent)// Noncompliant {{Avoid using AlarmManager or a SyncAdapter for an alarm. Instead use the JobScheduler because the alarm triggers are mutualized.}}

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, currentTime, 1000, pendingIntent)// Noncompliant {{Avoid using AlarmManager or a SyncAdapter for an alarm. Instead use the JobScheduler because the alarm triggers are mutualized.}}

        alarmManager.setWindow(AlarmManager.RTC_WAKEUP, 0, 1, pendingIntent)// Noncompliant {{Avoid using AlarmManager or a SyncAdapter for an alarm. Instead use the JobScheduler because the alarm triggers are mutualized.}}

        alarmManager.setWindow(AlarmManager.RTC_WAKEUP, 0, 1, alarmTag, onAlarmListener, handler)// Noncompliant {{Avoid using AlarmManager or a SyncAdapter for an alarm. Instead use the JobScheduler because the alarm triggers are mutualized.}}
*/

    }




}