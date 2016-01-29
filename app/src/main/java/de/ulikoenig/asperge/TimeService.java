package de.ulikoenig.asperge;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * Created by ukoenig on 30.12.15.
 */
public class TimeService extends Service {
    // constant
    public static final long NOTIFY_INTERVAL = 24* 60 *60 * 1000; // 24 Stunden
//    public static final long NOTIFY_INTERVAL =  60 * 1000; // 10 seconds


    // timer handling
    private Timer mTimer = null;
    private Intent intent;

    public TimeService() {
     }

    @Override
    public IBinder onBind(Intent _intent) {
        this.intent = _intent;

          return null;
    }

    @Override
    public void onCreate() {
        // cancel if already existed

        if(mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,11);
        calendar.set(Calendar.MINUTE,00);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        if (calendar.before(new Date())){
            calendar.add(Calendar.DAY_OF_MONTH,1);
        };

        //Find next weekday
        int tag = calendar.get(Calendar.DAY_OF_WEEK);
        while  ((tag < 2) || (tag > 6)) {
            calendar.add(Calendar.DAY_OF_MONTH,1);
            tag = calendar.get(Calendar.DAY_OF_WEEK);
        };

        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(this.getApplicationContext()),calendar.getTime(),NOTIFY_INTERVAL);
    }
}
