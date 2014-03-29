package com.djmedia.maifang51.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.activity.InfoDetailActivity;
import com.djmedia.maifang51.tools.Constants;

public class MoreFragment extends Fragment {
    private static final String TAG = MoreFragment.class.getSimpleName();
    private Button mReviewButton;
    private Button mCommunityButton;
    private Button mCalculatorButton;

    public MoreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(getString(R.string.more));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mReviewButton = (Button) getActivity().findViewById(R.id.id_review_project_button);
        mReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
                intent.putExtra(Constants.DETAIL_TYPE, Constants.TYPE_REVIEW_PROJECT);
                startActivity(intent);
            }
        });

        mCommunityButton = (Button) getActivity().findViewById(R.id.id_community_button);
        mCommunityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
                intent.putExtra(Constants.DETAIL_TYPE, Constants.TYPE_COMMUNITY);
                startActivity(intent);
            }
        });

        mCalculatorButton = (Button) getActivity().findViewById(R.id.id_calculator_button);
        mCalculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
                intent.putExtra(Constants.DETAIL_TYPE, Constants.TYPE_CALCULATOR);
                startActivity(intent);
            }
        });
    }
}
