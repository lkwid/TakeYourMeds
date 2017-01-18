package lkwid.takeyourmeds.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;
import java.util.List;

import lkwid.takeyourmeds.database.MedDatabase;
import lkwid.takeyourmeds.model.Medicine;
import lkwid.takeyourmeds.service.MedNotificationService;

public class NotificationPlanner {
    public static final int MORNING = 8;
    public static final int NOON = 12;
    public static final int EVENING = 20;

    private MedDatabase mMedDatabase;
    private Context mContext;

    public NotificationPlanner(MedDatabase mMedDatabase, Context context) {
        this.mMedDatabase = mMedDatabase;
        this.mContext = context;
    }

    public void remindToTakeMeds() {
        List<Medicine> medicines = mMedDatabase.getMeds();

        for (Medicine med : medicines) {
            String regularity = med.getRegularity();
            Intent serviceIntent = new Intent(mContext, MedNotificationService.class);
            serviceIntent.putExtra("id", med.getId());
            PendingIntent pendingIntent = PendingIntent.getService(mContext,
                    med.getId(), serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (regularity.charAt(0) == '1') {
                setAlarm(MORNING, pendingIntent);
            }
            if (regularity.charAt(1) == '1') {
                setAlarm(NOON, pendingIntent);
            }
            if (regularity.charAt(2) == '1') {
                setAlarm(EVENING, pendingIntent);
            }
        }
    }

    private long setTime(int hour) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTimeInMillis();
    }

    private AlarmManager setAlarm(int hour, PendingIntent pendingIntent) {
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, setTime(hour), pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, setTime(hour), pendingIntent);
        }
        return alarmManager;
    }
}



