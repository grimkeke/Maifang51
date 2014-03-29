package com.djmedia.maifang51.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.djmedia.maifang51.R;

public class SlideImageFragment extends Fragment {
    private static final String TAG = SlideImageFragment.class.getSimpleName();
    private ViewFlipper mViewFlipper;
    private TextView mTextView;
    private String[] titles;
    private int[] imageResId;
    private RadioGroup mTopPage;
    private float mDx = 0f;
    private final int length = 5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slide_image, container, false);
        mViewFlipper = (ViewFlipper) view.findViewById(R.id.id_view_flipper);

        mTopPage = (RadioGroup) view.findViewById(R.id.id_top_page);
        mTextView = (TextView) view.findViewById(R.id.id_top_message);
        imageResId = new int[] {R.drawable.img0, R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4};
        titles = new String[] {"test1", "test2", "test3", "test4", "test5"};
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showSlideImages();
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
        showMessage(0);
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
        showMessage(mViewFlipper.getDisplayedChild());
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
        showMessage(mViewFlipper.getDisplayedChild());
    }
    private void showMessage(int position) {
        mTextView.setText(titles[position]);
    }
    private void changeShowNextBanner(int postion) {
        RadioButton radioButton = (RadioButton) mTopPage.getChildAt(postion);
        if (radioButton == null) {
            return ;
        }
        radioButton.setChecked(true);
    }
}
