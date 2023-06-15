package com.example.d1ctionary;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {


    private static AppExecutors instance;
    public static AppExecutors getInstance(){
        if(instance == null){
            instance = new AppExecutors();
        }
        return instance;
    }

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService getScheduledExecutorService(){
        return scheduledExecutorService;
    }



    private final Executor diskIO = Executors.newSingleThreadExecutor();


    private final Executor mainThread = new MainThreadExecutor();

    public Executor getDiskIO(){return diskIO;}
    public Executor getMainThread(){return mainThread;}

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
