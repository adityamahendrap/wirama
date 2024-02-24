package com.wirama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wirama.adapter.ExampleRecyclerViewAdapter
import com.wirama.model.ExampleRecyclerViewModel

class ExampleRecyclerViewActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ExampleRecyclerViewAdapter
    var modelList: MutableList<ExampleRecyclerViewModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_recycler_view)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = ExampleRecyclerViewAdapter(modelList)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager

        recyclerView.itemAnimator = DefaultItemAnimator()

        recyclerView.adapter = adapter

        this.insertData()
    }

    private fun insertData() {
        var model1 = ExampleRecyclerViewModel("Bakemonogatari", "2008")
        var model2 = ExampleRecyclerViewModel("Kizumonogatari", "20015")
        modelList.add(model1)
        modelList.add(model2)

        adapter.notifyDataSetChanged()
    }
}