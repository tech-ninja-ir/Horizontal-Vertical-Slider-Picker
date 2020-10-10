package com.example.nbtk.slider.java;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

class SliderLayoutManagerJava extends LinearLayoutManager {

    public OnItemSelectedListener callback;
    private RecyclerView recyclerView;

    public SliderLayoutManagerJava(Context context) {
        super(context);
    }

    public SliderLayoutManagerJava(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public SliderLayoutManagerJava(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(orientation);
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        recyclerView = view;

        // Smart snapping
        new LinearSnapHelper().attachToRecyclerView(recyclerView);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        scaleDownView();
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getOrientation() == LinearLayoutManager.HORIZONTAL) {
            int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
            scaleDownView();
            return scrolled;
        } else {
            return 0;
        }
    }

    private void scaleDownView() {
        double mid = getWidth() / 2.0f;
        for (int i = 0; i < getChildCount(); i++) {

            // Calculating the distance of the child from the center
            View child = getChildAt(i);
            double childMid = (getDecoratedLeft(child) + getDecoratedRight(child)) / 2.0f;
            double distanceFromCenter = Math.abs(mid - childMid);

            // The scaling formula
            double scale = 1 - Math.sqrt((distanceFromCenter / getWidth())) * 0.66f;

            // Set scale to view
            child.setScaleX((float) scale);
            child.setScaleY((float) scale);
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        // When scroll stops we notify on the selected item
        if (state == RecyclerView.SCROLL_STATE_IDLE) {

            // Find the closest child to the recyclerView center --> this is the selected item.
            int recyclerViewCenterX = getRecyclerViewCenterX();
            int minDistance = recyclerView.getWidth();
            int position = -1;
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                View child = recyclerView.getChildAt(i);
                int childCenterX = getDecoratedLeft(child) + (getDecoratedRight(child) - getDecoratedLeft(child)) / 2;
                int newDistance = Math.abs(childCenterX - recyclerViewCenterX);
                if (newDistance < minDistance) {
                    minDistance = newDistance;
                    position = recyclerView.getChildLayoutPosition(child);
                }
            }

            // Notify on item selection
            callback.onItemSelected(position);
        }
    }

    private int getRecyclerViewCenterX() {
        return (recyclerView.getRight() - recyclerView.getLeft()) / 2 + recyclerView.getLeft();
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int layoutPosition);
    }
}
