package lkwid.takeyourmeds;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MedsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this);
    }
}
