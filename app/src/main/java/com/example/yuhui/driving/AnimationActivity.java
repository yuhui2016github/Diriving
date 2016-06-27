package com.example.yuhui.driving;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yuhui on 2016-6-24.
 */
public class AnimationActivity extends Activity {
    private static final String TAG = "AnimationActivity";
    @BindView(R.id.view_animiation)
    ImageView viewAnimation;
    @BindView(R.id.drawable_animation)
    ImageView drawableAnimation;
    @BindView(R.id.object_animation)
    TextView objectAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_animation_activity);
        ButterKnife.bind(this);


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        viewAnimation.setImageResource(R.drawable.topic_vivid_check);
        viewAnimation.setAnimation(animation);

        drawableAnimation.setBackgroundResource(R.drawable.scan);
        AnimationDrawable animationDrawable = (AnimationDrawable) drawableAnimation.getBackground();
        animationDrawable.start();

        ValueAnimator anim = ValueAnimator.ofFloat(0f, 500f);
        anim.setDuration(500);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                Log.e(TAG, "cuurent value is " + currentValue);
            }
        });
        anim.start();

//        ObjectAnimator animator = ObjectAnimator.ofFloat(objectAnimation, "alpha", 1f, 0f, 1f);
//        animator.setDuration(5000);
//        animator.start();
//
//        animator = ObjectAnimator.ofFloat(objectAnimation, "rotation", 0f, 360f);
//        animator.setDuration(5000);
//        animator.start();

        ObjectAnimator moveIn = ObjectAnimator.ofFloat(objectAnimation, "translationX", -500f, 0f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(objectAnimation, "rotation", 0f, 360f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(objectAnimation, "alpha", 1f, 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(rotate).with(fadeInOut).after(moveIn);
        animSet.setDuration(5000);
//        animSet.start();

        anim.addListener(new ValueAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
            }
        });

        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.animator_file);
        animator.setTarget(objectAnimation);
        animator.start();
    }
}
