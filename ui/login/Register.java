package com.slyfoxdevelopment.example2.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.slyfoxdevelopment.example2.R;
import com.slyfoxdevelopment.example2.data.Account;
import com.slyfoxdevelopment.example2.utils.PasswdUtils;

import java.util.Date;

import static com.slyfoxdevelopment.example2.utils.Constants.FIRST_TIME_USER;

public class Register extends AppCompatActivity {

    EditText register_firstname, register_lastname, register_username, register_passwd, register_repasswd;
    TextView register_terms_conditions, register_accepted_label,register_back_to_login, register_welcome_text;
    Button register_btn;
    Switch register_accept_terms;
    RegisterViewModel mViewModel;
    String TAG ="Register: ";
    boolean first_time_user = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("Register and Accept Terms");

        register_firstname = findViewById(R.id.register_firstname);
        register_lastname = findViewById(R.id.register_lastname);
        register_username = findViewById(R.id.register_username);
        register_passwd = findViewById(R.id.register_passwd);
        register_repasswd = findViewById(R.id.register_repasswd);
        register_terms_conditions = findViewById(R.id.register_terms_conditions);
        register_accept_terms = findViewById(R.id.register_accept_terms);
        register_accepted_label = findViewById(R.id.register_accepted_label);
        register_back_to_login = findViewById(R.id.register_back_to_login);
        register_btn = findViewById(R.id.register_btn);
        register_welcome_text = findViewById(R.id.register_welcome_text);


        initViewModel();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            first_time_user = extras.getBoolean(FIRST_TIME_USER);
        }



        if(first_time_user){
            register_welcome_text.setText("Looks like you are a first time user!\nPlease register to begin using this app!");
            register_back_to_login.setVisibility(View.INVISIBLE);
        }




        register_terms_conditions.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), TermsAndConditions.class);
            startActivity(intent);
        });

        register_accept_terms.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if(isChecked){
                register_accepted_label.setText("Accepted");
                register_accepted_label.setTextColor(getResources().getColor(R.color.colorPrimary));
            }else{
                register_accepted_label.setText("Accept");
                register_accepted_label.setTextColor(getResources().getColor(R.color.colorWarningText));
            }
        });


        register_back_to_login.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        });

        register_btn.setOnClickListener(view -> {
            String username = register_username.getText().toString(),
                    passwd = register_passwd.getText().toString(),
                    repasswd = register_repasswd.getText().toString(),
                    firstname = register_firstname.getText().toString(),
                    lastname = register_lastname.getText().toString();
            int errors = 0;

            if(username.isEmpty()){
                errors++;
                register_username.setHint(getResources().getString(R.string.username_error));
                register_username.setHintTextColor(getResources().getColor(R.color.colorWarningText));
            }

            if(passwd.isEmpty() || repasswd.isEmpty()){
               errors++;
                register_passwd.setHint(getResources().getString(R.string.passwd_error));
                register_repasswd.setHint(getResources().getString(R.string.repasswd_error));
                register_passwd.setHintTextColor(getResources().getColor(R.color.colorWarningText));
                register_repasswd.setHintTextColor(getResources().getColor(R.color.colorWarningText));
            }else{
                if(!passwd.equals(repasswd)){
                    errors++;
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.password_match_error), Toast.LENGTH_SHORT).show();
                }
            }

            if(firstname.isEmpty() || lastname.isEmpty()){
                errors++;
                register_firstname.setHint(getResources().getString(R.string.firstname_error));
                register_lastname.setHint(getResources().getString(R.string.lastname_error));
                register_firstname.setHintTextColor(getResources().getColor(R.color.colorWarningText));
                register_lastname.setHintTextColor(getResources().getColor(R.color.colorWarningText));
            }

            if(!register_accept_terms.isChecked()){
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.accept_terms_error), Toast.LENGTH_SHORT).show();
            }

            if(errors == 0){
                //do the deed


                String salt = PasswdUtils.getSalt(30);
                String mySecurePassword = PasswdUtils.generateSecurePassword(passwd, salt);
                Date created = new Date();

                Log.i(TAG, created.toString());
                Account account = new Account(username, mySecurePassword, firstname, lastname, salt,  created, false);
                mViewModel.insertAccount(account);
                Intent intent  = new Intent(this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);



//        mViewModel.mLiveAccount.ob
    }


}
