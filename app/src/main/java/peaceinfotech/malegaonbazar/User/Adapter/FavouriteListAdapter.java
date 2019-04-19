package peaceinfotech.malegaonbazar.User.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.User.Model.FavouriteListModel;

public class FavouriteListAdapter extends RecyclerView.Adapter<FavouriteListAdapter.FavViewHolder> {

    private Context context;
    private List<FavouriteListModel> favList;

    public FavouriteListAdapter(Context context, List<FavouriteListModel> favList) {
        this.context = context;
        this.favList = favList;
    }

    public class FavViewHolder extends RecyclerView.ViewHolder{

        public TextView tvTitle,tvVotes,tvMobile,tvEmail,tvAddress,tvOffers;
        public RatingBar ratingBar;
        public ImageView imgDel;

        public FavViewHolder(@NonNull View view) {
            super(view);

            tvTitle = view.findViewById(R.id.tv_list_title);
            tvVotes = view.findViewById(R.id.tv_list_votes);
            tvMobile = view.findViewById(R.id.tv_list_contact);
            tvEmail = view.findViewById(R.id.tv_list_email);
            tvAddress = view.findViewById(R.id.tv_list_vic);
            tvOffers = view.findViewById(R.id.tv_list_offer_num);
            ratingBar = view.findViewById(R.id.rating_list_star);
            imgDel=view.findViewById(R.id.img_fav_del);
        }
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FavViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favourites_list_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, final int i) {

        final FavouriteListModel fav  = favList.get(i);

        holder.ratingBar.setRating(fav.getRating());
        holder.tvTitle.setText(fav.getTitle());
        holder.tvVotes.setText(fav.getVotes());
        holder.tvOffers.setText(fav.getOffersNum());
        holder.tvAddress.setText(fav.getAddress());
        holder.tvMobile.setText(fav.getContact());
        holder.tvEmail.setText(fav.getEmail());

        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemRemoved(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return favList.size();
    }
}
