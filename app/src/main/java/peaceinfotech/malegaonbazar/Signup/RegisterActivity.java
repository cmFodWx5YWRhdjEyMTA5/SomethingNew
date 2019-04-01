package peaceinfotech.malegaonbazar.Signup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Random;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.StartUI.SelectionActivity;
import peaceinfotech.malegaonbazar.User.UI.UserActivity;
import peaceinfotech.malegaonbazar.Vendor.UI.VendorActivity;

public class RegisterActivity extends AppCompatActivity {
    Button submituser,submitven;
    Button btlogo,btban;
    ImageView imgDemo;
    EditText etusername,etuserloc,etuserpass,etuserrepass;
    EditText etvenname,etvenloc,etvenbname,etvencat,etvenmail,etvenpass,etvenrepass;
    String type;
    LinearLayout userlay;
    ScrollView vendorlay;
    public final int LOGO=1;
    public final int BAN=2;

    Random rand = new Random();

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

        imgDemo=findViewById(R.id.img_demo);

        etvenname=findViewById(R.id.etvenname);
        etvenloc=findViewById(R.id.etvenloc);
        etvenbname=findViewById(R.id.etvenbname);
        etvencat=findViewById(R.id.etvencat);

        etvenmail=findViewById(R.id.etvenmail);
        etvenpass=findViewById(R.id.etvenpass);
        etvenrepass=findViewById(R.id.etvenrepass);
        btlogo=findViewById(R.id.btlogo);
        btban=findViewById(R.id.btban);
        submitven=findViewById(R.id.btvensubmit);

        Intent getin =getIntent();
        type=getin.getStringExtra("type");

        if(type.equalsIgnoreCase("user")){
            userlay.setVisibility(View.VISIBLE);
        }
        else if(type.equalsIgnoreCase("vendor")){
            vendorlay.setVisibility(View.VISIBLE);
        }

        btlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,LOGO);
            }
        });


        btban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,BAN);
            }
        });

        submituser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=etusername.getText().toString();
                String location=etuserloc.getText().toString();
                String pass=etuserpass.getText().toString();
                String repass=etuserrepass.getText().toString();


                if(name.isEmpty()||location.isEmpty()||pass.isEmpty()||repass.isEmpty()){
                    if(name.isEmpty()){
                        etusername.setError("PLease enter this field");
                    }
                    if(location.isEmpty()){
                        etuserloc.setError("Please enter this field");
                    }
                    if(pass.isEmpty()){
                        etuserpass.setError("Please enter this field");
                    }
                    if(repass.isEmpty()){
                        etuserrepass.setError("Please enter this field");
                    }
                }
                else{
                    if(pass.equals(repass)){
                        String userReferenceId="userref"+rand.nextInt(10000);
                        SaveSharedPreference.setUserReference(RegisterActivity.this,userReferenceId);
                        AlertDialog("user");
                    }
                    else{
                        etuserrepass.setError("Password Don't Match");
//                        Toast.makeText(RegisterActivity.this,"Password do not Match",Toast.LENGTH_LONG).show();
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
                String email=etvenmail.getText().toString();
                String pass=etvenpass.getText().toString();
                String repass=etvenrepass.getText().toString();

                if(name.isEmpty()||location.isEmpty()||brand.isEmpty()||category.isEmpty()||email.isEmpty()||pass.isEmpty()||repass.isEmpty()){
                    if(name.isEmpty()){
                        etvenname.setError("Please Enter this Field");
                    }
                    if(location.isEmpty()){
                        etvenloc.setError("Please Enter this Field");
                    }
                    if(brand.isEmpty()){
                        etvenbname.setError("Please Enter this Field");
                    }
                    if(category.isEmpty()){
                        etvencat.setError("Please Enter this Field");
                    }
                    if(email.isEmpty()){
                        etvenmail.setError("Please Enter this Field");
                    }
                    if(pass.isEmpty()){
                        etvenpass.setError("Please Enter this Field");
                    }
                    if(repass.isEmpty()){
                        etvenrepass.setError("Please Enter this Field");
                    }
                }
                else{
                    if(pass.equals(repass)){
                        String vendorReferenceId="venref"+rand.nextInt(10000);;
                        SaveSharedPreference.setVendorReference(RegisterActivity.this,vendorReferenceId);
                        AlertDialog("vendor");
                    }
                    else{
                        etvenrepass.setError("Password Don't Match");
//                        Toast.makeText(RegisterActivity.this,"Password do not Match",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == LOGO && resultCode == RESULT_OK && null != data){
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imgDemo.setImageURI(selectedImage);
        }
        if(requestCode == BAN && resultCode == RESULT_OK && null != data){
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imgDemo.setImageURI(selectedImage);
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(RegisterActivity.this, SelectionActivity.class));
        finish();

    }

    public void AlertDialog(final String type){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Registered Successfully");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(type.equals("user")){
                    startActivity(new Intent(RegisterActivity.this, UserActivity.class));
                    finish();
                }
                else if(type.equals("vendor")){
                    startActivity(new Intent(RegisterActivity.this, VendorActivity.class));
                    finish();
                }
            }
        });
        final AlertDialog alertDialog=builder.create();

        alertDialog.show();
    }
}
