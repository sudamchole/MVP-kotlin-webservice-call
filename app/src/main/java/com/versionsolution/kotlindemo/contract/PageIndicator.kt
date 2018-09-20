package com.kotlin.kotlinprojectbase.contract

import android.support.v4.view.ViewPager

interface PageIndicator : ViewPager.OnPageChangeListener {
    /**
     * Bind the indicator to a ViewPager.
     *
     * @param view
     */
    abstract fun setViewPager(view: ViewPager)

    /**
     * Bind the indicator to a ViewPager.
     *
     * @param view
     * @param initialPosition
     */
    abstract fun setViewPager(view: ViewPager, initialPosition: Int)

    /**
     *
     * Set the current page of both the ViewPager and indicator.
     *
     *
     * This **must** be used if you need to set the page before
     * the views are drawn on screen (e.g., default start page).
     *
     * @param item
     */
    abstract fun setCurrentItem(item: Int)

    /**
     * Set a page change listener which will receive forwarded events.
     *
     * @param listener
     */
    abstract fun setOnPageChangeListener(listener: ViewPager.OnPageChangeListener)

    /**
     * Notify the indicator that the fragment list has changed.
     */
    abstract fun notifyDataSetChanged()
}
