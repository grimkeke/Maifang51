package com.djmedia.maifang51.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.activity.InfoDetailActivity;
import com.djmedia.maifang51.adapter.InfoAdapter;
import com.djmedia.maifang51.tools.Constants;

import org.json.JSONArray;

/**
 * Created by rd on 14-3-20.
 */
public class MemberSpecialFragment extends ObjListFragment {
    private static final String TAG = MemberSpecialFragment.class.getSimpleName();

    public MemberSpecialFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_special, container, false);
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
                intent.putExtra(Constants.DETAIL_TYPE, Constants.TYPE_INFO_DETAIL);
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
