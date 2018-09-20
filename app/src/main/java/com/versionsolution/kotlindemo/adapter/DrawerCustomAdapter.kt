package com.kotlin.kotlinprojectbase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.kotlin.kotlinprojectbase.model.ItemObject
import com.versionsolution.kotlindemo.R

internal class DrawerCustomAdapter(context: Context, private val listStorage: List<ItemObject>) : BaseAdapter() {

    private val lInflater: LayoutInflater

    init {
        lInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return listStorage.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val listViewHolder: ViewHolder

        if (convertView == null) {
            listViewHolder = ViewHolder()
            convertView = lInflater.inflate(R.layout.layout_drawer_listview, parent, false)
            listViewHolder.textInListView = convertView!!.findViewById(R.id.textView) as TextView
            listViewHolder.imageInListView = convertView.findViewById(R.id.imageView) as ImageView
            listViewHolder.view_line = convertView.findViewById(R.id.view_line) as View

            convertView.tag = listViewHolder
        } else {
            listViewHolder = convertView.tag as ViewHolder
        }
        if (!listStorage[position].radio!! && position < 3) {
            listViewHolder.textInListView!!.setText(listStorage[position].name)
            listViewHolder.imageInListView!!.setImageResource(listStorage[position].imageId)

        } else if (listStorage[position].radio!! && position == 0) {
            listViewHolder.imageInListView!!.setImageResource(listStorage[position].imageId)
            listViewHolder.textInListView!!.visibility = View.INVISIBLE

        } else {
            listViewHolder.textInListView!!.setText(listStorage[position].name)
            listViewHolder.imageInListView!!.setImageResource(listStorage[position].imageId)

        }

        return convertView
    }

    private class ViewHolder {
        internal var textInListView: TextView? = null
        internal var imageInListView: ImageView? = null
        internal var view_line: View? = null

    }
}