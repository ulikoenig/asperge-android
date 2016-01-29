package de.ulikoenig.asperge;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Writes Essen to ListView on UI.
 */
public class UpdateMenuList implements Runnable {

    private String fehler;
    private Essen[] essen;
    private Asperge asperge;

    public UpdateMenuList(Essen[] _essen, Asperge _asperge) {
        this.essen = _essen;
        this.asperge = _asperge;
    }

    public UpdateMenuList(String _fehler, Asperge _asperge) {
        this.essen = null;
        this.asperge = _asperge;
        this.fehler = _fehler;
    }

    @Override
    public void run() {
        SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) asperge.findViewById(R.id.swipe_container);
        swipeLayout.setRefreshing(false);

        if (essen == null) {
            //kein Essen
            View v = asperge.findViewById(R.id.root);
            if(fehler == null) {
                Toast.makeText(asperge, "keine Daten", Toast.LENGTH_LONG).show();
                //Snackbar.make(v, "Keine Daten", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            } else{
                Toast.makeText(asperge, fehler,Toast.LENGTH_LONG).show();
                //Snackbar.make(v, fehler, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

        } else {
            String[] essenName = new String[essen.length];
            for (int i = 0; i < essen.length; i++) {
                essenName[i] = essen[i].getName();
            }

            String[] essenPreis = new String[essen.length];
            for (int i = 0; i < essen.length; i++) {
                essenPreis[i] = essen[i].getPreisToString();
            }

            ListView listview = (ListView) asperge.findViewById(R.id.menu);
            EssenListAdapter ea = new EssenListAdapter(asperge, essen);

            try {
                listview.setAdapter(ea);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
