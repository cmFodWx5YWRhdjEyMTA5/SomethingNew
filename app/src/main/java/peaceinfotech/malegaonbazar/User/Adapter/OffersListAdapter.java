package peaceinfotech.malegaonbazar.User.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.List;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.User.Model.OffersListModel;

public class OffersListAdapter extends RecyclerView.Adapter<OffersListAdapter.MyviewHolder> {

    List<OffersListModel> offersList;
    Context context;

    public OffersListAdapter(List<OffersListModel> offersList,Context context){
        this.offersList=offersList;
        this.context=context;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{

        public TextView offertitle,offer,min,max,terms,finalPrice,offerPrice;
        public ImageView image;
        public Button getbutton;
        public RelativeLayout relayTerms;
        public ElegantNumberButton quantity;


        public MyviewHolder(@NonNull View view) {
            super(view);
            offertitle=view.findViewById(R.id.tvoffertitle);
            offer=view.findViewById(R.id.tvoffer);
            image=view.findViewById(R.id.imgoffer);
            getbutton=view.findViewById(R.id.btoffer);
            min=view.findViewById(R.id.et_min);
            max=view.findViewById(R.id.et_max);
            terms=view.findViewById(R.id.tv_terms);
            relayTerms=view.findViewById(R.id.relay_terms);
            finalPrice=view.findViewById(R.id.tv_final_price);
            offerPrice=view.findViewById(R.id.tv_offer_price);
            quantity=view.findViewById(R.id.eln_quantity);
        }
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.offers_list_item_user,viewGroup,false);

        return new MyviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, int i) {

        final OffersListModel offers = offersList.get(i);


        holder.offertitle.setText(offers.getOffersName());
        holder.offer.setText(offers.getOffer());
        holder.offerPrice.setText("\u20B9 "+offers.getOffersPrice());
        holder.terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.relayTerms.getVisibility()==View.VISIBLE){
                    holder.relayTerms.setVisibility(View.GONE);
                }
                else if(holder.relayTerms.getVisibility()==View.GONE){
                    holder.relayTerms.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.quantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                if(newValue>0){
                    int amount = newValue*offers.getOffersPrice();
                    int discount = amount*offers.getOfferPercent()/100;
                    int finalAmount = amount - discount;
                    holder.finalPrice.setText("\u20b9 "+finalAmount);
                }
                else if(newValue == 0){
                    holder.finalPrice.setText("");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }
}
