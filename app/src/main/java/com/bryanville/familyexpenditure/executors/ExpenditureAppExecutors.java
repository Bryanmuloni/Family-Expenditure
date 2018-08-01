package com.bryanville.familyexpenditure.executors;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Bryanville on 8/1/2018.
 */

public class ExpenditureAppExecutors {
    private static final Object LOCK = new Object();
    private static ExpenditureAppExecutors appExecutorInstance;
    private Executor diskIO;
    private Executor mainThread;
    private Executor networkIO;

    public ExpenditureAppExecutors(Executor diskIO, Executor mainThread, Executor networkIO) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
        this.networkIO = networkIO;
    }
    public static ExpenditureAppExecutors getAppExecutorInstance(){
        if (appExecutorInstance == null){
            synchronized (LOCK){
                if (appExecutorInstance == null){
                    appExecutorInstance = new ExpenditureAppExecutors(Executors.newSingleThreadExecutor(),
                            Executors.newFixedThreadPool(3),new MainThreadExecutor());
                }
            }
        }
        return appExecutorInstance;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }
    public static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }
}
