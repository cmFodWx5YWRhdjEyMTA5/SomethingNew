package peaceinfotech.malegaonbazar.Vendor.UI;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.RetrofitModel.ResponseMessageModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditOfferActivity extends AppCompatActivity {

    TextInputEditText ettitle,etdesc,etmin,etmax,etterms,etstart,etexpiry,etOfferPrice,etdiscountPercent;
    Button btEdit;
    Toolbar toolbar;
    List<String> discount = new ArrayList<String>();
    Boolean updateDone;
    Spinner spinDiscount;
    String offerType = "";
    Intent getIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_offer);
        super.onCreate(savedInstanceState);


        ettitle=findViewById(R.id.et_edit_title);
        etdesc=findViewById(R.id.et_edit_descp);
        etmin=findViewById(R.id.et_edit_min);
        etmax=findViewById(R.id.et_edit_max);
        etterms=findViewById(R.id.et_edit_terms);
        etstart=findViewById(R.id.et_edit_date_start);
        etexpiry=findViewById(R.id.et_edit_date_end);
        etOfferPrice=findViewById(R.id.et_edit_offer_price);
        etdiscountPercent=findViewById(R.id.et_edit_disc_percent);
        btEdit=findViewById(R.id.bt_edit_offer);
        toolbar=findViewById(R.id.tool_edit_offer);
        spinDiscount=findViewById(R.id.spinner_edit_discount);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Edit Offer");

        getIntent = getIntent();

        discount.add(0,"Percentage");
        discount.add(1,"Fixed");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditOfferActivity.this,android.R.layout.simple_spinner_item,discount);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDiscount.setAdapter(adapter);

        ettitle.setText(getIntent.getStringExtra("title"));
        etdesc.setText(getIntent.getStringExtra("desc"));
        etmin.setText(getIntent.getStringExtra("min"));
        etmax.setText(getIntent.getStringExtra("max"));
        etstart.setText(getIntent.getStringExtra("start"));
        etexpiry.setText(getIntent.getStringExtra("end"));
        etterms.setText(getIntent.getStringExtra("terms"));
        etOfferPrice.setText(getIntent.getStringExtra("price"));
        etdiscountPercent.setText(getIntent.getStringExtra("discount"));

        if(getIntent.getStringExtra("offertype").equalsIgnoreCase("percentage")){
            spinDiscount.setSelection(0);
        }
        else if(getIntent.getStringExtra("offertype").equalsIgnoreCase("fixed")){
            spinDiscount.setSelection(1);
        }

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


                String str[] = etstart.getText().toString().split("-");

                int mYear = Integer.parseInt(str[0]);
                int mMonth = Integer.parseInt(str[1])-1;
                int mDay = Integer.parseInt(str[2]);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditOfferActivity.this,R.style.datepickerCustom,
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

                String str[] = etexpiry.getText().toString().split("-");

                int mYear = Integer.parseInt(str[0]);
                int mMonth = Integer.parseInt(str[1])-1;
                int mDay = Integer.parseInt(str[2]);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditOfferActivity.this,R.style.datepickerCustom,
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


        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ettitle.getText().toString().isEmpty()
                        ||etdesc.getText().toString().isEmpty()
                        ||etOfferPrice.getText().toString().isEmpty()
                        ||offerType.isEmpty()
                        ||etdiscountPercent.getText().toString().isEmpty()
                        ||etmin.getText().toString().isEmpty()
                        ||etmax.getText().toString().isEmpty()
                        ||etstart.getText().toString().isEmpty()
                        ||etexpiry.getText().toString().isEmpty()
                        ||etterms.getText().toString().isEmpty()){

                    if(offerType.isEmpty()){
                        Toast.makeText(EditOfferActivity.this, "Please Select a Category", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(EditOfferActivity.this, "Please Enter All the Fields", Toast.LENGTH_SHORT).show();

                    }

                }
                else{

                    if(offerType.equalsIgnoreCase("percentage")){
                        String amount = etdiscountPercent.getText().toString();
                        int price= Integer.parseInt(amount);
                        if(price>100){
                            etdiscountPercent.setError("Percentage Should not be More Than 100 ");
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

    }

    public void AlertDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(EditOfferActivity.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to Edit this Offer");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editOffers();
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

    public void editOffers(){

        ApiUtils.getServiceClass().editOffers(getIntent.getStringExtra("id"),
                ettitle.getText().toString(),
                etdesc.getText().toString(),
                etOfferPrice.getText().toString(),
                etmin.getText().toString(),
                etmax.getText().toString(),
                etstart.getText().toString(),
                etexpiry.getText().toString(),
                etterms.getText().toString(),
                offerType,
                etdiscountPercent.getText().toString()).
                enqueue(new Callback<ResponseMessageModel>() {
            @Override
            public void onResponse(Call<ResponseMessageModel> call, Response<ResponseMessageModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getResponse().equalsIgnoreCase("success")){
                        Toast.makeText(EditOfferActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else if(response.body().getResponse().equalsIgnoreCase("failed")){
                        Toast.makeText(EditOfferActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMessageModel> call, Throwable t) {
                Toast.makeText(EditOfferActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
