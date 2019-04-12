package peaceinfotech.malegaonbazar.Vendor.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.RetrofitModel.AddServiceModel;
import peaceinfotech.malegaonbazar.RetrofitModel.ServiceHomeModel;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.Vendor.Adapter.ServicesListAdapter;
import peaceinfotech.malegaonbazar.Vendor.Model.ServicesListModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentServices extends Fragment {

    EditText etServiceName,etServiceDesc;
    TextView tvDemo;
    Button btAddService;
    List<ServicesListModel> serviceList;
    ServicesListAdapter servicesListAdapter;
    ServicesListAdapter listAdapter = new ServicesListAdapter();
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendor_services,container,false);


        tvDemo=view.findViewById(R.id.tv_demo);
        etServiceName=view.findViewById(R.id.et_service_name);
        etServiceDesc=view.findViewById(R.id.et_service_desc);
        btAddService=view.findViewById(R.id.bt_add_service);
        recyclerView=view.findViewById(R.id.recycle_service);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        getServices();

        btAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etServiceName.getText().toString().isEmpty()||etServiceDesc.getText().toString().isEmpty()){
                    if(etServiceName.getText().toString().isEmpty())
                    {
                        etServiceName.setError("Please Enter this Field");
                    }
                    if(etServiceDesc.getText().toString().isEmpty())
                    {
                        etServiceDesc.setError("Please Enter this Field");
                    }
                }
                else{
                    addService(etServiceName.getText().toString(),etServiceDesc.getText().toString());
                }
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Services");
    }



    public void addService(String name,String desc){
        ApiUtils.getServiceClass().addService(SaveSharedPreference.getKeyVendorId(getActivity()),name,desc).enqueue(new Callback<AddServiceModel>() {
            @Override
            public void onResponse(Call<AddServiceModel> call, Response<AddServiceModel> response) {
                Log.d("success", "onResponse: "+response.body().getResponse());
                if(response.isSuccessful()){
                    if(response.body().getResponse().equalsIgnoreCase("success")){
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        etServiceName.setText("");
                        etServiceDesc.setText("");

                        getServices();
                    }
                    else if(response.body().getResponse().equalsIgnoreCase("failed")){
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddServiceModel> call, Throwable t) {
                Log.d("failed", "onFailure: "+t);
            }
        });
    }




    public void getServices(){
        ApiUtils.getServiceClass().getServices(SaveSharedPreference.getKeyVendorId(getActivity())).enqueue(new Callback<ServiceHomeModel>() {
            @Override
            public void onResponse(Call<ServiceHomeModel> call, Response<ServiceHomeModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getResponse().equalsIgnoreCase("success")){

                        serviceList = new ArrayList<>();

                        for(int i=0;i<response.body().getServiceListModels().size();i++)
                        {
                            serviceList.add(i,new ServicesListModel(response.body().getServiceListModels().get(i).getServiceId(),
                                    response.body().getServiceListModels().get(i).getServiceName(),
                                    response.body().getServiceListModels().get(i).getServiceDesc()));
                        }

                        servicesListAdapter = new ServicesListAdapter(getActivity(),serviceList);
                        recyclerView.setAdapter(servicesListAdapter);
                        servicesListAdapter.notifyDataSetChanged();

                    }
                    else if(response.body().getResponse().equalsIgnoreCase("failed")){
                        Toast.makeText(getActivity(),"No services to show please add a service.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServiceHomeModel> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getServices();
    }
}
