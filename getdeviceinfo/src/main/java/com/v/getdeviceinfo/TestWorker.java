package com.v.getdeviceinfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.Field;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * @author V
 * @since 2018/10/15
 */
public class TestWorker extends Worker {
    public TestWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String dataValue = getInputData().getString("dataKey");
        long duration = getInputData().getLong("duration", 1000);
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            return Result.FAILURE;
        }
        Data data = new Data.Builder().putDouble("outKey", 233).build();
        setOutputData(data);
        Log.i("testTag", dataValue);
        return Result.SUCCESS;
    }
}
