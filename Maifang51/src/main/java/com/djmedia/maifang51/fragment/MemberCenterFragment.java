package com.djmedia.maifang51.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.activity.InfoDetailActivity;
import com.djmedia.maifang51.tools.Constants;

/**
 * Created by rd on 14-3-15.
 */
public class MemberCenterFragment extends Fragment {
    public static final String TAG = MemberCenterFragment.class.getSimpleName();
    private TextView nameTextView;
    private TextView descTextView;
    private Button mineButton;
    private Button mailButton;
    private Button remainderButton;

    public MemberCenterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_center, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameTextView = (TextView) getActivity().findViewById(R.id.id_member_center_name);
        nameTextView.setText("测试用户");

        descTextView = (TextView) getActivity().findViewById(R.id.id_member_center_description);
        descTextView.setText("职位：初级用户");

        mineButton = (Button) getActivity().findViewById(R.id.id_member_center_mine_button);
        mineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDetailActivity(1);
            }
        });

        mailButton = (Button) getActivity().findViewById(R.id.id_member_center_mail_button);
        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDetailActivity(2);
            }
        });

        remainderButton = (Button) getActivity().findViewById(R.id.id_member_center_remind_button);
        remainderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDetailActivity(3);
            }
        });
    }

    private void startDetailActivity(int type) {
        Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
        intent.putExtra(Constants.DETAIL_TYPE, type);
        startActivity(intent);
    }

}
