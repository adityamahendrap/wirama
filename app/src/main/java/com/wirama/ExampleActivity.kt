package com.wirama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import com.wirama.adapter.ExamplaAdapter

class ExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        val gridView: GridView = findViewById(R.id.exampleGrid)
        gridView.adapter = ExamplaAdapter(this)
        gridView.numColumns = 2
        gridView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, "clicked at $i", Toast.LENGTH_SHORT).show()
        }
    }
}