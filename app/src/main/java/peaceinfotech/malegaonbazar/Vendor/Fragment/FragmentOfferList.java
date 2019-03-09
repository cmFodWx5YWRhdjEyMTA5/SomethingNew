package peaceinfotech.malegaonbazar.Vendor.Fragment;

import android.database.Cursor;
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

import peaceinfotech.malegaonbazar.DatabaseHelper;
import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Vendor.OfferPreviewAdapter;
import peaceinfotech.malegaonbazar.Vendor.OfferPreviewModel;

public class FragmentOfferList extends Fragment {



    List<OfferPreviewModel> offerList=new ArrayList<>();
    RecyclerView recyclerView;
    OfferPreviewAdapter offerPreviewAdapter;
    TextView textView;
    DatabaseHelper myDb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_vendor_offer_list,container,false);

        recyclerView=view.findViewById(R.id.preview_recycler);
        textView=view.findViewById(R.id.demo);
        myDb=new DatabaseHelper(getActivity());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        Cursor res=myDb.GetAllOffers();

        if(res.getCount()==0){
            Toast.makeText(getActivity(),"No offers to Show",Toast.LENGTH_LONG).show();
        }
        else {
            while (res.moveToNext()) {
                offerList.add(new OfferPreviewModel(res.getString(0),res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5)));
            }
            offerPreviewAdapter = new OfferPreviewAdapter(offerList, getActivity());
            recyclerView.setAdapter(offerPreviewAdapter);
            offerPreviewAdapter.notifyDataSetChanged();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Offers List");
    }

    @Override
    public void onResume() {
        super.onResume();

      //  reloadRecycle();

    }

    public void reloadRecycle(){
        Cursor res=myDb.GetAllOffers();

        if(res.getCount()==0){
            Toast.makeText(getActivity(),"No offers to Show",Toast.LENGTH_LONG).show();
        }
        else {
            while (res.moveToNext()) {
                offerList.add(new OfferPreviewModel(res.getString(0),res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5)));
            }
            offerPreviewAdapter = new OfferPreviewAdapter(offerList, getActivity());
            recyclerView.setAdapter(offerPreviewAdapter);
            offerPreviewAdapter.notifyDataSetChanged();
        }
    }

}
