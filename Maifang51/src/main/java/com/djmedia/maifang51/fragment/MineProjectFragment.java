package com.djmedia.maifang51.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.activity.InfoDetailActivity;
import com.djmedia.maifang51.adapter.ProjectAdapter;
import com.djmedia.maifang51.tools.Constants;

import org.json.JSONArray;

/**
 * Created by rd on 14-3-20.
 */
public class MineProjectFragment extends ObjListFragment {
    private static final String TAG = MineProjectFragment.class.getSimpleName();

    private Button createClientButton;
    private Button createProjectButton;

    public MineProjectFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(getString(R.string.my_project));
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_project, container, false);
        createClientButton = (Button) view.findViewById(R.id.id_create_client_info_button);
        createClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
                intent.putExtra(Constants.DETAIL_TYPE, Constants.TYPE_CREATE_PROJECT);
                startActivity(intent);
            }
        });

        createProjectButton = (Button) view.findViewById(R.id.id_create_project_button);
        createProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
                intent.putExtra(Constants.DETAIL_TYPE, Constants.TYPE_CREATE_PROJECT);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProjectAdapter projectAdapter = new ProjectAdapter(getActivity(), getActivity().getLayoutInflater(), item_url);
        mainListView.setAdapter(projectAdapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "checked: " + i, Toast.LENGTH_LONG).show();
            }
        });

        JSONArray jsonArray = null;
        try {
            String ss = "{'title':'hello 1', 'step':'0', 'time':'2013-1-2'}, {'title':'title 2', 'step':'1', 'time':'2012-1-1'}, ";
            String testList = "[" + ss + ss + ss + ss + ss + ss + ss + ss + "{'title':'title 2', 'step':'2', 'time':'2012-1-1'}" + "]";
            jsonArray = new JSONArray(testList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        projectAdapter.addData(jsonArray);
    }
}
