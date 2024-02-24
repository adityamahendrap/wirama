package com.wirama.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import com.wirama.R

class Example2Fragment : ListFragment() {

    private var titles: Array<String> = arrayOf(
        "Learn Android",
        "Java",
        "Kotlin"
    )

    private var descriptions: Array<String> = arrayOf(
        "Hello World",
        "HAHAHA",
        "999"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_example2, container, false)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, titles)

        val listView: ListView = view.findViewById(android.R.id.list)
        listView.adapter = adapter

        return view
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        var fragment1: ExampleFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.exampleFragment_1) as ExampleFragment
        fragment1.change(titles[position], "Version: ${descriptions[position]}")
    }

}