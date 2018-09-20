package com.kotlin.kotlinprojectbase.utilities

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ListView
import android.widget.ScrollView

internal class ScrollableViewHelper {

    /**
     * Returns the current scroll position of the scrollable view. If this method returns zero or
     * less, it means at the scrollable view is in a position such as the panel should handle
     * scrolling. If the method returns anything above zero, then the panel will let the scrollable
     * view handle the scrolling
     *
     * @param scrollableView the scrollable view
     * @param isSlidingUp whether or not the panel is sliding up or down
     * @return the scroll position
     */
    fun getScrollableViewScrollPosition(scrollableView: View?, isSlidingUp: Boolean): Int {
        if (scrollableView == null) return 0
        if (scrollableView is ScrollView) {
            if (isSlidingUp) {
                return scrollableView.scrollY
            } else {
                val sv = scrollableView as ScrollView?
                val child = sv!!.getChildAt(0)
                return child.bottom - (sv!!.getHeight() + sv!!.getScrollY())
            }
        } else if (scrollableView is ListView && scrollableView.childCount > 0) {
            val lv = scrollableView as ListView?
            if (lv!!.getAdapter() == null) return 0
            if (isSlidingUp) {
                val firstChild = lv!!.getChildAt(0)
                // Approximate the scroll position based on the top child and the first visible item
                return lv.getFirstVisiblePosition() * firstChild.height - firstChild.top
            } else {
                val lastChild = lv!!.getChildAt(lv.getChildCount() - 1)
                // Approximate the scroll position based on the bottom child and the last visible item
                return (lv.getAdapter().count - lv.getLastVisiblePosition() - 1) * lastChild.height + lastChild.bottom - lv.getBottom()
            }
        } else if (scrollableView is RecyclerView && scrollableView.childCount > 0) {
            val rv = scrollableView as RecyclerView?
            val lm = rv!!.getLayoutManager()
            if (rv.getAdapter() == null) return 0
            if (isSlidingUp) {
                val firstChild = rv.getChildAt(0)
                // Approximate the scroll position based on the top child and the first visible item
                return rv.getChildLayoutPosition(firstChild) * lm.getDecoratedMeasuredHeight(firstChild) - lm.getDecoratedTop(firstChild)
            } else {
                val lastChild = rv.getChildAt(rv.getChildCount() - 1)
                // Approximate the scroll position based on the bottom child and the last visible item
                return (rv.getAdapter().itemCount - 1) * lm.getDecoratedMeasuredHeight(lastChild) + lm.getDecoratedBottom(lastChild) - rv.getBottom()
            }
        } else {
            return 0
        }
    }
}
