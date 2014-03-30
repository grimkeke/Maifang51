package com.djmedia.maifang51.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.tools.Constants;

/**
 * Created by rd on 2014/3/30.
 */
public class BizInfoFragment extends Fragment {
    private TextView titleTextView;
    private TextView contentTextView;

    public BizInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_detail, container, false);
        titleTextView = (TextView) view.findViewById(R.id.detail_title);
        contentTextView = (TextView) view.findViewById(R.id.detail_author);
        getActivity().getActionBar().setTitle(getString(R.string.project_info));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String infoId = getArguments().getString(Constants.INFO_ID);
        titleTextView.setText(infoId);
        contentTextView.setText("biz infoXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    }
}
