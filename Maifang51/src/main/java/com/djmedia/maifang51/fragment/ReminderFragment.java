package com.djmedia.maifang51.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.djmedia.maifang51.R;

/**
 * Created by rd on 14-3-20.
 */
public class ReminderFragment extends Fragment {
    public ReminderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);
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
    }
}
