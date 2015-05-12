package com.example.android.sunshine.app.service;

/**
 * Created by Arthur on 15-04-24.
 */
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
public class RefreshService extends Service {
    static final String TAG = "RefreshService"; //
    @Override
    public IBinder onBind(Intent intent) { //
        return null;
    }
    @Override
    public void onCreate() { //
        super.onCreate();
        Log.d(TAG, "onCreated");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) { //
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "onStarted");
        return START_STICKY;
    }
    @Override
    public void onDestroy() { //
        super.onDestroy();
        Log.d(TAG, "onDestroyed");
    }
}