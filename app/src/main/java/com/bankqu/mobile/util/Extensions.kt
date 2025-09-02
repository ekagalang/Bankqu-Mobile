// util/Extensions.kt
package com.bankqu.mobile.util

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

// Context Extensions
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

// Fragment Extensions
fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    requireContext().showToast(message, duration)
}

// String Extensions
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

// Double Extensions
fun Double.toCurrency(): String {
    return "Rp ${String.format("%,.0f", this)}"
}