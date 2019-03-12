package peaceinfotech.malegaonbazar.Vendor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import peaceinfotech.malegaonbazar.DatabaseHelper;
import peaceinfotech.malegaonbazar.LoginActivity;
import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.User.UI.UserActivity;

public class OfferPreviewAdapter extends RecyclerView.Adapter<OfferPreviewAdapter.PreviewHolder> {

    List<OfferPreviewModel> offerPreviewList;
    Context context;
    Boolean deleteClick=false;

    public OfferPreviewAdapter(List<OfferPreviewModel> offerPreviewList, Context context) {
        this.offerPreviewList = offerPreviewList;
        this.context = context;
    }

    public class PreviewHolder extends RecyclerView.ViewHolder{

        public TextView offertitle,offer,min,max,delete,start,expiry;
        public ImageView image;
        public Button getbutton;
        public LinearLayout linearMain;

        public PreviewHolder(@NonNull View view) {
            super(view);

            offertitle=view.findViewById(R.id.tvoffertitle);
            offer=view.findViewById(R.id.tvoffer);
            image=view.findViewById(R.id.imgoffer);
            min=view.findViewById(R.id.tv_min);
            max=view.findViewById(R.id.tv_max);
            getbutton=view.findViewById(R.id.btoffer);
            delete=view.findViewById(R.id.tv_delete);
            start=view.findViewById(R.id.tv_st_date);
            expiry=view.findViewById(R.id.tv_ex_date);
            linearMain=view.findViewById(R.id.linear_main);
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
        final DatabaseHelper mdb=new DatabaseHelper(context);

        holder.offertitle.setText(offers.getOffersName());
        holder.offer.setText(offers.getOffer());
        holder.min.setText(offers.getMin());
        holder.max.setText(offers.getMax());
        holder.start.setText(offers.getStart_date());
        holder.expiry.setText(offers.getEnd_date());
        final String uid=offers.getUid();

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
                        mdb.DeleteData(uid);
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
    }

    @Override
    public int getItemCount() {
        return offerPreviewList.size();
    }

}
