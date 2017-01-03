package se.ericwenn.reseplaneraren.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ericwenn on 8/16/16.
 */
public class SimpleRecyclerViewDivider extends RecyclerView.ItemDecoration {
    private final int mVerticalSpaceHeight;

    public SimpleRecyclerViewDivider(int mVerticalSpaceHeight) {
        this.mVerticalSpaceHeight = mVerticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = mVerticalSpaceHeight;
    }
}
