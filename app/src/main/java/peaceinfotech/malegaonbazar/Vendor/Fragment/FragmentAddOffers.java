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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.RetrofitModel.ResponseMessageModel;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAddOffers extends Fragment {
    TextInputEditText ettitle,etdesc,etmin,etmax,etterms,etstart,etexpiry,etofferPrice,etdiscPer;
    Spinner spinDiscount;
    List<String> discount = new ArrayList<String>();
    String offerType = "";

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
        discount.add(1,"Fixed");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,discount);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDiscount.setAdapter(adapter);

        spinDiscount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinDiscount.getSelectedItem().equals("Percentage")){
                    offerType="percentage";
                }
                else if(spinDiscount.getSelectedItem().equals("Fixed")){
                    offerType="fixed";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

                if(ettitle.getText().toString().isEmpty()
                        ||etdesc.getText().toString().isEmpty()
                        ||etofferPrice.getText().toString().isEmpty()
                        ||offerType.isEmpty()
                        ||etdiscPer.getText().toString().isEmpty()
                        ||etmin.getText().toString().isEmpty()
                        ||etmax.getText().toString().isEmpty()
                        ||etstart.getText().toString().isEmpty()
                        ||etexpiry.getText().toString().isEmpty()
                        ||etterms.getText().toString().isEmpty())
                {

                    if(offerType.isEmpty()){
                        Toast.makeText(getActivity(), "Please Select a Category", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "Please Enter All the Fields", Toast.LENGTH_SHORT).show();

                    }
//                    if(ettitle.getText().toString().isEmpty())
//                        ettitle.setError("Please Enter this Field");
//
//                    if(etdesc.getText().toString().isEmpty())
//                        etdesc.setError("Please Enter this Field");
//
//                    if(etofferPrice.getText().toString().isEmpty())
//                        etofferPrice.setError("Please Enter this Field");
//
//                    if(offerType.isEmpty())
//                        Toast.makeText(getActivity(),"Please Choose a Discount type", Toast.LENGTH_SHORT).show();
//
//                    if(etdiscPer.getText().toString().isEmpty())
//                        etdiscPer.setError("Please Enter this Field");
//
//                    if(etmin.getText().toString().isEmpty())
//                        etmin.setError("Please Enter this Field");
//
//                    if(etmax.getText().toString().isEmpty())
//                        etmax.setError("Please Enter this Field");
//
//                    if(etstart.getText().toString().isEmpty())
//                        etstart.setError("Please Enter this Field");
//
//                    if(etexpiry.getText().toString().isEmpty())
//                        etexpiry.setError("Please Enter this Field");
//
//                    if(etterms.getText().toString().isEmpty())
//                        etterms.setError("Please Enter this Field");
                }
                else {

                    if(offerType.equalsIgnoreCase("percentage")){
                        String amount = etdiscPer.getText().toString();
                        int price= Integer.parseInt(amount);
                        if(price>100){
                            etdiscPer.setError("Percentage Should not be More Than 100 ");
                        }
                        else
                        {
                            AlertDialog();
                        }
                    }
                    else{
                        AlertDialog();
                    }
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

        ApiUtils.getServiceClass().addOffers(SaveSharedPreference.getKeyVendorId(getActivity()),
                ettitle.getText().toString(),
                etdesc.getText().toString(),
                etofferPrice.getText().toString(),
                etmin.getText().toString(),
                etmax.getText().toString(),
                etstart.getText().toString(),
                etexpiry.getText().toString(),
                etterms.getText().toString(),
                offerType,
                etdiscPer.getText().toString()).enqueue(new Callback<ResponseMessageModel>() {
            @Override
            public void onResponse(Call<ResponseMessageModel> call, Response<ResponseMessageModel> response) {
                if(response.isSuccessful()){

                    if(response.body().getResponse().equalsIgnoreCase("success")){
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else if(response.body().getResponse().equalsIgnoreCase("failed")){
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseMessageModel> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void AlertDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to add the offers");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddOffer();
                ettitle.setText("");
                etdesc.setText("");
                etofferPrice.setText("");
                etdiscPer.setText("");
                etmin.setText("");
                etmax.setText("");
                etstart.setText("");
                etexpiry.setText("");
                etterms.setText("");

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
