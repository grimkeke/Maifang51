package com.djmedia.maifang51.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.djmedia.maifang51.R;

/**
 * Created by rd on 14-3-21.
 */
public class CalculatorFragment extends Fragment {
    private static final String TAG = CalculatorFragment.class.getSimpleName();

    public CalculatorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calculator, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(getString(R.string.calculator));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
