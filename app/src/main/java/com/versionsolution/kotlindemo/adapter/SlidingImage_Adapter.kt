package com.worldbestcompany.wbc.adapter

import android.app.Activity
import android.content.Context
import android.os.Parcelable
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.view.PagerAdapter
import android.view.View
import android.widget.ImageView
import com.versionsolution.kotlindemo.R


class SlidingImage_Adapter(val context: Context, private val IMAGES: ArrayList<Int>) : PagerAdapter() {
    private val inflater: LayoutInflater


    init {
        inflater = LayoutInflater.from(context)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return IMAGES.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false)!!

        val imageView = imageLayout!!
                .findViewById(R.id.image) as ImageView


        imageView.setImageResource(IMAGES[position])

        view.addView(imageLayout, 0)


        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view.equals(`object`)
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }


}
