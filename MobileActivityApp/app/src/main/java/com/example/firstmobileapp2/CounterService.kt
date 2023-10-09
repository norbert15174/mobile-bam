package com.example.firstmobileapp2;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CounterService extends Service {

    private static Integer xd = 0;
    private static final String TAG = "MyService";
    private static List<ThreadManager> threadManagers = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            int i = 0;
            @Override
            public void run() {
                Log.d(TAG, "count: " + i++);
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(runnable);
        threadManagers.add(new ThreadManager(handler, runnable));
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        threadManagers.forEach(threadManager -> threadManager.getHandler().removeCallbacks(threadManager.getRunnable()));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class ThreadManager {
        private final Handler handler;
        private final Runnable runnable;

        public ThreadManager(Handler handler, Runnable runnable) {
            this.handler = handler;
            this.runnable = runnable;
        }

        public Handler getHandler() {
            return handler;
        }

        public Runnable getRunnable() {
            return runnable;
        }
    }

}
