package com.daemon1993.drecyclerview;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * 带上泛型的RecyclerView.ViewHolder 简化代码
 * Created by Daemon1993 on 16/3/24.
 */
public abstract class DBaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {


    public DBaseRecyclerViewAdapter dBaseRecyclerViewAdapter;
    public DRecyclerViewAdapter mDRecyclerViewAdapter;

    DBaseRecyclerViewAdapter.OnClickItemListsner onClickItemListsner;

    public DBaseRecyclerViewAdapter.OnClickItemListsner getOnClickItemListsner() {
        return onClickItemListsner;
    }


    public DBaseRecyclerViewHolder(View itemView,DBaseRecyclerViewAdapter dBaseRecyclerViewAdapter) {
        super(itemView);

        this.dBaseRecyclerViewAdapter=dBaseRecyclerViewAdapter;
        mDRecyclerViewAdapter=dBaseRecyclerViewAdapter.getmDRecyclerViewAdapter();
        onClickItemListsner=dBaseRecyclerViewAdapter.getOnClickItemListsner();
    }

    public DBaseRecyclerViewHolder(ViewGroup parent, @LayoutRes int res, DBaseRecyclerViewAdapter dBaseRecyclerViewAdapter) {

        super(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));

        mDRecyclerViewAdapter=dBaseRecyclerViewAdapter.getmDRecyclerViewAdapter();
        this.dBaseRecyclerViewAdapter=dBaseRecyclerViewAdapter;
        onClickItemListsner=dBaseRecyclerViewAdapter.getOnClickItemListsner();

    }

    protected <T extends View> T $(@IdRes int id) {
        return (T) itemView.findViewById(id);
    }

    public abstract void setData(T data,int position);



    /**
     * 处理有头部和尾部的情况 返回点击的位置
     * @return
     */
    /**
     * 获取点击的item的position
     * @return
     */
    public int getAdapterItemPosition() {
        int oldPosition =getAdapterPosition();

        if(dBaseRecyclerViewAdapter==null){
            return oldPosition;
        }

        if (dBaseRecyclerViewAdapter.isHeader(oldPosition) || dBaseRecyclerViewAdapter.isFooter(oldPosition)) {
            return -1;
        } else {
            return oldPosition - dBaseRecyclerViewAdapter.getHeaderViewsCount();
        }
    }

}
