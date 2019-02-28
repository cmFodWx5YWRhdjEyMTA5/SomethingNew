package peaceinfotech.malegaonbazar.Signup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SignUpActivity extends AppCompatActivity {

    TextView warnmobile,warnotp;
    EditText mobile;
    Button sendotp,veruser,vervendor;
    Pinview pinview;
    Animation RightSwipe,LeftSwipe,BackRight,BackLeft;
    LinearLayout layMobile,layotp;
    Boolean submitButtonClicked=false;
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_signup);

        mobile=findViewById(R.id.etmobile);
        warnmobile=findViewById(R.id.tvwmobile);
        warnotp=findViewById(R.id.warnotp);
        sendotp=findViewById(R.id.btotp);
        layMobile=findViewById(R.id.laymobile);
        layotp=findViewById(R.id.layotp);
        pinview=findViewById(R.id.pinview);
        veruser=findViewById(R.id.btveruser);
        vervendor=findViewById(R.id.btverven);



        BackLeft = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.back_left);



        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mob=mobile.getText().toString();


                if(mob.isEmpty()||mob.length()!=10){
                    warnmobile.setVisibility(View.VISIBLE);
                }
                else
                {
                    if(warnmobile.getVisibility()==View.VISIBLE){
                        warnmobile.setVisibility(View.GONE);

                        LeftSwipe = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.left_swipe);
                        layMobile.startAnimation(LeftSwipe);
                        layMobile.setVisibility(View.GONE);

                        RightSwipe = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.right_swipe);
                        layotp.setVisibility(View.VISIBLE);
                        layotp.setAnimation(RightSwipe);

                        submitButtonClicked=true;
                    }
                    else{

                        LeftSwipe = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.left_swipe);
                        layMobile.startAnimation(LeftSwipe);
                        layMobile.setVisibility(View.GONE);

                        RightSwipe = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.right_swipe);
                        layotp.setVisibility(View.VISIBLE);
                        layotp.setAnimation(RightSwipe);

                        submitButtonClicked=true;
                    }
                }
            }
        });

        veruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otp = pinview.getValue();
                type = "user";
                if (otp.isEmpty() || otp.length() != 4) {
//                    Toast.makeText(SignUpActivity.this,"Plese Enter the Correct Otp",Toast.LENGTH_LONG).show();
                    warnotp.setVisibility(View.VISIBLE);
                }
                else {

                    if (warnotp.getVisibility() == View.VISIBLE) {
                        warnotp.setVisibility(View.GONE);
                        Intent in = new Intent(SignUpActivity.this, RegisterActivity.class);
                        in.putExtra("type", type);
                        startActivity(in);
                        finish();
                    }
                    else{
                        Intent in = new Intent(SignUpActivity.this, RegisterActivity.class);
                        in.putExtra("type", type);
                        startActivity(in);
                        finish();
                    }
                }
            }
        });

        vervendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otp=pinview.getValue();
                type="vendor";
                if (otp.isEmpty()||otp.length()!=4)
                {
                   // Toast.makeText(SignUpActivity.this,"Plese Enter the Correct Otp",Toast.LENGTH_LONG).show();
                    warnotp.setVisibility(View.VISIBLE);

                }
                else{
                    if (warnotp.getVisibility() == View.VISIBLE) {
                        warnotp.setVisibility(View.GONE);
                        Intent in = new Intent(SignUpActivity.this, RegisterActivity.class);
                        in.putExtra("type", type);
                        startActivity(in);
                        finish();
                    }
                    else{
                        Intent in = new Intent(SignUpActivity.this, RegisterActivity.class);
                        in.putExtra("type", type);
                        startActivity(in);
                        finish();
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
            layotp.setVisibility(View.GONE);

            BackLeft = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.back_left);
            layMobile.setVisibility(View.VISIBLE);
            layMobile.setAnimation(BackLeft);

            submitButtonClicked=false;

        }
        else {
            super.onBackPressed();
        }
    }
}
