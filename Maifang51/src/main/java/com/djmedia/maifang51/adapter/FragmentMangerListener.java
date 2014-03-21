package com.djmedia.maifang51.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioGroup;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.tools.FID;
import com.djmedia.maifang51.tools.Utils;

import java.util.List;

/**
 * Created by rd on 14-3-21.
 */
public class FragmentMangerListener implements RadioGroup.OnCheckedChangeListener {
    private static final String TAG = FragmentMangerListener.class.getSimpleName();
    private FragmentActivity mContext;
    private List<Fragment> fragments;
    private RadioGroup radioGroup;
    private int layoutId;
    private int currentId;

    public FragmentMangerListener(FragmentActivity mContext, List<Fragment> fragments, RadioGroup radioGroup, int layoutId) {
        this.mContext = mContext;
        this.fragments = fragments;
        this.radioGroup = radioGroup;
        this.layoutId = layoutId;
    }

    public void selectFragmentAt(int index) {
        this.currentId = index;
        FragmentTransaction transaction = mContext.getSupportFragmentManager().beginTransaction();
        transaction.add(layoutId, fragments.get(index));
        transaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup rgs, int selectedId) {
        Log.d(TAG, "onCheckedChanged called. selectedId: " + selectedId);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            if (radioGroup.getChildAt(i).getId() == selectedId) {
                if (Utils.isUserOnLine(mContext)) {
                    if (i == FID.LOGIN.getId()) {
                        i = FID.MEMBER_CENTER.getId();
                    } else if (i == FID.CLOUD_PHONE.getId()) {
                        i = FID.MEMBER_SPECIAL.getId();
                    }
                }
                Fragment fragment = fragments.get(i);
                FragmentTransaction transaction = mContext.getSupportFragmentManager().beginTransaction();
                fragments.get(i).onPause();
                if (fragment.isAdded()) {
                    fragment.onResume();
                } else {
                    transaction.add(layoutId, fragment);
                }

                for (int j = 0; j < fragments.size(); j++) {
                    transaction.hide(fragments.get(j));
                }
                transaction.show(fragments.get(selectedId));
                transaction.commit();

                currentId = selectedId;
                return ;
            }
        }

    }

    private FragmentTransaction getFragmentTransaction(int index){
        FragmentTransaction transaction = mContext.getSupportFragmentManager().beginTransaction();
        // 设置切换动画
        if(index > currentId){
            transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        }else{
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
        }
        return transaction;
    }

}
