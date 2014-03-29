package com.djmedia.maifang51.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.fragment.CloudPhoneFragment;
import com.djmedia.maifang51.fragment.LoginFragment;
import com.djmedia.maifang51.fragment.MainFragment;
import com.djmedia.maifang51.fragment.MemberCenterFragment;
import com.djmedia.maifang51.fragment.MemberSpecialFragment;
import com.djmedia.maifang51.fragment.MoreFragment;
import com.djmedia.maifang51.fragment.SearchApartmentFragment;
import com.djmedia.maifang51.tools.Constants;
import com.djmedia.maifang51.tools.Utils;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private PopupWindow mPopupWindow;
    private RadioGroup tabRadioGroup;
    private int currentIndex = -1;
    private int currentFragmentIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.id_progress_bar);
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);

        createTab(savedInstanceState);
        createPopupWindow();
    }

    private void createPopupWindow() {
        View dialogView = getLayoutInflater().inflate(R.layout.popup_buttons, null);
        mPopupWindow = new PopupWindow(dialogView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
    }

    public void checkTag(int tabIndex) {
        Log.d(TAG, "currentIndex: " + currentIndex + " currentFragmentIndex: " +
                currentFragmentIndex + " checkedIndex: " + tabIndex);

        ((RadioButton) tabRadioGroup.getChildAt(tabIndex)).setChecked(true);
    }

    public void checkTagAndRefresh(int tabIndex) {
        checkTag(tabIndex);
        refreshFragment(tabIndex);
    }

    private void createTab(Bundle bundle) {
        mFragmentList.add(Constants.MAIN, new MainFragment());
        mFragmentList.add(Constants.SEARCH_APARTMENT, new SearchApartmentFragment());
        mFragmentList.add(Constants.MEMBER_CENTER, new MemberCenterFragment());
        mFragmentList.add(Constants.CLOUD_PHONE, new CloudPhoneFragment());
        mFragmentList.add(Constants.MORE, new MoreFragment());
        mFragmentList.add(Constants.LOGIN, new LoginFragment());
        mFragmentList.add(Constants.MEMBER_SPECIAL, new MemberSpecialFragment());

        tabRadioGroup = (RadioGroup) findViewById(R.id.id_radio_group);
        checkTagAndRefresh(0); // you need to check one first before add listener.

        tabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int selectedId) {
                int tabIndex = -1;
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    if (radioGroup.getChildAt(i).getId() == selectedId) {
                        tabIndex = i;
                        break;
                    }
                }
                Assert.assertTrue(-1 != tabIndex);

                refreshFragment(tabIndex);

                if (tabIndex == Constants.MAIN) {
//                    mPopupWindow.showAtLocation(tabRadioGroup, 1, 10, 10);
                }
            }
        });
    }

    private void refreshFragment(int tabIndex) {
        FragmentTransaction transaction = getFragmentTransaction(tabIndex);
        boolean userOnLine = Utils.isUserOnLine(MainActivity.this);

        int fragmentIndex = tabIndex;
        if (!userOnLine) {
            if (tabIndex == Constants.MEMBER_CENTER || tabIndex == Constants.CLOUD_PHONE) {
                fragmentIndex = Constants.LOGIN;
            }
        }

        // 未登录情况下，在个人中心和云拷客之间点击tab，则无需切换fragment
        if (currentFragmentIndex == fragmentIndex && fragmentIndex == Constants.LOGIN) {
            Log.d(TAG, "need not to change fragment" +
                    "currentIndex:" + currentIndex + " tabIndex: " + tabIndex);
            return ;
        }

        if (currentFragmentIndex != -1) {
            Fragment currentFragment = mFragmentList.get(currentFragmentIndex);
            currentFragment.onPause();
            transaction.hide(currentFragment);
            Log.d(TAG, "hide " + currentFragmentIndex + " at index: " + currentIndex);
        }

        Fragment fragment = mFragmentList.get(fragmentIndex);
        if (fragment.isAdded()) {
            fragment.onResume();
        } else {
            transaction.add(R.id.id_main_fragment, fragment);
        }
        transaction.show(fragment);
        Log.d(TAG, "show " + fragmentIndex + " at index: " + tabIndex);

        transaction.commit();

        currentIndex = tabIndex;
        currentFragmentIndex = fragmentIndex;
    }

    private FragmentTransaction getFragmentTransaction(int tabIndex){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (tabIndex > currentIndex) {
            ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        } else {
            ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
        }
        return ft;
    }
}
