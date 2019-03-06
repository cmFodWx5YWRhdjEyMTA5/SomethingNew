package peaceinfotech.malegaonbazar;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;


public class SaveSharedPreference {

    final public static String LOGGED_IN_PREF="logged_in";

    final public static String KEY_TITLE="title";
    final public static String KEY_DESC="desc";
    final public static String KEY_MIN="min";
    final public static String KEY_MAX="max";

    public static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     */
    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }

    public static void setNewOffers(Context context,String title,String desc,String min,String max) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(KEY_TITLE,title);
        editor.putString(KEY_DESC,desc);
        editor.putString(KEY_MIN,min);
        editor.putString(KEY_MAX,max);
        editor.apply();
    }

    public static List<String> getNewOffers(Context context){

        List list = new ArrayList<>();

        list.add(getPreferences(context).getString(KEY_TITLE,""));
        list.add(getPreferences(context).getString(KEY_DESC,""));
        list.add(getPreferences(context).getString(KEY_MIN,""));
        list.add(getPreferences(context).getString(KEY_MAX,""));

        return list;
    }




}

