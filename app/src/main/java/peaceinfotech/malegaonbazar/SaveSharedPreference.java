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
    final public static String KEY_OTP="otp";
    final public static String KEY_MOBILE="mobile";

    //Role Data
    final public static String KEY_ROLE_NAME="role_name";
    final public static String KEY_ROLE_ID="role_id";

    //User Profile
    final public static String KEY_USER_ID = "user_id";
    final public static String KEY_USER_NAME = "user_name";
    final public static String KEY_USER_LOCATION = "user_location";
    final public static String KEY_USER_MOBILE = "user_mobile";
    final public static String KEY_USER_REFER_ID = "user_reference_id";

    //Vendor Profile
    final public static String KEY_VENDOR_ID = "vendor_id";
    final public static String KEY_VENDOR_NAME = "vendor_name";
    final public static String KEY_VENDOR_LOCATION = "vendor_location";
    final public static String KEY_VENDOR_MOBILE = "vendor_mobile";
    final public static String KEY_VENDOR_REFER_ID = "vendor_reference_id";



    public static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

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

    public static void setOTP(Context context,int otp){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putInt(KEY_OTP,otp);
        editor.apply();
    }

    public static  int getOTP(Context context){
        return  getPreferences(context).getInt(KEY_OTP,0);
    }

    public static void setMobile(Context context,String mob){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(KEY_MOBILE,mob);
        editor.apply();
    }

    public static String getMobile(Context context){
        return getPreferences(context).getString(KEY_MOBILE,"");
    }

    public static void setVendorProfileData(Context context,String roleName ){


    }




    public static void setUserProfileData(Context context,String roleName ){

    }

}

