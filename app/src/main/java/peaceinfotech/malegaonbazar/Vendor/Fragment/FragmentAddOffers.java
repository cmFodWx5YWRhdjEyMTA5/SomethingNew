package peaceinfotech.malegaonbazar.Vendor.Fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

import peaceinfotech.malegaonbazar.DatabaseHelper;
import peaceinfotech.malegaonbazar.LoginActivity;
import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.Vendor.VendorActivity;

public class FragmentAddOffers extends Fragment {
    TextInputEditText ettitle,etdesc,etmin,etmax,etdetails,etstart,etexpiry;
    DatabaseHelper myDb;

    Button preview,add;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_vendor_add_offers,container,false);


        ettitle=view.findViewById(R.id.et_title);
        etdesc=view.findViewById(R.id.et_descp);
        etmin=view.findViewById(R.id.et_min);
        etmax=view.findViewById(R.id.et_max);
        etdetails=view.findViewById(R.id.et_details);
        add=view.findViewById(R.id.bt_add_offer);
        etstart=view.findViewById(R.id.et_date_start);
        etexpiry=view.findViewById(R.id.et_date_end);

        myDb=new DatabaseHelper(getActivity());

        etstart.setOnClickListener(new View.OnClickListener() {
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
                                    etstart.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                else
                                    etstart.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        etexpiry.setOnClickListener(new View.OnClickListener() {
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
                                    etexpiry.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                else
                                    etexpiry.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String title=ettitle.getText().toString();
                final String desc=etdesc.getText().toString();
                final String min=etmin.getText().toString();
                final String max=etmax.getText().toString();

                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to add the offers");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDb.InsertData(ettitle.getText().toString(),etdesc.getText().toString(),etmin.getText().toString(),etmax.getText().toString(),etdetails.getText().toString(),etstart.getText().toString(),etexpiry.getText().toString());
                        Toast.makeText(getActivity(),"Offer Added Successfully",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                final AlertDialog alert = builder.create();
                alert.show();

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Offers");
    }
}
