package lkwid.takeyourmeds.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import lkwid.takeyourmeds.R;
import lkwid.takeyourmeds.activity.MedPreviewActivity;
import lkwid.takeyourmeds.database.MedDatabase;
import lkwid.takeyourmeds.database.SqliteMedDatabase;
import lkwid.takeyourmeds.model.Medicine;

public class MedNotificationService extends IntentService {
    MedDatabase mMedDatabase;

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
        int medId = intent.getIntExtra("id", -1);
        Medicine medicine = mMedDatabase.getMed(medId);

        if (medicine == null) {
            return;
        }

        Intent previewIntent = new Intent(this, MedPreviewActivity.class);
        previewIntent.putExtra("pos", medId);

        PendingIntent pendingIntent = PendingIntent.getActivity
                (this, medId, previewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(medicine.getName())
                .setContentInfo("Info")
                .setContentText("Przypomnienie")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setTicker(medicine.getName())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(medId, notification);

    }
}
