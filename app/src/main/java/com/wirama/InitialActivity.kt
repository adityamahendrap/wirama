package com.wirama

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.wirama.databinding.ActivityInitialBinding

class InitialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInitialBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("STARTUP", "Application started")
        binding = ActivityInitialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i("STARTUP","Checking authentication")
        try {
            auth = FirebaseAuth.getInstance()
        } catch (e: Exception) {
            Log.e("STARTUP", "FirebaseAuth initialization failed", e)
        }

        val user = auth.currentUser

        if(user != null) {
            Log.i("STARTUP", "Authenticated, redirecting to Main activity")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Log.i("STARTUP", "Not authenticated, redirecting to Login activity")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}