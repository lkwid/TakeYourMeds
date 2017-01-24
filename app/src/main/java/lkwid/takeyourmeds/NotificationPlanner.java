package lkwid.takeyourmeds;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import lkwid.takeyourmeds.database.MedDatabase;
import lkwid.takeyourmeds.model.Medicine;
import lkwid.takeyourmeds.service.MedNotificationService;

public class NotificationPlanner {
    public static final int MORNING = 8;
    public static final int NOON = 14;
    public static final int EVENING = 20;

    private MedDatabase mMedDatabase;
    private Context mContext;
    private ArrayList<Integer> mMorningMeds = new ArrayList<>();
    private ArrayList<Integer> mNoonMeds = new ArrayList<>();
    private ArrayList<Integer> mEveningMeds = new ArrayList<>();

    public NotificationPlanner(MedDatabase mMedDatabase, Context context) {
        this.mMedDatabase = mMedDatabase;
        this.mContext = context;
    }

    public void remindToTakeMeds() {
        List<Medicine> medicines = mMedDatabase.getMeds();

        for (Medicine med : medicines) {
            String regularity = med.getRegularity();

            if (regularity.charAt(0) == '1') {
                mMorningMeds.add(med.getId());
            }
            if (regularity.charAt(1) == '1') {
                mNoonMeds.add(med.getId());
            }
            if (regularity.charAt(2) == '1') {
                mEveningMeds.add(med.getId());
            }
            setAlarm(MORNING, pendingIntent(mMorningMeds, MORNING));
            setAlarm(NOON, pendingIntent(mNoonMeds, NOON));
            setAlarm(EVENING, pendingIntent(mEveningMeds, EVENING));
        }
    }

    private PendingIntent pendingIntent(ArrayList<Integer> arrayList, int requestCode) {
        Intent serviceIntent = new Intent(mContext, MedNotificationService.class);
        serviceIntent.putIntegerArrayListExtra(String.valueOf(requestCode), arrayList);


        return PendingIntent.getService(mContext, requestCode,
                serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
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
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, setTime(hour),
                AlarmManager.INTERVAL_DAY, pendingIntent);

        return alarmManager;
    }
}



