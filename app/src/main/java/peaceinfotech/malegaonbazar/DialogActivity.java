package peaceinfotech.malegaonbazar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import peaceinfotech.malegaonbazar.StartUI.LoginActivity;
import peaceinfotech.malegaonbazar.User.UI.UserActivity;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Log-Out");
        builder.setMessage("Are you sure you want to Log-Out");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setCancelable(true);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }
}
