package com.mauriciotogneri.tpgwear.app;

import android.app.Application;
import android.os.StrictMode;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(formUri = "http://zeronest.com/acra/report.php")
public class TpgWear extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        ACRA.init(this);
        ACRA.getErrorReporter().putCustomData("PACKAGE_NAME", getPackageName());

        StrictMode.ThreadPolicy.Builder threadBuilder = new StrictMode.ThreadPolicy.Builder();
        threadBuilder.detectAll();
        threadBuilder.penaltyLog();
        StrictMode.setThreadPolicy(threadBuilder.build());

        StrictMode.VmPolicy.Builder vmBuilder = new StrictMode.VmPolicy.Builder();
        vmBuilder.detectAll();
        vmBuilder.penaltyLog();
        StrictMode.setVmPolicy(vmBuilder.build());
    }
}