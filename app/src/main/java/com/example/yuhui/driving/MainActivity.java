package com.example.yuhui.driving;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.example.yuhui.driving.adapter.TitleAdapter;
import com.example.yuhui.driving.customview.TitleListView;
import com.example.yuhui.driving.fragments.FragmentsActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements TitleAdapter.OnItemClickListener {

    @BindView(R.id.recycler_view)
    TitleListView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] tempTitles = getResources().getStringArray(R.array.titles);
        List<String> titles = Arrays.asList(tempTitles);
        TitleAdapter titleAdapter = new TitleAdapter(this);
        titleAdapter.setTitles(titles);
        titleAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(titleAdapter);
    }


    @Override
    public void onClick(String item) {
        Intent intent = new Intent();
        if (item.equals("Fragments")) {
            intent.setClass(this, FragmentsActivity.class);
        } else if (item.equals("DashBoardView")) {
            intent.setClass(this, DashBoardActivity.class);
        } else if (item.equals("Animation")) {
            intent.setClass(this, AnimationActivity.class);
        } else {
            return;
        }
        startActivity(intent);
    }
}
