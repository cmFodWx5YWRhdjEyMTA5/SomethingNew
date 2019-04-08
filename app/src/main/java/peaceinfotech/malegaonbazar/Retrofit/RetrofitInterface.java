package peaceinfotech.malegaonbazar.Retrofit;

import peaceinfotech.malegaonbazar.RetrofitModel.AddServiceModel;
import peaceinfotech.malegaonbazar.RetrofitModel.CategoriesHomeModel;
import peaceinfotech.malegaonbazar.RetrofitModel.CityListModel;
import peaceinfotech.malegaonbazar.RetrofitModel.ForgetSendOtpModel;
import peaceinfotech.malegaonbazar.RetrofitModel.LogInModel;
import peaceinfotech.malegaonbazar.RetrofitModel.OfferRetroListModel;
import peaceinfotech.malegaonbazar.RetrofitModel.ResponseMessageModel;
import peaceinfotech.malegaonbazar.RetrofitModel.SendOTPModel;
import peaceinfotech.malegaonbazar.RetrofitModel.ServiceHomeModel;
import peaceinfotech.malegaonbazar.RetrofitModel.StateListModel;
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
    @POST("forgetsendotp/")
    Call<ForgetSendOtpModel> forgetSendOTP(@Field("mobile")String mobile);

    @FormUrlEncoded
    @POST("forgetotpverify/")
    Call<VerifyOTPModel> forgetVerifyOTP(@Field("mobile")String mobile,
                                   @Field("ActualOtp")int orgOTP,
                                   @Field("EnteredOtp")String entOTP);

    @FormUrlEncoded
    @POST(("passwordchange/"))
    Call<ResponseMessageModel> passwordChange(@Field("mobile")String mobile,
                                              @Field("password")String password);

    @FormUrlEncoded
    @POST("usersignup/")
    Call<UserRegisterModel> userRegister(@Field("Roleid")String roleId,
                                         @Field("Fullname")String fullName,
                                         @Field("Location")String location,
                                         @Field("Mobile")String mobile,
                                         @Field("Password")String password);


    @GET("category/")
    Call<CategoriesHomeModel> categoriesRegister();

    @GET("states/")
    Call<StateListModel> stateListRegister();

    @GET("cities/")
    Call<CityListModel> cityListRegister(@Query("state_name")String stateName);


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

    @GET("offers/")
    Call<OfferRetroListModel> getOffers(@Query("vendor_id")String id);

    @FormUrlEncoded
    @POST("addoffer/")
    Call<ResponseMessageModel> addOffers(@Field("vendor_id")String vendorId,
                                         @Field("title")String offerTile,
                                         @Field("description")String offerDescription,
                                         @Field("product_price")String offerPrice,
                                         @Field("min_trans")String offerMinTrans,
                                         @Field("max_trans")String offerMaxTrans,
                                         @Field("start_date")String offerStartDate,
                                         @Field("end_date")String offerEndDate,
                                         @Field("termcondition")String offerTermCond,
                                         @Field("offertype")String offerType,
                                         @Field("discount")String offerDiscount);

    @FormUrlEncoded
    @POST("updateoffers/")
    Call<ResponseMessageModel> editOffers(@Field("offerid")String offerId,
                                          @Field("title")String offerTile,
                                          @Field("description")String offerDescription,
                                          @Field("product_price")String offerPrice,
                                          @Field("min_trans")String offerMinTrans,
                                          @Field("max_trans")String offerMaxTrans,
                                          @Field("start_date")String offerStartDate,
                                          @Field("end_date")String offerEndDate,
                                          @Field("termcondition")String offerTermCond,
                                          @Field("offertype")String offerType,
                                          @Field("discount")String offerDiscount);


    @FormUrlEncoded
    @POST("deleteoffers/")
    Call<ResponseMessageModel> deleteOffers(@Field("offerid")String offerId);

    @FormUrlEncoded
    @POST("changepassword/")
    Call<ResponseMessageModel> changePassword(@Field("mobile")String mobile,@Field("OldPassword")String oldPass,@Field("NewPassword")String newPass);

}

