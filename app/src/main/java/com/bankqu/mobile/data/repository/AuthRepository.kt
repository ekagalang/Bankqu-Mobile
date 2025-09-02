package com.bankqu.mobile.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.bankqu.mobile.data.api.ApiClient
import com.bankqu.mobile.data.model.*
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AuthRepository(private val context: Context) {

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences("bankqu_prefs", Context.MODE_PRIVATE)

    private val gson = Gson()

    // Authentication state
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: Flow<Boolean> = _isLoggedIn

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: Flow<User?> = _currentUser

    init {
        // Check if user is already logged in
        loadSavedAuth()
    }

    suspend fun login(email: String, password: String): Result<AuthData> {
        return try {
            val request = LoginRequest(email, password)
            val response = ApiClient.apiService.login(request)

            if (response.isSuccessful && response.body()?.success == true) {
                val authData = response.body()!!.data!!

                // Save auth data
                saveAuthData(authData)

                // Update API client with token
                ApiClient.setAuthToken(authData.access_token)

                // Update state
                _currentUser.value = authData.user
                _isLoggedIn.value = true

                Result.success(authData)
            } else {
                val errorMsg = response.body()?.message ?: "Login failed"
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(name: String, email: String, password: String): Result<AuthData> {
        return try {
            val request = RegisterRequest(name, email, password, password)
            val response = ApiClient.apiService.register(request)

            if (response.isSuccessful && response.body()?.success == true) {
                val authData = response.body()!!.data!!

                // Save auth data
                saveAuthData(authData)

                // Update API client with token
                ApiClient.setAuthToken(authData.access_token)

                // Update state
                _currentUser.value = authData.user
                _isLoggedIn.value = true

                Result.success(authData)
            } else {
                val errorMsg = response.body()?.message ?: "Registration failed"
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout(): Result<Boolean> {
        return try {
            // Call API logout
            ApiClient.apiService.logout()

            // Clear local data
            clearAuthData()

            // Update state
            _currentUser.value = null
            _isLoggedIn.value = false

            Result.success(true)
        } catch (e: Exception) {
            // Even if API call fails, clear local data
            clearAuthData()
            _currentUser.value = null
            _isLoggedIn.value = false

            Result.success(true)
        }
    }

    private fun saveAuthData(authData: AuthData) {
        with(sharedPrefs.edit()) {
            putString("access_token", authData.access_token)
            putString("user_data", gson.toJson(authData.user))
            putBoolean("is_logged_in", true)
            apply()
        }
    }

    private fun loadSavedAuth() {
        val token = sharedPrefs.getString("access_token", null)
        val userJson = sharedPrefs.getString("user_data", null)
        val isLoggedIn = sharedPrefs.getBoolean("is_logged_in", false)

        if (isLoggedIn && token != null && userJson != null) {
            try {
                val user = gson.fromJson(userJson, User::class.java)
                ApiClient.setAuthToken(token)
                _currentUser.value = user
                _isLoggedIn.value = true
            } catch (e: Exception) {
                // Clear corrupted data
                clearAuthData()
            }
        }
    }

    private fun clearAuthData() {
        with(sharedPrefs.edit()) {
            remove("access_token")
            remove("user_data")
            remove("is_logged_in")
            apply()
        }
        ApiClient.setAuthToken(null)
    }
}