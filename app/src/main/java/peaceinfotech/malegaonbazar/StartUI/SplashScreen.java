package peaceinfotech.malegaonbazar.StartUI;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.SaveSharedPreference;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                if(!SaveSharedPreference.getFirstTimeLaunch(SplashScreen.this)) {
                    startActivity(new Intent(SplashScreen.this, LaunchActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    finish();
                }
            }

        }, 300);
    }
}
