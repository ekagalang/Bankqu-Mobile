// BankQuApplication.kt
package com.bankqu.mobile

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BankQuApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}