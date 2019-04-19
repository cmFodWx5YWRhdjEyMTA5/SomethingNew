package peaceinfotech.malegaonbazar;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
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
    final public static String KEY_USER_CITY = "user_city";
    final public static String KEY_USER_REFER_ID = "user_reference_id";
    final public static String KEY_USER_PASSWORD = "user_password";
    final public static String KEY_USER_PROFILE = "user_profile";


    //Vendor Profile
    final public static String KEY_VENDOR_PASSWORD = "vendor_pass";
    final public static String KEY_VENDOR_ID = "vendor_id";
    final public static String KEY_VENDOR_NAME = "vendor_name";
    final public static String KEY_VENDOR_LOCATION = "vendor_location";
    final public static String KEY_VENDOR_MOBILE = "vendor_mobile";
    final public static String KEY_VENDOR_BRAND = "vendor_brand";
    final public static String KEY_VENDOR_IMGLOGO = "vendor_img_logo";
    final public static String KEY_VENDOR_IMGBAN = "vendor_img_ban";
    final public static String KEY_VENDOR_CATNAME = "vendor_catName";
    final public static String KEY_VENDOR_CATID = "vendor_email";
    final public static String KEY_VENDOR_STATE = "vendor_state";
    final public static String KEY_VENDOR_CITY = "vendor_city";
    final public static String Key_vendor_email="email";
    final public static String KEY_VENDOR_LAT="vendor_latitude";
    final public static String KEY_VENDOR_LNG="vendor_longitude";





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

    public static void setPasswordMobile(Context context,String mob){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(KEY_MOBILE,mob);
        editor.apply();
    }

    public static String getMobile(Context context){
        return getPreferences(context).getString(KEY_MOBILE,"");
    }

    public static void setUserMobilePassword(Context context,String mobile,String password){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(KEY_USER_MOBILE,mobile);
        editor.putString(KEY_USER_PASSWORD,password);
        editor.apply();
    }

    public static List<String> getUserMobilePassword(Context context){
        List<String> data = new ArrayList<>();

        data.add(0,getPreferences(context).getString(KEY_USER_MOBILE,""));
        data.add(1,getPreferences(context).getString(KEY_USER_PASSWORD,""));

        return data;
    }

    public static void setMobileAndPassword(Context context,String mobile,String password){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(KEY_VENDOR_MOBILE,mobile);
        editor.putString(KEY_VENDOR_PASSWORD,password);
        editor.apply();
    }

    public static List<String> getMobileAndPassword(Context context){
        List<String> data = new ArrayList<>();

        data.add(0,getPreferences(context).getString(KEY_VENDOR_MOBILE,""));
        data.add(1,getPreferences(context).getString(KEY_VENDOR_PASSWORD,""));

        return data;
    }

    public static void setRole(Context context,String roleId,String roleName){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(KEY_ROLE_ID,roleId);
        editor.putString(KEY_ROLE_NAME,roleName);
        editor.apply();
    }

    public static String getRole (Context context){
        return getPreferences(context).getString(KEY_ROLE_NAME,"");
    }

    public static void setUserProfileData(Context context,String id,String name,String location,String city,String mobile,String referenceId,String progImgUrl){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(KEY_USER_ID,id);
        editor.putString(KEY_USER_NAME,name);
        editor.putString(KEY_USER_LOCATION,location);
        editor.putString(KEY_USER_CITY,city);
        editor.putString(KEY_USER_MOBILE,mobile);
        editor.putString(KEY_USER_REFER_ID,referenceId);
        editor.putString(KEY_USER_PROFILE,progImgUrl);
        editor.apply();
    }

    public static List<String> getUserProfileData(Context context){
        List<String> data = new ArrayList<>();

        data.add(0,getPreferences(context).getString(KEY_USER_ID,""));
        data.add(1,getPreferences(context).getString(KEY_USER_NAME,""));
        data.add(2,getPreferences(context).getString(KEY_USER_LOCATION,""));
        data.add(3,getPreferences(context).getString(KEY_USER_CITY,""));
        data.add(4,getPreferences(context).getString(KEY_USER_MOBILE,""));
        data.add(5,getPreferences(context).getString(KEY_USER_REFER_ID,""));
        data.add(6,getPreferences(context).getString(KEY_USER_PROFILE,""));
        return data;
    }


    public static void setVendorProfileData(Context context,
                                            String id,
                                            String name,
                                            String location,
                                            String mobile,
                                            String brand,
                                            String imgLogo,
                                            String imgBan,
                                            String email,
                                            String catName,
                                            String catId,
                                            String state,
                                            String city){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(KEY_VENDOR_ID,id);
        editor.putString(KEY_VENDOR_NAME,name);
        editor.putString(KEY_VENDOR_LOCATION,location);
        editor.putString(KEY_VENDOR_MOBILE,mobile);
        editor.putString(KEY_VENDOR_BRAND,brand);
        editor.putString(KEY_VENDOR_IMGLOGO,imgLogo);
        editor.putString(KEY_VENDOR_IMGBAN,imgBan);
        editor.putString(Key_vendor_email,email);
        editor.putString(KEY_VENDOR_CATNAME,catName);
        editor.putString(KEY_VENDOR_CATID,catId);
        editor.putString(KEY_VENDOR_STATE,state);
        editor.putString(KEY_VENDOR_CITY,city);
        editor.apply();

        Log.d("setshare", "setVendorProfileData: "+email);
    }

    public static void setVendorLatLng (Context context,String lat,String lng){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(KEY_VENDOR_LAT,lat);
        editor.putString(KEY_VENDOR_LNG,lng);
        editor.apply();
    }

    public static List<String> getVendorLatLng(Context context){
        List<String> data = new ArrayList<>();

        data.add(0,SaveSharedPreference.getPreferences(context).getString(KEY_VENDOR_LAT,""));
        data.add(1,SaveSharedPreference.getPreferences(context).getString(KEY_VENDOR_LNG,""));

        return data;
    }

    public static List<String> getVendorProfileData(Context context){
        List<String> data = new ArrayList<>();

       data.add(0,SaveSharedPreference.getPreferences(context).getString(KEY_VENDOR_ID,""));
       data.add(1,SaveSharedPreference.getPreferences(context).getString(KEY_VENDOR_NAME,""));
       data.add(2,SaveSharedPreference.getPreferences(context).getString(KEY_VENDOR_LOCATION,""));
       data.add(3,SaveSharedPreference.getPreferences(context).getString(KEY_VENDOR_MOBILE,""));
       data.add(4,SaveSharedPreference.getPreferences(context).getString(KEY_VENDOR_BRAND,""));
       data.add(5,SaveSharedPreference.getPreferences(context).getString(KEY_VENDOR_CATNAME,""));
       data.add(6,SaveSharedPreference.getPreferences(context).getString(Key_vendor_email,""));
       data.add(7,SaveSharedPreference.getPreferences(context).getString(KEY_VENDOR_CATID,""));
       data.add(8,SaveSharedPreference.getPreferences(context).getString(KEY_VENDOR_IMGLOGO,""));
       data.add(9,SaveSharedPreference.getPreferences(context).getString(KEY_VENDOR_IMGBAN,""));
       data.add(10,SaveSharedPreference.getPreferences(context).getString(KEY_VENDOR_STATE,""));
       data.add(11,SaveSharedPreference.getPreferences(context).getString(KEY_VENDOR_CITY,""));

        Log.d("setshare1", "getVendorProfileData: "+SaveSharedPreference.getPreferences(context).getString(Key_vendor_email,""));


        return data;

    }

    public static String getKeyVendorId(Context context){
        return getPreferences(context).getString(KEY_VENDOR_ID,"");
    }

    public static void changePassword(Context context,String password){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(KEY_VENDOR_PASSWORD,password);
        editor.apply();
    }


}

