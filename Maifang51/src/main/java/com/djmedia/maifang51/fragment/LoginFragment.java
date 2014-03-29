package com.djmedia.maifang51.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.activity.MainActivity;
import com.djmedia.maifang51.tools.Constants;
import com.djmedia.maifang51.tools.Utils;

/**
 * Created by rd on 14-3-15.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = LoginFragment.class.getSimpleName();
    private Button mButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(getString(R.string.login));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mButton = (Button) getActivity().findViewById(R.id.id_login_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.makeUserOnLine(getActivity());
                ((MainActivity) getActivity()).checkTagAndRefresh(Constants.MEMBER_CENTER);
                Toast.makeText(getActivity(), "Login success.", Toast.LENGTH_SHORT).show();
            }
       });
    }
}
