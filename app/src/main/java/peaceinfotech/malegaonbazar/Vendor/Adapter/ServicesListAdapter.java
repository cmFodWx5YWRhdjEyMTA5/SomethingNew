package peaceinfotech.malegaonbazar.Vendor.Adapter;

import android.content.Context;
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
import peaceinfotech.malegaonbazar.Vendor.Model.ServicesListModel;

public class ServicesListAdapter extends RecyclerView.Adapter<ServicesListAdapter.ServicesViewHolder> {

    Context context;
    List<ServicesListModel> serviceList = new ArrayList<>();

    public ServicesListAdapter(Context context, List<ServicesListModel> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
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
    public void onBindViewHolder(@NonNull ServicesViewHolder holder, int i) {

        ServicesListModel services = serviceList.get(i);

        holder.tvName.setText(services.getServiceName());
        holder.tvDesc.setText(services.getServiceDesc());

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }
}
