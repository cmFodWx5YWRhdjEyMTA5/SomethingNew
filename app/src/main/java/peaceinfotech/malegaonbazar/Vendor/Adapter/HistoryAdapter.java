package peaceinfotech.malegaonbazar.Vendor.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Vendor.Model.HistoryModel;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    public List<HistoryModel> historyList;
    public Context context;

    public HistoryAdapter(List<HistoryModel> historyList, Context context) {
        this.historyList = historyList;
        this.context = context;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        public TextView tvProductName,tvProductCost, tvBuyerName,tvPaymentMode;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductCost=itemView.findViewById(R.id.tv_product_cost);
            tvBuyerName=itemView.findViewById(R.id.tv_buyer_name);
            tvPaymentMode=itemView.findViewById(R.id.tv_payment_mode);
        }
    }


    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.history_list_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int i) {
        HistoryModel history = historyList.get(i);

        holder.tvProductName.setText(history.getProductName());
        holder.tvProductCost.setText(history.getProductCost());
        holder.tvBuyerName.setText(history.getBuyerName());
        holder.tvPaymentMode.setText(history.getPaymentMode());

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}
