package com.djmedia.maifang51.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.adapter.TextAdapter;

public class PopListView extends RelativeLayout implements ViewBaseAction{
    private static String TAG = PopListView.class.getSimpleName();
	private ListView mListView;
	private final String[] items = new String[] {"100米", "500米", "1000米", "1500米", "2000米"};//显示字段
	private final String[] itemsVaule = new String[] { "1", "2", "3", "4", "5"};//隐藏id
    private int mBgDrawable = R.drawable.choosearea_bg_left;
	private OnSelectListener mOnSelectListener;
	private TextAdapter adapter;
	private String mDistance;
	private String showText = "按距离";
	private Context mContext;

	public String getShowText() {
		return showText;
	}

	public PopListView(Context context, int bgDrawable) {
		super(context);

        this.mBgDrawable = bgDrawable;
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.pw_view_distance, this, true);
		setBackgroundDrawable(getResources().getDrawable(mBgDrawable));
		mListView = (ListView) findViewById(R.id.listView);
		adapter = new TextAdapter(context, items, R.drawable.choose_item_right, R.drawable.choose_eara_item_selector);
		adapter.setTextSize(17);
		if (mDistance != null) {
			for (int i = 0; i < itemsVaule.length; i++) {
				if (itemsVaule[i].equals(mDistance)) {
					adapter.setSelectedPositionNoNotify(i);
					showText = items[i];
					break;
				}
			}
		}
		mListView.setAdapter(adapter);
		adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {

				if (mOnSelectListener != null) {
					showText = items[position];
					mOnSelectListener.getValue(itemsVaule[position], items[position]);
				}
			}
		});
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String distance, String showText);
	}

	@Override
	public void hide() {
        Log.e(TAG, "*************");
	}

	@Override
	public void show() {
        Log.e(TAG, "*************");
    }

}
