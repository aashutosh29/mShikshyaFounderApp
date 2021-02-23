package com.bihanitech.shikshyaprasasak.utility;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.LinkedList;
import java.util.Queue;

/**
 * See https://gist.github.com/tvkkpt/2e667cb707859123b422
 *
 * @param <VH>
 */
public abstract class RecycledPagerAdapter<VH extends RecycledPagerAdapter.ViewHolder> extends PagerAdapter {

    Queue<VH> destroyedItems = new LinkedList<>();

    @Override
    public final Object instantiateItem(ViewGroup container, int position) {
        VH viewHolder = destroyedItems.poll();

        if (viewHolder != null) {
            // Re-add existing view before rendering so that we can make change inside getView()
            container.addView(viewHolder.itemView);
            onBindViewHolder(viewHolder, position);
        } else {
            viewHolder = onCreateViewHolder(container);
            onBindViewHolder(viewHolder, position);
            container.addView(viewHolder.itemView);
        }

        return viewHolder;
    }

    @Override
    public final void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((VH) object).itemView);
        destroyedItems.add((VH) object);
    }

    @Override
    public final boolean isViewFromObject(View view, Object object) {
        return ((VH) object).itemView == view;
    }

    /**
     * Create a new view holder
     *
     * @param parent
     * @return view holder
     */
    public abstract VH onCreateViewHolder(ViewGroup parent);

    /**
     * Bind data at position into viewHolder
     *
     * @param viewHolder
     * @param position
     */
    public abstract void onBindViewHolder(VH viewHolder, int position);

    public static abstract class ViewHolder {
        final View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }
}
