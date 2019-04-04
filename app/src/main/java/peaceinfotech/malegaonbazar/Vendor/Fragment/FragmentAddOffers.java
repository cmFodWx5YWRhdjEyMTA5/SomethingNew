package peaceinfotech.malegaonbazar.Vendor.Fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import peaceinfotech.malegaonbazar.R;

public class FragmentAddOffers extends Fragment {
    TextInputEditText ettitle,etdesc,etmin,etmax,etterms,etstart,etexpiry,etofferPrice,etdiscPer;
    Spinner spinDiscount;
    List<String> discount = new ArrayList<String>();

    Button preview,add;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_vendor_add_offers,container,false);

        ettitle=view.findViewById(R.id.et_title);
        etdesc=view.findViewById(R.id.et_descp);
        etmin=view.findViewById(R.id.et_min);
        etmax=view.findViewById(R.id.et_max);
        etterms=view.findViewById(R.id.et_details);
        add=view.findViewById(R.id.bt_add_offer);
        etstart=view.findViewById(R.id.et_date_start);
        etexpiry=view.findViewById(R.id.et_date_end);
        etofferPrice=view.findViewById(R.id.et_offer_price);
        etdiscPer=view.findViewById(R.id.et_disc_percent);
        spinDiscount=view.findViewById(R.id.spinner_discount);


        discount.add(0,"Percentage");
        discount.add(1,"Fixed Value");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,discount);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDiscount.setAdapter(adapter);

        spinDiscount.setSelection(0);

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

                if(ettitle.getText().toString().isEmpty()||etdesc.getText().toString().isEmpty()){

                }

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Offers");
    }




    public void AddOffer(){

    }

    private void AlertDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to add the offers");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ettitle.setText("");
                etdesc.setText("");
                etofferPrice.setText("");
                etdiscPer.setText("");
                etmin.setText("");
                etmax.setText("");
                etstart.setText("");
                etexpiry.setText("");
                etterms.setText("");
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
}
