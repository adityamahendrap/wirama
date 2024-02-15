package com.wirama

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseUser

class ProfileActivity : AppCompatActivity() {
    lateinit var logoutBtn: Button
    lateinit var userName: TextView
    lateinit var userEmail: TextView
    lateinit var userId: TextView
    lateinit var profileImage: ImageView

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        logoutBtn = findViewById<Button>(R.id.logoutBtn)
        userName = findViewById<TextView>(R.id.name)
        userEmail = findViewById<TextView>(R.id.email)
        userId = findViewById<TextView>(R.id.userId)
        profileImage = findViewById<ImageView>(R.id.profileImage)

        auth = FirebaseAuth.getInstance()
        googleSignInClient = GoogleSignIn.getClient(this, this.createGso())

        logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            googleSignInClient.signOut().addOnCompleteListener {
                goToLoginPage()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUI(currentUser)
        } else {
            goToLoginPage()
        }
    }

    private fun createGso(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }

    private fun updateUI(user: FirebaseUser) {
        userName.text = user.displayName
        userEmail.text = user.email
        userId.text = user.uid
        Glide.with(this).load(user.photoUrl).into(profileImage)
    }

    private fun goToLoginPage() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}