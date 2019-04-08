package peaceinfotech.malegaonbazar.StartUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.RetrofitModel.ResponseMessageModel;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText etNewPass,etRePass;
    Button btChangePass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        etNewPass=findViewById(R.id.et_fch_new_pass);
        etRePass=findViewById(R.id.et_fch_new_re_pass);
        btChangePass=findViewById(R.id.bt_forget_change_pass);

        btChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNewPass.getText().toString().isEmpty()||etRePass.getText().toString().isEmpty()){
                    if(etNewPass.getText().toString().isEmpty()){
                        etNewPass.setError("Please Enter This Field");
                    }
                    if(etRePass.getText().toString().isEmpty()){
                        etRePass.setError("Please Enter This Field");
                    }
                }
                else {
                    if(etNewPass.getText().toString().equals(etRePass.getText().toString())){
                        passwordChange(etRePass.getText().toString());
                    }
                    else{
                        Toast.makeText(ChangePasswordActivity.this, "Password Don't Match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ChangePasswordActivity.this,PasswordOTPVerification.class));
        finish();
    }

    public void passwordChange(String password){
        ApiUtils.getServiceClass().passwordChange(SaveSharedPreference.getMobile(ChangePasswordActivity.this),
                password).enqueue(new Callback<ResponseMessageModel>() {
            @Override
            public void onResponse(Call<ResponseMessageModel> call, Response<ResponseMessageModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getResponse().equalsIgnoreCase("success")){
                        Toast.makeText(ChangePasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ChangePasswordActivity.this,LoginActivity.class));
                        finish();
                    }
                    else if(response.body().getResponse().equalsIgnoreCase("failed")){
                        Toast.makeText(ChangePasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMessageModel> call, Throwable t) {

            }
        });
    }


}
