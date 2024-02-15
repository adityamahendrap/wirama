package com.wirama

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.wirama.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("APP", "Main activity started")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // handle logout
        binding.logoutButton.setOnClickListener {
            Log.i("ACTION", "Logout button clicked")
            this.firebaseLogout()
        }
    }

    private fun firebaseLogout() {
        Log.i("AUTH", "Trying to logout...")
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        Log.i("AUTH","Logout success, redirecting to Login activity")
        Toast.makeText(this, "Logout suceess", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}