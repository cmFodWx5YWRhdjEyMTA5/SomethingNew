package peaceinfotech.malegaonbazar.Vendor.Fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.Vendor.OfferPreviewAdapter;
import peaceinfotech.malegaonbazar.Vendor.OfferPreviewModel;

public class FragmentOfferList extends Fragment {


    List<String> getList = new ArrayList<>();
    List<OfferPreviewModel> offerList=new ArrayList<>();
    RecyclerView recyclerView;
    OfferPreviewAdapter offerPreviewAdapter;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_vendor_offer_list,container,false);

        recyclerView=view.findViewById(R.id.preview_recycler);
        textView=view.findViewById(R.id.demo);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        getList=SaveSharedPreference.getNewOffers(getActivity());

        String title=getList.get(0);
        String desc=getList.get(1);
        String min=getList.get(2);
        String max=getList.get(3);

        for(int i=0;i<5;i++) {

            offerList.add(i,new OfferPreviewModel(title, desc, "1", min, max));

        }

//        if (!title.isEmpty() && !desc.isEmpty() && !min.isEmpty() && !max.isEmpty()) {
//
//
////
//
//        } else {
//            Toast.makeText(getActivity(), "Some Error Please Restart the application", Toast.LENGTH_LONG).show();
//        }

        offerPreviewAdapter = new OfferPreviewAdapter(offerList, getActivity());
        recyclerView.setAdapter(offerPreviewAdapter);
        offerPreviewAdapter.notifyDataSetChanged();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Offers List");
    }
}
