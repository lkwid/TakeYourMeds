package lkwid.takeyourmeds.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import lkwid.takeyourmeds.database.MedDatabase;
import lkwid.takeyourmeds.database.SqliteMedDatabase;
import lkwid.takeyourmeds.NotificationPlanner;

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MedDatabase medDatabase = new SqliteMedDatabase(context);

        new NotificationPlanner(medDatabase, context).remindToTakeMeds();
    }
}
