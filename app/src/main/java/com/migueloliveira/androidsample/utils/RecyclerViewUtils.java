package com.migueloliveira.androidsample.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import com.migueloliveira.androidsample.GridAutofitLayoutManager;
import com.migueloliveira.androidsample.LayoutManagerType;

/**
 * Created by migueloliveira on 19/10/2016.
 */

public class RecyclerViewUtils {

    public static LayoutManagerType setRecyclerViewLayoutManager(Context context, RecyclerView recyclerView, LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        if (recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                recyclerView.setLayoutManager(new GridAutofitLayoutManager(context, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 114,
                        context.getResources().getDisplayMetrics())));
                recyclerView.scrollToPosition(scrollPosition);
                return LayoutManagerType.GRID_LAYOUT_MANAGER;
            case LINEAR_LAYOUT_MANAGER:
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.scrollToPosition(scrollPosition);
                return LayoutManagerType.LINEAR_LAYOUT_MANAGER;
            default:
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.scrollToPosition(scrollPosition);
                return LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }
    }
}
