package za.co.dapps.mpandroidchart.playground;

import android.app.Application;
import android.os.StrictMode;

import net.danlew.android.joda.JodaTimeAndroid;

public class MainApplication extends Application {

    public static boolean DEVELOPER_MODE = true;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);

        if (DEVELOPER_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
    }
}
