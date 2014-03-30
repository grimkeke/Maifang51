package com.djmedia.maifang51.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.activity.InfoDetailActivity;
import com.djmedia.maifang51.tools.Constants;

import org.json.JSONObject;

/**
 * Created by rd on 14-3-25.
 */
public class NewsListFragment extends ObjListFragment {
    private static final String TAG = NewsListFragment.class.getSimpleName();


    public NewsListFragment() {
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getActionBar().setTitle(getString(R.string.apartment_news));
        limit = 10;
        queryStr = "android";
        queryBooks(queryStr, offset, limit);
    }

    @Override
    public void onObjItemClicked(int position) {
        Log.d(TAG, "onObjItemClicked called at position: " + position);
        JSONObject jsonObject = (JSONObject) adapter.getItem(position);
        String coverId = jsonObject.optString("cover_i", "");
        String title = jsonObject.optString("title", "");
        String author = jsonObject.optString("author_name", "");

        Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
        intent.putExtra(Constants.DETAIL_TYPE, Constants.TYPE_APARTMENT_DETAIL);
        intent.putExtra("coverId", coverId);
        intent.putExtra("title", title);
        intent.putExtra("author", author);
        startActivity(intent);
    }
}
