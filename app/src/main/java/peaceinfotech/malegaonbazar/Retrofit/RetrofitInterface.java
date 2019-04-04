package peaceinfotech.malegaonbazar.Retrofit;

import peaceinfotech.malegaonbazar.RetrofitModel.AddServiceModel;
import peaceinfotech.malegaonbazar.RetrofitModel.CategoriesHomeModel;
import peaceinfotech.malegaonbazar.RetrofitModel.LogInModel;
import peaceinfotech.malegaonbazar.RetrofitModel.OfferRetroListModel;
import peaceinfotech.malegaonbazar.RetrofitModel.ResponseMessageModel;
import peaceinfotech.malegaonbazar.RetrofitModel.SendOTPModel;
import peaceinfotech.malegaonbazar.RetrofitModel.ServiceHomeModel;
import peaceinfotech.malegaonbazar.RetrofitModel.UpdateServiceModel;
import peaceinfotech.malegaonbazar.RetrofitModel.UserRegisterModel;
import peaceinfotech.malegaonbazar.RetrofitModel.VerifyOTPModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {


    @FormUrlEncoded
    @POST("login/")
    Call<LogInModel> logIn(@Field("mobile")String mobile, @Field("password")String password);

    @FormUrlEncoded
    @POST("sendotp/")
    Call<SendOTPModel> sendOTP(@Field("mobile")String mobile);

    @FormUrlEncoded
    @POST("otpverify/")
    Call<VerifyOTPModel> verifyOTP(@Field("mobile")String mobile,
                                   @Field("ActualOtp")int orgOTP,
                                   @Field("EnteredOtp")String entOTP);

    @FormUrlEncoded
    @POST("usersignup/")
    Call<UserRegisterModel> userRegister(@Field("Roleid")String roleId,
                                         @Field("Fullname")String fullName,
                                         @Field("Location")String location,
                                         @Field("Mobile")String mobile,
                                         @Field("Password")String password);


    @GET("category/")
    Call<CategoriesHomeModel> categoriesRegister();

    @FormUrlEncoded
    @POST("addservice/")
    Call<AddServiceModel> addService(@Field("vendor_id")String id, @Field("service_name")String name,@Field("service_description")String desc);

    @GET("services/")
    Call<ServiceHomeModel> getServices(@Query("vendor_id")String id);

    @FormUrlEncoded
    @POST("updateservices/")
    Call<UpdateServiceModel> editServices(@Field("serviceid")String id,@Field("service_name")String name,@Field("service_description")String desc);

    @FormUrlEncoded
    @POST("deleteservices/")
    Call<ResponseMessageModel> deleteServices(@Field("serviceid")String id);

    @GET("addoffer/")
    Call<OfferRetroListModel> getOffers(@Query("vendor_id")String id);



}

