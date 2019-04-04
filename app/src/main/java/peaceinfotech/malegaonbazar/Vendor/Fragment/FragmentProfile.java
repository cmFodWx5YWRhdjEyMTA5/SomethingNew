package peaceinfotech.malegaonbazar.Vendor.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.SaveSharedPreference;

public class FragmentProfile extends Fragment {

    ImageView imgBan;
    CircularImageView imgLogo;
    TextView tvName,tvBrandName,tvLocation,tvMobile,tvEmail;
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

        tvName.setText(SaveSharedPreference.getVendorProfileData(getActivity()).get(1));
        tvLocation.setText(SaveSharedPreference.getVendorProfileData(getActivity()).get(2));
        tvMobile.setText(SaveSharedPreference.getVendorProfileData(getActivity()).get(3));
        tvBrandName.setText(SaveSharedPreference.getVendorProfileData(getActivity()).get(4));
        tvEmail.setText(SaveSharedPreference.getVendorProfileData(getActivity()).get(5));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");
    }
}
