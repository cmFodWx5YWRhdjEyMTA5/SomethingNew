package peaceinfotech.malegaonbazar.Signup;

import android.app.Activity;
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
import android.util.Log;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.Signup.RetrofitModel.CategoriesListModel;
import peaceinfotech.malegaonbazar.Signup.RetrofitModel.HomeModel;
import peaceinfotech.malegaonbazar.Signup.RetrofitModel.UserRegisterModel;
import peaceinfotech.malegaonbazar.Signup.RetrofitModel.VendorRegisterModel;
import peaceinfotech.malegaonbazar.StartUI.SelectionActivity;
import peaceinfotech.malegaonbazar.User.UI.UserActivity;
import peaceinfotech.malegaonbazar.Vendor.UI.VendorActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

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
    MultipartBody.Part userFileToUpload,vendorFileToUpload;
    RequestBody userFileName,vendorFileName;
    List<String> categoryName;
    List<String> categoryId;



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

        categoryName=new ArrayList<>();
        categoryId=new ArrayList<>();

        Intent getin =getIntent();
        type=getin.getStringExtra("type");

        if(type.equalsIgnoreCase("user")){
            userlay.setVisibility(View.VISIBLE);
        }
        else if(type.equalsIgnoreCase("vendor")){
            vendorlay.setVisibility(View.VISIBLE);
        }

        ApiUtils.getServiceClass().categoriesRegister().enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getResponse().equalsIgnoreCase("success")){
                        for(int i=0;i<response.body().getCategoriesListModels().size();i++){
                            categoryName.add(response.body().getCategoriesListModels().get(i).getCatName());
                            categoryId.add(response.body().getCategoriesListModels().get(i).getCatId());
                        }
                    }
                    else if(response.body().getResponse().equalsIgnoreCase("failed")){

                    }
                }
            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {

            }
        });



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
                        UserRegister("2",name,location,SaveSharedPreference.getMobile(RegisterActivity.this),repass);
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

                if(name.isEmpty()||location.isEmpty()||brand.isEmpty()||category.isEmpty()||pass.isEmpty()||repass.isEmpty()){
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
                    if(pass.isEmpty()){
                        etvenpass.setError("Please Enter this Field");
                    }
                    if(repass.isEmpty()){
                        etvenrepass.setError("Please Enter this Field");
                    }
                }
                else{
                    if(pass.equals(repass)){
                        String vendorReferenceId="venref"+rand.nextInt(10000);
                        String finalEmail;
                        if(email.isEmpty()){
                            finalEmail = " ";
                        }
                        else{
                            finalEmail=email;
                        }

                        SaveSharedPreference.setVendorReference(RegisterActivity.this,vendorReferenceId);
                        VendorRegister("3",name,location,brand,category,email,repass,userFileToUpload,userFileName,vendorFileToUpload,vendorFileName);
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
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//            imgDemo.setImageURI(selectedImage);
            String filePath = getRealPathFromURIPath(selectedImage, RegisterActivity.this);
            File file = new File(filePath);
            //Log.d(TAG, "Filename " + file.getName());
            //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            userFileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
            userFileName = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        }
        if(requestCode == BAN && resultCode == RESULT_OK && null != data){
            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//            imgDemo.setImageURI(selectedImage);
            String filePath = getRealPathFromURIPath(selectedImage, RegisterActivity.this);
            File file = new File(filePath);
            //Log.d(TAG, "Filename " + file.getName());
            //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            vendorFileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
            vendorFileName = RequestBody.create(MediaType.parse("text/plain"), file.getName());

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

    public void UserRegister(String roleId,String fullname,String location,String mobile,String password){
        ApiUtils.getServiceClass().userRegister(roleId,fullname,location,mobile,password).enqueue(new Callback<UserRegisterModel>() {
            @Override
            public void onResponse(Call<UserRegisterModel> call, Response<UserRegisterModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getResponse().equalsIgnoreCase("success")){
                        AlertDialog("user");
                    }
                    else if(response.body().getResponse().equalsIgnoreCase("failed")){
                        Toast.makeText(RegisterActivity.this, "Something Went Wrong Please Try Again Later !!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserRegisterModel> call, Throwable t) {

            }
        });
    }

    public void VendorRegister(String roleId, String fullName, String location, String brand, String category, String emailId, String password, MultipartBody.Part userFileUpload,RequestBody userFilename,MultipartBody.Part vendorFileUpload,RequestBody vendorFilename){
        ApiUtils.getServiceClass().vendorRegister(roleId,fullName,location,brand,category,emailId,password,userFileUpload,userFilename,vendorFileUpload,vendorFilename).enqueue(new Callback<VendorRegisterModel>() {
            @Override
            public void onResponse(Call<VendorRegisterModel> call, Response<VendorRegisterModel> response) {
                if(response.isSuccessful()){
                    Log.d("vendorreg",response.body().getResponse());
                    if(response.body().getResponse().equalsIgnoreCase("success")){
                        AlertDialog("vendor");
                    }
                    else  if(response.body().getResponse().equalsIgnoreCase("response")){
                        Toast.makeText(RegisterActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<VendorRegisterModel> call, Throwable t) {

            }
        });
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

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

}
