package com.wirama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wirama.fragment.Example2Fragment
import com.wirama.fragment.ExampleFragment

class ExampleFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_fragment)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.exampleFragment_1, ExampleFragment())
            .commit()
    }
}