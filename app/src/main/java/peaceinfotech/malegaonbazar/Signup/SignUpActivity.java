package peaceinfotech.malegaonbazar.Signup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.Signup.RetrofitModel.SendOTPModel;
import peaceinfotech.malegaonbazar.Signup.RetrofitModel.VerifyOTPModel;
import peaceinfotech.malegaonbazar.StartUI.LoginActivity;
import peaceinfotech.malegaonbazar.StartUI.SelectionActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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

            BackRight = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.back_right);
            layotp.setAnimation(BackRight);
            layotp.setVisibility(View.INVISIBLE);

            BackLeft = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.back_left);
            layMobile.setVisibility(View.VISIBLE);
            layMobile.setAnimation(BackLeft);

            submitButtonClicked=false;

        }
        else {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        }
    }

    public void sendOTP (String mobile){
        SaveSharedPreference.setMobile(SignUpActivity.this,mobile);
        ApiUtils.getServiceClass().sendOTP(mobile).enqueue(new Callback<SendOTPModel>() {
            @Override
            public void onResponse(Call<SendOTPModel> call, Response<SendOTPModel> response) {
                if (response.isSuccessful()) {
                    Log.d("sendotp", response.body().getOtp() + "/" + response.body().getReponse() + "/" + response.body().getMessage() + "/" + response.body().getMobile());
                    if(response.body().getReponse().equalsIgnoreCase("success")){
                        SaveSharedPreference.setOTP(SignUpActivity.this,response.body().getOtp());

                        LeftSwipe = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.left_swipe);
                        layMobile.startAnimation(LeftSwipe);
                        layMobile.setVisibility(View.INVISIBLE);

                        RightSwipe = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.right_swipe);
                        layotp.setVisibility(View.VISIBLE);
                        layotp.setAnimation(RightSwipe);
                    }
                    else if(response.body().getReponse().equalsIgnoreCase("failed")){
                        Toast.makeText(SignUpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<SendOTPModel> call, Throwable t) {

            }
        });
    }

    public void VerifyOTp(){


        ApiUtils.getServiceClass().verifyOTP(mob,SaveSharedPreference.getOTP(SignUpActivity.this),otp).enqueue(new Callback<VerifyOTPModel>() {
            @Override
            public void onResponse(Call<VerifyOTPModel> call, Response<VerifyOTPModel> response) {

                if(response.isSuccessful()) {
                    Log.d("verify", "onResponse: " + response.body().getReponse() + "/" + response.body().getMessage());
                    if(response.body().getReponse().equalsIgnoreCase("success")) {

                        Intent in = new Intent(SignUpActivity.this, SelectionActivity.class);
                        startActivity(in);
                        finish();
                    }
                    else if(response.body().getReponse().equalsIgnoreCase("failed")){
                        Toast.makeText(SignUpActivity.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<VerifyOTPModel> call, Throwable t) {
                Log.d("failure",t.toString());
            }
        });
    }



}
