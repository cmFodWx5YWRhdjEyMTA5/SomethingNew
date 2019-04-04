package peaceinfotech.malegaonbazar.Vendor.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.List;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Vendor.Model.OfferPreviewModel;
import peaceinfotech.malegaonbazar.Vendor.UI.EditOfferActivity;

public class OfferPreviewAdapter extends RecyclerView.Adapter<OfferPreviewAdapter.PreviewHolder> {

    List<OfferPreviewModel> offerPreviewList;
    Context context;


    public OfferPreviewAdapter(List<OfferPreviewModel> offerPreviewList, Context context) {
        this.offerPreviewList = offerPreviewList;
        this.context = context;
    }

    public class PreviewHolder extends RecyclerView.ViewHolder{

        public TextView offertitle,offer,offerPrice,finalPrice,min,max,delete,start,expiry,terms,termsCondition,edit;
        public ImageView image;
        public Button getbutton;
        public LinearLayout linearMain;
        public RelativeLayout relayTerms;
        public ElegantNumberButton quantity;

        public PreviewHolder(@NonNull View view) {
            super(view);

            offertitle=view.findViewById(R.id.tvoffertitle);
            offer=view.findViewById(R.id.tvoffer);
            offerPrice=view.findViewById(R.id.tv_ven_offer_price);
            finalPrice=view.findViewById(R.id.tv_ven_final_price);
            image=view.findViewById(R.id.imgoffer);
            min=view.findViewById(R.id.tv_min);
            max=view.findViewById(R.id.tv_max);
            getbutton=view.findViewById(R.id.btoffer);
            delete=view.findViewById(R.id.tv_delete);
            start=view.findViewById(R.id.tv_st_date);
            expiry=view.findViewById(R.id.tv_ex_date);
            linearMain=view.findViewById(R.id.linear_main);
            relayTerms=view.findViewById(R.id.relay_terms);
            terms=view.findViewById(R.id.tv_terms);
            termsCondition=view.findViewById(R.id.tv_terms_condition);
            edit=view.findViewById(R.id.tv_edit);
            quantity=view.findViewById(R.id.eln_ven_quantity);
        }
    }

    @NonNull
    @Override
    public PreviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.offer_list_item_vendor,viewGroup,false);

        return new OfferPreviewAdapter.PreviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PreviewHolder holder, final int i) {

        final OfferPreviewModel offers = offerPreviewList.get(i);

        holder.offertitle.setText(offers.getOfferTitle());
        holder.offer.setText(offers.getOfferDesc());
        holder.min.setText(offers.getMin());
        holder.max.setText(offers.getMax());
        holder.start.setText(offers.getStart_date());
        holder.expiry.setText(offers.getEnd_date());
        holder.termsCondition.setText(offers.getTerms());

//
//
//        final String uid=offers.getUid();

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete this Offer");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Animation exit= AnimationUtils.loadAnimation(context,R.anim.left_swipe);
                        holder.linearMain.startAnimation(exit);
                        holder.linearMain.setVisibility(View.GONE);
                        offerPreviewList.remove(i);
                        notifyItemRemoved(i);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent putIntent = new Intent(context, EditOfferActivity.class);
                putIntent.putExtra("id","dfg");
                context.startActivity(putIntent);
            }
        });

        holder.terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.relayTerms.getVisibility()==View.GONE){
                    holder.relayTerms.setVisibility(View.VISIBLE);
                }
                else if(holder.relayTerms.getVisibility()==View.VISIBLE){
                    holder.relayTerms.setVisibility(View.GONE);
                }
            }
        });

        holder.quantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
//                if(newValue>0){
//                    int amount = Integer.parseInt(offers.getOfferPrice())*newValue;
//                    int discount = amount*Integer.parseInt(offers.getDiscPercent())/100;
//                    int finalPrice=amount-discount;
//
//                    holder.finalPrice.setText("\u20b9 "+finalPrice);
//                }
//                else if(newValue==0)
//                    holder.finalPrice.setText("");
            }
        });


    }

    @Override
    public int getItemCount() {
        return offerPreviewList.size();
    }

}
