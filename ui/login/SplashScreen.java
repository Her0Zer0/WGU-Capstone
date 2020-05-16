package com.slyfoxdevelopment.example2.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.slyfoxdevelopment.example2.R;
import com.slyfoxdevelopment.example2.data.Account;
import com.slyfoxdevelopment.example2.data.AppRepository;
import com.slyfoxdevelopment.example2.utils.FormUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.slyfoxdevelopment.example2.utils.Constants.FIRST_TIME_USER;
import static com.slyfoxdevelopment.example2.utils.Constants.LOGGED_IN_USER;
import static com.slyfoxdevelopment.example2.utils.Constants.LOGGED_IN_USER_ID;

public class SplashScreen extends AppCompatActivity {
    int SLEEP_TIMER = 3;
    String TAG = "SplashScreen: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        LogoLauncher logoLauncher = new LogoLauncher();

        logoLauncher.start();
    }

    private class LogoLauncher extends Thread{

        private Executor executor = Executors.newSingleThreadExecutor();
        private AppRepository mRepo  = AppRepository.getInstance(getApplication());;
        public void run(){

                    try {
                        sleep(1000 * SLEEP_TIMER);

                        executor.execute(new Runnable() {
                            @Override
                            public void run() {

                               int count =  mRepo.getAccountCount();
                               Account keepMeLoggedIn = mRepo.isKeepMeLoggedIn();

                                Log.i(TAG, "run: "+ count );
                                if(count > 0){
                                    Log.i(TAG, "Not a first time user");
                                    //check if we have it set to not ask for login

                                    if(keepMeLoggedIn != null){
                                        FormUtils.setLoggedInUserAccount(keepMeLoggedIn);
                                        Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                                        intent.putExtra(LOGGED_IN_USER_ID, keepMeLoggedIn.getId());
                                        intent.putExtra(LOGGED_IN_USER, keepMeLoggedIn.getFirstname());
                                        startActivity(intent);
                                    }else{
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                    }


                                    SplashScreen.this.finish();
                                }else{
                                    Log.i(TAG, "First time user");
                                    Intent intent = new Intent(getApplicationContext(), Register.class);
                                    intent.putExtra(FIRST_TIME_USER, true);
                                    startActivity(intent);
                                    SplashScreen.this.finish();
                                }
                            }
                        });

                    }catch (InterruptedException err){
                        err.printStackTrace();
                    }
                }


        }
}



