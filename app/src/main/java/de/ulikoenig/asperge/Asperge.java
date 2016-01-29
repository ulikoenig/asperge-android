package de.ulikoenig.asperge;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Asperge extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final boolean test = false;
    private NotificationCompat.Builder mBuilder;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;

    private Asperge asperge;
    public BackgroundTask task;
    public Lock tasklock = new ReentrantLock();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asperge);
        startService(new Intent(this, TimeService.class));

        asperge = this;

        //Set Title
        android.support.v7.widget.Toolbar t = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        t.setTitle("Asperge");

        //Set Subtitle
        Date now = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat();
        format1.applyLocalizedPattern("DE");
        format1.applyPattern("EEEE");
        String subtitle = "Speiseplan vom " + format1.format(now);
        t.setSubtitle(subtitle);


        SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        //swipeLayout.setColorSchemeResources(1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        View.OnClickListener l = new View.OnClickListener() {

            public void onClick(View view) {
                //Snackbar.make(view, "xxx", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                tasklock.lock();
                task = new BackgroundTask(new BGTCallBack(asperge), asperge.getApplicationContext());
                task.execute();
                tasklock.unlock();
            }
        };
        fab.setOnClickListener(l);
        fab.callOnClick();
        fab.hide();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_asperge, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        mClient.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Asperge Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://de.ulikoenig.asperge/http/host/path")
//        );
        //AppIndex.AppIndexApi.start(mClient, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Asperge Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://de.ulikoenig.asperge/http/host/path")
//        );
        //AppIndex.AppIndexApi.end(mClient, viewAction);
        mClient.disconnect();
    }

    public void setMenu() {

    }

    @Override
    public void onRefresh() {
        tasklock.lock();
        if (task == null) {
            //Not existing
            task = new BackgroundTask(new BGTCallBack(asperge), this.getApplicationContext());
            task.execute();
            SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
            swipeLayout.setRefreshing(true);
        } else if (task.getStatus() == AsyncTask.Status.PENDING) {
            //not yet running
            task.execute();
        } else if (task.getStatus() == AsyncTask.Status.RUNNING) {
            // My AsyncTask is currently doing work in doInBackground()
            // do nothing
            SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
            swipeLayout.setRefreshing(true);
        } else if (task.getStatus() == AsyncTask.Status.FINISHED) {
            // My AsyncTask is done and onPostExecute was called
            task = new BackgroundTask(new BGTCallBack(asperge), this.getApplicationContext());
            task.execute();
            SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
            swipeLayout.setRefreshing(true);
        }
        tasklock.unlock();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
