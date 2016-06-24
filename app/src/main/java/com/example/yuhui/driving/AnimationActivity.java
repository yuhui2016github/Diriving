package com.example.yuhui.driving;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yuhui on 2016-6-24.
 */
public class AnimationActivity extends Activity {
    @BindView(R.id.view_animiation)
    ImageView viewAnimation;
    @BindView(R.id.drawable_animation)
    ImageView drawableAnimation;

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
    }
}
