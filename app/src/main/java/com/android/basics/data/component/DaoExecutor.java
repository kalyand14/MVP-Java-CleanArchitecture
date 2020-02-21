package com.android.basics.data.component;

import android.os.AsyncTask;

public class DaoExecutor {

    public DaoExecutor() {
    }

    public <T> void start(DaoCallback<T> daoCallback) {
        new DaoProcessAsyncTask<T>(daoCallback).execute();
    }

    private static class DaoProcessAsyncTask<T> extends
            AsyncTask<Object, Void, T> {

        DaoCallback<T> callback;

        DaoProcessAsyncTask(DaoCallback<T> callback) {
            this.callback = callback;
        }

        @Override
        protected T doInBackground(Object... voids) {
            return callback.doAsync();
        }

        @Override
        protected void onPostExecute(T result) {
            callback.onComplete(result);
        }
    }

}