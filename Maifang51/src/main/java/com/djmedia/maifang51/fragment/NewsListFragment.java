package com.djmedia.maifang51.fragment;

import android.os.Bundle;
import android.view.View;

import com.djmedia.maifang51.R;

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
}
