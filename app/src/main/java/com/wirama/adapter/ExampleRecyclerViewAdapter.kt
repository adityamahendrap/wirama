package com.wirama.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wirama.R
import com.wirama.model.ExampleRecyclerViewModel

class ExampleRecyclerViewAdapter(private var model: List<ExampleRecyclerViewModel>):
    RecyclerView.Adapter<ExampleRecyclerViewAdapter.ExampleViewHolder>() {

    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTV: TextView = itemView.findViewById(R.id.title)
        var dateTV: TextView = itemView.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
                            .inflate(R.layout.example_row, parent, false)
        return ExampleViewHolder((itemView))
    }

    override fun getItemCount(): Int {
        return model.size
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        var model: ExampleRecyclerViewModel = model[position]
        holder.titleTV.text = model.getTitle()
        holder.dateTV.text = model.getDate()
    }

}