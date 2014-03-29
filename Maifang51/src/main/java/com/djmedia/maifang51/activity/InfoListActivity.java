package com.djmedia.maifang51.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.fragment.MarketInfoListFragment;
import com.djmedia.maifang51.fragment.MemberSpecialFragment;
import com.djmedia.maifang51.fragment.NewsListFragment;
import com.djmedia.maifang51.tools.Constants;

/**
 * Created by rd on 14-3-21.
 */
public class InfoListActivity extends BaseActivity {
    private static final String TAG = InfoListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_list);

        progressBar = (ProgressBar) findViewById(R.id.id_progress_bar);
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);

        int listType = getIntent().getIntExtra(Constants.LIST_TYPE, Constants.TYPE_APARTMENT_NEWS);
        Log.d(TAG, "get type: " + listType);

        Fragment fragment = null;

        switch (listType) {
            case Constants.TYPE_APARTMENT_NEWS:
                fragment = new NewsListFragment();
                break;
            case Constants.TYPE_MARKET_INFO:
                fragment = new MarketInfoListFragment();
                break;
            case Constants.TYPE_MEMBER_SPECIAL:
                fragment = new MemberSpecialFragment();
                break;
            default:
                throw new IllegalArgumentException();
        }

        FragmentManager manager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            manager.beginTransaction().add(R.id.id_info_list_layout, fragment).commit();
        }
    }
}
