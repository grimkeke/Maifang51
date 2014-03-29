package com.djmedia.maifang51.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ViewFlipper;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.activity.InfoListActivity;
import com.djmedia.maifang51.activity.MainActivity;
import com.djmedia.maifang51.tools.Constants;
import com.djmedia.maifang51.tools.Utils;

public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();
    private ViewFlipper mViewFlipper;
    private int[] imageResId;
    private RadioGroup mTopPage;
    private float mDx = 0f;
    private final int length = 5;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mTopPage = (RadioGroup) view.findViewById(R.id.id_top_page);
        imageResId = new int[] {R.drawable.img0, R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4};

        mViewFlipper = (ViewFlipper) view.findViewById(R.id.id_view_flipper);
        mViewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mDx = motionEvent.getX();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        return true;
                    case MotionEvent.ACTION_UP:
                        float newX = motionEvent.getX();
                        float diffX = newX - mDx;
                        mDx = 0.0f;
                        if (diffX > 0) {
                            showPrev();
                        } else {
                            showNext();
                        }
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(getString(R.string.index));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showSlideImages();

        Button mNewsButton = (Button) getActivity().findViewById(R.id.id_news_button);
        mNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InfoListActivity.class);
                intent.putExtra(Constants.LIST_TYPE, Constants.TYPE_APARTMENT_NEWS);
                startActivity(intent);
            }
        });

        Button mInfoButton = (Button) getActivity().findViewById(R.id.id_info_button);
        mInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InfoListActivity.class);
                intent.putExtra(Constants.LIST_TYPE, Constants.TYPE_MARKET_INFO);
                startActivity(intent);
            }
        });

        Button mSpecialButton = (Button) getActivity().findViewById(R.id.id_special_button);
        mSpecialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isUserOnLine(getActivity())) {
                    Intent intent = new Intent(getActivity(), InfoListActivity.class);
                    intent.putExtra(Constants.LIST_TYPE, Constants.TYPE_MEMBER_SPECIAL);
                    startActivity(intent);
                } else {
                    ((MainActivity) getActivity()).checkTag(Constants.MEMBER_CENTER); // listener will auto refresh.
                }
            }
        });
    }

    private void showSlideImages() {
        mViewFlipper.removeAllViews();
        mTopPage.removeAllViews();

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(imageResId[i]);
            mViewFlipper.addView(imageView);
        }

        RadioGroup.LayoutParams rpm = new RadioGroup.LayoutParams(25, 25);
        for (int i = 0; i < length; i++) {
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setButtonDrawable(R.drawable.res_radio_home_page);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setLayoutParams(rpm);
            radioButton.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            radioButton.setPadding(0, 0, 0, 0);
            mTopPage.addView(radioButton);
        }
        changeShowNextBanner(0);
    }

    private void changeShowNextBanner(int postion) {
        RadioButton radioButton = (RadioButton) mTopPage.getChildAt(postion);
        if (radioButton == null) {
            return ;
        }
        radioButton.setChecked(true);
    }

    private void showNext() {
        if (mViewFlipper.getChildCount() <= 1) {
            return ;
        }
        int in = R.anim.push_left_in;
        int out = R.anim.push_left_out;
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), in));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), out));
        mViewFlipper.showNext();
        changeShowNextBanner(mViewFlipper.getDisplayedChild());
    }

    private void showPrev() {
        if (mViewFlipper.getChildCount() <= 1) {
            return ;
        }
        int in = R.anim.push_right_in;
        int out = R.anim.push_right_out;
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), in));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), out));
        mViewFlipper.showPrevious();
        changeShowNextBanner(mViewFlipper.getDisplayedChild());
    }

}
