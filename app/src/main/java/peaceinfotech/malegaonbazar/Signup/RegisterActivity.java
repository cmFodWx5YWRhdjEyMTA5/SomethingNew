package peaceinfotech.malegaonbazar.Signup;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import peaceinfotech.malegaonbazar.R;

public class RegisterActivity extends AppCompatActivity {
    Button submituser,submitven;
    Button btlogo,btban;
    EditText etusername,etuserloc,etuserpass,etuserrepass;
    EditText etvenname,etvenloc,etvenbname,etvencat,etvenser,etvenmail,etvenpass,etvenrepass;
    String type;
    LinearLayout userlay;
    ScrollView vendorlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        userlay=findViewById(R.id.userlay);
        vendorlay=findViewById(R.id.vendorlay);

        submituser=findViewById(R.id.btusersubmit);
        etusername=findViewById(R.id.etusername);
        etuserloc=findViewById(R.id.etuserlocation);
        etuserpass=findViewById(R.id.etuserpass);
        etuserrepass=findViewById(R.id.etuserrepass);

        etvenname=findViewById(R.id.etvenname);
        etvenloc=findViewById(R.id.etvenloc);
        etvenbname=findViewById(R.id.etvenbname);
        etvencat=findViewById(R.id.etvencat);
        etvenser=findViewById(R.id.etvenser);
        etvenmail=findViewById(R.id.etvenmail);
        etvenpass=findViewById(R.id.etvenpass);
        etvenrepass=findViewById(R.id.etvenrepass);
        submitven=findViewById(R.id.btvensubmit);

        Intent getin =getIntent();
        type=getin.getStringExtra("type");

        if(type.equalsIgnoreCase("user")){
            userlay.setVisibility(View.VISIBLE);
        }
        else if(type.equalsIgnoreCase("vendor")){
            vendorlay.setVisibility(View.VISIBLE);
        }

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Registered Successfully");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        final AlertDialog alertDialog=builder.create();



        submituser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=etusername.getText().toString();
                String location=etuserloc.getText().toString();
                String pass=etuserpass.getText().toString();
                String repass=etuserrepass.getText().toString();


                if(name.isEmpty()||location.isEmpty()||pass.isEmpty()||repass.isEmpty()){

                    Toast.makeText(RegisterActivity.this,"Please Eneter All the Fields",Toast.LENGTH_LONG).show();
                }
                else{
                    if(pass.equals(repass)){
                        alertDialog.show();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Password do not Match",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        submitven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=etvenname.getText().toString();
                String location=etvenloc.getText().toString();
                String brand=etvenbname.getText().toString();
                String category=etvencat.getText().toString();
                String service=etvenser.getText().toString();
                String email=etvenmail.getText().toString();
                String pass=etvenpass.getText().toString();
                String repass=etvenrepass.getText().toString();

                if(name.isEmpty()||location.isEmpty()||brand.isEmpty()||category.isEmpty()||service.isEmpty()||email.isEmpty()||pass.isEmpty()||repass.isEmpty()){
                    Toast.makeText(RegisterActivity.this,"Please Eneter All the Fields",Toast.LENGTH_LONG).show();
                }
                else{
                    if(pass.equals(repass)){
                        alertDialog.show();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Password do not Match",Toast.LENGTH_LONG).show();
                    }
                }


            }
        });


    }
}
