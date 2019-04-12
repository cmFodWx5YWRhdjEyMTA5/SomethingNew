package peaceinfotech.malegaonbazar.Vendor.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Vendor.Model.RequestListModel;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.RequestViewHolder> {

    List<RequestListModel> requestList;
    Context context;

    public RequestListAdapter(List<RequestListModel> requestList, Context context) {
        this.requestList = requestList;
        this.context = context;
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder{

        TextView name,price,product,tvAccept,tvReject;
        Button accept,reject;
        RelativeLayout relayButton;

        public RequestViewHolder(@NonNull View view) {
            super(view);

            name= view.findViewById(R.id.tv_custname);
            price=view.findViewById(R.id.tv_price);
            product=view.findViewById(R.id.tv_product);
            accept=view.findViewById(R.id.bt_accept);
            reject=view.findViewById(R.id.bt_reject);
            relayButton=view.findViewById(R.id.relay_accept_reject);
            tvAccept=view.findViewById(R.id.tv_request_accept);
            tvReject=view.findViewById(R.id.tv_request_reject);
        }
    }


    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.request_item_layout,viewGroup,false);
        return new RequestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestViewHolder holder, int position) {

        RequestListModel request = requestList.get(position);

        holder.name.setText(request.getName());
        holder.price.setText(request.getPrice());
        holder.product.setText(request.getProduct());

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.relayButton.setVisibility(View.GONE);
                holder.tvAccept.setVisibility(View.VISIBLE);
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.relayButton.setVisibility(View.GONE);
                holder.tvReject.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }
}
