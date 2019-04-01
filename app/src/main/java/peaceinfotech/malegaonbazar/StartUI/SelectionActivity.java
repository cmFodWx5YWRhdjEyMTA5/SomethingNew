package peaceinfotech.malegaonbazar.StartUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Signup.RegisterActivity;
import peaceinfotech.malegaonbazar.Signup.SignUpActivity;

public class SelectionActivity extends AppCompatActivity {

    Button btUser,btVendor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.user_vendor_selection);

        btUser=findViewById(R.id.bt_user);
        btVendor=findViewById(R.id.bt_vendor);


        btUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type="user";
                Intent in = new Intent(SelectionActivity.this, RegisterActivity.class);
                in.putExtra("type", type);
                startActivity(in);
                finish();
            }
        });

        btVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type="vendor";
                Intent in = new Intent(SelectionActivity.this, RegisterActivity.class);
                in.putExtra("type", type);
                startActivity(in);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SelectionActivity.this, SignUpActivity.class));
        finish();
    }
}
