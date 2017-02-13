package lkwid.takeyourmeds.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

import lkwid.takeyourmeds.NotificationPlanner;
import lkwid.takeyourmeds.R;
import lkwid.takeyourmeds.activity.MedPreviewActivity;
import lkwid.takeyourmeds.database.MedDatabase;
import lkwid.takeyourmeds.database.SqliteMedDatabase;
import lkwid.takeyourmeds.model.Medicine;

public class MedNotificationService extends IntentService {
    MedDatabase mMedDatabase;
    private int mTimeId;
    private ArrayList<Integer> mActiveList;

    public MedNotificationService() {
        super("MedNotificationService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMedDatabase = new SqliteMedDatabase(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.hasExtra(String.valueOf(NotificationPlanner.MORNING))) {
            mActiveList = intent.getIntegerArrayListExtra(String.valueOf(NotificationPlanner.MORNING));
            mTimeId = NotificationPlanner.MORNING;
        } else if (intent.hasExtra(String.valueOf(NotificationPlanner.NOON))) {
            mActiveList = intent.getIntegerArrayListExtra(String.valueOf(NotificationPlanner.NOON));
            mTimeId = NotificationPlanner.NOON;
        } else if (intent.hasExtra(String.valueOf(NotificationPlanner.EVENING))) {
            mActiveList = intent.getIntegerArrayListExtra(String.valueOf(NotificationPlanner.EVENING));
            mTimeId = NotificationPlanner.EVENING;
        } else {
            return;
        }

        Intent previewIntent = new Intent(this, MedPreviewActivity.class);
        previewIntent.putIntegerArrayListExtra(String.valueOf(mTimeId), mActiveList);

        PendingIntent pendingIntent = PendingIntent.getActivity
                (this, mTimeId, previewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = buildNotification(mActiveList, pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mTimeId, notification.build());

    }

    @NonNull
    private NotificationCompat.Builder buildNotification(ArrayList<Integer> arrayList, PendingIntent pi) {
        List<Medicine> medicines = mMedDatabase.getMeds();
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Take Your Meds")
                .setContentText("Przypomnienie o "+arrayList.size()+" lekach")
                .setContentInfo("Godzina "+ mTimeId)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pi)
                .setAutoCancel(true);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Pamiętaj o swoich lekach");
        for (Integer id : arrayList) {
            for (int i=0; i < medicines.size(); i++) {
                if (medicines.get(i).getId() == id) {
                    inboxStyle.addLine(medicines.get(i).getName());
                    medicines.remove(i);
                }
            }
        }
        notification.setStyle(inboxStyle);
        return notification;
    }
}
