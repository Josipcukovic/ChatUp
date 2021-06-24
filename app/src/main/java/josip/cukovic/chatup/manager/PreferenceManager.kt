package josip.cukovic.chatup.manager

import android.content.Context
import josip.cukovic.chatup.ChatUpApplication

class PreferenceManager {
    companion object {
        const val PREFS_FILE = "ChatUpPreferences"
        const val PREFS_KEY_EMAIL = "email"
        const val PREFS_KEY_PASSWORD = "password"
    }

    fun saveEmail(email: String) {
        val sharedPreferences = ChatUpApplication.ApplicationContext.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(PREFS_KEY_EMAIL, email)
        editor.apply()
    }

    fun savePassword(password: String) {
        val sharedPreferences = ChatUpApplication.ApplicationContext.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(PREFS_KEY_PASSWORD, password)
        editor.apply()
    }

    fun retrieveEmail(): String {
        val sharedPreferences = ChatUpApplication.ApplicationContext.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        return sharedPreferences.getString(PREFS_KEY_EMAIL, "default")!!
    }

    fun retrievePassword(): String {
        val sharedPreferences = ChatUpApplication.ApplicationContext.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        return sharedPreferences.getString(PREFS_KEY_PASSWORD, "default")!!
    }
}