package peaceinfotech.malegaonbazar.User.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.User.Model.WalletHistoryModel;

public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.WalletHistoryViewHolder> {

    List<WalletHistoryModel> histList;
    Context context;

    public WalletHistoryAdapter(List<WalletHistoryModel> histList, Context context) {
        this.histList = histList;
        this.context = context;
    }

    public class WalletHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvLoc,tvAmount,tvDate;

        public WalletHistoryViewHolder(@NonNull View view) {
            super(view);

            tvName=view.findViewById(R.id.hist_wall_prod);
            tvAmount=view.findViewById(R.id.hist_wall_amount);
            tvLoc=view.findViewById(R.id.hist_wall_location);
            tvDate=view.findViewById(R.id.hist_wall_date);
        }
    }

    @NonNull
    @Override
    public WalletHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new WalletHistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.user_hist_list_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull WalletHistoryViewHolder holder, int i) {
        WalletHistoryModel history = histList.get(i);

        holder.tvName.setText(history.getProdName());
        holder.tvAmount.setText(history.getProdCost());
        holder.tvLoc.setText(history.getProdLoc());
        holder.tvDate.setText(history.getProdDate());

    }

    @Override
    public int getItemCount() {
        return histList.size();
    }
}
