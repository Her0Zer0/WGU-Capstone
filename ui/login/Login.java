package com.slyfoxdevelopment.example2.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.slyfoxdevelopment.example2.R;
import com.slyfoxdevelopment.example2.data.Account;
import com.slyfoxdevelopment.example2.utils.FormUtils;
import com.slyfoxdevelopment.example2.utils.PasswdUtils;

import java.util.ArrayList;
import java.util.List;

import static com.slyfoxdevelopment.example2.utils.Constants.LOGGED_IN_USER;

public class Login extends AppCompatActivity {


    EditText login_username, login_passwd;
    Button login_btn;
    TextView login_register;
    LoginViewModel mViewModel;
    CheckBox login_keep_me_logged_in;
    String TAG = "Login: ";
    List<Account> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login or Register");

        login_username = findViewById(R.id.login_username);
        login_passwd = findViewById(R.id.login_passwd);
        login_btn = findViewById(R.id.login_btn);
        login_register = findViewById(R.id.login_register);
        login_keep_me_logged_in = findViewById(R.id.login_keep_me_logged_in);

        initViewModel();

        login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(view -> {

            String username = login_username.getText().toString(),
                    passwd = login_passwd.getText().toString();
            Boolean keepMeLoggedIn = login_keep_me_logged_in.isChecked();
            int errors = 0;

            if(username.isEmpty()){
                errors++;
                login_username.setHint(getResources().getString(R.string.username_error));
                login_username.setHintTextColor(getResources().getColor(R.color.colorWarningText));
            }

            if(passwd.isEmpty()){
                errors++;
                login_passwd.setHint(getResources().getString(R.string.passwd_error));
                login_passwd.setHintTextColor(getResources().getColor(R.color.colorWarningText));
            }

            if(errors == 0) {
                //do the deed
                for (Account acc : userList) {

                    boolean passwordMatch = PasswdUtils.verifyUserPassword(passwd, acc.getPasswd(), acc.getSalt());
                    //user and password match
                    if (username.equals(acc.getUserName()) && passwordMatch) {
                            //check if the don't ask me to log in again flag is set
                            if (keepMeLoggedIn != acc.isKeepMeLoggedIn()) {
                                //update the user account
                                mViewModel.logEveryoneOut();
                                mViewModel.updateUser(acc.getId(), keepMeLoggedIn);
                            }

                            //Save user for recall later
                            FormUtils.setLoggedInUserAccount(acc);

                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.success_login), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                            intent.putExtra(LOGGED_IN_USER, acc.getFirstname());
                            startActivity(intent);
                            finish();
                            return;
                    }

                }
                    //we should only see this if nothing matched.
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_login), Toast.LENGTH_SHORT).show();
            }

        });
    }



    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        final Observer<List<Account>> accountObserver = accounts -> {

            for(Account account : accounts){
                userList.add(account);
            }
        };

        mViewModel.mLiveAccount.observe(this, accountObserver);
    }


}
