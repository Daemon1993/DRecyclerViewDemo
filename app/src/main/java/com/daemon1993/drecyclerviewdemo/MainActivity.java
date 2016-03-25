package com.daemon1993.drecyclerviewdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DRecyclerViewAdapter dRecyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Button bt_linear;
    private Button bt_grid;
    private Button bt_stagge;
    private MyAdpater myAdpater;
    private GridLayoutManager gridLayoutManager;

    private DStaggeredGridLayoutManager staggeLayoutManager;



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_linear:


                if (linearLayoutManager == null) {
                    linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                }
                rcv_list.setLayoutManager(linearLayoutManager);
                dRecyclerViewAdapter.notifyDataSetChanged();

                rcv_list.addOnScrollListener(new DRecyclerViewScrollListener() {
                    @Override
                    public void onLoadNextPage(RecyclerView view) {
                        Toast.makeText(MainActivity.this,"底部",Toast.LENGTH_SHORT).show();
                    }
                });

                break;

            case R.id.bt_grid:

                if (gridLayoutManager == null) {
                    gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                    gridLayoutManager.setSpanSizeLookup(new DSpanSizeLookup(dRecyclerViewAdapter, gridLayoutManager.getSpanCount()));
                }
                rcv_list.setLayoutManager(gridLayoutManager);
                dRecyclerViewAdapter.notifyDataSetChanged();
                rcv_list.addOnScrollListener(new DRecyclerViewScrollListener() {
                    @Override
                    public void onLoadNextPage(RecyclerView view) {
                        Toast.makeText(MainActivity.this,"底部",Toast.LENGTH_SHORT).show();
                    }
                });

                break;

            case R.id.bt_stagge:


                Intent intent=new Intent(MainActivity.this,Main2Activity.class);

                startActivity(intent);


                break;

        }
    }

    private RecyclerView rcv_list;
    private List<Data> list;

    public static class Data{
        public String value;
        public int type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcv_list = (RecyclerView) findViewById(R.id.rcv_list);

        bt_linear = (Button) findViewById(R.id.bt_linear);
        bt_grid = (Button) findViewById(R.id.bt_grid);
        bt_stagge = (Button) findViewById(R.id.bt_stagge);


        list = new ArrayList<Data>();


        bt_linear.setOnClickListener(this);
        bt_grid.setOnClickListener(this);
        bt_stagge.setOnClickListener(this);
        for (int i = 0; i < 20; i++) {
            Data data=new Data();
            data.value="Daemon data " + i;
            data.type=0;

            list.add(data);
            //一种判断标示 可以根据实际需求来做标识
            if (i % 5 == 0) {
                 data.type=1;
            }
        }


        myAdpater = new MyAdpater(list, this);
        dRecyclerViewAdapter = new DRecyclerViewAdapter(myAdpater);

        linearLayoutManager = new LinearLayoutManager(MainActivity.this);

        rcv_list.setLayoutManager(linearLayoutManager);

        addHeadViews();
        addFootviews();
        addRandomView();

        myAdpater.setOnClickItemListsner(new DBaseRecyclerViewAdapter.OnClickItemListsner() {
            @Override
            public void onClick(int poisiton) {
                Toast.makeText(MainActivity.this, poisiton + "", Toast.LENGTH_SHORT).show();
            }
        });



        rcv_list.setAdapter(dRecyclerViewAdapter);

        rcv_list.addOnScrollListener(new DRecyclerViewScrollListener() {
            @Override
            public void onLoadNextPage(RecyclerView view) {
                Toast.makeText(MainActivity.this,"底部",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 添加全部的Views 中间插入的广告栏 等等
     */
    private void addRandomView() {
        for(int i=0 ; i<list.size();i++){
            if(list.get(i).type==1){
                View view = LayoutInflater.from(this).inflate(R.layout.random_item, rcv_list, false);
                dRecyclerViewAdapter.addRandomView(view,i);
            }
        }
    }

    /**
     * add HeadView
     */
    private void addHeadViews() {
        View head = LayoutInflater.from(this).inflate(R.layout.head, rcv_list, false);
        View head1 = LayoutInflater.from(this).inflate(R.layout.head, rcv_list, false);
        dRecyclerViewAdapter.addHeadView(head);
        dRecyclerViewAdapter.addHeadView(head1);

    }

    /**
     * add Footview
     */
    private void addFootviews() {
        View foot = LayoutInflater.from(this).inflate(R.layout.foot, rcv_list, false);
        View foot1 = LayoutInflater.from(this).inflate(R.layout.foot, rcv_list, false);

        dRecyclerViewAdapter.addFootView(foot);
        dRecyclerViewAdapter.addFootView(foot1);
    }

    /**
     * 继承封装过的DBaseRecyclerViewAdapter
     */
     class MyAdpater extends DBaseRecyclerViewAdapter<Data> {

        public MyAdpater(List<Data> mDatas, Context mContext) {
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
    class MyViewHoder extends DBaseRecyclerViewHolder<Data> implements View.OnClickListener {

        private TextView tv_content;


        public MyViewHoder(ViewGroup parent, @LayoutRes int res, DBaseRecyclerViewAdapter dBaseRecyclerViewAdapter) {
            super(parent, res, dBaseRecyclerViewAdapter);
            tv_content = $(R.id.tv_content);

            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Data data, int position) {

            //每次默认初始化 因为Stagge会改变高度
            ViewGroup.LayoutParams layoutParams1 = tv_content.getLayoutParams();
            layoutParams1.height = 80;
            tv_content.setLayoutParams(layoutParams1);

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
