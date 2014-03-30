package com.djmedia.maifang51.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.djmedia.maifang51.R;

import junit.framework.Assert;

/**
 * Created by rd on 14-2-25.
 */
public class InfoDetailFragment extends Fragment {
    public static final String TAG = "ApartmentDetailFragment";
    private TextView titleTextView;
    private TextView contentTextView;
    ShareActionProvider mShareActionProvider;

    public InfoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleTextView = (TextView) getActivity().findViewById(R.id.detail_title);
        contentTextView = (TextView) getActivity().findViewById(R.id.detail_author);

        Bundle bundle = getActivity().getIntent().getExtras();
        String title = bundle.getString("title");
        String author = bundle.getString("author");

        titleTextView.setText(title);
        contentTextView.setText(author);
        getActivity().setTitle(title);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main, menu);
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);
        Assert.assertNotNull(shareItem);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        Assert.assertNotNull(mShareActionProvider);
        setShareIntent();
    }

    private void setShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Book Recommendation!");
        shareIntent.putExtra(Intent.EXTRA_TEXT, titleTextView.getText());
        mShareActionProvider.setShareIntent(shareIntent);
    }
}
