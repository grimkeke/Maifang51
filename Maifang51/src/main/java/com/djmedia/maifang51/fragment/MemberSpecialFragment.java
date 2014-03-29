package com.djmedia.maifang51.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioGroup;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.activity.InfoDetailActivity;
import com.djmedia.maifang51.adapter.InfoAdapter;
import com.djmedia.maifang51.tools.Constants;

import junit.framework.Assert;

import org.json.JSONArray;

/**
 * Created by rd on 14-3-20.
 */
public class MemberSpecialFragment extends ObjListFragment {
    private static final String TAG = MemberSpecialFragment.class.getSimpleName();
    private RadioGroup radioGroup;
    private int infoType = Constants.TYPE_BIZ_INFO;

    public MemberSpecialFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_special, container, false);
        radioGroup = (RadioGroup) view.findViewById(R.id.id_member_special_radio_group);
        radioGroup.check(radioGroup.getChildAt(0).getId());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkdIndex = -1;
                for (int i = 0; i < group.getChildCount(); i++) {
                    if (group.getChildAt(i).getId() == checkedId) {
                        checkdIndex = i;
                        break;
                    }
                }
                Assert.assertTrue(checkdIndex != -1);

                if (checkdIndex == 0) {
                    infoType = Constants.TYPE_BIZ_INFO;
                } else if (checkdIndex == 1) {
                    infoType = Constants.TYPE_SALES_MISSION;
                } else {
                    Assert.assertTrue(false);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(getString(R.string.member_special));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InfoAdapter infoAdapter = new InfoAdapter(getActivity(), getActivity().getLayoutInflater(), item_url);
        mainListView.setAdapter(infoAdapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
                intent.putExtra(Constants.DETAIL_TYPE, infoType);
                intent.putExtra(Constants.INFO_ID, "111111");
                startActivity(intent);
            }
        });

        JSONArray jsonArray = null;
        try {
            String ss = "{'title':'hello 1', 'time':'2013-1-2'}, {'title':'title 2', 'time':'2012-1-1'}, {'title':'title 4444', 'time':'2001-2-2'}, ";
            String testList = "[" + ss + ss + ss + ss + ss + ss + ss + ss + "{'title':'title 2', 'time':'2012-1-1'}" + "]";
            jsonArray = new JSONArray(testList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        infoAdapter.addData(jsonArray);
    }

}
