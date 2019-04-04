package peaceinfotech.malegaonbazar.Vendor.Fragment;

import android.content.Intent;
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
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.RetrofitModel.OfferRetroListModel;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.Vendor.Adapter.OfferPreviewAdapter;
import peaceinfotech.malegaonbazar.Vendor.Model.OfferPreviewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOfferList extends Fragment {

    List<OfferPreviewModel> offerList ;
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

        getOffersList();

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
    }

    public void getOffersList(){

        ApiUtils.getServiceClass().getOffers(SaveSharedPreference.getKeyVendorId(getActivity())).enqueue(new Callback<OfferRetroListModel>() {
            @Override
            public void onResponse(Call<OfferRetroListModel> call, Response<OfferRetroListModel> response) {

                if(response.isSuccessful()){
                    if(response.body().getResponse().equalsIgnoreCase("success")){

                        offerList = new ArrayList<>();
                        for(int i=0;i<response.body().getOfferlistModels().size();i++){

                            offerList.add(i,new OfferPreviewModel(response.body().getOfferlistModels().get(i).getOfferId(),
                                    response.body().getOfferlistModels().get(i).getOfferTitle(),
                                    response.body().getOfferlistModels().get(i).getOfferDesc(),
                                    response.body().getOfferlistModels().get(i).getOfferMinTrans(),
                                    response.body().getOfferlistModels().get(i).getOfferMaxTrans(),
                                    response.body().getOfferlistModels().get(i).getOfferStartDate(),
                                    response.body().getOfferlistModels().get(i).getOfferEndDate(),
                                    response.body().getOfferlistModels().get(i).getTermCondition(),
                                    response.body().getOfferlistModels().get(i).getOfferType(),
                                    response.body().getOfferlistModels().get(i).getOfferDiscount()));


                        }
                        offerPreviewAdapter = new OfferPreviewAdapter(offerList,getActivity());
                        recyclerView.setAdapter(offerPreviewAdapter);
                        offerPreviewAdapter.notifyDataSetChanged();
                    }
                    else if(response.body().getResponse().equalsIgnoreCase("failed")){

                    }
                }

            }

            @Override
            public void onFailure(Call<OfferRetroListModel> call, Throwable t) {

            }
        });
    }

}
