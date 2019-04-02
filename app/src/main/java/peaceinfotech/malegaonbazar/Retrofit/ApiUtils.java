package peaceinfotech.malegaonbazar.Retrofit;

public class ApiUtils {

    private static final String BASE_URL = "http://autoreplyz.com/Malegaon/Api/Userapi/";


    public static RetrofitInterface getServiceClass(){
        return RetrofitApi.getRetrofit(BASE_URL).create(RetrofitInterface.class);
    }
}
