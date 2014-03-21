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
import android.widget.Toast;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.activity.ApartmentDetailActivity;
import com.djmedia.maifang51.adapter.JSONAdapter;
import com.djmedia.maifang51.view.ExpandTabView;
import com.djmedia.maifang51.view.PopDualListView;
import com.djmedia.maifang51.view.PopListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by rd on 14-3-15.
 */
public class ApartmentListFragment extends Fragment {
    private static final String TAG = ApartmentListFragment.class.getSimpleName();
    private static final String QUERY_URL = "http://openlibrary.org/search.json?q=";
    private String item_url = "http://covers.openlibrary.org/b/id/";
    ListView mainListView;
    JSONAdapter mJSONAdapter;
    ArrayList mNameList = new ArrayList();

    private int offset = 0;
    private int limit = 20;

    private ExpandTabView expandTabView;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    private PopListView viewLeft;
    private PopDualListView viewMiddle;
    private PopListView viewRight;

    public ApartmentListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apartment_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initExpandView();

        mJSONAdapter = new JSONAdapter(getActivity(), getActivity().getLayoutInflater(), item_url);
        Assert.assertNotNull(mJSONAdapter);

        mainListView = (ListView) view.findViewById(R.id.id_main_listview);
        Assert.assertNotNull(mainListView);

        mainListView.setAdapter(mJSONAdapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "position: " + i);
                JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(i);
                String coverId = jsonObject.optString("cover_i", "");
                String title = jsonObject.optString("title", "");
                String author = jsonObject.optString("author_name", "");

                Intent detailIntent = new Intent(getActivity(), ApartmentDetailActivity.class);
                detailIntent.putExtra("coverId", coverId);
                detailIntent.putExtra("title", title);
                detailIntent.putExtra("author", author);
                startActivity(detailIntent);
            }
        });
        mainListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int visibleLastIndex = 0;
            private int visibleItemCount;

            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                int itemsLastIndex = mJSONAdapter.getCount() - 1;
                Log.d(TAG, "itemsLastIndex: " + itemsLastIndex + " visibleLastIndex: " + visibleLastIndex);
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == itemsLastIndex) {
                    offset = itemsLastIndex;
                    Log.d(TAG, "begin loading, offset: " + offset + " limit: " + limit);
                    queryBooks("android", offset, limit);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.visibleItemCount = visibleItemCount;
                this.visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
            }
        });
        queryBooks("android", offset, limit);
    }

    private void queryBooks(String searchString, int offset, int limit) {
        String urlString = "";
        try {
            /*
            urlString += URLEncoder.encode("&offset=" + offset, "utf-8");
            urlString += URLEncoder.encode("&limit=" + limit, "utf-8");
            */
            urlString = URLEncoder.encode(searchString, "utf-8");
            urlString += "&offset=" + offset + "&limit=" + limit;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "query string: " + urlString);
        AsyncHttpClient client = new AsyncHttpClient();
        getActivity().setProgressBarIndeterminateVisibility(true);
        client.get(QUERY_URL + urlString, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Toast.makeText(getActivity(), "Success!", Toast.LENGTH_LONG).show();
                Log.d(TAG, jsonObject.toString());
                JSONArray docs = jsonObject.optJSONArray("docs");
//                mJSONAdapter.updateData(docs);
                mJSONAdapter.addData(docs);
                getActivity().setProgressBarIndeterminateVisibility(false);
            }

            @Override
            public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                Toast.makeText(getActivity(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, statusCode + " " + throwable.getMessage());
                getActivity().setProgressBarIndeterminateVisibility(false);
            }
        });
    }

    private void initExpandView() {
        expandTabView = (ExpandTabView) getActivity().findViewById(R.id.id_expandtab_view);
        viewLeft = new PopListView(getActivity(), R.drawable.choosearea_bg_left);
        viewRight = new PopListView(getActivity(), R.drawable.choosearea_bg_right);
        viewMiddle = new PopDualListView(getActivity());
        mViewArray = new ArrayList<View>(Arrays.asList(viewLeft, viewMiddle, viewRight));

        ArrayList<String> mTextArray = new ArrayList<String>(Arrays.asList("按区域", "按价格", "按类型"));
        expandTabView.setValue(mTextArray, mViewArray);
        expandTabView.setTitle(viewLeft.getShowText(), 0);
        expandTabView.setTitle(viewMiddle.getShowText(), 1);
        expandTabView.setTitle(viewRight.getShowText(), 2);

        viewLeft.setOnSelectListener(new PopListView.OnSelectListener() {
            @Override
            public void getValue(String distance, String showText) {
                onRefresh(viewLeft, showText);
            }
        });

        viewMiddle.setOnSelectListener(new PopDualListView.OnSelectListener() {
            @Override
            public void getValue(String showText) {
                onRefresh(viewMiddle,showText);
            }
        });

        viewRight.setOnSelectListener(new PopListView.OnSelectListener() {
            @Override
            public void getValue(String distance, String showText) {
                onRefresh(viewRight, showText);
            }
        });
    }

    private void onRefresh(View view, String showText) {
        expandTabView.onPressBack();
        int position = getPositon(view);
        if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
            expandTabView.setTitle(showText, position);
        }
        Toast.makeText(getActivity(), showText, Toast.LENGTH_SHORT).show();
    }

    private int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
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
