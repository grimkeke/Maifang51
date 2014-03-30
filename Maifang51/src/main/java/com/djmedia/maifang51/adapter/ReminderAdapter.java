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
public class ReminderAdapter extends JSONAdapter {
    public ReminderAdapter(Context context, LayoutInflater inflater, String url) {
        super(context, inflater, url);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.obj_list_item, null);

            holder = new ViewHolder();
            holder.imgView = (ImageView) view.findViewById(R.id.img_thumbnail);
            holder.titleTextView = (TextView) view.findViewById(R.id.text_title);
            holder.contentTextView = (TextView) view.findViewById(R.id.text_content);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        JSONObject jsonObject = getItem(position);
        if (jsonObject.has("image")) {

        } else {
            holder.imgView.setImageResource(android.R.drawable.ic_menu_search);
        }
        holder.titleTextView.setText(jsonObject.optString("title", ""));
        holder.contentTextView.setText(jsonObject.optString("content", ""));

        return view;
    }

    private class ViewHolder {
        public TextView titleTextView;
        public TextView contentTextView;
        public ImageView imgView;
    }
}
