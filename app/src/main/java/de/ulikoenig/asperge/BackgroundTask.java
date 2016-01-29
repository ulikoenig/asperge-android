package de.ulikoenig.asperge;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


/**
 * Created by ukoenig on 08.12.15.
 */

public class BackgroundTask extends AsyncTask<Void, Void, Void> {
    private String TAG = "BackgroundTask";

    private OnTaskCompleted listener;
    private Context context;

    public BackgroundTask(OnTaskCompleted listener, Context _context) {
        this.listener = listener;
        this.context = _context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.i(TAG, "doInBackground");
        Essen[] e = new Essen[0];
        if (!isNetworkAvailable()) {
            listener.onTaskCompleted("Keine Verbindung zum Neuland.");
        } else {
            try {
                e = Util.calculate();
                listener.onTaskCompleted(e);
            } catch (UnknownHostException e1) {
                listener.onTaskCompleted("Keine Verbindung zu asperge.de.");
            } catch (SocketTimeoutException e1) {
                listener.onTaskCompleted("Keine Verbindung zu asperge.de. (Timeout)");
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Log.i(TAG, "onPostExecute");
    }


    @Override
    protected void onPreExecute() {
        Log.i(TAG, "onPreExecute");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        Log.i(TAG, "onProgressUpdate");
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}