package peaceinfotech.malegaonbazar.Retrofit;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import peaceinfotech.malegaonbazar.Signup.RetrofitModel.CategoriesListModel;
import peaceinfotech.malegaonbazar.Signup.RetrofitModel.HomeModel;
import peaceinfotech.malegaonbazar.Signup.RetrofitModel.SendOTPModel;
import peaceinfotech.malegaonbazar.Signup.RetrofitModel.UserRegisterModel;
import peaceinfotech.malegaonbazar.Signup.RetrofitModel.VendorRegisterModel;
import peaceinfotech.malegaonbazar.Signup.RetrofitModel.VerifyOTPModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitInterface {

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

    @Multipart
    @POST("vendorsignup/")
    Call<VendorRegisterModel> vendorRegister(@Part("Roleid")String roleId,
                                             @Part("Fullname")String fullName,
                                             @Part("Location")String location,
                                             @Part("Brand")String brand,
                                             @Part("Category")String category,
                                             @Part("EmailId")String emailId,
                                             @Part("Password")String password,
                                             @Part MultipartBody.Part logoFile,
                                             @Part("Logo") RequestBody logo,
                                             @Part MultipartBody.Part bannerFile,
                                             @Part("Banner") RequestBody banner);


    @GET("category/")
    Call<HomeModel> categoriesRegister();
}

