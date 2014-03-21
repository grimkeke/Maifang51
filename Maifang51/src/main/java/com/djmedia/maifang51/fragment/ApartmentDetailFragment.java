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
import android.widget.ImageView;
import android.widget.TextView;

import com.djmedia.maifang51.R;
import com.squareup.picasso.Picasso;

import junit.framework.Assert;

/**
 * Created by rd on 14-2-25.
 */
public class ApartmentDetailFragment extends Fragment {
    public static final String TAG = "ApartmentDetailFragment";
    private static final String IMAGE_URL_BASE = "http://covers.openlibrary.org/b/id/";
    String mImageUrl;
    ShareActionProvider mShareActionProvider;

    public ApartmentDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apartment_detail, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imageView = (ImageView) getActivity().findViewById(R.id.img_cover);
        TextView titleTextView = (TextView) getActivity().findViewById(R.id.detail_title);
        TextView authorTextView = (TextView) getActivity().findViewById(R.id.detail_author);

        Bundle bundle = getActivity().getIntent().getExtras();
        String coverId = bundle.getString("coverId");
        String title = bundle.getString("title");
        String author = bundle.getString("author");

        if (coverId.length() > 0) {
            mImageUrl = IMAGE_URL_BASE + coverId + "-L.jpg";
            Picasso.with(getActivity()).load(mImageUrl).placeholder(R.drawable.img_books_loading).into(imageView);
        }
        titleTextView.setText(title);
        authorTextView.setText(author);
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
        shareIntent.putExtra(Intent.EXTRA_TEXT, mImageUrl);
        mShareActionProvider.setShareIntent(shareIntent);
    }
}
