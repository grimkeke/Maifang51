package com.djmedia.maifang51.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.fragment.ApartmentListFragment;
import com.djmedia.maifang51.fragment.CloudPhoneFragment;
import com.djmedia.maifang51.fragment.LoginFragment;
import com.djmedia.maifang51.fragment.MainFragment;
import com.djmedia.maifang51.fragment.MemberCenterFragment;
import com.djmedia.maifang51.fragment.MemberSpecialFragment;
import com.djmedia.maifang51.fragment.MoreFragment;
import com.djmedia.maifang51.tools.FID;
import com.djmedia.maifang51.tools.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private PopupWindow mPopupWindow;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private RadioGroup tabRadioGroup;

    private int currentIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setProgressBarIndeterminateVisibility(false);

        setContentView(R.layout.activity_main);

        createTab(savedInstanceState);
        createPopupWindow(savedInstanceState);
    }

    private void createPopupWindow(Bundle savedInstanceState) {
        View dialogView = getLayoutInflater().inflate(R.layout.popup_buttons, null);
        mPopupWindow = new PopupWindow(dialogView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
    }

    public void checkTag(int tabIndex) {
        int lastIndex = currentIndex;

        ((RadioButton) tabRadioGroup.getChildAt(tabIndex)).setChecked(true);
        currentIndex = tabIndex;

        refreshFragment(lastIndex);
    }

    private void createTab(Bundle bundle) {
        mFragmentList.add(FID.MAIN.getId(), new MainFragment());
        mFragmentList.add(FID.APARTMENT_LIST.getId(), new ApartmentListFragment());
        mFragmentList.add(FID.LOGIN.getId(), new LoginFragment());
        mFragmentList.add(FID.CLOUD_PHONE.getId(), new CloudPhoneFragment());
        mFragmentList.add(FID.MORE.getId(), new MoreFragment());
        mFragmentList.add(FID.MEMBER_CENTER.getId(), new MemberCenterFragment());
        mFragmentList.add(FID.MEMBER_SPECIAL.getId(), new MemberSpecialFragment());

        tabRadioGroup = (RadioGroup) findViewById(R.id.id_radio_group);
        checkTag(0); // you need to check one first before listen.

        tabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int selectedId) {
                Log.d(TAG, "onCheckedChanged called. count: " + radioGroup.getChildCount() + " selectedId: " + selectedId);

                int lastIndex = currentIndex;

                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    if (radioGroup.getChildAt(i).getId() == selectedId) {
                        currentIndex = i;
                        break;
                    }
                }
                Log.d(TAG, "lastIndex: " + lastIndex + " currentIndex: " + currentIndex);

                refreshFragment(lastIndex);
            }
        });

    }

    private void refreshFragment(int lastIndex) {
        if (Utils.isUserOnLine(MainActivity.this)) {
            if (currentIndex == FID.LOGIN.getId()) {
                currentIndex = FID.MEMBER_CENTER.getId();
            } else if (currentIndex == FID.CLOUD_PHONE.getId()) {
                currentIndex = FID.MEMBER_SPECIAL.getId();
            }
        }

        FragmentTransaction transaction = getFragmentTransaction(lastIndex);

        if (lastIndex != -1) {
            Fragment lastFragment = mFragmentList.get(lastIndex);
            lastFragment.onPause();
            transaction.hide(lastFragment);
        }

        Fragment fragment = mFragmentList.get(currentIndex);
        if (fragment.isAdded()) {
            fragment.onResume();
        } else {
            transaction.add(R.id.id_main_fragment, fragment);
        }
        transaction.show(fragment);

        transaction.commit();
    }

    private FragmentTransaction getFragmentTransaction(int lastIndex){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (lastIndex < currentIndex) {
            ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        } else {
            ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
        }
        return ft;
    }
}
