package pk.aspirasoft.tasbih.models

import android.content.Context
import android.content.SharedPreferences

object Database {
    private lateinit var preferences: SharedPreferences

    private var isInitialized: Boolean = false

    fun init(context: Context) {
        this.preferences = context.getSharedPreferences("counters", Context.MODE_PRIVATE)
        this.isInitialized = true
    }

    @Throws(RuntimeException::class)
    fun put(key: String, value: String?) {
        if (!isInitialized) throw RuntimeException("Ensure call to Database.init() before first use.")

        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    @Throws(RuntimeException::class)
    fun get(key: String): String? {
        if (!isInitialized) throw RuntimeException("Ensure call to Database.init() before first use.")

        return preferences.getString(key, null)
    }
}