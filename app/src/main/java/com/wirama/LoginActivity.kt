package com.wirama

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.GoogleAuthProvider
import com.wirama.databinding.ActivityLoginBinding
import com.wirama.helper.ViewHelper

class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var signInButton: SignInButton
    private lateinit var googleApiClient: GoogleApiClient
    private val RC_SIGN_IN = 1
    lateinit var name: String
    lateinit var email:kotlin.String
    lateinit var idToken: String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("APP", "Login activity started")
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // handle login
        auth = FirebaseAuth.getInstance()
        binding.LoginButton.setOnClickListener {
            Log.i("ACTION", "Login button clicked")
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()) {
                binding.emailInput.error = "Please fill your correct email"
                binding.emailInput.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()) {
                binding.passwordInput.error = "Please fill your password"
                binding.passwordInput.requestFocus()
                return@setOnClickListener
            }

            this.firebaseLoginWithEmailAndPassword(email, password)
        }

        // handle login google
        firebaseAuth = FirebaseAuth.getInstance()
        // this is where we start the Auth state Listener to listen for whether the user is signed in or not
        authStateListener = AuthStateListener { firebaseAuth ->
            // Get signedIn user
            val user = firebaseAuth.currentUser

            //if user is signed in, we call a helper method to save the user details to Firebase
            if (user != null) {
                // User is signed in
                // you could place other firebase code
                // logic to save the user details to Firebase
                Log.d("AUTH", "onAuthStateChanged:signed_in:" + user.uid)
            } else {
                // User is signed out
                Log.d("AUTH", "onAuthStateChanged:signed_out")
            }
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id)) //you can also use R.string.default_web_client_id
            .requestEmail()
            .build()
        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
        signInButton = findViewById<SignInButton>(R.id.googleOauthButton)
        signInButton.setOnClickListener(View.OnClickListener {
            val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient!!)
            startActivityForResult(intent, RC_SIGN_IN)
        })

        // link to login page
        binding.toRegisterPageButton.setOnClickListener {
            Log.i("ACTION", "Register activity link clicked")
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        // underline
        ViewHelper.setUnderlineTextView(this, R.id.toForgotPasswordPageButton)
        ViewHelper.setUnderlineTextView(this, R.id.toRegisterPageButton)

        // change oauth button text
        ViewHelper.setGooglePlusButtonText(signInButton, "Continue with Google")
        signInButton.getChildAt(0)?.let {
            val smaller = Math.min(it.paddingLeft, it.paddingRight)
            it.setPadding(smaller, it.paddingTop, smaller, it.paddingBottom)
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(
                data!!
            )
            handleSignInResult(result)
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult?) {
        if (result!!.isSuccess) {
            val account = result.signInAccount
            idToken = account!!.idToken.toString()
            name = account.displayName.toString()
            email = account.email.toString()
            // you can store user data to SharedPreference
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            firebaseAuthWithGoogle(credential)
        } else {
            // Google Sign In failed, update UI appropriately
            Log.e("AUTH", "Login Unsuccessful. $result")
            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(credential: AuthCredential) {
        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(
                this
            ) { task ->
                Log.d("AUTH", "signInWithCredential:onComplete:" + task.isSuccessful)
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    gotoProfile()
                } else {
                    Log.w("AUTH", "signInWithCredential" + task.exception!!.message)
                    task.exception!!.printStackTrace()
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun gotoProfile() {
        val intent: Intent = Intent(this, ProfileActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        if (authStateListener != null) {
            FirebaseAuth.getInstance().signOut()
        }
        firebaseAuth!!.addAuthStateListener(authStateListener!!)
    }

    override fun onStop() {
        super.onStop()
        if (authStateListener != null) {
            firebaseAuth!!.removeAuthStateListener(authStateListener!!)
        }
    }

    private fun firebaseLoginWithEmailAndPassword(email: String, password: String) {
        Log.i("AUTH", "Trying to logged in with email...")
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if(it.isSuccessful) {
                    Log.i("AUTH", "Login with email success")
                    Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
                    gotoProfile()
                } else {
                    Log.e("AUTH", "Login with email failed: ${it.exception?.message}")
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}