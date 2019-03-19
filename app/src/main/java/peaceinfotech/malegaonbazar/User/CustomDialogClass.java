package peaceinfotech.malegaonbazar.User;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import peaceinfotech.malegaonbazar.R;

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;

    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_alert_dialog_box);
        yes = (Button) findViewById(R.id.bt_alert_yes);
        no = (Button) findViewById(R.id.bt_alert_yes);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        CustomDialogClass cdd = new CustomDialogClass(c);
        cdd.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_alert_yes:
                dismiss();
                break;
            case R.id.bt_alert_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}