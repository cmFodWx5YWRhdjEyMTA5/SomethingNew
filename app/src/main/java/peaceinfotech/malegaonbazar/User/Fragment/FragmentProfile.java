package peaceinfotech.malegaonbazar.User.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.RetrofitModel.LogInModel;
import peaceinfotech.malegaonbazar.RetrofitModel.ResponseMessageModel;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.Signup.RegisterActivity;
import peaceinfotech.malegaonbazar.Signup.VolleyMultipartRequest;
import peaceinfotech.malegaonbazar.Vendor.UI.EditProfileActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class FragmentProfile extends Fragment {

    TextView tvName,tvMobile,tvReference,tvEmail,tvLocation,tvCity;
    EditText etName,etLoc,etCity;
    Button  btEdit,btEditProf,btUpdate;
    Dialog editDialog;
    public static final int PROF=1;
    CircleImageView imgUserProf;
    ImageView  imgDemo;
    Bitmap bitmapProf;
    TextView tvProf;
    ProgressBar pbEditProf;
    private RequestQueue requestQueue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_user_profile,container,false);
        tvName=view.findViewById(R.id.tv_user_name);
        tvMobile=view.findViewById(R.id.tv_user_mobile);
        tvReference=view.findViewById(R.id.tv_user_ref);
        tvEmail=view.findViewById(R.id.tv_user_email);
        tvLocation=view.findViewById(R.id.tv_user_loc);
        tvCity=view.findViewById(R.id.tv_user_city);
        btEdit=view.findViewById(R.id.bt_edit_user);
        imgUserProf=view.findViewById(R.id.img_user_profile);
        LogIn(SaveSharedPreference.getUserMobilePassword(getActivity()).get(0),SaveSharedPreference.getUserMobilePassword(getActivity()).get(1));


        getData();

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog = new Dialog(getActivity());
                editDialog.setContentView(R.layout.dialog_edit_user_profile);
                etName=editDialog.findViewById(R.id.et_edit_user_name);
                etLoc=editDialog.findViewById(R.id.et_edit_user_location);
                etCity=editDialog.findViewById(R.id.et_edit_user_City);
                tvProf=editDialog.findViewById(R.id.tv_user_prof_path);
                pbEditProf=editDialog.findViewById(R.id.progress_edit_user_profile);
                btUpdate=editDialog.findViewById(R.id.bt_user_update_profile);
                btEditProf=editDialog.findViewById(R.id.bt_edit_user_prof);
                imgDemo=editDialog.findViewById(R.id.img_edit_user_demo);

                etName.setText(SaveSharedPreference.getUserProfileData(getActivity()).get(1));
                etLoc.setText(SaveSharedPreference.getUserProfileData(getActivity()).get(2));
                etCity.setText(SaveSharedPreference.getUserProfileData(getActivity()).get(3));

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(etName.getText().toString().isEmpty()||etLoc.getText().toString().isEmpty()||etCity.getText().toString().isEmpty()){
                            if(etName.getText().toString().isEmpty())
                            {
                                etName.setError("Please Enter this Field");
                            }
                            if(etLoc.getText().toString().isEmpty())
                            {
                                etLoc.setError("Please Enter this Field");
                            }
                            if(etCity.getText().toString().isEmpty())
                            {
                                etCity.setError("Please Enter this Field");
                            }
                        }
                        else
                        {
                            pbEditProf.setVisibility(View.VISIBLE);
                            btUpdate.setVisibility(View.GONE);
                            requestQueue = Volley.newRequestQueue(getActivity());
//                           ApiUtils.getServiceClass().editUserProfile(SaveSharedPreference.getUserProfileData(getActivity()).get(0),
//                                   etName.getText().toString(),etLoc.getText().toString(),etCity.getText().toString()).enqueue(new Callback<ResponseMessageModel>() {
//                               @Override
//                               public void onResponse(Call<ResponseMessageModel> call, Response<ResponseMessageModel> response) {
//                                   if(response.isSuccessful()){
//                                       if(response.body().getResponse().equalsIgnoreCase("success")){
//                                           Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                           LogIn(SaveSharedPreference.getUserMobilePassword(getActivity()).get(0),
//                                                   SaveSharedPreference.getUserMobilePassword(getActivity()).get(1));
//                                           editDialog.dismiss();
//                                           tvName.setText(etName.getText());
//                                           tvLocation.setText(etLoc.getText());
//                                           tvCity.setText(etCity.getText());
//                                       }
//                                       else if(response.body().getResponse().equalsIgnoreCase("failed")){
//                                           Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
//                                       }
//                                   }
//                               }
//
//                               @Override
//                               public void onFailure(Call<ResponseMessageModel> call, Throwable t) {
//
//                               }
//                           });
                            userUploadBitmap(bitmapProf,etName.getText().toString(),etLoc.getText().toString(),etCity.getText().toString(),SaveSharedPreference.getUserProfileData(getActivity()).get(0));
                        }

                    }
                });

                btEditProf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i,PROF);
                    }
                });

                editDialog.create();
                editDialog.show();

            }
        });

        return view;
    }

    public String getFileName (String picturePath){
        String str[] = picturePath.split("/");
        List<String> al = new ArrayList<String>();
        al = Arrays.asList(str);

        return al.get(al.size()-1);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");
    }

    public void getData(){

        tvName.setText(SaveSharedPreference.getUserProfileData(getActivity()).get(1));
        tvLocation.setText(SaveSharedPreference.getUserProfileData(getActivity()).get(2));
        tvCity.setText(SaveSharedPreference.getUserProfileData(getActivity()).get(3));
        tvMobile.setText(SaveSharedPreference.getUserProfileData(getActivity()).get(4));
        tvReference.setText(SaveSharedPreference.getUserProfileData(getActivity()).get(5));

        if(!SaveSharedPreference.getVendorProfileData(getActivity()).get(8).isEmpty())
        {
            Picasso.with(getActivity())
                    .load("http://autoreplyz.com/Malegaon/"+SaveSharedPreference.getUserProfileData(getActivity()).get(6))
                    .fit()
                    .centerCrop()
                    .into(imgUserProf);
        }
    }

    public void LogIn (final String mobile, final String password) {


        ApiUtils.getServiceClass().logIn(mobile, password).enqueue(new Callback<LogInModel>() {
            @Override
            public void onResponse(Call<LogInModel> call, Response<LogInModel> response) {
                if (response.isSuccessful()) {
                    Log.d("login", response.body().getResponse());
                    if (response.body().getResponse().equalsIgnoreCase("success")) {

                        if (response.body().getDetailsModels().getRoleName().equalsIgnoreCase("user")) {

//                            SaveSharedPreference.setRole(getActivity(),
//                                    response.body().getDetailsModels().getRoleID(),
//                                    response.body().getDetailsModels().getRoleName());
//
//                            SaveSharedPreference.setUserProfileData(getActivity(),
//                                    response.body().getDetailsModels().getUserId(),
//                                    response.body().getDetailsModels().getFullName(),
//                                    response.body().getDetailsModels().getLocation(),
//                                    response.body().getDetailsModels().getCity(),
//                                    response.body().getDetailsModels().getMobile(),
//                                    response.body().getDetailsModels().getReferenceId(),
//                                    response.body().getDetailsModels().getProfileUrl());

                        } else if (response.body().getDetailsModels().getRoleName().equalsIgnoreCase("vendor")) {
//                            SaveSharedPreference.setRole(getActivity(),
//                                    response.body().getDetailsModels().getRoleID(),
//                                    response.body().getDetailsModels().getRoleName());
//
//                            SaveSharedPreference.setVendorProfileData(getActivity(),
//                                    response.body().getDetailsModels().getVendorId(),
//                                    response.body().getDetailsModels().getFullName(),
//                                    response.body().getDetailsModels().getLocation(),
//                                    response.body().getDetailsModels().getMobile(),
//                                    response.body().getDetailsModels().getBrand(),
//                                    response.body().getDetailsModels().getImgLogoURL(),
//                                    response.body().getDetailsModels().getImgBanURL(),
//                                    response.body().getDetailsModels().getEmail(),
//                                    response.body().getDetailsModels().getCatName(),
//                                    response.body().getDetailsModels().getCatID(),
//                                    response.body().getDetailsModels().getState(),
//                                    response.body().getDetailsModels().getCity());
//
//                            SaveSharedPreference.setVendorLatLng(getActivity(),
//                                    response.body().getDetailsModels().getLat(),
//                                    response.body().getDetailsModels().getLng());

                        }
                    } else if (response.body().getResponse().equalsIgnoreCase("failed")) {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LogInModel> call, Throwable t) {


                Log.d("loginfail", "onFailure: " + t);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PROF && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imgDemo.setImageURI(selectedImage);
            bitmapProf = ((BitmapDrawable) imgDemo.getDrawable()).getBitmap();
//            String file = getFileName(picturePath);
//            tvProf.setText(file);
//            Toast.makeText(getActivity(), "Logo Uploaded Successfully : " + picturePath, Toast.LENGTH_SHORT).show();
//            Log.d("path", "onActivityResult: " + picturePath);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        LogIn(SaveSharedPreference.getUserMobilePassword(getActivity()).get(0),SaveSharedPreference.getUserMobilePassword(getActivity()).get(1));
        getData();

    }

    private void userUploadBitmap(final Bitmap bitmapProf,
                                  final String name,
                                  final String location,
                                  final String city,
                                  final String userId){

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST," http://autoreplyz.com/Malegaon/Api/Userapi/updateuser",
                new com.android.volley.Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
//                            JSONObject o=jsonResponse.getJSONObject("details");
                            Log.d("SID", "onResponse: " + obj.getString("response"));
                            //If it is success
                            if (obj.getString("response").equalsIgnoreCase("success")) {

                                pbEditProf.setVisibility(View.GONE);
                                btUpdate.setVisibility(View.VISIBLE);
                                tvName.setText(etName.getText());
                                tvCity.setText(etCity.getText());
                                tvLocation.setText(etLoc.getText());
                                imgUserProf.setImageBitmap(bitmapProf);
                                editDialog.dismiss();
                                Toast.makeText(getActivity(), "Profile Edited Successfully", Toast.LENGTH_LONG).show();
                            } else {
                                pbEditProf.setVisibility(View.GONE);
                                btUpdate.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Some error", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("user_id",userId);
                params.put("Fullname",name);
                params.put("Location",location);
                params.put("City",city);
                params.put("Oldprofile",SaveSharedPreference.getUserProfileData(getActivity()).get(6));

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                if(bitmapProf!=null) {
                    Map<String, DataPart> params = new HashMap<>();
                    long profImageName = System.currentTimeMillis();
                    params.put("Profile", new DataPart(profImageName + ".png", getFileDataFromDrawable(bitmapProf)));
                    return params;
                }
                else {
                    return null;
                }
            }
        };

        requestQueue.add(volleyMultipartRequest);

    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
