package com.lapism.searchview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.lapism.searchview.R;

import java.util.ArrayList;
import java.util.List;


class SearchAnimator {
    // TODO FOCUS ,PERMISSION, FIX EDIT TEXT PROPERTIES
    // TODO fix out animation
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void revealInAnimation(final Context mContext, final View animatedView, final int startCy, int duration) {

        int cx = animatedView.getWidth() - mContext.getResources().getDimensionPixelSize(R.dimen.search_key_line);
        int cy = startCy == -1 ? animatedView.getHeight() / 2 : startCy;

        if (cx != 0 && cy != 0) {
            float initialRadius = 0.0f;
            float finalRadius = Math.max(animatedView.getWidth(), animatedView.getHeight());

            Animator anim = ViewAnimationUtils.createCircularReveal(animatedView, cx, cy, initialRadius, finalRadius);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.setDuration(duration);
            animatedView.setVisibility(View.VISIBLE);
            anim.start();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void revealOutAnimation(final Context mContext, final View animatedView, final int endCy, int duration) {

        int cx = animatedView.getWidth() - mContext.getResources().getDimensionPixelSize(R.dimen.search_key_line);
        int cy = endCy == -1 ? animatedView.getHeight() / 2 : endCy;

        if (cx != 0 && cy != 0) {
            float initialRadius = animatedView.getWidth();
            float finalRadius = 0.0f;

            Animator anim = ViewAnimationUtils.createCircularReveal(animatedView, cx, cy, initialRadius, finalRadius);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.setDuration(duration);
            anim.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    animatedView.setVisibility(View.GONE);
                }
            });
            anim.start();
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void fadeInAnimation(final View view, int duration) {

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(duration);

        view.setAnimation(anim);
        view.setVisibility(View.VISIBLE);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void fadeOutAnimation(final View view, int duration) {

        Animation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(duration);

        view.setAnimation(anim);
        view.setVisibility(View.GONE);
    }


    public static List<Animator> getCardWithMarginsMarginsAnimators(final CardView view,
                                                                    int toHeight,
                                                                    int toWidth,
                                                                    int fromMarginTop,
                                                                    int toMarginTop,
                                                                    int fromMarginLeftRight,
                                                                    int toMarginLeftRight) {

        List<Animator> animatorList = new ArrayList<>();
        animatorList.add(getTopMarginAnimator(fromMarginTop, toMarginTop, view));
        animatorList.add(getWidthMarginAnimator(fromMarginLeftRight, toMarginLeftRight, view));
        animatorList.add(getHeightAnimator(view.getHeight(), toHeight, view));
        animatorList.add(getWidthAnimator(view.getWidth(), toWidth, view));
        return animatorList;
    }

    private static Animator getWidthAnimator(int from, int to, final CardView view) {
        ValueAnimator animatorWidth = ValueAnimator.ofInt(from, to);
        animatorWidth.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                if (params != null) {
                    params.width = (int) (Integer) valueAnimator.getAnimatedValue();
                    view.setLayoutParams(params);
                }
            }
        });
        return animatorWidth;
    }

    private static Animator getHeightAnimator(int from, int to, final CardView view) {
        ValueAnimator animatorHeight = ValueAnimator.ofInt(from, to);

        animatorHeight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                if (params != null) {
                    params.height = (int) (Integer) valueAnimator.getAnimatedValue();
                    view.setLayoutParams(params);
                }
            }
        });
        return animatorHeight;
    }

    private static Animator getWidthMarginAnimator(int fromValue, int toValue, final CardView view) {
        ValueAnimator animatorWidthMargins = ValueAnimator.ofInt(fromValue, toValue);

        animatorWidthMargins.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                if (params != null) {
                    params.leftMargin = (int) (Integer) valueAnimator.getAnimatedValue();
                    params.rightMargin = (int) (Integer) valueAnimator.getAnimatedValue();
                    view.setLayoutParams(params);
                }
            }
        });
        return animatorWidthMargins;
    }

    private static Animator getTopMarginAnimator(int fromValue, int toValue, final CardView view) {
        ValueAnimator animatorHeightMargins = ValueAnimator.ofInt(fromValue, toValue);

        animatorHeightMargins.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                if (params != null) {
                    params.topMargin = (int) (Integer) valueAnimator.getAnimatedValue();
                    view.setLayoutParams(params);
                }
            }
        });
        return animatorHeightMargins;
    }

    public static void runAnimatorSet(List<Animator> animatorList, int duration, Animator.AnimatorListener animatorListener) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.playTogether(animatorList);
        animatorSet.addListener(animatorListener);
        animatorSet.start();
    }
}