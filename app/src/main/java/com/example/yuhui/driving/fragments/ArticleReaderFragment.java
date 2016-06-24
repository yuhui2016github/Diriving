package com.example.yuhui.driving.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuhui.driving.R;

import butterknife.ButterKnife;

/**
 * Created by yuhui on 2016-6-15.
 */
public class ArticleReaderFragment extends Fragment {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.article_reader_fragment,container,false);
        ButterKnife.bind(this, view);
        return view;
    }
}
