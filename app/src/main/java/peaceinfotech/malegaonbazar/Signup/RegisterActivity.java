package peaceinfotech.malegaonbazar.Signup;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.isapanah.awesomespinner.AwesomeSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.RetrofitModel.CityListModel;
import peaceinfotech.malegaonbazar.RetrofitModel.LogInModel;
import peaceinfotech.malegaonbazar.RetrofitModel.StateListModel;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.RetrofitModel.CategoriesHomeModel;
import peaceinfotech.malegaonbazar.RetrofitModel.UserRegisterModel;
import peaceinfotech.malegaonbazar.StartUI.SelectionActivity;
import peaceinfotech.malegaonbazar.User.UI.UserActivity;
import peaceinfotech.malegaonbazar.Vendor.UI.VendorActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    Button submituser,submitven;
    Button btlogo,btban,btprof;
    ImageView imgDemo;
    EditText etusername,etuserloc,etusercity,etuserpass,etuserrepass;
    EditText etvenname,etvenloc,etvenbname,etvencat,etvenmail,etvenpass,etvenrepass;
    String type;
    LinearLayout userlay;
    ScrollView vendorlay;
    public final int LOGO=1;
    public final int BAN=2;
    public final int USER_PROFILE = 3;
    List<String> categoryName;
    List<String> categoryId;
    List<String> catState;
    List<String> catCity;
    AwesomeSpinner spinCat;
   // SearchableSpinner spinState,spinCity;
    TextView tvCatHint,tvDemo;
    String catid="";
    String state = "";
    String city = "";
    String id = "";
    private RequestQueue requestQueue;
    Bitmap bitmapLogo,bitmapBan,bitmapProf;
  //  SearchableSpinner spinDemo;
    String strLat,strLng;
    Double latitude,longitude;
    com.toptoche.searchablespinnerlibrary.SearchableSpinner searchSpinState,searchSpinCity;
    TextView tvLogo,tvBan,tvUserProf;
    ProgressBar pbVendor,pbUser;



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
        etusercity=findViewById(R.id.etusercity);
        etuserloc=findViewById(R.id.etuserlocation);
        etuserpass=findViewById(R.id.etuserpass);
        etuserrepass=findViewById(R.id.etuserrepass);
        tvLogo=findViewById(R.id.tv_logo_path);
        tvBan=findViewById(R.id.tv_ban_path);
        tvCatHint=findViewById(R.id.tv_cat_hint);
        tvDemo=findViewById(R.id.tv_demo);
        tvUserProf=findViewById(R.id.tv_user_prof_path);
        btprof=findViewById(R.id.btuserprofimg);
        imgDemo=findViewById(R.id.img_demo);
        pbVendor=findViewById(R.id.progress_vendor);
        pbUser=findViewById(R.id.progress_user);
