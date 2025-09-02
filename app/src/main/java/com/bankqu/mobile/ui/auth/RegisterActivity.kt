package com.bankqu.mobile.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bankqu.mobile.MainActivity
import com.bankqu.mobile.data.repository.AuthRepository
import com.bankqu.mobile.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authRepository = AuthRepository(this)

        setupUI()
    }

    private fun setupUI() {
        binding.btnRegister.setOnClickListener {
            performRegister()
        }

        binding.tvLogin.setOnClickListener {
            finish() // Go back to login
        }
    }

    private fun performRegister() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        // Validation
        if (name.isEmpty()) {
            binding.etName.error = "Name required"
            return
        }

        if (email.isEmpty()) {
            binding.etEmail.error = "Email required"
            return
        }

        if (password.isEmpty()) {
            binding.etPassword.error = "Password required"
            return
        }

        if (password != confirmPassword) {
            binding.etConfirmPassword.error = "Passwords don't match"
            return
        }

        if (password.length < 6) {
            binding.etPassword.error = "Password must be at least 6 characters"
            return
        }

        // Show loading
        binding.btnRegister.isEnabled = false
        binding.btnRegister.text = "Registering..."

        // Perform register
        lifecycleScope.launch {
            val result = authRepository.register(name, email, password)

            // Hide loading
            binding.btnRegister.isEnabled = true
            binding.btnRegister.text = "Register"

            result.fold(
                onSuccess = { authData ->
                    Toast.makeText(this@RegisterActivity,
                        "Registration successful! Welcome, ${authData.user.name}!",
                        Toast.LENGTH_SHORT).show()
                    navigateToMain()
                },
                onFailure = { error ->
                    Toast.makeText(this@RegisterActivity,
                        "Registration failed: ${error.message}", Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}