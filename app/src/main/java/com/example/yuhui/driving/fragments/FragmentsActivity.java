package com.example.yuhui.driving.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yuhui.driving.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuhui on 2016-6-24.
 */
public class FragmentsActivity extends FragmentActivity implements View.OnKeyListener {
    @BindView(R.id.butterKnife_button)
    Button butterKnife_button;
    @BindView(R.id.butterKnife_textview)
    TextView butterKnife_textview;
    @BindString(R.string.app_name)
    String title;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fragments);
        ButterKnife.bind(this);
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.article_reader_container, new ArticleReaderFragment(), "ArticleReaderFragment01");
        fragmentTransaction.add(R.id.article_reader_container, new ArticleReaderFragment(), "ArticleReaderFragment02");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {//方法注册监听器，用于监听后退栈的变化
            @Override
            public void onBackStackChanged() {
                ((TextView) findViewById(R.id.textView)).setText("onBackStackChanged");
            }
        });

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            fragmentManager.popBackStack();
        }
        return false;
    }

    public void change(View v) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.article_reader_container, new ArticleListFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.butterKnife_button)
    public void butterKnifeButtonOnClick(Button button) {
        button.setText(title);
    }
}
