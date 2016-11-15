package de.ulikoenig.asperge;

import android.content.Context;
import android.os.Handler;

import java.util.Calendar;
import java.util.TimerTask;

/**
 * Created by ukoenig on 30.12.15.
 */
class TimeDisplayTimerTask extends TimerTask {

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    private Context context;

    public TimeDisplayTimerTask(Context applicationContext) {
        this.context = applicationContext;
    }

    @Override
    public void run() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (hour == 11) {

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
}