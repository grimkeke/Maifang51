package com.djmedia.maifang51.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.fragment.ApartmentDetailFragment;
import com.djmedia.maifang51.fragment.ApartmentListFragment;
import com.djmedia.maifang51.fragment.SlideImageFragment;

public class ApartmentDetailActivity extends ActionBarActivity {
    private static final String TAG = ApartmentListFragment.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_detail);

        if (savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .add(R.id.id_apartment_detail_layout, new ApartmentDetailFragment())
                    .add(R.id.id_slide_frame_layout, new SlideImageFragment())
                    .commit();
        }
    }
}
