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
import peaceinfotech.malegaonbazar.Vendor.Adapter.RequestListAdapter;
import peaceinfotech.malegaonbazar.Vendor.Model.RequestListModel;

public class FragmentRequest extends Fragment {

    RecyclerView recyclerView;
    List<RequestListModel> requestList = new ArrayList<>();
    RequestListAdapter requestListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vendor_requests,container,false);

        recyclerView = view.findViewById(R.id.recycler_request);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        for(int i=0;i<3;i++){
            requestList.add(new RequestListModel("Siddhant","100","Food"));
        }

        requestListAdapter = new RequestListAdapter(requestList,getActivity());
        recyclerView.setAdapter(requestListAdapter);
        requestListAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Requests");
    }
}
