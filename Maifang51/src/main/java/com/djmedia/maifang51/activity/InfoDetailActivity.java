package com.djmedia.maifang51.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.fragment.BizInfoFragment;
import com.djmedia.maifang51.fragment.InfoDetailFragment;
import com.djmedia.maifang51.fragment.SalesMissionFragment;
import com.djmedia.maifang51.fragment.SearchApartmentFragment;
import com.djmedia.maifang51.fragment.CalculatorFragment;
import com.djmedia.maifang51.fragment.CommunityFragment;
import com.djmedia.maifang51.fragment.MapFragment;
import com.djmedia.maifang51.fragment.MemberSpecialFragment;
import com.djmedia.maifang51.fragment.MineProjectFragment;
import com.djmedia.maifang51.fragment.ReminderFragment;
import com.djmedia.maifang51.tools.Constants;

/**
 * Created by rd on 14-3-20.
 */
public class InfoDetailActivity extends BaseActivity {
    private static final String TAG = InfoDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);

        progressBar = (ProgressBar) findViewById(R.id.id_progress_bar);
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);

        int detailType = getIntent().getIntExtra(Constants.DETAIL_TYPE, Constants.TYPE_SPECIAL);
        Log.d(TAG, "get type: " + detailType);

        Fragment fragment = null;

        switch (detailType) {
            case Constants.TYPE_MINE_PROJECT:
                fragment = new MineProjectFragment();
                break;
            case Constants.TYPE_SPECIAL:
                fragment = new MemberSpecialFragment();
                break;
            case Constants.TYPE_REMIND:
                fragment = new ReminderFragment();
                break;
            case Constants.TYPE_REVIEW_PROJECT:
                fragment = new SearchApartmentFragment();
                break;
            case Constants.TYPE_COMMUNITY:
                fragment = new CommunityFragment();
                break;
            case Constants.TYPE_CALCULATOR:
                fragment = new CalculatorFragment();
                break;
            case Constants.TYPE_MAP:
                fragment = new MapFragment();
                break;
            case Constants.TYPE_INFO_DETAIL:
                fragment = new InfoDetailFragment();
                fragment.setArguments(getIntent().getExtras());
                break;
            case Constants.TYPE_BIZ_INFO:
                fragment = new BizInfoFragment();
                fragment.setArguments(getIntent().getExtras());
                break;
            case Constants.TYPE_SALES_MISSION:
                fragment = new SalesMissionFragment();
                fragment.setArguments(getIntent().getExtras());
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
