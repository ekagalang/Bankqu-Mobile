package com.bankqu.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bankqu.mobile.data.repository.AuthRepository
import com.bankqu.mobile.databinding.ActivityMainBinding
import com.bankqu.mobile.ui.auth.LoginActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authRepository = AuthRepository(this)

        checkAuthentication()
        setupUI()
    }

    private fun checkAuthentication() {
        lifecycleScope.launch {
            authRepository.isLoggedIn.collect { isLoggedIn ->
                if (!isLoggedIn) {
                    navigateToLogin()
                }
            }
        }
    }

    private fun setupUI() {
        // Display user info
        lifecycleScope.launch {
            authRepository.currentUser.collect { user ->
                user?.let {
                    binding.tvWelcome.text = "Welcome, ${it.name}!"
                    binding.tvEmail.text = it.email
                }
            }
        }

        // Logout button
        binding.btnLogout.setOnClickListener {
            performLogout()
        }
    }

    private fun performLogout() {
        lifecycleScope.launch {
            authRepository.logout()
            Toast.makeText(this@MainActivity, "Logged out successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}