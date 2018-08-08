package pk.aspirasoft.tasbih.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

class Database {
    private static Database ourInstance;
    private final SharedPreferences preferences;

    private Database(Activity activity) {
        this.preferences = activity.getSharedPreferences("counters", Context.MODE_PRIVATE);
    }

    static Database getInstance(Activity activity) throws NullPointerException {
        if (ourInstance == null) {
            if (activity == null)
                throw new NullPointerException();

            ourInstance = new Database(activity);
        }

        return ourInstance;
    }

    void initialize() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isInitialized", true);
        editor.apply();
    }

    boolean isInitialized() {
        return preferences.getBoolean("isInitialized", false);
    }

    void put(String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("counters", value);
        editor.apply();
    }

    String get() {
        return preferences.getString("counters", null);
    }
}