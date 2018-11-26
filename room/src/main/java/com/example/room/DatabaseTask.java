package com.example.room;

import android.os.AsyncTask;

/**
 * @author V
 * @since 2018/10/22
 */
public class DatabaseTask<Params, Result> extends AsyncTask<Params, Void, Result> {

    private BackgroundRunner<Params, Result> runner;
    private OnCompleteListener<Result> listener;

    public DatabaseTask(BackgroundRunner<Params, Result> runner, OnCompleteListener<Result> listener) {
        this.runner = runner;
        this.listener = listener;
    }

    @Override
    protected Result doInBackground(Params[] params) {
        return runner.doInBackground(params);
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (listener != null) listener.onComplete(result);
    }

    public interface OnCompleteListener<T> {
        void onComplete(T result);
    }

    public interface BackgroundRunner<T, R> {
        R doInBackground(T[] params);
    }

    public static class TaskBuilder<P, R> {
        private BackgroundRunner<P, R> runner;
        private OnCompleteListener<R> listener;

        public static <P, R> TaskBuilder<P, R> newBuilder() {
            return new TaskBuilder<>();
        }

        public TaskBuilder<P, R> doInBackground(BackgroundRunner<P, R> runner) {
            this.runner = runner;
            return this;
        }

        public TaskBuilder<P, R> onCompleteListener(OnCompleteListener<R> listener) {
            this.listener = listener;
            return this;
        }

        public DatabaseTask build() {
            if (runner == null) throw new NullPointerException("runner cannot be null");
            return new DatabaseTask<>(runner, listener);
        }
    }
}
