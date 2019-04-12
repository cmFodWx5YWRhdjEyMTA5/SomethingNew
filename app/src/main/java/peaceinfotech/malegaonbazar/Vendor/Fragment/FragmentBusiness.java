package peaceinfotech.malegaonbazar.Vendor.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import peaceinfotech.malegaonbazar.R;

public class FragmentBusiness extends Fragment {

    EditText etStart,etEnd,etEarning,etDiscount;
    PieChart mChart;
    Button btGetEarnings;
    ArrayList<Integer> colors = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendor_business,container,false);


        etStart=view.findViewById(R.id.et_bus_date_start);
        etEnd=view.findViewById(R.id.et_bus_date_end);
        mChart=view.findViewById(R.id.pie_bus);
        etEarning=view.findViewById(R.id.et_bus_earning);
        etDiscount=view.findViewById(R.id.et_bus_discount);
        btGetEarnings=view.findViewById(R.id.bt_get_earnings);



       colors.add(Color.GREEN);
       colors.add(Color.RED);


        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(50f, "Earnings"));
        entries.add(new PieEntry(50f, "Discount"));


        PieDataSet set = new PieDataSet(entries,"Earning Data");
        PieData data = new PieData(set);
        set.setValueFormatter(new PercentFormatter());
        set.setColors(colors);
        set.setValueTextSize(30f);


        mChart.setEntryLabelTextSize(20f);
        mChart.setEntryLabelColor(Color.BLACK);
        mChart.setRotationEnabled(false);
        mChart.getDescription().setText(" ");
        mChart.setData(data);
        mChart.invalidate();
        mChart.setDrawHoleEnabled(false);

        btGetEarnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etEarning.getText().toString().isEmpty()||etDiscount.getText().toString().isEmpty()){
                    if(etEarning.getText().toString().isEmpty()){
                        etEarning.setError("Enter This Field");
                    }
                    if(etDiscount.getText().toString().isEmpty()){
                        etDiscount.setError("Enter this Field");
                    }
                }
                else{
                    if(Integer.parseInt(etDiscount.getText().toString())>Integer.parseInt(etEarning.getText().toString())){
                        Toast.makeText(getActivity(), "No Earnings", Toast.LENGTH_SHORT).show();

                        List<PieEntry> entries = new ArrayList<>();

                        entries.add(new PieEntry(0f, "Earnings"));
                        entries.add(new PieEntry(100f, "Discount"));


                        PieDataSet set = new PieDataSet(entries,"Earning Data");
                        PieData data = new PieData(set);
                        set.setColors(colors);
                        set.setValueTextSize(30f);


                        mChart.setEntryLabelTextSize(20f);
                        mChart.setEntryLabelColor(Color.BLACK);
                        mChart.setRotationEnabled(false);
                        mChart.getDescription().setText(" ");
                        mChart.setData(data);
                        mChart.invalidate();
                        mChart.setDrawHoleEnabled(false);


                    }
                    else{
                        List<PieEntry> entries = new ArrayList<>();

                        int earnings = Integer.parseInt(etEarning.getText().toString());
                        int discount = Integer.parseInt(etDiscount.getText().toString());

                        float discPercent = discount*100/earnings;
                        float earnPercent = (earnings-discount)*100/earnings;

                        Log.d("percent", "onCreateView: "+discPercent+" / "+earnPercent);

                        entries.add(new PieEntry(earnPercent, "Earnings"));
                        entries.add(new PieEntry(discPercent, "Discount"));


                        PieDataSet set = new PieDataSet(entries,"Earning Data");
                        PieData data = new PieData(set);
                        set.setColors(colors);
                        set.setValueTextSize(30f);


                        mChart.setEntryLabelTextSize(20f);
                        mChart.setEntryLabelColor(Color.BLACK);
                        mChart.setRotationEnabled(false);
                        mChart.getDescription().setText(" ");
                        mChart.setData(data);
                        mChart.invalidate();
                        mChart.setDrawHoleEnabled(false);
                    }
                }


            }
        });






       etStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),R.style.datepickerCustom,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                if (monthOfYear + 1 > 9)
                                    etStart.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                else
                                    etStart.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

       etEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),R.style.datepickerCustom,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                if (monthOfYear + 1 > 9)
                                    etEnd.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                else
                                    etEnd.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
       });


       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Business");
    }
}
