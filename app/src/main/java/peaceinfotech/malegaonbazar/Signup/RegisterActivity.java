package peaceinfotech.malegaonbazar.Signup;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.isapanah.awesomespinner.AwesomeSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
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
    Button btlogo,btban;
    ImageView imgDemo;
    EditText etusername,etuserloc,etuserpass,etuserrepass;
    EditText etvenname,etvenloc,etvenbname,etvencat,etvenmail,etvenpass,etvenrepass;
    String type;
    LinearLayout userlay;
    ScrollView vendorlay;
    public final int LOGO=1;
    public final int BAN=2;
    MultipartBody.Part logoFileToUpload,banFileToUpload;
    RequestBody logoFileName,banFileName;
    List<String> categoryName = new ArrayList<>();
    List<String> categoryId = new ArrayList<>();
    AwesomeSpinner spinCat;
    TextView tvCatHint,tvDemo;
    String catid="";
    private RequestQueue requestQueue;
    Bitmap bitmapLogo,bitmapBan;



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

        tvCatHint=findViewById(R.id.tv_cat_hint);
        tvDemo=findViewById(R.id.tv_demo);
        imgDemo=findViewById(R.id.img_demo);

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


        bitmapLogo = BitmapFactory.decodeResource(RegisterActivity.this.getResources(),R.drawable.profilephoto);
        bitmapBan = BitmapFactory.decodeResource(RegisterActivity.this.getResources(),R.drawable.new_ban);

        Intent getin =getIntent();
        type=getin.getStringExtra("type");

        if(type.equalsIgnoreCase("user")){
            userlay.setVisibility(View.VISIBLE);
        }
        else if(type.equalsIgnoreCase("vendor")){
            vendorlay.setVisibility(View.VISIBLE);
        }



        ApiUtils.getServiceClass().categoriesRegister().enqueue(new Callback<CategoriesHomeModel>() {
            @Override
            public void onResponse(Call<CategoriesHomeModel> call, Response<CategoriesHomeModel> response) {
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
            public void onFailure(Call<CategoriesHomeModel> call, Throwable t) {

            }
        });



        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryName);
        spinCat.setAdapter(categoriesAdapter);
        categoriesAdapter.notifyDataSetChanged();

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

//                requestQueue = Volley.newRequestQueue(RegisterActivity.this);
//                vendorUploadBitmap(bitmapLogo,bitmapBan);

                String name=etvenname.getText().toString();
                String location=etvenloc.getText().toString();
                String brand=etvenbname.getText().toString();
                String email=etvenmail.getText().toString();
                String pass=etvenpass.getText().toString();
                String repass=etvenrepass.getText().toString();

                if(name.isEmpty()||location.isEmpty()||brand.isEmpty()||catid.isEmpty()||pass.isEmpty()||repass.isEmpty()){
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

                }
                else{
                    if(isValidEmail(email)){
                        if(pass.equals(repass)){
                            requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                            vendorUploadBitmap(bitmapLogo,bitmapBan,name,location,brand,catid,repass,email);
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
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
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

//    public void VendorRegister(String roleId,String fullName, String location, String mobile,String brand, String category, String emailId, String password){
//        ApiUtils.getServiceClass().vendorRegister(roleId,fullName,location,brand,mobile,category,emailId,password,logoFileToUpload,logoFileName,banFileToUpload,banFileName).enqueue(new Callback<VendorRegisterModel>() {
//            @Override
//            public void onResponse(Call<VendorRegisterModel> call, Response<VendorRegisterModel> response) {
//                if(response.isSuccessful()){
//                    Log.d("vendorreg",response.body().getResponse());
//                    if(response.body().getResponse().equalsIgnoreCase("success")){
//                        AlertDialog("vendor");
//                    }
//                    else  if(response.body().getResponse().equalsIgnoreCase("response")){
//                        Toast.makeText(RegisterActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<VendorRegisterModel> call, Throwable t) {
//
//            }
//        });
//    }

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


    private void vendorUploadBitmap(final Bitmap bitmapLogo, final Bitmap bitmapBan, final String name, final String location, final String brand, final String catid, final String password, final String email ) {


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

                                AlertDialog("vendor");

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
