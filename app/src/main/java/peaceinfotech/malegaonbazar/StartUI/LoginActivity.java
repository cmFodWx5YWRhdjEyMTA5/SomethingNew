package peaceinfotech.malegaonbazar.StartUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.Signup.SignUpActivity;
import peaceinfotech.malegaonbazar.User.UI.UserActivity;
import peaceinfotech.malegaonbazar.Vendor.VendorActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etmob,etpass;
    TextView warn;
    Button btsignin,btsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        etmob=findViewById(R.id.etmob);
        etpass=findViewById(R.id.etpass);
        btsignin=findViewById(R.id.btsignin);
        btsignup=findViewById(R.id.btsignup);
        warn=findViewById(R.id.tvwarn);

        if(SaveSharedPreference.getLoggedStatus(getApplicationContext())){
            startActivity(new Intent(LoginActivity.this, UserActivity.class));
            finish();
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
                            SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                            startActivity(new Intent(LoginActivity.this, UserActivity.class));
                            finish();
                        }
                        else {
                            SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                            startActivity(new Intent(LoginActivity.this, UserActivity.class));
                            finish();
                        }
                    }
                }
            }
        });

        btsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });

    }

}
