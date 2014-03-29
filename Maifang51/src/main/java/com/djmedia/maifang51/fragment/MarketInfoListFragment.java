package com.djmedia.maifang51.fragment;

import android.os.Bundle;
import android.view.View;

import com.djmedia.maifang51.R;

/**
 * Created by rd on 14-3-25.
 */
public class MarketInfoListFragment extends ObjListFragment {
    private static final String TAG = MarketInfoListFragment.class.getSimpleName();

    public MarketInfoListFragment() {
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getActionBar().setTitle(getString(R.string.market_info));
        queryStr = "linux";
        queryBooks(queryStr, offset, limit);
    }
}
