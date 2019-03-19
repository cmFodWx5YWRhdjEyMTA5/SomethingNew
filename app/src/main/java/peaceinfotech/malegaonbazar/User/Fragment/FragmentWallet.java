package peaceinfotech.malegaonbazar.User.Fragment;


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
import peaceinfotech.malegaonbazar.SaveSharedPreference;


public class FragmentWallet extends Fragment {

    Random random=new Random();
    TextView refId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_user_wallet,container,false);

        refId = view.findViewById(R.id.tv_ref_id);
        String refid = "UseRef"+random.nextInt(10000);

        if(refid != SaveSharedPreference.getUserReference(getActivity())) {
            refId.setText(SaveSharedPreference.getUserReference(getActivity()));
        }
        else {
            refId.setText(refid);
        }


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Wallet");
    }
}
