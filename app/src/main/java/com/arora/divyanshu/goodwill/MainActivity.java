package com.arora.divyanshu.goodwill;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                SessionManager sessionManager =new SessionManager(MainActivity.this);

                if (!sessionManager.getString("user_name").isEmpty())
                {
                    Intent intent = new Intent(MainActivity.this,Dashboard.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                }
                finish();


            }


        };
        timerThread.start();



    }
}
