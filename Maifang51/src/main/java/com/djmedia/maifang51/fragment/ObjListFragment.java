package com.djmedia.maifang51.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.activity.BaseActivity;
import com.djmedia.maifang51.activity.InfoDetailActivity;
import com.djmedia.maifang51.adapter.JSONAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by rd on 14-3-25.
 */
public class ObjListFragment extends Fragment {
    private static final String TAG = ObjListFragment.class.getSimpleName();

    protected final String QUERY_URL = "http://openlibrary.org/search.json?q=";
    protected String item_url = "http://covers.openlibrary.org/b/id/";
    protected int offset = 0;
    protected int limit = 20;

    protected JSONAdapter adapter;
    protected ListView mainListView;
    protected ProgressBar progressBar;
    protected String queryStr = "help";

    public ObjListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        progressBar = ((BaseActivity)getActivity()).getProgressBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_obj_list, container, false);
    }

    public void onObjItemClicked(int position) {
        Log.d(TAG, "onObjItemClicked called at position: " + position);
        JSONObject jsonObject = (JSONObject) adapter.getItem(position);
        String coverId = jsonObject.optString("cover_i", "");
        String title = jsonObject.optString("title", "");
        String author = jsonObject.optString("author_name", "");

        Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
        intent.putExtra("coverId", coverId);
        intent.putExtra("title", title);
        intent.putExtra("author", author);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new JSONAdapter(getActivity(), getActivity().getLayoutInflater(), item_url);
        mainListView = (ListView) view.findViewById(R.id.id_main_listview);
        mainListView.setAdapter(adapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ObjListFragment.this.onObjItemClicked(position);
            }
        });
        mainListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int visibleLastIndex = 0;
            private int visibleItemCount;

            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                int itemsLastIndex = adapter.getCount() - 1;
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == itemsLastIndex) {
                    offset = itemsLastIndex;
                    queryBooks(queryStr, offset, limit);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.visibleItemCount = visibleItemCount;
                this.visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
            }
        });
    }

    public void queryBooks(String searchString, int offset, int limit) {
        String urlString = "";
        try {
            urlString = URLEncoder.encode(searchString, "utf-8");
            urlString += "&offset=" + offset + "&limit=" + limit;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "query string: " + urlString);
        AsyncHttpClient client = new AsyncHttpClient();
        progressBar.setVisibility(View.VISIBLE);

        client.get(QUERY_URL + urlString, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "Success!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, jsonObject.toString());
                    JSONArray docs = jsonObject.optJSONArray("docs");
//                adapter.updateData(docs);
                    adapter.addData(docs);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, statusCode + " " + throwable.getMessage());
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem item = menu.findItem(R.id.menu_item_search);

        SearchView searchView = (SearchView) item.getActionView();
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "item called");
        return super.onOptionsItemSelected(item);
    }
}
