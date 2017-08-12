package org.jraf.test.util;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

public abstract class Async {

    protected abstract void doInBackground();

    protected void onPostExecute() {}

    @SuppressLint("StaticFieldLeak")
    public void execute() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Async.this.doInBackground();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Async.this.onPostExecute();
            }
        }.execute();
    }
}
