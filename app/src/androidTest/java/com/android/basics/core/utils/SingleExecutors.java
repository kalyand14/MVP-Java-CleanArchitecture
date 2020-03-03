package com.android.basics.core.utils;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Allow instant execution of tasks.
 */
public class SingleExecutors extends AppExecutors {
    private static Executor instant = new Executor() {
        @Override
        public void execute(@NonNull Runnable command) {
            command.run();
        }
    };

    public SingleExecutors() {
        super(instant, instant, instant);
    }
}