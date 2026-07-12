package madproject.deepaks3533898.fitnessstepscounter

import android.content.Context



object AppUserData {

    private const val PREF_NAME = "FITNESS_TESTER_PREFS"
    private const val KEY_LOGIN_STATUS = "FITNESS_TESTER_LOGIN_STATUS"
    private const val DATA_STATUS = "DATA_LOGIN_STATUS"
    private const val KEY_USER_NAME = "FITNESS_TESTER_USERNAME"
    private const val KEY_USER_EMAIL = "FITNESS_TESTER_USEREMAIL"

    fun saveLoginStatus(context: Context, isLoggedIn: Boolean) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_LOGIN_STATUS, isLoggedIn).apply()
    }

    fun getLoginStatus(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_LOGIN_STATUS, false)
    }

    fun saveUserName(context: Context, name: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_USER_NAME, name).apply()
    }

    fun getUserName(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_USER_NAME, "") ?: ""
    }

    fun saveUserEmail(context: Context, email: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_USER_EMAIL, email).apply()
    }

    fun getUserEmail(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_USER_EMAIL, "") ?: ""
    }


    fun saveDataGeneratedStatus(context: Context, isLoggedIn: Boolean) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(DATA_STATUS, isLoggedIn).apply()
    }

    fun getDataGeneratedStatus(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(DATA_STATUS, false)
    }
}
