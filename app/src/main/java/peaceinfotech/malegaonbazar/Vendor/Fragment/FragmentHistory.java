package peaceinfotech.malegaonbazar.Vendor.Fragment;

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
import peaceinfotech.malegaonbazar.Vendor.Adapter.HistoryAdapter;
import peaceinfotech.malegaonbazar.Vendor.Model.HistoryModel;

public class FragmentHistory extends Fragment {
    RecyclerView recyclerView;
    List<HistoryModel> historyList;
    HistoryAdapter historyAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_vendor_history,container,false);

        recyclerView = view.findViewById(R.id.recycler_history);

        historyList=new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        for(int i=0;i<5;i++){
            historyList.add(new HistoryModel("Food","100","Siddhant Jain","Cash"));
        }

        historyAdapter = new HistoryAdapter(historyList,getActivity());
        recyclerView.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("History");
    }
}
