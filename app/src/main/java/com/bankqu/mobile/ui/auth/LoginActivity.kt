package com.bankqu.mobile.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bankqu.mobile.MainActivity
import com.bankqu.mobile.data.repository.AuthRepository
import com.bankqu.mobile.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authRepository = AuthRepository(this)

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        binding.btnLogin.setOnClickListener {
            performLogin()
        }

        binding.tvRegister.setOnClickListener {
            // Navigate to register screen
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Pre-fill with test credentials for development
        binding.etEmail.setText("admin@bankqu.com")
        binding.etPassword.setText("admin123")
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            authRepository.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    navigateToMain()
                }
            }
        }
    }

    private fun performLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()

        // Validation
        if (email.isEmpty()) {
            binding.etEmail.error = "Email required"
            return
        }

        if (password.isEmpty()) {
            binding.etPassword.error = "Password required"
            return
        }

        // Show loading
        binding.btnLogin.isEnabled = false
        binding.btnLogin.text = "Logging in..."

        // Perform login
        lifecycleScope.launch {
            val result = authRepository.login(email, password)

            // Hide loading
            binding.btnLogin.isEnabled = true
            binding.btnLogin.text = "Login"

            result.fold(
                onSuccess = { authData ->
                    Toast.makeText(this@LoginActivity,
                        "Welcome, ${authData.user.name}!", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                },
                onFailure = { error ->
                    Toast.makeText(this@LoginActivity,
                        "Login failed: ${error.message}", Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}