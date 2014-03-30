package com.djmedia.maifang51.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.djmedia.maifang51.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by rd on 14-2-21.
 */
public class JSONAdapter extends BaseAdapter {
    private static final String TAG = JSONAdapter.class.getSimpleName();
    protected String item_url;
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected JSONArray mJsonAarray;

    public JSONAdapter(Context context, LayoutInflater inflater, String url) {
        mContext = context;
        mInflater = inflater;
        item_url = url;
        mJsonAarray = new JSONArray();
    }

    @Override
    public int getCount() {
        return mJsonAarray.length();
    }

    @Override
    public JSONObject getItem(int position) {
        return mJsonAarray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.obj_list_item, null);

            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView) view.findViewById(R.id.img_thumbnail);
            holder.titleTextView = (TextView) view.findViewById(R.id.text_title);
            holder.authorTextView = (TextView) view.findViewById(R.id.text_content);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        JSONObject jsonObject = getItem(position);
        if (jsonObject.has("cover_i")) {
            String imageId = jsonObject.optString("cover_i");
            String imageUrl = item_url + imageId + "-S.jpg";
            Picasso.with(mContext).load(imageUrl).placeholder(R.drawable.ic_books).into(holder.thumbnailImageView);
        } else {
            holder.thumbnailImageView.setImageResource(R.drawable.ic_books);
        }

        String bookTitle = "";
        String authorName = "";
        if (jsonObject.has("title")) {
            bookTitle = jsonObject.optString("title");
        }
        if (jsonObject.has("author_name")) {
            authorName = jsonObject.optJSONArray("author_name").optString(0);
        }
        holder.titleTextView.setText(bookTitle);
        holder.authorTextView.setText(authorName);

        return view;
    }

    public void updateData(JSONArray jsonArray) {
        mJsonAarray = jsonArray;
        notifyDataSetChanged();
    }

    public static JSONArray joinJSONArray(JSONArray mData, JSONArray array) {
        StringBuffer buffer = new StringBuffer();
        try {
            int len = mData.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj1 = (JSONObject) mData.get(i);
                if (i == len - 1)
                    buffer.append(obj1.toString());
                else
                    buffer.append(obj1.toString()).append(",");
            }
            len = array.length();
            if (len > 0)
                buffer.append(",");
            for (int i = 0; i < len; i++) {
                JSONObject obj1 = (JSONObject) array.get(i);
                if (i == len - 1)
                    buffer.append(obj1.toString());
                else
                    buffer.append(obj1.toString()).append(",");
            }
            buffer.insert(0, "[").append("]");
            return new JSONArray(buffer.toString());
        } catch (Exception e) {
        }
        return null;
    }
    public void addData(JSONArray jsonArray) {
//        mJsonAarray = joinJSONArray(mJsonAarray, jsonArray);
        int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            mJsonAarray.put(jsonArray.optJSONObject(i));
        }
        Log.d(TAG, "jsonArray count:" + mJsonAarray.length());
        notifyDataSetChanged();
    }

    private class ViewHolder {
        public ImageView thumbnailImageView;
        public TextView titleTextView;
        public TextView authorTextView;
    }
}
