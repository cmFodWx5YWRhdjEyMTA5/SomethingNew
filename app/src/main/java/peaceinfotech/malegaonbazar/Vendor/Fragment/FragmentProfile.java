package peaceinfotech.malegaonbazar.Vendor.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import peaceinfotech.malegaonbazar.R;

public class FragmentProfile extends Fragment {
    Random random=new Random();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_vendor_profile,container,false);

        TextView textView=view.findViewById(R.id.textView3);
        textView.setText("vendorref"+random.nextInt(10000));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");
    }
}
