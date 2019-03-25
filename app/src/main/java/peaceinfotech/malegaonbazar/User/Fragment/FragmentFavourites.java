package peaceinfotech.malegaonbazar.User.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.User.Adapter.FavouriteListAdapter;
import peaceinfotech.malegaonbazar.User.Model.FavouriteListModel;

public class FragmentFavourites extends Fragment {
    RecyclerView recyclerView;
    List<FavouriteListModel> favList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_favourites,container,false);

        recyclerView = view.findViewById(R.id.recycle_fav);
        favList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        for(int i=0;i<5;i++){
            favList.add(new FavouriteListModel("sddfsdf","54","+91-8548548548","sid.j@gmail.com","vasind","5",3.5f));
        }

        FavouriteListAdapter favouriteListAdapter = new FavouriteListAdapter(getActivity(),favList);
        recyclerView.setAdapter(favouriteListAdapter);
        favouriteListAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Favourites");
    }
}
