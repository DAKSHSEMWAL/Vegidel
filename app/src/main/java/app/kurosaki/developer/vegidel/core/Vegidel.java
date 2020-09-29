package app.kurosaki.developer.vegidel.core;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LifecycleObserver;

import com.google.firebase.analytics.FirebaseAnalytics;

import app.kurosaki.developer.vegidel.interfaces.Constants;
import app.kurosaki.developer.vegidel.utils.Common;

public class Vegidel extends Application implements Constants, LifecycleObserver {
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    public void onCreate() {
        super.onCreate();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        AppSignatureHelper appSignatureHelper = new AppSignatureHelper(this);
        Log.e("device Key", Common.deviceId(getApplicationContext()));
        appSignatureHelper.getAppSignatures();
        Log.e("apphash",appSignatureHelper.getAppSignatures().toString());
    }

}