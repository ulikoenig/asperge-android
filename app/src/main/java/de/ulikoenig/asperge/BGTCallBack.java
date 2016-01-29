package de.ulikoenig.asperge;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by ukoenig on 09.12.15.
 */
public class BGTCallBack implements OnTaskCompleted {
    private Asperge masperge;
    private Context context;

    /**
     * Init Callback with UI.
     *
     * @param asperge
     */
    public BGTCallBack(Asperge asperge) {
        this.masperge = asperge;
        this.context = asperge.getApplicationContext();
    }


    /**
     * Init Callback without UI.
     *
     * @param context
     */
    public BGTCallBack(Context context) {
        this.context = context;
    }


    @Override
    public void onTaskCompleted(Essen[] essen) {
        Log.d("BGTCallBack", "exec");
        if (masperge == null) {
            if (essen != null) {
                showNotification(essen, context);
            }
        } else {
            UpdateMenuList uml = new UpdateMenuList(essen, masperge);
            masperge.runOnUiThread(uml);
        }
    }

    private void showNotification(Essen[] essen, Context context) {
        String s = "";
        for (int i = 0; i < essen.length; i++) {
            s += essen[i].getName() + " " + essen[i].getPreisToString() + "\r\n";
        }

        Bitmap bitmapLogo = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
//                        .setLargeIcon(R.mipmap.logo)
                        .setSmallIcon(R.mipmap.logo_sw)
                        .setLargeIcon(bitmapLogo)
                        .setContentTitle("Asperge Speiseplan")
                        .setContentText(s);

        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
// Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Asperge Speiseplan:");
// Moves events into the expanded layout

        for (int i = 0; i < essen.length; i++) {
            String e = essen[i].getName() + " " + essen[i].getPreisToString() + "\r\n";
            inboxStyle.addLine(e);
        }

        NotificationCompat.BigTextStyle bTextSt = new NotificationCompat.BigTextStyle();
        bTextSt.setBigContentTitle("Asperge Speiseplan:");
        bTextSt.bigText(s);
        mBuilder.setStyle(bTextSt);


        Intent notificationIntent = new Intent(context, Asperge.class);
        PendingIntent contentIntent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.

        mBuilder.setContentIntent(contentIntent);

        Notification notif = mBuilder.build();
        mNotificationManager.notify(0, notif);
    }

    public void onTaskCompleted(String fehler) {
        Log.d("BGTCallBack", "exec fehler");
        if (masperge != null) {
            UpdateMenuList uml = new UpdateMenuList(fehler, masperge);
            masperge.runOnUiThread(uml);
        }
    }
}

