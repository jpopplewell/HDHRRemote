package popplewell.email.hdremote;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by john on 2/6/16.
 */
public class SettingsPreferences {
    private static final String PREF_SETTINGS = "settings";

    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_SETTINGS, null);
    }

    public static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SETTINGS, query)
                .apply();
    }

}
