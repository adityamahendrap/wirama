package com.wirama.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.wirama.R

class ExamplaAdapter(private var ctx: Context): BaseAdapter() {

    private var images: Array<Int> = arrayOf(
        R.drawable.logo,
        R.drawable.logo2
    )

    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return images[position].toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        lateinit var imageView: ImageView

        if(convertView == null) {
            imageView = ImageView(ctx)
            imageView.apply {
//                layoutParams = GridView.LayoutParams(80, 80)
                layoutParams = ViewGroup.LayoutParams(500, 500)
                scaleType = ImageView.ScaleType.CENTER_CROP
                setPadding(8, 8, 8, 8)
            }
        } else {
            imageView = convertView as ImageView
        }

        imageView.setImageResource(images[position])

        return imageView
    }
}

