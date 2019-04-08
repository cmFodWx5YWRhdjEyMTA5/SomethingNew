package peaceinfotech.malegaonbazar.StartUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.RetrofitModel.ForgetSendOtpModel;
import peaceinfotech.malegaonbazar.RetrofitModel.SendOTPModel;
import peaceinfotech.malegaonbazar.RetrofitModel.VerifyOTPModel;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.Signup.RegisterActivity;
import peaceinfotech.malegaonbazar.Signup.SignUpActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordOTPVerification extends AppCompatActivity {

    TextView warnmobile,warnotp;
    EditText mobile;
    Button sendotp,btVerify;
    Pinview pinview;
    Animation RightSwipe,LeftSwipe,BackRight,BackLeft;
    LinearLayout layMobile,layotp;
    Boolean submitButtonClicked=false;
    String type;
    String mob,otp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_signup);

        mobile=findViewById(R.id.etmobile);
        warnmobile=findViewById(R.id.tvwmobile);
        warnotp=findViewById(R.id.warnotp);
        sendotp=findViewById(R.id.btotp);
        layMobile=findViewById(R.id.lay_mobile);
        layotp=findViewById(R.id.layotp);
        pinview=findViewById(R.id.pinview);
        btVerify=findViewById(R.id.btverify);



        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mob=mobile.getText().toString();


                if(mob.isEmpty()||mob.length()!=10){
                    warnmobile.setVisibility(View.VISIBLE);
                }
                else
                {
                    if(warnmobile.getVisibility()==View.VISIBLE){
                        warnmobile.setVisibility(View.GONE);

                        sendOTP(mob);

                        submitButtonClicked=true;
                    }
                    else{

                        sendOTP(mob);
                        submitButtonClicked=true;
                    }
                }
            }
        });


        btVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otp=pinview.getValue();

                if (otp.isEmpty()||otp.length()!=4)
                {
                    // Toast.makeText(SignUpActivity.this,"Plese Enter the Correct Otp",Toast.LENGTH_LONG).show();
                    warnotp.setVisibility(View.VISIBLE);

                }
                else{
                    if (warnotp.getVisibility() == View.VISIBLE) {
                        warnotp.setVisibility(View.GONE);

                        VerifyOTp();

                    }
                    else{
                        VerifyOTp();
                    }
                }
            }
        });
    }


    @Override
    public void onBackPressed() {

        if(submitButtonClicked) {

            BackRight = AnimationUtils.loadAnimation(PasswordOTPVerification.this, R.anim.back_right);
            layotp.setAnimation(BackRight);
            layotp.setVisibility(View.INVISIBLE);

            BackLeft = AnimationUtils.loadAnimation(PasswordOTPVerification.this, R.anim.back_left);
            layMobile.setVisibility(View.VISIBLE);
            layMobile.setAnimation(BackLeft);

            submitButtonClicked=false;

        }
        else {
            startActivity(new Intent(PasswordOTPVerification.this, LoginActivity.class));
            finish();
        }
    }

    public void sendOTP (String mobile){
        SaveSharedPreference.setPasswordMobile(PasswordOTPVerification.this,mobile);
        ApiUtils.getServiceClass().forgetSendOTP(mobile).enqueue(new Callback<ForgetSendOtpModel>() {
            @Override
            public void onResponse(Call<ForgetSendOtpModel> call, Response<ForgetSendOtpModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getReponse().equalsIgnoreCase("success")){
                        SaveSharedPreference.setOTP(PasswordOTPVerification.this,response.body().getOtp());
                        LeftSwipe = AnimationUtils.loadAnimation(PasswordOTPVerification.this, R.anim.left_swipe);
                        layMobile.startAnimation(LeftSwipe);
                        layMobile.setVisibility(View.INVISIBLE);

                        RightSwipe = AnimationUtils.loadAnimation(PasswordOTPVerification.this, R.anim.right_swipe);
                        layotp.setVisibility(View.VISIBLE);
                        layotp.setAnimation(RightSwipe);
                        Log.d("changeotp", "onResponse: "+response.body().getOtp());
                    }
                    else if(response.body().getReponse().equalsIgnoreCase("failed")){
                        Toast.makeText(PasswordOTPVerification.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgetSendOtpModel> call, Throwable t) {

            }
        });
    }



    public void VerifyOTp(){

        ApiUtils.getServiceClass().forgetVerifyOTP(mob,
                SaveSharedPreference.getOTP(PasswordOTPVerification.this),
                otp).enqueue(new Callback<VerifyOTPModel>() {
            @Override
            public void onResponse(Call<VerifyOTPModel> call, Response<VerifyOTPModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getReponse().equalsIgnoreCase("success")){

                        Intent in = new Intent(PasswordOTPVerification.this, ChangePasswordActivity.class);
                        startActivity(in);
                        finish();
                    }
                    else if(response.body().getReponse().equalsIgnoreCase("failed")){
                        Toast.makeText(PasswordOTPVerification.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<VerifyOTPModel> call, Throwable t) {

            }
        });
    }

}
