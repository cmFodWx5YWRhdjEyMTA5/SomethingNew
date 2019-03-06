package peaceinfotech.malegaonbazar.Vendor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.User.OffersListAdapter;

public class OfferPreviewAdapter extends RecyclerView.Adapter<OfferPreviewAdapter.PreviewHolder> {

    List<OfferPreviewModel> offerPreviewList;
    Context context;

    public OfferPreviewAdapter(List<OfferPreviewModel> offerPreviewList, Context context) {
        this.offerPreviewList = offerPreviewList;
        this.context = context;
    }


    public class PreviewHolder extends RecyclerView.ViewHolder{

        public TextView offertitle,offer;
        public ImageView image;
        public Button getbutton;

        public PreviewHolder(@NonNull View view) {
            super(view);

            offertitle=view.findViewById(R.id.tvoffertitle);
            offer=view.findViewById(R.id.tvoffer);
            image=view.findViewById(R.id.imgoffer);
            getbutton=view.findViewById(R.id.btoffer);
        }
    }

    @NonNull
    @Override
    public PreviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.offers_list_item,viewGroup,false);

        return new OfferPreviewAdapter.PreviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewHolder holder, int i) {


    }

    @Override
    public int getItemCount() {
        return offerPreviewList.size();
    }
}
