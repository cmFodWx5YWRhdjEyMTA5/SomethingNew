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
    final public static String KEY_USERREF="userref";
    final public static String KEY_VENREF="venref";
    final public static String KEY_FIRST_TIME="first_time";

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

        list.add(0,getPreferences(context).getString(KEY_TITLE,""));
        list.add(1,getPreferences(context).getString(KEY_DESC,""));
        list.add(2,getPreferences(context).getString(KEY_MIN,""));
        list.add(3,getPreferences(context).getString(KEY_MAX,""));

        return list;
    }


    public static void  setUserReference (Context context,String string){
        SharedPreferences.Editor editor=getPreferences(context).edit();
        editor.putString(KEY_USERREF,string);
        editor.apply();
    }

    public static String getUserReference(Context context){
        return getPreferences(context).getString(KEY_USERREF,"nothing");
    }

    public static void setVendorReference(Context context,String string){
        SharedPreferences.Editor editor=getPreferences(context).edit();
        editor.putString(KEY_VENREF,string);
        editor.apply();
    }

    public static String getVendorReference(Context context){
        return getPreferences(context).getString(KEY_VENREF,"nothing");
    }


    public static void setFirstTimeLaunch(Context context,Boolean put){
        SharedPreferences.Editor editor=getPreferences(context).edit();
        editor.putBoolean(KEY_FIRST_TIME,put);
        editor.apply();
    }

    public static boolean getFirstTimeLaunch(Context context){
        return getPreferences(context).getBoolean(KEY_FIRST_TIME,false);
    }


}

