package de.ulikoenig.asperge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by ukoenig on 30.12.15.
 */
public class EssenListAdapter extends BaseAdapter {
    private final Essen[] essen;
    private LayoutInflater mInflater;

    public EssenListAdapter(Context context, Essen[] _essen) {
        mInflater = LayoutInflater.from(context);
        this.essen = _essen;
    }

    @Override
    public int getCount() {
        return essen.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row, null);
            holder = new ViewHolder();
            holder.text1 = (TextView) convertView
                    .findViewById(android.R.id.text1);
            holder.text2 = (TextView) convertView
                    .findViewById(android.R.id.text2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if ((position >= 0) && (position < essen.length)) {
            holder.text1.setText(essen[position].getName());
            holder.text2.setText(essen[position].getPreisToString());
        }

        return convertView;
    }

    static class ViewHolder {
        TextView text1;
        TextView text2;
    }

}
