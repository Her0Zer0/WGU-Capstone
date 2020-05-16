package com.slyfoxdevelopment.example2.ui.login;

import android.os.Bundle;

import com.slyfoxdevelopment.example2.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TermsAndConditions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        setTitle("Terms and Conditions");
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}
