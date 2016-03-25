package com.daemon1993.drecyclerviewdemo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daemon1993.drecyclerview.DBaseRecyclerViewAdapter;
import com.daemon1993.drecyclerview.DBaseRecyclerViewHolder;
import com.daemon1993.drecyclerview.DRecyclerViewAdapter;
import com.daemon1993.drecyclerview.DRecyclerViewScrollListener;
import com.daemon1993.drecyclerview.DSpanSizeLookup;
import com.daemon1993.drecyclerview.DStaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private RecyclerView rcv_list2;
    private DStaggeredGridLayoutManager staggeLayoutManager;
    private List<MainActivity.Data> list;
    private MyAdpater myAdpater;
    private DRecyclerViewAdapter dRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        rcv_list2 = (RecyclerView) findViewById(R.id.rcv_list2);

        list = new ArrayList<MainActivity.Data>();
        for (int i = 0; i < 20; i++) {
            MainActivity.Data data = new MainActivity.Data();
            data.value = "Daemon data " + i;
            data.type = 0;

            list.add(data);
            //一种判断标示 可以根据实际需求来做标识
            if (i % 5 == 0) {
                data.type = 1;
            }
        }

        myAdpater = new MyAdpater(list, this);
        dRecyclerViewAdapter = new DRecyclerViewAdapter(myAdpater);

        staggeLayoutManager = new DStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeLayoutManager.setSpanSizeLookup(new DSpanSizeLookup(dRecyclerViewAdapter, staggeLayoutManager.getSpanCount()));

        rcv_list2.setLayoutManager(staggeLayoutManager);

        addHeadViews();
        addFootviews();
        addRandomView();

        rcv_list2.setAdapter(dRecyclerViewAdapter);

        rcv_list2.addOnScrollListener(new DRecyclerViewScrollListener() {
            @Override
            public void onLoadNextPage(RecyclerView view) {
                Toast.makeText(Main2Activity.this, "底部", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 添加全部的Views 中间插入的广告栏 等等
     */
    private void addRandomView() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).type == 1) {
                View view = LayoutInflater.from(this).inflate(R.layout.random_item, rcv_list2, false);
                dRecyclerViewAdapter.addRandomView(view, i);
            }
        }
    }

    /**
     * add HeadView
     */
    private void addHeadViews() {
        View head = LayoutInflater.from(this).inflate(R.layout.head, rcv_list2, false);
        View head1 = LayoutInflater.from(this).inflate(R.layout.head, rcv_list2, false);
        dRecyclerViewAdapter.addHeadView(head);
        dRecyclerViewAdapter.addHeadView(head1);

    }

    /**
     * add Footview
     */
    private void addFootviews() {
        View foot = LayoutInflater.from(this).inflate(R.layout.foot, rcv_list2, false);
        View foot1 = LayoutInflater.from(this).inflate(R.layout.foot, rcv_list2, false);

        dRecyclerViewAdapter.addFootView(foot);
        dRecyclerViewAdapter.addFootView(foot1);
    }

    /**
     * 继承封装过的DBaseRecyclerViewAdapter
     */
    class MyAdpater extends DBaseRecyclerViewAdapter<MainActivity.Data> {

        public MyAdpater(List<MainActivity.Data> mDatas, Context mContext) {
            super(mDatas, mContext);
        }

        @Override
        protected DBaseRecyclerViewHolder onCreateViewHolder1(ViewGroup parent, int viewType) {
            return new MyViewHoder(parent, R.layout.item, this);
        }

    }


    /**
     * 继承封装过的 DBaseRecyclerViewHolder
     */
    class MyViewHoder extends DBaseRecyclerViewHolder<MainActivity.Data> implements View.OnClickListener {

        private TextView tv_content;


        public MyViewHoder(ViewGroup parent, @LayoutRes int res, DBaseRecyclerViewAdapter dBaseRecyclerViewAdapter) {
            super(parent, res, dBaseRecyclerViewAdapter);
            tv_content = $(R.id.tv_content);

            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(MainActivity.Data data, int position) {


            ViewGroup.LayoutParams layoutParams = tv_content.getLayoutParams();

            Log.e("Staage", position + "");
            if (position % 2 == 0) {
                layoutParams.height = 200;
            } else {
                layoutParams.height = 70;
            }


            tv_content.setText(data.value);
        }


        @Override
        public void onClick(View v) {
            if (getOnClickItemListsner() != null) {
                getOnClickItemListsner().onClick(getAdapterItemPosition());
            }
        }
    }

}
