package nam.poly.bangiay_app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Simple wrapper managing user login state.
 * Replace with a more advanced solution when backend is ready.
 */
public class SessionManager {

    private static final String PREF_NAME = "BanGiaySession";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setLoggedIn(boolean isLoggedIn) {
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void clearSession() {
        prefs.edit().clear().apply();
    }
}

