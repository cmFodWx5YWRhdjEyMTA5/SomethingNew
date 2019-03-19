package peaceinfotech.malegaonbazar.User.Fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



import java.util.Random;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.User.CustomDialogClass;
import peaceinfotech.malegaonbazar.User.NotificationActivity;

public class FragmentWallet extends Fragment {

    Random random=new Random();
    TextView refId;
    Button notify;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_user_wallet,container,false);

        refId = view.findViewById(R.id.tv_ref_id);
        notify = view.findViewById(R.id.bt_notify);



        String refid = "UseRef"+random.nextInt(10000);

        if(refid != SaveSharedPreference.getUserReference(getActivity())) {
            refId.setText(SaveSharedPreference.getUserReference(getActivity()));
        }
        else {
            refId.setText(refid);
        }

        notify.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

//                CustomDialogClass cdd=new CustomDialogClass(getActivity());
//                Intent notifyIntent = new Intent(getActivity(),getActivity().getClass());
//
//                PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0,notifyIntent, 0);
////                mBuilder.setContentIntent(contentIntent);
//
//                int notificationId = new Random().nextInt(); // just use a counter in some util class...
//                PendingIntent dismissIntent = NotificationActivity.getDismissIntent(notificationId, getActivity());
//
//                Notification.Builder mBuilder = new Notification.Builder(getActivity());
//                mBuilder.setSmallIcon(R.drawable.offerd);
//                mBuilder.setContentTitle("Notification Alert, Click Me!");
//                mBuilder.setContentText("Hi, This is Android Notification Detail!");
//                mBuilder.setAutoCancel(true);
//                mBuilder.addAction(R.drawable.yes_notify,"yes",contentIntent);
//                mBuilder.addAction(R.drawable.no_notify,"no",dismissIntent);
//                mBuilder.setStyle(new Notification.MediaStyle());
//                NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
//                manager.notify(notificationId, mBuilder.build());

                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                TextView wallet ;
                wallet = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_wallet));
                wallet.setGravity(Gravity.CENTER_VERTICAL);
                wallet.setTypeface(null, Typeface.BOLD);
                wallet.setTextColor(getResources().getColor(R.color.colorAccent));
                wallet.setText("7");

                //Gravity property aligns the text

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Wallet");
//        NavigationView navigationView = (NavigationView) getView().findViewById(R.id.nav_view);


    }
}
