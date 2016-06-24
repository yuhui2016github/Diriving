package com.example.yuhui.driving.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuhui.driving.R;


/**
 * Created by yuhui on 2016-6-15.
 */

public class ArticleListFragment extends Fragment {

    public interface OnArticleSelectedListener {
        public void onArticleSelected(Uri articleUri);
    }

    private OnArticleSelectedListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.article_list_fragment, container, false);
        //若传入attachToRoot为true的话，Activity调用fragmentTransaction.replace(ContentViewId, new ArticleListFragment());
        //或者fragmentTransaction.add(..)
        //时就会报错， 因为一个view不能attach两次
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnArticleSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implements OnArticleSelectedListener");
        }
    }
}
