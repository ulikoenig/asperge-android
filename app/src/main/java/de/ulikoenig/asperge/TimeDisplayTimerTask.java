package de.ulikoenig.asperge;

import android.content.Context;
import android.os.Handler;

import java.util.TimerTask;

/**
 * Created by ukoenig on 30.12.15.
 */
class TimeDisplayTimerTask extends TimerTask {

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    private Context context;

    public TimeDisplayTimerTask(Context applicationContext){
        this.context = applicationContext;
    }

    @Override
    public void run() {
        // run on another thread
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                BackgroundTask task = new BackgroundTask(new BGTCallBack(context), context.getApplicationContext());
                task.execute();
            }

        });
    }
}