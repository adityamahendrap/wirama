package com.wirama.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wirama.R

class ExampleFragment : Fragment() {
    private lateinit var titleTV: TextView
    private lateinit var descriptionTV: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_example, container, false)

        titleTV = view.findViewById(R.id.title)
        descriptionTV = view.findViewById(R.id.description)

        return view
    }

    public fun change(title: String, description: String) {
        titleTV.text = title
        descriptionTV.text = description
    }
}