package uio.androidbootcamp.moviesapp.model.repositories

import android.content.Context
import uio.androidbootcamp.moviesapp.R


class SharedPreferencesRepository(val context: Context) {

    fun saveStringInSharedPreferences(key: String, value: String) {
        val sharedPref = context.getSharedPreferences(
                context.resources.getString(R.string.package_name), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringFromSharedPreferences(key: String): String {
        val sharedPref = context.getSharedPreferences(
                context.resources.getString(R.string.package_name), Context.MODE_PRIVATE)
        return sharedPref.getString(key, "")
    }
}