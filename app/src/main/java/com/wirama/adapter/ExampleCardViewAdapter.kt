package com.wirama.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wirama.R
import com.wirama.model.ExampleCardViewModel
import com.bumptech.glide.Glide

class ExampleCardViewAdapter(
    private var context: Context,
    private var model: List<ExampleCardViewModel>
): RecyclerView.Adapter<ExampleCardViewAdapter.ExampleViewHolder>() {

    inner class ExampleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.title)
        var thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        var itemView: View = LayoutInflater.from(parent.context)
                            .inflate(R.layout.example_card_layout, parent, false)
        return ExampleViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return model.size
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        var model: ExampleCardViewModel = model[position]
        holder.title.text = model.getName()

//        Glide.with(context)
//            .load(model.getThumbnail())
//            .into(holder.thumbnail)
    }
}