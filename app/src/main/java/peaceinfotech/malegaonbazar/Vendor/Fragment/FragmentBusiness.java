package peaceinfotech.malegaonbazar.Vendor.Fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import peaceinfotech.malegaonbazar.R;

public class FragmentBusiness extends Fragment {

    EditText etStart,etEnd;
    PieChart mChart;

    private int[] yValues = {21, 2, 2};
    private String[] xValues = {"Present Days", "Absents", "Leaves"};

    // colors for different sections in pieChart
    public static  final int[] MY_COLORS = {
            Color.rgb(84,124,101), Color.rgb(64,64,64), Color.rgb(153,19,0),
            Color.rgb(38,40,53)};

    ArrayList<Integer> colors = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendor_business,container,false);


        etStart=view.findViewById(R.id.et_bus_date_start);
        etEnd=view.findViewById(R.id.et_bus_date_end);
        mChart=view.findViewById(R.id.pie_bus);

        List<PieEntry> entries = new ArrayList<>();

       colors.add(Color.GREEN);
       colors.add(Color.YELLOW);
//       colors.add(Color.RED);
//       colors.add(Color.BLUE);


       int earnings = 1000;
       int discount = 2000;

       float discPercent = 2000*100/30000;
       float earnPercent = (30000-2000)*100/30000;

        Log.d("percent", "onCreateView: "+discPercent+" / "+earnPercent);

       entries.add(new PieEntry(earnPercent, "Earnings"));
       entries.add(new PieEntry(discPercent, "Discount"));


       PieDataSet set = new PieDataSet(entries, "Election Results");
       PieData data = new PieData(set);
       set.setColors(colors);
       mChart.setData(data);
       mChart.invalidate();
       mChart.setDrawHoleEnabled(false);



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
