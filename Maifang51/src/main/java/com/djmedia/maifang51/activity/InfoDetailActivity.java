package com.djmedia.maifang51.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.fragment.InfoDetailFragment;
import com.djmedia.maifang51.fragment.MineProjectFragment;
import com.djmedia.maifang51.fragment.ReminderFragment;
import com.djmedia.maifang51.tools.Constants;

/**
 * Created by rd on 14-3-20.
 */
public class InfoDetailActivity extends FragmentActivity {
    private static final String TAG = InfoDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_info_detail);

        int detailType = getIntent().getIntExtra(Constants.DETAIL_TYPE, Constants.TYPE_SPECIAL);
        Log.d(TAG, "get type: " + detailType);

        Fragment fragment = null;

        switch (detailType) {
            case Constants.TYPE_MINE:
                fragment = new MineProjectFragment();
                break;
            case Constants.TYPE_SPECIAL:
                fragment = new InfoDetailFragment();
                break;
            case Constants.TYPE_REMIND:
                fragment = new ReminderFragment();
                break;
            default:
                throw new IllegalArgumentException();
        }

        FragmentManager manager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            manager.beginTransaction().add(R.id.id_info_detail_layout, fragment).commit();
        }

    }
}
