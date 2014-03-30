package com.djmedia.maifang51.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.activity.InfoDetailActivity;
import com.djmedia.maifang51.tools.Constants;
import com.djmedia.maifang51.view.ExpandTabView;
import com.djmedia.maifang51.view.PopDualListView;
import com.djmedia.maifang51.view.PopListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by rd on 14-3-15.
 */
public class ReviewProjectFragment extends ObjListFragment {
    private static final String TAG = ReviewProjectFragment.class.getSimpleName();

    protected ExpandTabView expandTabView;
    protected ArrayList<View> mViewArray = new ArrayList<View>();
    protected PopListView viewLeft;
    protected PopDualListView viewMiddle;
    protected PopListView viewRight;

    public ReviewProjectFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apartment_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(getString(R.string.review_project));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initExpandView();

        queryStr = "apple";
        queryBooks(queryStr, offset, limit);
    }

    protected void initExpandView() {
        expandTabView = (ExpandTabView) getActivity().findViewById(R.id.id_expandtab_view);
        viewLeft = new PopListView(getActivity(), R.drawable.choosearea_bg_left);
        viewRight = new PopListView(getActivity(), R.drawable.choosearea_bg_right);
        viewMiddle = new PopDualListView(getActivity());
        mViewArray = new ArrayList<View>(Arrays.asList(viewLeft, viewMiddle, viewRight));

        ArrayList<String> mTextArray = new ArrayList<String>(Arrays.asList("按区域", "按价格", "按类型"));
        expandTabView.setValue(mTextArray, mViewArray);
        expandTabView.setTitle(viewLeft.getShowText(), 0);
        expandTabView.setTitle(viewMiddle.getShowText(), 1);
        expandTabView.setTitle(viewRight.getShowText(), 2);

        viewLeft.setOnSelectListener(new PopListView.OnSelectListener() {
            @Override
            public void getValue(String distance, String showText) {
                onRefresh(viewLeft, showText);
            }
        });

        viewMiddle.setOnSelectListener(new PopDualListView.OnSelectListener() {
            @Override
            public void getValue(String showText) {
                onRefresh(viewMiddle,showText);
            }
        });

        viewRight.setOnSelectListener(new PopListView.OnSelectListener() {
            @Override
            public void getValue(String distance, String showText) {
                onRefresh(viewRight, showText);
            }
        });
    }

    protected void onRefresh(View view, String showText) {
        expandTabView.onPressBack();
        int position = getPositon(view);
        if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
            expandTabView.setTitle(showText, position);
        }
        Toast.makeText(getActivity(), showText, Toast.LENGTH_SHORT).show();
    }

    protected int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
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
        Log.d(TAG, "start InfoDetailActivity set type: " + Constants.TYPE_APARTMENT_DETAIL);
        intent.putExtra("coverId", coverId);
        intent.putExtra("title", title);
        intent.putExtra("author", author);
        startActivity(intent);
    }
}
