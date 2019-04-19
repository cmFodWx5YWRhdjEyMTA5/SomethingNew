package peaceinfotech.malegaonbazar.User.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.User.Adapter.WalletHistoryAdapter;
import peaceinfotech.malegaonbazar.User.Model.WalletHistoryModel;


public class FragmentWallet extends Fragment {

    Random random=new Random();
    TextView tvRefId,tvAmount,tvUserNum;
    RecyclerView recyclerView;
    List<WalletHistoryModel> histList;
    WalletHistoryAdapter walletHistoryAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_user_wallet,container,false);

        tvRefId = view.findViewById(R.id.tv_ref_id);
        tvAmount=view.findViewById(R.id.tv_amount);
        tvUserNum=view.findViewById(R.id.tv_number_user);
        recyclerView=view.findViewById(R.id.recycler_user_wallet);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL   ,false);
        recyclerView.setLayoutManager(layoutManager);

        tvRefId.setText(SaveSharedPreference.getUserProfileData(getActivity()).get(5));

        histList = new ArrayList<>();
        for(int i=0;i<5;i++){
            histList.add(i,new WalletHistoryModel("Noodels","\u20B9 1000","Golden Dragon","23/01/2019"));
        }
        walletHistoryAdapter = new WalletHistoryAdapter(histList,getActivity());
        recyclerView.setAdapter(walletHistoryAdapter);
        walletHistoryAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Wallet");
    }
}
