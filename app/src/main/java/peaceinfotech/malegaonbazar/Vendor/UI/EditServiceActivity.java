package peaceinfotech.malegaonbazar.Vendor.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.RetrofitModel.UpdateServiceModel;
import peaceinfotech.malegaonbazar.Vendor.Adapter.ServicesListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditServiceActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText etName,etDesc;
    Button btEdit;
    ServicesListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_service);

        toolbar=findViewById(R.id.tool_edit_service);
        etName=findViewById(R.id.et_edit_service_name);
        etDesc=findViewById(R.id.et_edit_service_desc);
        btEdit=findViewById(R.id.bt_edit_service);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Edit Service");


        final Intent getIn=getIntent();
        String id = getIn.getStringExtra("service_id");
        String name=getIn.getStringExtra("service_name");
        String desc=getIn.getStringExtra("service_desc");

        Log.d("editvalues",getIn.getStringExtra("service_id")+"/"+getIn.getStringExtra("service_name")+"/"+getIn.getStringExtra("service_desc"));
//

        etName.setText(getIn.getStringExtra("service_name"));
        etDesc.setText(getIn.getStringExtra("service_desc"));
//

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etName.getText().toString().isEmpty()||etDesc.getText().toString().isEmpty()){
                    if(etName.getText().toString().isEmpty())
                    {
                        etName.setError("Please Enter this Field");
                    }
                    if(etDesc.getText().toString().isEmpty())
                    {
                        etDesc.setError("Please Enter this Field");
                    }
                }
                else
                {
                    ApiUtils.getServiceClass().editServices(getIn.getStringExtra("service_id"),
                            etName.getText().toString(),
                            etDesc.getText().toString()).enqueue(new Callback<UpdateServiceModel>() {
                        @Override
                        public void onResponse(Call<UpdateServiceModel> call, Response<UpdateServiceModel> response) {
                            if(response.isSuccessful())
                            {
                                if(response.body().getResponse().equalsIgnoreCase("success")){
                                    adapter=new ServicesListAdapter();
                                    Toast.makeText(EditServiceActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    adapter.notifyDataSetChanged();
                                    finish();
                                }
                                else{
                                    Toast.makeText(EditServiceActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateServiceModel> call, Throwable t) {
                            Toast.makeText(EditServiceActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });



    }

}
