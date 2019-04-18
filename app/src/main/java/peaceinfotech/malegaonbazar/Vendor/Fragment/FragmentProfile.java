package peaceinfotech.malegaonbazar.Vendor.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.RetrofitModel.LogInModel;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.Signup.RegisterActivity;
import peaceinfotech.malegaonbazar.StartUI.LoginActivity;
import peaceinfotech.malegaonbazar.Vendor.UI.EditProfileActivity;
import peaceinfotech.malegaonbazar.Vendor.UI.VendorActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProfile extends Fragment {

    ImageView imgBan;
    TextView tvName,tvBrandName,tvLocation,tvMobile,tvEmail,tvCatName,tvState,tvCity;
    Button btEdit;
    List<String> data = new ArrayList<>();
    ProgressBar pbInProfile;
    ScrollView svProfile;
    CircleImageView imgLogo;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_vendor_profile,container,false);

        imgBan=view.findViewById(R.id.img_banner);
        tvName=view.findViewById(R.id.tv_vendor_name);
        tvBrandName=view.findViewById(R.id.tv_vendor_bname);
        tvLocation=view.findViewById(R.id.tv_vendor_location);
        tvMobile=view.findViewById(R.id.tv_vendor_mobile);
        tvEmail=view.findViewById(R.id.tv_vendor_email);
        tvState=view.findViewById(R.id.tv_vendor_city);
        tvCity=view.findViewById(R.id.tv_vendor_state);
        tvCatName=view.findViewById(R.id.tv_cat_name);
        btEdit=view.findViewById(R.id.bt_ven_edit_profile);
        pbInProfile=view.findViewById(R.id.progress_in_profile);
        svProfile=view.findViewById(R.id.scroll_in_profile);
        imgLogo=view.findViewById(R.id.img_profile_logo);

        LogIn(SaveSharedPreference.getMobileAndPassword(getActivity()).get(0),SaveSharedPreference.getMobileAndPassword(getActivity()).get(1));

        getData();

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");
    }



    public void getData(){


        tvName.setText(SaveSharedPreference.getVendorProfileData(getActivity()).get(1));
        tvLocation.setText(SaveSharedPreference.getVendorProfileData(getActivity()).get(2));
        tvMobile.setText(SaveSharedPreference.getVendorProfileData(getActivity()).get(3));
        tvBrandName.setText(SaveSharedPreference.getVendorProfileData(getActivity()).get(4));
        tvEmail.setText(SaveSharedPreference.getVendorProfileData(getActivity()).get(6));
        tvCatName.setText(SaveSharedPreference.getVendorProfileData(getActivity()).get(5));
        tvCity.setText(SaveSharedPreference.getVendorProfileData(getActivity()).get(10));
        tvState.setText(SaveSharedPreference.getVendorProfileData(getActivity()).get(11));

        Log.d("email", "getData: "+SaveSharedPreference.getVendorProfileData(getActivity()).get(6)+" / "+SaveSharedPreference.getVendorProfileData(getActivity()).get(10)+" / "+SaveSharedPreference.getVendorProfileData(getActivity()).get(11));

        if(!SaveSharedPreference.getVendorProfileData(getActivity()).get(8).isEmpty())
        {
            Picasso.with(getActivity())
                .load("http://autoreplyz.com/Malegaon/"+SaveSharedPreference.getVendorProfileData(getActivity()).get(8))
                .fit()
                .centerCrop()
                .into(imgLogo);
        }

        if(!SaveSharedPreference.getVendorProfileData(getActivity()).get(9).isEmpty())
        {
            Picasso.with(getActivity())
                    .load("http://autoreplyz.com/Malegaon/" + SaveSharedPreference.getVendorProfileData(getActivity()).get(9))
                    .fit()
                    .centerCrop()
                    .into(imgBan);
        }
        pbInProfile.setVisibility(View.GONE);
        svProfile.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    public void LogIn (final String mobile, final String password) {


        ApiUtils.getServiceClass().logIn(mobile, password).enqueue(new Callback<LogInModel>() {
            @Override
            public void onResponse(Call<LogInModel> call, Response<LogInModel> response) {
                if (response.isSuccessful()) {
                    Log.d("login", response.body().getResponse());
                    if (response.body().getResponse().equalsIgnoreCase("success")) {

                        if (response.body().getDetailsModels().getRoleName().equalsIgnoreCase("user")) {

//                            SaveSharedPreference.setRole(RegisterActivity.this,
//                                    response.body().getDetailsModels().getRoleId(),
//                                    response.body().getDetailsModels().getRoleName());
//
//                            SaveSharedPreference.setUserProfileData(RegisterActivity.this,
//                                    response.body().getDetailsModels().getUserId(),
//                                    response.body().getDetailsModels().getFullName(),
//                                    response.body().getDetailsModels().getLocation(),
//                                    response.body().getDetailsModels().getMobile(),
//                                    response.body().getDetailsModels().getReferenceId());

                        } else if (response.body().getDetailsModels().getRoleName().equalsIgnoreCase("vendor")) {
                            SaveSharedPreference.setRole(getActivity(),
                                    response.body().getDetailsModels().getRoleID(),
                                    response.body().getDetailsModels().getRoleName());

                            SaveSharedPreference.setVendorProfileData(getActivity(),
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

                            SaveSharedPreference.setVendorLatLng(getActivity(),
                                    response.body().getDetailsModels().getLat(),
                                    response.body().getDetailsModels().getLng());

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


}
