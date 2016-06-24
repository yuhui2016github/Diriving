package com.example.yuhui.driving.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yuhui.driving.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yuhui on 2016-6-24.
 */
public class TitleAdapter extends RecyclerView.Adapter {
    public interface OnItemClickListener {
        void onClick(String item);
    }

    Context mContext;
    List<String> mTitles;
    OnItemClickListener mClickListener;

    public TitleAdapter(Context context) {
        this.mContext = context;
    }

    public void setTitles(List<String> list) {
        mTitles = list;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_title_item, parent, false);
        return new TitleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
        final String title = getItem(position);
        titleViewHolder.title.setText(title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onClick(title);
                }
            }
        });
    }

    protected String getItem(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    static class TitleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


}