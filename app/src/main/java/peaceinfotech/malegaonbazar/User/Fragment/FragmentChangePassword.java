package peaceinfotech.malegaonbazar.User.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.Retrofit.ApiUtils;
import peaceinfotech.malegaonbazar.RetrofitModel.ResponseMessageModel;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentChangePassword extends Fragment {

    EditText etOldPass,etNewPass,etNewRePass;
    Button btChangePass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_change_password,container,false);

        etOldPass=view.findViewById(R.id.et_user_old_pass);
        etNewPass=view.findViewById(R.id.et_user_new_pass);
        etNewRePass=view.findViewById(R.id.et_user_new_re_pass);
        btChangePass=view.findViewById(R.id.bt_user_change_pass);

        btChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etOldPass.getText().toString().isEmpty()||etNewPass.getText().toString().isEmpty()||etNewRePass.getText().toString().isEmpty()){

                    if(etOldPass.getText().toString().isEmpty()){
                        etOldPass.setError("Please Enter This Field");
                    }
                    if(etNewPass.getText().toString().isEmpty()){
                        etNewPass.setError("Please Enter This Field");
                    }
                    if(etNewRePass.getText().toString().isEmpty()){
                        etNewRePass.setError("Please Enter This Filed");
                    }
                }
                else{
                    if(etNewPass.getText().toString().equals(etNewRePass.getText().toString())){
                        AlertDialog(etNewRePass.getText().toString());
                    }
                    else{
                        Toast.makeText(getActivity(), "Password Don't Match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Change Password");
    }

    public void changePassword(final String newPass){
        ApiUtils.getServiceClass().changePassword(SaveSharedPreference.getMobileAndPassword(getActivity()).get(0),
                SaveSharedPreference.getUserMobilePassword(getActivity()).get(1),
                newPass).enqueue(new Callback<ResponseMessageModel>() {
            @Override
            public void onResponse(Call<ResponseMessageModel> call, Response<ResponseMessageModel> response) {

                if(response.isSuccessful()){
                    if(response.body().getResponse().equalsIgnoreCase("success")){
                        SaveSharedPreference.changePassword(getActivity(),newPass);
                        etOldPass.setText("");
                        etNewPass.setText("");
                        etNewRePass.setText("");
                        Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    else if(response.body().getResponse().equalsIgnoreCase("failed")){

                    }
                }

            }
            @Override
            public void onFailure(Call<ResponseMessageModel> call, Throwable t) {

            }
        });
    }

    public void AlertDialog(final String newPass){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Change Password");
        builder.setMessage("Are you sure you want to Change the Password");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changePassword(newPass);
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
}
