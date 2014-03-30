package com.djmedia.maifang51.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.activity.InfoDetailActivity;
import com.djmedia.maifang51.adapter.ReminderAdapter;
import com.djmedia.maifang51.tools.Constants;

import org.json.JSONArray;

/**
 * Created by rd on 14-3-20.
 */
public class ReminderFragment extends ObjListFragment {
    private static final String TAG = ReminderFragment.class.getSimpleName();
    private Button createReminderButton;

    public ReminderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);
        createReminderButton = (Button) view.findViewById(R.id.id_create_reminder_button);
        createReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
                intent.putExtra(Constants.DETAIL_TYPE, Constants.TYPE_CREATE_REMIND);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(getString(R.string.my_reminder));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ReminderAdapter reminderAdapter = new ReminderAdapter(getActivity(), getActivity().getLayoutInflater(), item_url);
        mainListView.setAdapter(reminderAdapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
                intent.putExtra(Constants.DETAIL_TYPE, Constants.TYPE_CREATE_REMIND);
                intent.putExtra(Constants.INFO_ID, "111111");
                startActivity(intent);
            }
        });

        JSONArray jsonArray = null;
        try {
            String ss = "{'title':'hello 1', 'content':'预约看房2013-1-2'}, {'title':'title 2', 'content':'浦东新区看房2012-1-1'}, {'title':'title 4444', 'content':'2001-2-2'}, ";
            String testList = "[" + ss + ss + ss + ss + ss + ss + ss + ss + "{'title':'title 2', 'content':'2012-1-1'}" + "]";
            jsonArray = new JSONArray(testList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reminderAdapter.addData(jsonArray);
    }

}
