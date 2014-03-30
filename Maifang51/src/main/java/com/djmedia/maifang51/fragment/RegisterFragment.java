package com.djmedia.maifang51.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.djmedia.maifang51.R;

/**
 * Created by rd on 14-3-20.
 */
public class RegisterFragment extends Fragment {
    private static final String TAG = RegisterFragment.class.getSimpleName();

    private EditText nameEditText;
    private EditText passwordEditText;
    private EditText rePasswordEditText;
    private EditText uidEditText;
    private Button uploadButton;
    private Button registerButton;
    private ImageView uploadedImageView;

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        nameEditText = (EditText) view.findViewById(R.id.id_register_name);
        passwordEditText = (EditText) view.findViewById(R.id.id_register_password);
        rePasswordEditText = (EditText) view.findViewById(R.id.id_register_password_re);
        uidEditText = (EditText) view.findViewById(R.id.id_register_uid);
        uploadedImageView = (ImageView) view.findViewById(R.id.id_register_uploaded_image);

        uploadButton = (Button) view.findViewById(R.id.id_register_upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "upload image");
            }
        });

        registerButton = (Button) view.findViewById(R.id.id_register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "submit register info");
//                ((MainActivity) getActivity()).checkTagAndRefresh(Constants.MEMBER_CENTER);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
