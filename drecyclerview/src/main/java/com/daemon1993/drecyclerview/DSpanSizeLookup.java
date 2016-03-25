package com.daemon1993.drecyclerview;

import android.support.v7.widget.GridLayoutManager;

/**
 * Created by Daemon on 2015/11/10.
 */
public class DSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private DRecyclerViewAdapter adapter;
    private int mSpanSize = 1;

    public DSpanSizeLookup(DRecyclerViewAdapter adapter, int spanSize) {
        this.adapter = adapter;
        this.mSpanSize = spanSize;
    }

    @Override
    public int getSpanSize(int position) {
        boolean isHeaderOrFooterOrRandom = adapter.isHeader(position) || adapter.isFooter(position)
        || adapter.isRandom(position);
        return isHeaderOrFooterOrRandom ? mSpanSize : 1;
    }
}