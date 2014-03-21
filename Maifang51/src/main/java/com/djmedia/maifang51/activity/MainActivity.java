package com.djmedia.maifang51.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.fragment.ApartmentListFragment;
import com.djmedia.maifang51.fragment.LoginFragment;
import com.djmedia.maifang51.fragment.MainFragment;
import com.djmedia.maifang51.fragment.MemberCenterFragment;
import com.djmedia.maifang51.fragment.MemberSpecialFragment;
import com.djmedia.maifang51.fragment.MoreFragment;
import com.djmedia.maifang51.tools.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private FragmentTabHost mTabHost;
    private PopupWindow mPopupWindow;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setProgressBarIndeterminateVisibility(false);

        createTab(savedInstanceState);
    }

    private View getTabView(int drawableId, int stringId) {
        View tabIndicator = getLayoutInflater().inflate(R.layout.tab_indicator, mTabHost, false);

        ImageView tabImage = (ImageView) tabIndicator.findViewById(R.id.id_tab_icon);
        tabImage.setImageResource(drawableId);

        TextView tabLabel = (TextView) tabIndicator.findViewById(R.id.id_tab_label);
        tabLabel.setText(stringId);

        return tabIndicator;
    }

    private void createTab(Bundle bundle) {
        setContentView(R.layout.activity_main);

        mTabHost = (FragmentTabHost) findViewById(R.id.id_tab_host);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.id_main_fragment);

        TabHost.TabSpec tab1 = mTabHost.newTabSpec("tab1").setIndicator(getTabView(R.drawable.button_index, R.string.index));
        TabHost.TabSpec tab2 = mTabHost.newTabSpec("tab2").setIndicator(getTabView(R.drawable.button_apartment, R.string.looking_for_apartment));
        TabHost.TabSpec tab3 = mTabHost.newTabSpec("tab3").setIndicator(getTabView(R.drawable.button_member_center, R.string.member_center));
        TabHost.TabSpec tab4 = mTabHost.newTabSpec("tab4").setIndicator(getTabView(R.drawable.button_cloud_phone, R.string.cloud_phone));
        TabHost.TabSpec tab5 = mTabHost.newTabSpec("tab5").setIndicator(getTabView(R.drawable.button_more, R.string.more));

        mTabHost.addTab(tab1, MainFragment.class, bundle);
        mTabHost.addTab(tab2, ApartmentListFragment.class, bundle);
        mTabHost.addTab(tab3, LoginFragment.class, bundle);
//        mTabHost.addTab(tab3, MemberCenterFragment.class, bundle);
        mTabHost.addTab(tab4, MemberSpecialFragment.class, bundle);
//        mTabHost.addTab(tab4, CloudPhoneFragment.class, bundle);
        mTabHost.addTab(tab5, MoreFragment.class, bundle);

        // resize width of each tab widget
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int screeWidth = displayMetrics.widthPixels;

        TabWidget tabWidget = mTabHost.getTabWidget();
        int count = tabWidget.getTabCount();
        for (int i = 0; i < count; i++) {
            tabWidget.getChildAt(i).getLayoutParams().width = screeWidth / count;
        }

        View dialogView = getLayoutInflater().inflate(R.layout.popup_buttons, null);
        mPopupWindow = new PopupWindow(dialogView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        mPopupWindow.setTouchable(true);
//        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String selectedTab) {
                Log.d(TAG, "tab changed:" + selectedTab);
                if ("tab1".equals(selectedTab)) {
                    mPopupWindow.showAsDropDown(mTabHost.getCurrentTabView(), -10, 10);
                } else if ("tab3".equals(selectedTab)) {
                    boolean userOnLine = Utils.isUserOnLine(MainActivity.this);
                    Log.d(TAG, "userOnLine: " + userOnLine);
                    if (userOnLine) {
                        FragmentManager manager = MainActivity.this.getSupportFragmentManager();
                        manager.beginTransaction().
                                replace(R.id.id_main_fragment, new MemberCenterFragment()).commit();
                    }
                }
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.id_company_info_button:
                if (checked) {

                }
                break;
            case R.id.id_sales_mission_button:
                if (checked) {


                }
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
