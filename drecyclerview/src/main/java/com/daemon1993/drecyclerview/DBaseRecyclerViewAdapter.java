package com.daemon1993.drecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Daemon1993 on 16/3/24.
 */
public abstract class DBaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<DBaseRecyclerViewHolder> {

    private List<T> mDatas;
    private Context mContext;


    /**
     * 默认点击监听事件
     */
    public interface OnClickItemListsner{
        void onClick(int poisiton);
    }

    OnClickItemListsner onClickItemListsner;

    public void setOnClickItemListsner(OnClickItemListsner onClickItemListsner) {
        this.onClickItemListsner = onClickItemListsner;
    }

    public OnClickItemListsner getOnClickItemListsner() {
        return onClickItemListsner;
    }

    private DRecyclerViewAdapter mDRecyclerViewAdapter;

    public DRecyclerViewAdapter getmDRecyclerViewAdapter() {
        return mDRecyclerViewAdapter;
    }

    public void setDRecyclerViewAdapter(DRecyclerViewAdapter mDRecyclerViewAdapter) {
        this.mDRecyclerViewAdapter = mDRecyclerViewAdapter;
    }



    private DRecyclerViewAdapter dRecyclerViewAdapter;



    public DBaseRecyclerViewAdapter(List<T> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    @Override
    public DBaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder1(parent,viewType);
    }

    protected abstract DBaseRecyclerViewHolder onCreateViewHolder1(ViewGroup parent, int viewType);


    @Override
    public void onBindViewHolder(DBaseRecyclerViewHolder holder, int position) {
        holder.setData(mDatas.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }



    public boolean isHeader(int position) {
        return getHeaderViewsCount() > 0 && position < getHeaderViewsCount();
    }


    public boolean isFooter(int position) {
        int lastPosition = getItemCount() - 1;
        return getFooterViewsCount() > 0 && position > lastPosition - getFooterViewsCount() && position <= lastPosition;
    }

    private int getFooterViewsCount() {
        return mDRecyclerViewAdapter.getFootSize();
    }

    public int getHeaderViewsCount() {
        return mDRecyclerViewAdapter.getHeadSize();
    }
}
