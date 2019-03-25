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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import peaceinfotech.malegaonbazar.DatabaseHelper;
import peaceinfotech.malegaonbazar.R;

public class EditOfferActivity extends AppCompatActivity {

    TextInputEditText ettitle,etdesc,etmin,etmax,etdetails,etstart,etexpiry;
    DatabaseHelper myDb;
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
        btEdit=findViewById(R.id.bt_edit_offer);
        toolbar=findViewById(R.id.tool_edit_offer);
        myDb=new DatabaseHelper(EditOfferActivity.this);

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

        Cursor res=myDb.GetSpecificOffer(id);

        ettitle.setText(res.getString(1));
        etdesc.setText(res.getString(2));
        etmin.setText(res.getString(3));
        etmax.setText(res.getString(4));
        etdetails.setText(res.getString(5));
        etstart.setText(res.getString(6));
        etexpiry.setText(res.getString(7));

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
                        myDb.UpdateData(id,
                                ettitle.getText().toString(),
                                etdesc.getText().toString(),
                                etmin.getText().toString(),
                                etmax.getText().toString(),
                                etdetails.getText().toString(),
                                etstart.getText().toString(),
                                etexpiry.getText().toString());
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
