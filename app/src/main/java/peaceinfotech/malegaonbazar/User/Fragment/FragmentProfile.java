package peaceinfotech.malegaonbazar.User.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.RetrofitModel.LogInModel;
import peaceinfotech.malegaonbazar.RetrofitModel.ResponseMessageModel;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProfile extends Fragment {

    TextView tvName,tvMobile,tvReference,tvEmail,tvLocation,tvCity;
    Button  btEdit;
    Dialog editDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_user_profile,container,false);
        tvName=view.findViewById(R.id.tv_user_name);
        tvMobile=view.findViewById(R.id.tv_user_mobile);
        tvReference=view.findViewById(R.id.tv_user_ref);
        tvEmail=view.findViewById(R.id.tv_user_email);
        tvLocation=view.findViewById(R.id.tv_user_loc);
        tvCity=view.findViewById(R.id.tv_user_city);
        btEdit=view.findViewById(R.id.bt_edit_user);


        LogIn(SaveSharedPreference.getUserMobilePassword(getActivity()).get(0),SaveSharedPreference.getUserMobilePassword(getActivity()).get(1));

        getData();

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog = new Dialog(getActivity());
                editDialog.setContentView(R.layout.dialog_edit_user_profile);
                final EditText etName=editDialog.findViewById(R.id.et_edit_user_name);
                final EditText etLoc=editDialog.findViewById(R.id.et_edit_user_location);
                final EditText etCity=editDialog.findViewById(R.id.et_edit_user_City);
                Button btUpdate=editDialog.findViewById(R.id.bt_user_update_profile);

                etName.setText(SaveSharedPreference.getUserProfileData(getActivity()).get(1));
                etLoc.setText(SaveSharedPreference.getUserProfileData(getActivity()).get(2));
                etCity.setText(SaveSharedPreference.getUserProfileData(getActivity()).get(3));

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(etName.getText().toString().isEmpty()||etLoc.getText().toString().isEmpty()||etCity.getText().toString().isEmpty()){
                            if(etName.getText().toString().isEmpty())
                            {
                                etName.setError("Please Enter this Field");
                            }
                            if(etLoc.getText().toString().isEmpty())
                            {
                                etLoc.setError("Please Enter this Field");
                            }
                            if(etCity.getText().toString().isEmpty())
                            {
                                etCity.setError("Please Enter this Field");
                            }
                        }
                        else
                        {
                           ApiUtils.getServiceClass().editUserProfile(SaveSharedPreference.getUserProfileData(getActivity()).get(0),
                                   etName.getText().toString(),etLoc.getText().toString(),etCity.getText().toString()).enqueue(new Callback<ResponseMessageModel>() {
                               @Override
                               public void onResponse(Call<ResponseMessageModel> call, Response<ResponseMessageModel> response) {
                                   if(response.isSuccessful()){
                                       if(response.body().getResponse().equalsIgnoreCase("success")){
                                           Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                           LogIn(SaveSharedPreference.getUserMobilePassword(getActivity()).get(0),
                                                   SaveSharedPreference.getUserMobilePassword(getActivity()).get(1));
                                           editDialog.dismiss();
                                           tvName.setText(etName.getText());
                                           tvLocation.setText(etLoc.getText());
                                           tvCity.setText(etCity.getText());
                                       }
                                       else if(response.body().getResponse().equalsIgnoreCase("failed")){
                                           Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                       }
                                   }
                               }

                               @Override
                               public void onFailure(Call<ResponseMessageModel> call, Throwable t) {

                               }
                           });
                        }
                    }
                });

                editDialog.create();
                editDialog.show();

            }
        });

        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");
    }

    public void getData(){

        tvName.setText(SaveSharedPreference.getUserProfileData(getActivity()).get(1));
        tvLocation.setText(SaveSharedPreference.getUserProfileData(getActivity()).get(2));
        tvCity.setText(SaveSharedPreference.getUserProfileData(getActivity()).get(3));
        tvMobile.setText(SaveSharedPreference.getUserProfileData(getActivity()).get(4));
        tvReference.setText(SaveSharedPreference.getUserProfileData(getActivity()).get(5));
    }

    public void LogIn (final String mobile, final String password) {


        ApiUtils.getServiceClass().logIn(mobile, password).enqueue(new Callback<LogInModel>() {
            @Override
            public void onResponse(Call<LogInModel> call, Response<LogInModel> response) {
                if (response.isSuccessful()) {
                    Log.d("login", response.body().getResponse());
                    if (response.body().getResponse().equalsIgnoreCase("success")) {

                        if (response.body().getDetailsModels().getRoleName().equalsIgnoreCase("user")) {


//                            SaveSharedPreference.setUserProfileData(getActivity(),
//                                    response.body().getDetailsModels().getUserId(),
//                                    response.body().getDetailsModels().getFullName(),
//                                    response.body().getDetailsModels().getLocation(),
//                                    response.body().getDetailsModels().getCity(),
//                                    response.body().getDetailsModels().getMobile(),
//                                    response.body().getDetailsModels().getReferenceId());

                        } else if (response.body().getDetailsModels().getRoleName().equalsIgnoreCase("vendor")) {
//                            SaveSharedPreference.setRole(getActivity(),
//                                    response.body().getDetailsModels().getRoleID(),
//                                    response.body().getDetailsModels().getRoleName());
//
//                            SaveSharedPreference.setVendorProfileData(getActivity(),
//                                    response.body().getDetailsModels().getVendorId(),
//                                    response.body().getDetailsModels().getFullName(),
//                                    response.body().getDetailsModels().getLocation(),
//                                    response.body().getDetailsModels().getMobile(),
//                                    response.body().getDetailsModels().getBrand(),
//                                    response.body().getDetailsModels().getImgLogoURL(),
//                                    response.body().getDetailsModels().getImgBanURL(),
//                                    response.body().getDetailsModels().getEmail(),
//                                    response.body().getDetailsModels().getCatName(),
//                                    response.body().getDetailsModels().getCatID(),
//                                    response.body().getDetailsModels().getState(),
//                                    response.body().getDetailsModels().getCity());
//
//                            SaveSharedPreference.setVendorLatLng(getActivity(),
//                                    response.body().getDetailsModels().getLat(),
//                                    response.body().getDetailsModels().getLng());

                        }
                    } else if (response.body().getResponse().equalsIgnoreCase("failed")) {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LogInModel> call, Throwable t) {


                Log.d("loginfail", "onFailure: " + t);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }


}
