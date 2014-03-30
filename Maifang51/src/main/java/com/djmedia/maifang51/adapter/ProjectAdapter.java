package com.djmedia.maifang51.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.djmedia.maifang51.R;

import org.json.JSONObject;

/**
 * Created by rd on 14-3-20.
 */
public class ProjectAdapter extends JSONAdapter {
    public ProjectAdapter(Context context, LayoutInflater inflater, String url) {
        super(context, inflater, url);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.project_list_item, null);

            holder = new ViewHolder();
            holder.titleTextView = (TextView) view.findViewById(R.id.id_project_title);
            holder.timeTextView = (TextView) view.findViewById(R.id.id_project_time);
            holder.radioGroup = (RadioGroup) view.findViewById(R.id.id_project_step);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        JSONObject jsonObject = getItem(position);
        holder.titleTextView.setText(jsonObject.optString("title", ""));
        holder.timeTextView.setText(jsonObject.optString("time", ""));
        holder.radioGroup.check(holder.radioGroup.getChildAt(jsonObject.optInt("step", 0)).getId());

        return view;
    }

    private class ViewHolder {
        public TextView titleTextView;
        public TextView timeTextView;
        public RadioGroup radioGroup;
    }
}
