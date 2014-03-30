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
 * Created by rd on 14-3-20.
 */
public class SalesMissionFragment extends Fragment {
    private TextView nameTextView;
    private TextView timeTextView;
    private TextView clientTextView;
    private TextView expireTextView;
    private TextView goalTextView;
    private TextView descriptionTextView;

    public SalesMissionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales_mission, container, false);
        getActivity().getActionBar().setTitle(getString(R.string.sales_mission));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String infoId = getArguments().getString(Constants.INFO_ID);

        nameTextView = (TextView) getActivity().findViewById(R.id.id_project_name);
        timeTextView = (TextView) getActivity().findViewById(R.id.id_project_time);
        clientTextView = (TextView) getActivity().findViewById(R.id.id_project_client);
        expireTextView = (TextView) getActivity().findViewById(R.id.id_project_expire);
        goalTextView = (TextView) getActivity().findViewById(R.id.id_project_goal);
        descriptionTextView = (TextView) getActivity().findViewById(R.id.id_project_description);
        nameTextView.setText("项目名称： " + "万科梦想园");
        timeTextView.setText("项目日期： " + "2014-01-11");
        clientTextView.setText("客户名称： " + "万科");
        expireTextView.setText("项目截止日期： " + "2014-04-22");
        goalTextView.setText("项目指标： " + "10000万");
        descriptionTextView.setText("项目描述： " + "万科梦想园XXXXXXXXXXXXXXX" + " infoId: " + infoId);
    }
}
