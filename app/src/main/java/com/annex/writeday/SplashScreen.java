package com.annex.writeday;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import com.annex.writeday.apiservices.PersistentCookieStore;
import com.annex.writeday.sign.LoginActivity;

import java.net.HttpCookie;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView tv      = (TextView) findViewById(R.id.splash_text);
        TextView tv_desc = (TextView) findViewById(R.id.splash_text_description);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/corbel.ttf");


        tv.setTypeface(type);
        tv_desc.setTypeface(type);

        Timer timer = new Timer();
        timer.schedule(task, 1500);

    }


    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            PersistentCookieStore persistentCookieStore = new PersistentCookieStore(getApplicationContext());
            List<HttpCookie> cooks = persistentCookieStore.getCookies();

            if (cooks.size() > 0) {

                boolean JSESSIONID = false;

                for (HttpCookie st : cooks) {
                    if (st.getName().equals("JSESSIONID")) {
                        JSESSIONID = true;
                        break;
                    }
                }
                if(JSESSIONID)
                    startActivityEx(MainActivity.class);
                else
                    startActivityEx(LoginActivity.class);
            }
            else {
                startActivityEx(LoginActivity.class);
            }

        }
    };

    private void startActivityEx(Class activity) {
        Intent in = new Intent(SplashScreen.this, activity);
        startActivity(in);
        finish();
    }

}
