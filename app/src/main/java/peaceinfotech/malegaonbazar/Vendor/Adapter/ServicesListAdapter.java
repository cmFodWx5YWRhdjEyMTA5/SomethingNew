package peaceinfotech.malegaonbazar.Vendor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.RetrofitModel.ResponseMessageModel;
import peaceinfotech.malegaonbazar.Vendor.Model.ServicesListModel;
import peaceinfotech.malegaonbazar.Vendor.UI.EditServiceActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesListAdapter extends RecyclerView.Adapter<ServicesListAdapter.ServicesViewHolder> {

    Context context;
    List<ServicesListModel> serviceList = new ArrayList<>();

    public ServicesListAdapter(Context context, List<ServicesListModel> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    public ServicesListAdapter(){

    }

    public class ServicesViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName,tvDesc;
        public Button btDelete,btEdit;


        public ServicesViewHolder(@NonNull View view) {
            super(view);

            tvName=view.findViewById(R.id.tv_service_name);
            tvDesc=view.findViewById(R.id.tv_service_desc);
            btDelete=view.findViewById(R.id.bt_service_delete);
            btEdit=view.findViewById(R.id.bt_service_edit);
        }
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ServicesViewHolder(LayoutInflater.from(context).inflate(R.layout.vendor_service_list,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesViewHolder holder, final int i) {

        final ServicesListModel services = serviceList.get(i);

        holder.tvName.setText(services.getServiceName());
        holder.tvDesc.setText(services.getServiceDesc());

        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent putIntent = new Intent(context, EditServiceActivity.class);
                putIntent.putExtra("service_id",services.getServiceId());
                putIntent.putExtra("service_name",services.getServiceName());
                putIntent.putExtra("service_desc",services.getServiceDesc());
                context.startActivity(putIntent);
            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtils.getServiceClass().deleteServices(services.getServiceId()).enqueue(new Callback<ResponseMessageModel>() {
                    @Override
                    public void onResponse(Call<ResponseMessageModel> call, Response<ResponseMessageModel> response) {
                        if(response.isSuccessful()){
                            if(response.body().getResponse().equalsIgnoreCase("success")){
                                serviceList.remove(i);
                                notifyItemRemoved(i);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMessageModel> call, Throwable t) {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }


}
