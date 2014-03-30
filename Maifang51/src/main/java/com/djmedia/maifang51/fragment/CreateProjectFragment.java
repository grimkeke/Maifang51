package com.djmedia.maifang51.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.djmedia.maifang51.R;

/**
 * Created by rd on 2014/3/30.
 */
public class CreateProjectFragment extends Fragment {
    private static final String TAG = CreateProjectFragment.class.getSimpleName();
    private Button confirmProjectButton;

    public CreateProjectFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_project, container, false);
        confirmProjectButton = (Button) view.findViewById(R.id.id_confirm_project_button);
        confirmProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "创建成功", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
