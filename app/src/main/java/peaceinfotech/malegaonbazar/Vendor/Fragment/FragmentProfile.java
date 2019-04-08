package peaceinfotech.malegaonbazar.Vendor.Fragment;

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
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.RetrofitModel.LogInModel;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.StartUI.LoginActivity;
import peaceinfotech.malegaonbazar.Vendor.UI.EditProfileActivity;
import peaceinfotech.malegaonbazar.Vendor.UI.VendorActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProfile extends Fragment {

    ImageView imgBan;
    CircularImageView imgLogo;
    TextView tvName,tvBrandName,tvLocation,tvMobile,tvEmail,tvCatName,tvState,tvCity;
    Button btEdit;
    List<String> data = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_vendor_profile,container,false);

        imgBan=view.findViewById(R.id.img_banner);
        imgLogo=view.findViewById(R.id.img_logo);
        tvName=view.findViewById(R.id.tv_vendor_name);
        tvBrandName=view.findViewById(R.id.tv_vendor_bname);
        tvLocation=view.findViewById(R.id.tv_vendor_location);
        tvMobile=view.findViewById(R.id.tv_vendor_mobile);
        tvEmail=view.findViewById(R.id.tv_vendor_email);
        tvState=view.findViewById(R.id.tv_vendor_city);
        tvCity=view.findViewById(R.id.tv_vendor_state);
        tvCatName=view.findViewById(R.id.tv_cat_name);
        btEdit=view.findViewById(R.id.bt_ven_edit_profile);


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
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }



}
