package peaceinfotech.malegaonbazar.StartUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.RetrofitModel.LogInModel;
import peaceinfotech.malegaonbazar.Signup.RegisterActivity;
import peaceinfotech.malegaonbazar.Signup.SignUpActivity;
import peaceinfotech.malegaonbazar.User.UI.UserActivity;
import peaceinfotech.malegaonbazar.Vendor.Fragment.FragmentProfile;
import peaceinfotech.malegaonbazar.Vendor.UI.VendorActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etmob,etpass;
    TextView warn,tvSignUp,tvForgetPass;
    Button btsignin;
    ProgressBar pbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        etmob=findViewById(R.id.etmob);
        etpass=findViewById(R.id.etpass);
        btsignin=findViewById(R.id.btsignin);
        tvSignUp=findViewById(R.id.tv_signup);
        tvForgetPass=findViewById(R.id.tv_forget_pass);
        warn=findViewById(R.id.tvwarn);
        pbLogin=findViewById(R.id.progress_in_login);

        if(SaveSharedPreference.getLoggedStatus(getApplicationContext())){
            if(SaveSharedPreference.getRole(LoginActivity.this).equalsIgnoreCase("user")){
                startActivity(new Intent(LoginActivity.this,UserActivity.class));
                finish();
            }
            else if(SaveSharedPreference.getRole(LoginActivity.this).equalsIgnoreCase("vendor")){
                startActivity(new Intent(LoginActivity.this, VendorActivity.class));
                finish();
            }
        }

        btsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile=etmob.getText().toString();
                String pass=etpass.getText().toString();

                if(mobile.isEmpty()||pass.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Please Enter Both the Field",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(mobile.length()!=10)
                    {
                        warn.setVisibility(View.VISIBLE);
                    }
                    else {
                        if(warn.getVisibility()==View.VISIBLE){
                            warn.setVisibility(View.GONE);
                            btsignin.setVisibility(View.GONE);
                            LogIn(mobile,pass);
                        }
                        else {
                            LogIn(mobile,pass);
                        }
                    }
                }
//                SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
//                startActivity(new Intent(LoginActivity.this,UserActivity.class));
//                finish();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent putIntent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(putIntent);
                finish();

//                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//                finish();
            }
        });

        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,PasswordOTPVerification.class));
                finish();
            }
        });

    }

    public void LogIn (final String mobile, final String password){


       ApiUtils.getServiceClass().logIn(mobile,password).enqueue(new Callback<LogInModel>() {
           @Override
           public void onResponse(Call<LogInModel> call, Response<LogInModel> response) {
               if(response.isSuccessful())
               {
                   Log.d("login",response.body().getResponse());
                   if(response.body().getResponse().equalsIgnoreCase("success")){

                       if(response.body().getDetailsModels().getRoleName().equalsIgnoreCase("user")){

                           Toast.makeText(LoginActivity.this, "This Service is Not Yet Available", Toast.LENGTH_SHORT).show();
                           SaveSharedPreference.setUserMobilePassword(LoginActivity.this,mobile,password);
                           SaveSharedPreference.setRole(LoginActivity.this,
                                   response.body().getDetailsModels().getRoleID(),
                                   response.body().getDetailsModels().getRoleName());

                           SaveSharedPreference.setUserProfileData(LoginActivity.this,
                                   response.body().getDetailsModels().getUserId(),
                                   response.body().getDetailsModels().getFullName(),
                                   response.body().getDetailsModels().getLocation(),
                                   response.body().getDetailsModels().getCity(),
                                   response.body().getDetailsModels().getMobile(),
                                   response.body().getDetailsModels().getReferenceId(),
                                   response.body().getDetailsModels().getProfileUrl());

                           SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                           startActivity(new Intent(LoginActivity.this, UserActivity.class));
                           finish();
                       }
                       else if(response.body().getDetailsModels().getRoleName().equalsIgnoreCase("vendor")){
                           SaveSharedPreference.setRole(LoginActivity.this,
                                   response.body().getDetailsModels().getRoleID(),
                                   response.body().getDetailsModels().getRoleName());

                           SaveSharedPreference.setMobileAndPassword(LoginActivity.this,mobile,password);

                           SaveSharedPreference.setVendorProfileData(LoginActivity.this,
                                   response.body().getDetailsModels().getVendorId(),
                                   response.body().getDetailsModels().getFullName(),
                                   response.body().getDetailsModels().getLocation(),
                                   response.body().getDetailsModels().getMobile(),
                                   response.body().getDetailsModels().getBrand(),
                                   response.body().getDetailsModels().getImgLogoURL(),
                                   response.body().getDetailsModels().getImgBanURL(),
                                   response.body().getDetailsModels().getEmail(),
                                   response.body().getDetailsModels().getCatName(),
                                   response.body().getDetailsModels().getCatID(),
                                   response.body().getDetailsModels().getState(),
                                   response.body().getDetailsModels().getCity());

                           SaveSharedPreference.setVendorLatLng(LoginActivity.this,
                                   response.body().getDetailsModels().getLat(),
                                   response.body().getDetailsModels().getLng());

                           Log.d("idemail", "onResponse: "+response.body().getDetailsModels().getEmail()+" / "+response.body().getDetailsModels().getCity()+" / "+response.body().getDetailsModels().getState());

                           SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                           Intent putIntent = new Intent(LoginActivity.this, VendorActivity.class);
                           startActivity(putIntent);
                           finish();
                       }
                   }
                   else if(response.body().getResponse().equalsIgnoreCase("failed")){
                       btsignin.setVisibility(View.VISIBLE);
                       Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                   }
               }
           }

           @Override
           public void onFailure(Call<LogInModel> call, Throwable t) {
               btsignin.setVisibility(View.VISIBLE);

               Log.d("loginfail", "onFailure: "+t);
           }
       });


    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to Exit");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=builder.create();

        alertDialog.show();

    }

}
