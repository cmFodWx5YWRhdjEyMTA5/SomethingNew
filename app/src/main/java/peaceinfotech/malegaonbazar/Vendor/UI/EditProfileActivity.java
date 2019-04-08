package peaceinfotech.malegaonbazar.Vendor.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;
import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.RetrofitModel.CategoriesHomeModel;
import peaceinfotech.malegaonbazar.RetrofitModel.CityListModel;
import peaceinfotech.malegaonbazar.RetrofitModel.LogInModel;
import peaceinfotech.malegaonbazar.RetrofitModel.StateListModel;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.Signup.RegisterActivity;
import peaceinfotech.malegaonbazar.Signup.VolleyMultipartRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static peaceinfotech.malegaonbazar.Signup.RegisterActivity.isValidEmail;

public class EditProfileActivity extends AppCompatActivity {

    EditText etName,etLocation,etBrand,etEmail;
    AwesomeSpinner spinCategory;
    SearchableSpinner spinState,spinCity;
    public final int LOGO=1;
    public final int BAN=2;
    List<String> listCategoryName;
    List<String> listCategoryId;
    List<String> listCategoryState;
    List<String> listCategoryCity;
    Button btLogo,btBan,btEdit;
    Toolbar toolbar;
    String catid = "";
    Bitmap bitmapLogo,bitmapBan;
    ImageView imgDemo;
    String state = "";
    String city = "";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vendor_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar=findViewById(R.id.tool_edit_ven_profile);
        etName=findViewById(R.id.et_ven_edit_name);
        etLocation=findViewById(R.id.et_ven_edit_loc);
        etBrand=findViewById(R.id.et_ven_edit_bname);
        etEmail=findViewById(R.id.et_ven_edit_mail);
        btLogo=findViewById(R.id.bt_edit_logo);
        btBan=findViewById(R.id.bt_edit_ban);
        btEdit=findViewById(R.id.bt_ven_edit);
        spinCategory=findViewById(R.id.spinner_edit_category);
        spinCity=findViewById(R.id.spin_edit_city);
        spinState=findViewById(R.id.spin_edit_state);
        imgDemo=findViewById(R.id.img_demo);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getCategoriesList();

        getStateList();

        getCityList(SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(10));

//        bitmapLogo = BitmapFactory.decodeResource(EditProfileActivity.this.getResources(),R.drawable.profilephoto);
//        bitmapBan = BitmapFactory.decodeResource(EditProfileActivity.this.getResources(),R.drawable.new_ban);

        etName.setText(SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(1));
        etLocation.setText(SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(2));
        etBrand.setText(SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(4));
        etEmail.setText(SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(6));




        spinState.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                if(!spinState.getSelectedItem().equals(SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(10))){
                    getCityList(spinState.getSelectedItem().toString());
                    state = spinState.getSelectedItem().toString();
                }
                else{
                    getCityList(SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(10));
                    state = SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(10);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        spinCity.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                if(!spinCity.getSelectedItem().equals(SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(11))){
                    city = spinCity.getSelectedItem().toString();
                }
                else{
                    city = SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(11);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });


