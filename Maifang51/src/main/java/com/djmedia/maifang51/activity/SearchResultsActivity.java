package com.djmedia.maifang51.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

/**
 * Created by rd on 14-3-18.
 */
public class SearchResultsActivity extends ActionBarActivity {
    private static final String TAG = SearchResultsActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(TAG, "query string: " + query);
            //use the query to search your data somehow
        }
    }
}
