package peaceinfotech.malegaonbazar.Vendor.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.RetrofitModel.ResponseMessageModel;
import peaceinfotech.malegaonbazar.RetrofitModel.UpdateServiceModel;
import peaceinfotech.malegaonbazar.Vendor.Model.ServicesListModel;
import peaceinfotech.malegaonbazar.Vendor.UI.EditServiceActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesListAdapter extends RecyclerView.Adapter<ServicesListAdapter.ServicesViewHolder> {

    Context context;
    List<ServicesListModel> serviceList = new ArrayList<>();
    View menuView;
//    ImageButton red, blue;

    private PopupWindow mDropdown = null;
    LayoutInflater mInflater;
    Button pop;

    public ServicesListAdapter(Context context, List<ServicesListModel> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    public ServicesListAdapter(){

    }

    public class ServicesViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName,tvDesc;
        public ImageView imgOptions;



        public ServicesViewHolder(@NonNull View view) {
            super(view);

            menuView = view;
            tvName=view.findViewById(R.id.tv_service_name);
            tvDesc=view.findViewById(R.id.tv_service_desc);
            imgOptions=view.findViewById(R.id.img_service_options);
        }
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ServicesViewHolder(LayoutInflater.from(context).inflate(R.layout.vendor_service_list,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ServicesViewHolder holder, final int i) {

        final ServicesListModel services = serviceList.get(i);

        holder.tvName.setText(services.getServiceName());
        holder.tvDesc.setText(services.getServiceDesc());

        holder.imgOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Context wrapper = new ContextThemeWrapper(context, R.style.NavigationDrawerStyle);
                PopupMenu menu = new PopupMenu (wrapper, holder.imgOptions);
                try {
                    Field[] fields = menu.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if ("mPopup".equals(field.getName())) {
                            field.setAccessible(true);
                            Object menuPopupHelper = field.get(menu);
                            Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                            Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                            setForceIcons.invoke(menuPopupHelper, true);
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                menu.setOnMenuItemClickListener (new PopupMenu.OnMenuItemClickListener ()
                {
                    @Override
                    public boolean onMenuItemClick (MenuItem item)
                    {
                        int id = item.getItemId();
                        switch (id)
                        {
                            case R.id.item_edit:

                                //Edit Option

                                final Dialog editDialog = new Dialog(context);
                                editDialog.setContentView(R.layout.dialog_vendor_edit_service);
                                final EditText etName=editDialog.findViewById(R.id.et_edit_service_name);
                                final EditText etDesc=editDialog.findViewById(R.id.et_edit_service_desc);
                                Button btEdit=editDialog.findViewById(R.id.bt_edit_service);

                                etName.setText(services.getServiceName());
                                etDesc.setText(services.getServiceDesc());

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
                                            ApiUtils.getServiceClass().editServices(services.getServiceId(),
                                                    etName.getText().toString(),
                                                    etDesc.getText().toString()).enqueue(new Callback<UpdateServiceModel>() {
                                                @Override
                                                public void onResponse(Call<UpdateServiceModel> call, Response<UpdateServiceModel> response) {
                                                    if(response.isSuccessful())
                                                    {
                                                        if(response.body().getResponse().equalsIgnoreCase("success")){
                                                            holder.tvName.setText(etName.getText().toString());
                                                            holder.tvDesc.setText(etDesc.getText().toString());
                                                            editDialog.dismiss();
                                                            Toast.makeText(context, "Service Edited Successfully", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else{
                                                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                                @Override
                                                public void onFailure(Call<UpdateServiceModel> call, Throwable t) {
                                                    Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                });

                                editDialog.create();
                                editDialog.show();

                                break;
                            case R.id.item_delete:

                                //Delete Option

                                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                                builder.setTitle("Delete");
                                builder.setMessage("Are you sure you want to delete this Offer");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       ApiUtils.getServiceClass().deleteServices(services.getServiceId()).enqueue(new Callback<ResponseMessageModel>() {
                                           @Override
                                           public void onResponse(Call<ResponseMessageModel> call, Response<ResponseMessageModel> response) {
                                               if(response.isSuccessful()){
                                                   if(response.body().getResponse().equalsIgnoreCase("success")){
                                                       notifyItemRemoved(i);
                                                       serviceList.remove(i);
                                                   }
                                               }
                                           }

                                           @Override
                                           public void onFailure(Call<ResponseMessageModel> call, Throwable t) {

                                           }
                                       });
                                   }
                               });
                               builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {

                                   }
                               });

                                AlertDialog alertDialog=builder.create();
                                alertDialog.show();
                                break;
                        }
                        return true;
                    }
                });
                menu.inflate (R.menu.service_option_menu_recycler);
                menu.show();

            }
        });

    }



    @Override
    public int getItemCount() {
        return serviceList.size();
    }


}
