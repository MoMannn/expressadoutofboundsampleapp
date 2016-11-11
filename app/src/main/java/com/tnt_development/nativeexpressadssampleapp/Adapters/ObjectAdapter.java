package com.tnt_development.nativeexpressadssampleapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.NativeExpressAdView;
import com.tnt_development.nativeexpressadssampleapp.R;
import com.tnt_development.nativeexpressadssampleapp.object;

import java.util.ArrayList;

/**
 * Created by tadejvengust1 on 10. 01. 16.
 */
public class ObjectAdapter extends BaseAdapter {

    private ArrayList<Object> items;
    private Context context;

    public ObjectAdapter(Context context)
    {
        this.context = context;

    }

    public void setList(ArrayList<Object> items)
    {
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(items != null)
            return items.size();
        else
            return 0;
    }


    @Override
    public Object getItem(int arg0) {
        if(items != null)
            return items.get(arg0);
        else
            return null;
    }

    @Override
    public long getItemId(int arg0) {

        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView name;
        TextView id;

        if (getItem(position) instanceof object) {

            final object item = (object) getItem(position);


            if (convertView == null || convertView.getTag() == null || !(convertView instanceof LinearLayout)) {
                convertView = LayoutInflater.from(context)
                        .inflate(R.layout.row, parent, false);
                name = (TextView) convertView.findViewById(R.id.name);
                id = (TextView) convertView.findViewById(R.id.id);

                convertView.setTag(new ViewHolder(name,id) {});

            } else {
                ViewHolder viewHolder = (ViewHolder) convertView.getTag();
                name = viewHolder.name;
                id = viewHolder.id;
            }

            name.setText(item.getName());
            id.setText(item.getId()+"");

            return convertView;

        } else
        {
            convertView = (NativeExpressAdView) getItem(position);
            return convertView;

        }

    }


    private static class ViewHolder
    {
        public final TextView name;
        public final TextView id;

        public ViewHolder(TextView name, TextView id)
        {
            this.name = name;
            this.id = id;
        }
    }
}