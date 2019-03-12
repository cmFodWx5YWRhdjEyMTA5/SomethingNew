package peaceinfotech.malegaonbazar.StartUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.SaveSharedPreference;

public class LaunchActivity extends AppCompatActivity {

    EditText etReference;
    Button btSubmit;
    TextView tvwarn,tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        etReference=findViewById(R.id.et_rid);
        btSubmit=findViewById(R.id.bt_rid_submit);
        tvwarn=findViewById(R.id.tv_warn_ref);
        tvSkip=findViewById(R.id.tv_skip);

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LaunchActivity.this,LoginActivity.class));
                finish();
            }
        });


        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etReference.getText().toString().length()<10){
                    tvwarn.setVisibility(View.VISIBLE);
                }
                else{
                    if(tvwarn.getVisibility()==View.VISIBLE){
                        tvwarn.setVisibility(View.GONE);
                        SaveSharedPreference.setFirstTimeLaunch(LaunchActivity.this,true);
                        startActivity(new Intent(LaunchActivity.this,LoginActivity.class));
                        finish();
                    }
                    else{
                        SaveSharedPreference.setFirstTimeLaunch(LaunchActivity.this,true);
                        startActivity(new Intent(LaunchActivity.this,LoginActivity.class));
                        finish();
                    }
                }
            }
        });


    }
}