//        spinState=findViewById(R.id.spin_state);
//        spinCity=findViewById(R.id.spin_city);
        spinCat=findViewById(R.id.spinner_category);
        etvenname=findViewById(R.id.etvenname);
        etvenloc=findViewById(R.id.etvenloc);
        etvenbname=findViewById(R.id.etvenbname);

        etvenmail=findViewById(R.id.etvenmail);
        etvenpass=findViewById(R.id.etvenpass);
        etvenrepass=findViewById(R.id.etvenrepass);
        btlogo=findViewById(R.id.btlogo);
        btban=findViewById(R.id.btban);
        submitven=findViewById(R.id.btvensubmit);

        searchSpinCity=findViewById(R.id.search_spin_city);
        searchSpinState=findViewById(R.id.search_spin_state);

        searchSpinState.setTitle("State");
        searchSpinCity.setTitle("City");

        bitmapLogo = BitmapFactory.decodeResource(RegisterActivity.this.getResources(),R.drawable.profilephoto);
        bitmapBan = BitmapFactory.decodeResource(RegisterActivity.this.getResources(),R.drawable.new_ban);
        bitmapProf = BitmapFactory.decodeResource(RegisterActivity.this.getResources(),R.drawable.profilephoto);

        Intent getin =getIntent();
        type=getin.getStringExtra("type");

        if(type.equalsIgnoreCase("user")){
            userlay.setVisibility(View.VISIBLE);
        }
        else if(type.equalsIgnoreCase("vendor")){
            vendorlay.setVisibility(View.VISIBLE);
        }

        getCategoriesList();
        getStateList();


        searchSpinState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(searchSpinState.getSelectedItem()==null){
                    Toast.makeText(RegisterActivity.this, "Select a option", Toast.LENGTH_SHORT).show();
                }
                else{
                    state=searchSpinState.getSelectedItem().toString();
                    getCityList(state);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchSpinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city=searchSpinCity.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        spinCat.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                catid=categoryId.get(position);
                tvDemo.setText(catid);
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

        btprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,USER_PROFILE);
            }
        });

        submituser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=etusername.getText().toString();
                String location=etuserloc.getText().toString();
                String pass=etuserpass.getText().toString();
                String repass=etuserrepass.getText().toString();
                String city=etusercity.getText().toString();


                if(name.isEmpty()||location.isEmpty()||pass.isEmpty()||repass.isEmpty()||city.isEmpty()){
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
                    if(city.isEmpty()){
                        etusercity.setError("Please enter this field");
                    }
                }
                else{
                    if(pass.equals(repass)){
                      //  UserRegister("2",name,location,city,SaveSharedPreference.getMobile(RegisterActivity.this),repass);
                        requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                        submituser.setVisibility(View.GONE);
                        pbUser.setVisibility(View.VISIBLE);
                        userUploadBitmap(bitmapProf,name,location,city,repass);
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

//                requestQueue = Volley.newRequestQueue(RegisterActivity.this);
//                vendorUploadBitmap(bitmapLogo,bitmapBan);

                String name=etvenname.getText().toString();
                String location=etvenloc.getText().toString();
                String brand=etvenbname.getText().toString();
                String email=etvenmail.getText().toString();
                String pass=etvenpass.getText().toString();
                String repass=etvenrepass.getText().toString();

                if(name.isEmpty()||location.isEmpty()||brand.isEmpty()||catid.isEmpty()||pass.isEmpty()||repass.isEmpty()||state.isEmpty()||city.isEmpty()){
                    if(name.isEmpty()){
                        etvenname.setError("Please Enter this Field");
                    }
                    if(location.isEmpty()){
                        etvenloc.setError("Please Enter this Field");
                    }
                    if(brand.isEmpty()){
                        etvenbname.setError("Please Enter this Field");
                    }
                    if(pass.isEmpty()){
                        etvenpass.setError("Please Enter this Field");
                    }
                    if(repass.isEmpty()){
                        etvenrepass.setError("Please Enter this Field");
                    }
                    if(catid.isEmpty()){
                        Toast.makeText(RegisterActivity.this, "Select a category", Toast.LENGTH_SHORT).show();
                    }
                    if(state.isEmpty()){
                        Toast.makeText(RegisterActivity.this, "Select State", Toast.LENGTH_SHORT).show();
                    }
                    if(city.isEmpty()){
                        Toast.makeText(RegisterActivity.this, "Select City", Toast.LENGTH_SHORT).show();
                    }


                }
                else{
                    if(isValidEmail(email)){
                        if(pass.equals(repass)){
                            requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                            getLocationFromAddress(etvenloc.getText().toString()+","+city+","+state);
                            submitven.setVisibility(View.VISIBLE);
                            pbVendor.setVisibility(View.VISIBLE);
                            vendorUploadBitmap(bitmapLogo,bitmapBan,name,location,brand,catid,repass,email,state,city);
                        }
                        else{
                            etvenrepass.setError("Password Don't Match");
//                        Toast.makeText(RegisterActivity.this,"Password do not Match",Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        etvenmail.setError("Enter Valid Email id");
                    }

                }
            }
        });

    }


    public static boolean isValidEmail(CharSequence target) {

        if(target.toString().isEmpty()){
            return true;
        }
        else{
            return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
        }

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
            bitmapLogo = ((BitmapDrawable) imgDemo.getDrawable()).getBitmap();
            String file = getFileName(picturePath);
            tvLogo.setText(file);
            Toast.makeText(RegisterActivity.this, "Logo Uploaded Successfully : "+picturePath, Toast.LENGTH_SHORT).show();
//            String filePath = getRealPathFromURIPath(selectedImage, RegisterActivity.this);
//            File file = new File(filePath);
//            //Log.d(TAG, "Filename " + file.getName());
//            //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
//            logoFileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
//            logoFileName = RequestBody.create(MediaType.parse("text/plain"), file.getName());
//
//            Log.d("image file",logoFileToUpload.toString()+"/"+logoFileName);

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
            bitmapBan = ((BitmapDrawable) imgDemo.getDrawable()).getBitmap();
            String file = getFileName(picturePath);
            tvBan.setText(file);
            Toast.makeText(RegisterActivity.this, "Banner Uploaded Successfully : "+picturePath, Toast.LENGTH_SHORT).show();

            //           imgDemo.setImageURI(selectedImage);
//            String filePath = getRealPathFromURIPath(selectedImage, RegisterActivity.this);
//            File file = new File(filePath);
//            //Log.d(TAG, "Filename " + file.getName());
//            //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
//            banFileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
//            banFileName = RequestBody.create(MediaType.parse("text/plain"), file.getName());
//
//            Log.d("image file1",banFileToUpload.toString()+"/"+banFileName);

        }
        if (requestCode == USER_PROFILE && resultCode == RESULT_OK && null != data){
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imgDemo.setImageURI(selectedImage);
            bitmapProf = ((BitmapDrawable) imgDemo.getDrawable()).getBitmap();
            String file = getFileName(picturePath);
            tvUserProf.setText(file);
            Toast.makeText(RegisterActivity.this, "Banner Uploaded Successfully : "+picturePath, Toast.LENGTH_SHORT).show();

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

    public void UserRegister(String roleId, String fullname, String location,String city,String mobile, final String password){
        ApiUtils.getServiceClass().userRegister(roleId,fullname,location,city,mobile,password).enqueue(new Callback<UserRegisterModel>() {
            @Override
            public void onResponse(Call<UserRegisterModel> call, Response<UserRegisterModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getResponse().equalsIgnoreCase("success")){
                        AlertDialog("user",password);
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


    public void AlertDialog(final String type, final String password){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Registered Successfully");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(type.equals("user")){
                    LogIn(SaveSharedPreference.getMobile(RegisterActivity.this),password);
                }
                else if(type.equals("vendor")){
                    LogIn(SaveSharedPreference.getMobile(RegisterActivity.this),password);
                }
            }
        });
        final AlertDialog alertDialog=builder.create();

        alertDialog.show();
    }


    private void userUploadBitmap(final Bitmap bitmapProf,
                                  final String name,
                                  final String location,
                                  final String city,
                                  final String password){

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST," http://autoreplyz.com/Malegaon/Api/Userapi/usersignup",
                new com.android.volley.Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
//                            JSONObject o=jsonResponse.getJSONObject("details");
                            Log.d("SID", "onResponse: " + obj.getString("response"));
                            //If it is success
                            if (obj.getString("response").equalsIgnoreCase("success")) {

                                submituser.setVisibility(View.VISIBLE);
                                pbUser.setVisibility(View.GONE);
                                AlertDialog("user",password);

                            } else {
                                Toast.makeText(RegisterActivity.this, "Some error", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("Roleid","2");
                params.put("Fullname",name);
                params.put("Location",location);
                params.put("Mobile",SaveSharedPreference.getMobile(RegisterActivity.this));
                params.put("Password",password);
                params.put("City",city);

                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long profImageName = System.currentTimeMillis();
                params.put("Profile", new DataPart(profImageName + ".png", getFileDataFromDrawable(bitmapProf)));
                return params;
            }
        };

        //adding the request to volley
        //idi requestQueue.add(stringRequest);
        requestQueue.add(volleyMultipartRequest);

    }


    private void vendorUploadBitmap(final Bitmap bitmapLogo,
                                    final Bitmap bitmapBan,
                                    final String name,
                                    final String location,
                                    final String brand,
                                    final String catid,
                                    final String password,
                                    final String email,
                                    final String state,
                                    final String city) {


        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST," http://autoreplyz.com/Malegaon/Api/Userapi/vendorsignup",
                new com.android.volley.Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
//                            JSONObject o=jsonResponse.getJSONObject("details");
                            Log.d("SID", "onResponse: " + obj.getString("response"));
                            //If it is success
                            if (obj.getString("response").equalsIgnoreCase("success")) {

                                pbVendor.setVisibility(View.GONE);
                                submitven.setVisibility(View.VISIBLE);
                                AlertDialog("vendor",password);

                            } else {
                                Toast.makeText(RegisterActivity.this, "Some error", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("Roleid","3");
                params.put("Fullname",name);
                params.put("Location",location);
                params.put("Brand",brand);
                params.put("Mobile",SaveSharedPreference.getMobile(RegisterActivity.this));
                params.put("Category",catid);
                params.put("Emailid",email);
                params.put("Password",password);
                params.put("state_name",state);
                params.put("city_name",city);
                params.put("latitude",strLat);
                params.put("longitude",strLng);

                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long logoImageName = System.currentTimeMillis();
                params.put("Logo", new DataPart(logoImageName + ".png", getFileDataFromDrawable(bitmapLogo)));

                long banImageName = System.currentTimeMillis();
                params.put("Banner",new DataPart(banImageName + ".png", getFileDataFromDrawable(bitmapBan)));
                return params;
            }
        };

        //adding the request to volley
        //idi requestQueue.add(stringRequest);
        requestQueue.add(volleyMultipartRequest);
    }



    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void LogIn (final String mobile, final String password) {

        ApiUtils.getServiceClass().logIn(mobile, password).enqueue(new Callback<LogInModel>() {
            @Override
            public void onResponse(Call<LogInModel> call, Response<LogInModel> response) {
                if (response.isSuccessful()) {
                    Log.d("login", response.body().getResponse());
                    if (response.body().getResponse().equalsIgnoreCase("success")) {

                        if (response.body().getDetailsModels().getRoleName().equalsIgnoreCase("user")) {

                            SaveSharedPreference.setUserMobilePassword(RegisterActivity.this,mobile,password);

                            SaveSharedPreference.setRole(RegisterActivity.this,
                                    response.body().getDetailsModels().getRoleID(),
                                    response.body().getDetailsModels().getRoleName());

                            SaveSharedPreference.setUserProfileData(RegisterActivity.this,
                                    response.body().getDetailsModels().getUserId(),
                                    response.body().getDetailsModels().getFullName(),
                                    response.body().getDetailsModels().getLocation(),
                                    response.body().getDetailsModels().getCity(),
                                    response.body().getDetailsModels().getMobile(),
                                    response.body().getDetailsModels().getReferenceId(),
                                    response.body().getDetailsModels().getProfileUrl());

                            SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                            startActivity(new Intent(RegisterActivity.this, UserActivity.class));
                            finish();
                        } else if (response.body().getDetailsModels().getRoleName().equalsIgnoreCase("vendor")) {
                            SaveSharedPreference.setRole(RegisterActivity.this,
                                    response.body().getDetailsModels().getRoleID(),
                                    response.body().getDetailsModels().getRoleName());

                            SaveSharedPreference.setVendorProfileData(RegisterActivity.this,
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

                            SaveSharedPreference.setVendorLatLng(RegisterActivity.this,
                                    response.body().getDetailsModels().getLat(),
                                    response.body().getDetailsModels().getLng());

                            SaveSharedPreference.setMobileAndPassword(RegisterActivity.this,
                                    mobile,
                                    password);

                            SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                            startActivity(new Intent(RegisterActivity.this, VendorActivity.class));
                            finish();
                        }
                    } else if (response.body().getResponse().equalsIgnoreCase("failed")) {
                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LogInModel> call, Throwable t) {


                Log.d("loginfail", "onFailure: " + t);
            }
        });

    }


    public void getCategoriesList(){

        ApiUtils.getServiceClass().categoriesRegister().enqueue(new Callback<CategoriesHomeModel>() {
            @Override
            public void onResponse(Call<CategoriesHomeModel> call, Response<CategoriesHomeModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getResponse().equalsIgnoreCase("success")){
                        categoryName = new ArrayList<>();
                        categoryId = new ArrayList<>();
                        for(int i=0;i<response.body().getCategoriesListModels().size();i++){
                            categoryName.add(i,response.body().getCategoriesListModels().get(i).getCatName());
                            categoryId.add(i,response.body().getCategoriesListModels().get(i).getCatId());
                        }
                        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item,categoryName);
                        categoriesAdapter.notifyDataSetChanged();
                        spinCat.setAdapter(categoriesAdapter);
                    }
                    else if(response.body().getResponse().equalsIgnoreCase("failed")){

                    }
                }
            }
            @Override
            public void onFailure(Call<CategoriesHomeModel> call, Throwable t) {

            }
        });


        // categoriesAdapter.notifyDataSetChanged();

    }

    public void getStateList(){

        ApiUtils.getServiceClass().stateListRegister().enqueue(new Callback<StateListModel>() {
            @Override
            public void onResponse(Call<StateListModel> call, Response<StateListModel> response) {

                if(response.isSuccessful()){
                    if(response.body().getResponse().equalsIgnoreCase("success")){
                        catState = new ArrayList<>();
                        for(int i=0;i<response.body().getStateLists().size();i++){
                            catState.add(i,response.body().getStateLists().get(i).getStateName());
                        }
                        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_spinner_dropdown_item,catState);
                        stateAdapter.notifyDataSetChanged();

                        searchSpinState.setAdapter(stateAdapter);
                    }
                    else if(response.body().getResponse().equalsIgnoreCase("failed")){

                    }
                }
            }
            @Override
            public void onFailure(Call<StateListModel> call, Throwable t) {

            }
        });

    }

    public void getCityList(String stateName){

        ApiUtils.getServiceClass().cityListRegister(stateName).enqueue(new Callback<CityListModel>() {
            @Override
            public void onResponse(Call<CityListModel> call, Response<CityListModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getResponse().equalsIgnoreCase("success")){
                        catCity = new ArrayList<>();

                        for(int i=0;i<response.body().getCityLists().size();i++){
                            catCity.add(i,response.body().getCityLists().get(i).getName());
                        }

                        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_spinner_dropdown_item,catCity);
                        cityAdapter.notifyDataSetChanged();

                        searchSpinCity.setAdapter(cityAdapter);
                    }
                    else if(response.body().getResponse().equalsIgnoreCase("failed")){

                    }
                }
            }

            @Override
            public void onFailure(Call<CityListModel> call, Throwable t) {

            }
        });

    }


    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(RegisterActivity.this, Locale.getDefault());
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            latitude=location.getLatitude();
            longitude=location.getLongitude();

            strLat=latitude.toString();
            strLng=longitude.toString();

            Log.d("latlng", "getLocationFromAddress: "+location.getLatitude()+location.getLongitude());
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

            return p1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p1;
    }

    public String getFileName (String picturePath){
        String str[] = picturePath.split("/");
        List<String> al = new ArrayList<String>();
        al = Arrays.asList(str);

        return al.get(al.size()-1);
    }
}

//   private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
//        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
//        if (cursor == null) {
//            return contentURI.getPath();
//        } else {
//            cursor.moveToFirst();
//            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            return cursor.getString(idx);
//        }
//    }