        spinCategory.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                catid=listCategoryId.get(position);
               // tvDemo.setText(catid);
            }
        });


        btLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,LOGO);
            }
        });

        btBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,BAN);
            }
        });

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue = Volley.newRequestQueue(EditProfileActivity.this);
                if(etName.getText().toString().isEmpty()
                        || etLocation.getText().toString().isEmpty()
                        ||etBrand.getText().toString().isEmpty()
                        ||catid.isEmpty()
                        ||state.isEmpty()
                        ||city.isEmpty()){
                    if(etName.getText().toString().isEmpty()){
                        etName.setError("Please Enter this Field");
                    }
                    if(etLocation.getText().toString().isEmpty()){
                        etLocation.setError("Please Enter this Field");
                    }
                    if(etBrand.getText().toString().isEmpty()){
                        etBrand.setError("Please Enter this Field");
                    }
                    if(catid.isEmpty()){
                        Toast.makeText(EditProfileActivity.this, "Select a category", Toast.LENGTH_SHORT).show();
                    }
                    if(state.isEmpty()){
                        Toast.makeText(EditProfileActivity.this, "Select State", Toast.LENGTH_SHORT).show();
                    }
                    if(city.isEmpty()){
                        Toast.makeText(EditProfileActivity.this, "Select City", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    if(isValidEmail(etEmail.getText().toString())){
                        requestQueue = Volley.newRequestQueue(EditProfileActivity.this);
                        vendorEditBitmap(bitmapLogo,bitmapBan,
                                etName.getText().toString(),
                                etLocation.getText().toString(),
                                etBrand.getText().toString(),
                                etEmail.getText().toString(),
                                catid,state,city);

                    }
                    else {
                        etEmail.setError("Enter Valid Email id");
                    }

                }
            }
        });

    }

    public void getCategoriesList(){

        ApiUtils.getServiceClass().categoriesRegister().enqueue(new Callback<CategoriesHomeModel>() {
            @Override
            public void onResponse(Call<CategoriesHomeModel> call, Response<CategoriesHomeModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getResponse().equalsIgnoreCase("success")){
                        listCategoryName = new ArrayList<>();
                        listCategoryId = new ArrayList<>();
                        for(int i=0;i<response.body().getCategoriesListModels().size();i++){
                            listCategoryName.add(i,response.body().getCategoriesListModels().get(i).getCatName());
                            listCategoryId.add(i,response.body().getCategoriesListModels().get(i).getCatId());
                        }
                        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(EditProfileActivity.this, android.R.layout.simple_spinner_item,listCategoryName);
                        categoriesAdapter.notifyDataSetChanged();
                        spinCategory.setAdapter(categoriesAdapter);
                        String compareValue = SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(5);
                        if (compareValue != null) {
                            int spinnerPosition = categoriesAdapter.getPosition(compareValue);
                            spinCategory.setSelection(spinnerPosition);
                            catid = SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(7);
                        }
                        //spinCategory.setSelection(SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(5));
                    }
                    else if(response.body().getResponse().equalsIgnoreCase("failed")){

                    }
                }
            }
            @Override
            public void onFailure(Call<CategoriesHomeModel> call, Throwable t) {

            }
        });
    }

    public void getStateList(){

        ApiUtils.getServiceClass().stateListRegister().enqueue(new Callback<StateListModel>() {
            @Override
            public void onResponse(Call<StateListModel> call, Response<StateListModel> response) {

                if(response.isSuccessful()){
                    if(response.body().getResponse().equalsIgnoreCase("success")){
                        listCategoryState = new ArrayList<>();
                        for(int i=0;i<response.body().getStateLists().size();i++){
                            listCategoryState.add(i,response.body().getStateLists().get(i).getStateName());
                        }
                        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(EditProfileActivity.this,android.R.layout.simple_spinner_item,listCategoryState);
                        stateAdapter.notifyDataSetChanged();
                        spinState.setAdapter(stateAdapter);
                        state=SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(10);

                        String compareValue = SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(10);
//                        if (compareValue != null) {
//                            int spinnerPosition = stateAdapter.getPosition(compareValue);
//                            Log.d("spinnerPosition", "onResponse: "+spinnerPosition);
//                            spinState.setSelectedItem(spinnerPosition);
//                        }
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
                        listCategoryCity = new ArrayList<>();

                        for(int i=0;i<response.body().getCityLists().size();i++){
                            listCategoryCity.add(i,response.body().getCityLists().get(i).getName());
                        }

                        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(EditProfileActivity.this,android.R.layout.simple_spinner_item,listCategoryCity);
                        cityAdapter.notifyDataSetChanged();
                        spinCity.setAdapter(cityAdapter);
                        city = SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(11);
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


    public void AlertDialog(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Profile Edited Successfully");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });
        final AlertDialog alertDialog=builder.create();

        alertDialog.show();
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

    private void vendorEditBitmap(final Bitmap bitmapLogo,
                                    final Bitmap bitmapBan,
                                    final String name,
                                    final String location,
                                    final String brand,
                                    final String email,
                                    final String catid,
                                    final String state,
                                    final String city) {


        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST," http://autoreplyz.com/Malegaon/Api/Userapi/vendorupdate",
                new com.android.volley.Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
//                            JSONObject o=jsonResponse.getJSONObject("details");
                            Log.d("SID", "onResponse: " + obj.getString("response"));
                            //If it is success
                            if (obj.getString("response").equalsIgnoreCase("success")) {


                                changeData(SaveSharedPreference.getMobileAndPassword(EditProfileActivity.this).get(0),
                                        SaveSharedPreference.getMobileAndPassword(EditProfileActivity.this).get(1));
                                AlertDialog();
                            } else {
                                Toast.makeText(EditProfileActivity.this, "Some error", Toast.LENGTH_LONG).show();
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
                        Log.d("error", "onErrorResponse: "+error.getMessage());
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

                params.put("Vendor_id",SaveSharedPreference.getKeyVendorId(EditProfileActivity.this));
                params.put("Fullname",name);
                params.put("Location",location);
                params.put("Brand",brand);
                params.put("Category",catid);
                params.put("Emailid",email);
                params.put("state_name",state);
                params.put("city_name",city);
                params.put("oldLogo",SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(8));
                params.put("oldBanner",SaveSharedPreference.getVendorProfileData(EditProfileActivity.this).get(9));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */

            @Override
            protected Map<String, DataPart> getByteData() {

                if(bitmapLogo != null || bitmapBan != null){
                    Map<String, DataPart> params = new HashMap<>();
                    if(bitmapLogo!=null && bitmapBan !=null){
                        long logoImageName = System.currentTimeMillis();
                        params.put("Logo", new DataPart(logoImageName + ".png", getFileDataFromDrawable(bitmapLogo)));

                        long banImageName = System.currentTimeMillis();
                        params.put("Banner",new DataPart(banImageName + ".png", getFileDataFromDrawable(bitmapBan)));

                        return params;
                    }
                    else if(bitmapLogo!=null){
                        long logoImageName = System.currentTimeMillis();
                        params.put("Logo", new DataPart(logoImageName + ".png", getFileDataFromDrawable(bitmapLogo)));
                        return params;
                    }
                    else if(bitmapBan!=null){
                        long banImageName = System.currentTimeMillis();
                        params.put("Banner",new DataPart(banImageName + ".png", getFileDataFromDrawable(bitmapBan)));

                        return params;
                    }
                    else{
                        return null;
                    }
                }
                else {
                    return null;
                }
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

    public void changeData (String mobile,String password){


        ApiUtils.getServiceClass().logIn(mobile,password).enqueue(new Callback<LogInModel>() {
            @Override
            public void onResponse(Call<LogInModel> call, Response<LogInModel> response) {
                if(response.isSuccessful())
                {
                    Log.d("login",response.body().getResponse());
                    if(response.body().getResponse().equalsIgnoreCase("success")){

                        if(response.body().getDetailsModels().getRoleName().equalsIgnoreCase("user")){

                            Toast.makeText(EditProfileActivity.this, "This Service is Not Yet Available", Toast.LENGTH_SHORT).show();
//                           SaveSharedPreference.setRole(LoginActivity.this,
//                                   response.body().getDetailsModels().getRoleId(),
//                                   response.body().getDetailsModels().getRoleName());
//
//                           SaveSharedPreference.setUserProfileData(LoginActivity.this,
//                                   response.body().getDetailsModels().getUserId(),
//                                   response.body().getDetailsModels().getFullName(),
//                                   response.body().getDetailsModels().getLocation(),
//                                   response.body().getDetailsModels().getMobile(),
//                                   response.body().getDetailsModels().getReferenceId());
//
//                           SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
//                           startActivity(new Intent(LoginActivity.this, UserActivity.class));
//                           finish();
                        }
                        else if(response.body().getDetailsModels().getRoleName().equalsIgnoreCase("vendor")){

                            SaveSharedPreference.setVendorProfileData(EditProfileActivity.this,
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

                            Log.d("idemail", "onResponse: "+response.body().getDetailsModels().getEmail()+" / "+response.body().getDetailsModels().getCity()+" / "+response.body().getDetailsModels().getState());

                        }
                    }
                    else if(response.body().getResponse().equalsIgnoreCase("failed")){
                        Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LogInModel> call, Throwable t) {



                Log.d("loginfail", "onFailure: "+t);
            }
        });


    }
}