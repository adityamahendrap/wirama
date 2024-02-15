package com.wirama

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.wirama.databinding.ActivityInitialBinding

class InitialActivity : AppCompatActivity() {
    private var MIN_SPLASH_SCREEN_DURATION = 2000
    private var MAX_SPLASH_SCREEN_DURATION = 10000 // TODO: implement RTO displaying splash screen
    private lateinit var binding: ActivityInitialBinding
    private lateinit var auth: FirebaseAuth
    private var splashScreenStartTime: Long = 0
    private var splashScreenStopped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("STARTUP", "Application started")
        binding = ActivityInitialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Start the splash screen timer
        splashScreenStartTime = System.currentTimeMillis()
        Handler(Looper.getMainLooper()).postDelayed({
            if (!splashScreenStopped) {
                checkUserAuthentication()
            }
        }, MIN_SPLASH_SCREEN_DURATION.toLong())
    }

    private fun checkUserAuthentication() {
        Log.i("STARTUP", "Checking authentication")

        // TODO: set RTO and make error handling
        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        if (user != null) {
            Log.i("STARTUP", "Authenticated, redirecting to Main activity")
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        } else {
            Log.i("STARTUP", "Not authenticated, redirecting to Login activity with force logout")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        splashScreenStopped = true
    }
}