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
import android.widget.Button;
import android.widget.DatePicker;

import peaceinfotech.malegaonbazar.R;

public class EditOfferActivity extends AppCompatActivity {

    TextInputEditText ettitle,etdesc,etmin,etmax,etdetails,etstart,etexpiry,etOfferPrice,etdiscountPercent;
    Button btEdit;
    Toolbar toolbar;
    Boolean updateDone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_offer);
        super.onCreate(savedInstanceState);


        ettitle=findViewById(R.id.et_edit_title);
        etdesc=findViewById(R.id.et_edit_descp);
        etmin=findViewById(R.id.et_edit_min);
        etmax=findViewById(R.id.et_edit_max);
        etdetails=findViewById(R.id.et_edit_details);
        etstart=findViewById(R.id.et_edit_date_start);
        etexpiry=findViewById(R.id.et_edit_date_end);
        etOfferPrice=findViewById(R.id.et_edit_offer_price);
        etdiscountPercent=findViewById(R.id.et_edit_disc_percent);
        btEdit=findViewById(R.id.bt_edit_offer);
        toolbar=findViewById(R.id.tool_edit_offer);


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

        Intent get = getIntent();
        final String id = get.getStringExtra("id");



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
                AlertDialog.Builder builder=new AlertDialog.Builder(EditOfferActivity.this);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to Edit this Offer");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
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
        });

    }
}
