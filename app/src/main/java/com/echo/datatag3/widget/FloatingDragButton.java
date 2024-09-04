package com.echo.datatag3.widget;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import java.util.function.Function;

public class FloatingDragButton implements View.OnTouchListener {

    private final Builder mBuilder;

    private int mDownX, mDownY, mLastX, mLastY;
    private boolean mTouchResult = false;

    private FloatingDragButton(Builder builder) {
        mBuilder = builder;
        initView();
    }

    public Activity getActivity() {
        return mBuilder.activity;
    }

    public View getView() {
        return mBuilder.view;
    }

    private void initView() {
        if (null == getActivity() || null == mBuilder.view) {
            throw new NullPointerException("life cycle error");
        }
        FrameLayout rootLayout = (FrameLayout) getActivity().getWindow().getDecorView();
        rootLayout.addView(mBuilder.view, mBuilder.layoutParams);
        getView().setOnTouchListener(this);
    }

    private static FloatingDragButton createView(Builder builder) {
        if (null == builder) {
            throw new NullPointerException("the param builder is null when execute method createDragView");
        }
        if (null == builder.activity || null == builder.view) {
            throw new NullPointerException("life cycle error");
        }
        return new FloatingDragButton(builder);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchResult = false;
                mDownX = mLastX = (int) event.getRawX();
                mDownY = mLastY = (int) event.getRawY();
                return true;
            case MotionEvent.ACTION_MOVE:
                int left, top, right, bottom;
                int dx = (int) event.getRawX() - mLastX;
                int dy = (int) event.getRawY() - mLastY;
                left = Math.max(v.getLeft() + dx, 0);
                right = left + v.getWidth();
                if (right > mBuilder.screenWidth) {
                    right = mBuilder.screenWidth;
                    left = right - v.getWidth();
                }
                top = v.getTop() + dy < mBuilder.screenHeight * 0.182 ? (int) (mBuilder.screenHeight * 0.182) : v.getTop() + dy;
                bottom = top + v.getHeight();
                if (bottom > mBuilder.screenHeight * 0.818) {
                    bottom = (int) (mBuilder.screenHeight * 0.818);
                    top = bottom - v.getHeight();
                }
                v.layout(left, top, right, bottom);
                mLastX = (int) event.getRawX();
                mLastY = (int) event.getRawY();
                return true;
            case MotionEvent.ACTION_UP:
                //这里需设置LayoutParams，不然按home后回再到页面等view会回到原来的地方
//                v.setLayoutParams(createLayoutParams(v.getLeft(), v.getTop(), 0, 0));
                float endX = event.getRawX();
                float endY = event.getRawY();
                if (Math.abs(endX - mDownX) > 10 || Math.abs(endY - mDownY) > 10) {
                    if (mBuilder.adsorbEdge) {
                        adsorbEdge();
                    }
                } else {
                    mBuilder.onClick.apply(mBuilder.view);
                }
                return true;
        }
        return false;
    }

    /**
     * 吸附至最近的边沿
     */
    private void adsorbEdge() {
        ImageView view = (ImageView) getView();
        int left = view.getLeft();
        int lastX;
        if (left + view.getWidth() / 2 <= mBuilder.screenWidth / 2) {
            lastX = 0;
        } else {
            lastX = mBuilder.screenWidth - view.getWidth();
        }
        ValueAnimator valueAnimator = ValueAnimator.ofInt(left, lastX);
        valueAnimator.setDuration(100);
        valueAnimator.setRepeatCount(0);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int left = (int) animation.getAnimatedValue();
                view.setLayoutParams(createLayoutParams(left, view.getTop(), 0, 0));
            }
        });
        valueAnimator.start();
    }

    private FrameLayout.LayoutParams createLayoutParams(int left, int top, int right, int bottom) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(mBuilder.size, mBuilder.size);
        layoutParams.setMargins(left, top, right, bottom);
        return layoutParams;
    }

    private int[] getViewSize(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }

    public static class Builder {
        private Activity activity;
        private int size;
        FrameLayout.LayoutParams layoutParams;
        private boolean adsorbEdge = true;//是否吸附边缘
        private int screenWidth;
        private int screenHeight;
        private int statusBarHeight;
        private View view;
        private Function<View, Void> onClick;

        public Builder setActivity(Activity activity) {
            this.activity = activity;
            init();
            return this;
        }

        private void init() {
            screenWidth = activity.getResources().getDisplayMetrics().widthPixels;
            screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = resources.getDimensionPixelSize(resourceId);
            }else statusBarHeight = 0;
            size = FrameLayout.LayoutParams.WRAP_CONTENT;
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(size, size);
            layoutParams.setMargins(50, (int) (screenHeight * 0.618), 0, 0);
            this.layoutParams = layoutParams;
            onClick = view -> null;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setLayoutParams(int left, int right, int top, int bottom) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(size, size);
            layoutParams.setMargins(left, top, right, bottom);
            this.layoutParams = layoutParams;
            return this;
        }

        public Builder setIcon(@DrawableRes int imageRes) {
            ImageView imageView = new ImageView(activity);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setImageResource(imageRes);
            this.view = imageView;
            return this;
        }

        public Builder setAdsorbEdge(boolean adsorbEdge) {
            this.adsorbEdge = adsorbEdge;
            return this;
        }

        public Builder setOnClick(Function<View, Void> onClick) {
            this.onClick = onClick;
            return this;
        }

        public FloatingDragButton build() {
            return createView(this);
        }
    }

}
