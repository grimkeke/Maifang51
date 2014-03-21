package com.djmedia.maifang51.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.djmedia.maifang51.R;

import org.json.JSONObject;

/**
 * Created by rd on 14-3-20.
 */
public class InfoAdapter extends JSONAdapter {
    public InfoAdapter(Context context, LayoutInflater inflater, String url) {
        super(context, inflater, url);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.info_list_item, null);

            holder = new ViewHolder();
            holder.imgView = (ImageView) view.findViewById(R.id.id_info_type_img);
            holder.titleTextView = (TextView) view.findViewById(R.id.id_info_title);
            holder.timeTextView = (TextView) view.findViewById(R.id.id_submit_time);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        JSONObject jsonObject = getItem(position);
        if (jsonObject.has("image")) {

        } else {
            holder.imgView.setImageResource(android.R.drawable.btn_star);
        }
        holder.titleTextView.setText(jsonObject.optString("title", ""));
        holder.timeTextView.setText(jsonObject.optString("time", ""));

        return view;
    }

    private static class ViewHolder {
        public ImageView imgView;
        public TextView titleTextView;
        public TextView timeTextView;
    }
}
