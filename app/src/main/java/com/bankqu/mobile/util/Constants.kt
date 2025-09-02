// util/Constants.kt
package com.bankqu.mobile.util

object Constants {
    // API Configuration
    const val BASE_URL = "http://10.0.2.2:8000/api/"
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L

    // SharedPreferences Keys
    const val PREF_NAME = "bankqu_prefs"
    const val PREF_ACCESS_TOKEN = "access_token"
    const val PREF_USER_DATA = "user_data"
    const val PREF_IS_LOGGED_IN = "is_logged_in"

    // Database
    const val DATABASE_NAME = "bankqu_database"
    const val DATABASE_VERSION = 1

    // Date Formats
    const val DATE_FORMAT_API = "yyyy-MM-dd"
    const val DATE_FORMAT_DISPLAY = "dd MMM yyyy"
    const val DATETIME_FORMAT_API = "yyyy-MM-dd HH:mm:ss"
}