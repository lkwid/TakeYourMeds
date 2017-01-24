package lkwid.takeyourmeds.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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
        ArrayList<Integer> morningMeds = intent.getIntegerArrayListExtra(String.valueOf(NotificationPlanner.MORNING));
        ArrayList<Integer> noonMeds = intent.getIntegerArrayListExtra(String.valueOf(NotificationPlanner.NOON));
        ArrayList<Integer> eveningMeds = intent.getIntegerArrayListExtra(String.valueOf(NotificationPlanner.EVENING));

        ArrayList<Integer> activeList;
        if (morningMeds != null) {
            activeList = morningMeds;
            mTimeId = NotificationPlanner.MORNING;
        } else if (noonMeds != null) {
            activeList = noonMeds;
            mTimeId = NotificationPlanner.NOON;
        } else if (eveningMeds != null) {
            activeList = eveningMeds;
            mTimeId = NotificationPlanner.EVENING;
        } else
            return;


//        Intent previewIntent = new Intent(this, MedPreviewActivity.class);
//        previewIntent.putExtra("pos", medId);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity
//                (this, medId, previewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = setNotoficationBuilder(activeList);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mTimeId, notification.build());

    }

    @NonNull
    private NotificationCompat.Builder setNotoficationBuilder(ArrayList<Integer> arrayList) {
        List<Medicine> medicines = mMedDatabase.getMeds();
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Take Your Meds")
                .setContentText("Przypomnienie o "+arrayList.size()+" lekach")
                .setContentInfo("Godzina "+ mTimeId)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
//                .setTicker(medicine.getName())
                .setPriority(NotificationCompat.PRIORITY_MAX)
//                .setContentIntent(pendingIntent)
                .
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
