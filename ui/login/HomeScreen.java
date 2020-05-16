package com.slyfoxdevelopment.example2.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.slyfoxdevelopment.example2.R;
import com.slyfoxdevelopment.example2.data.Budget;
import com.slyfoxdevelopment.example2.ui.home.HomeViewModel;
import com.slyfoxdevelopment.example2.ui.home.LatestBudgetAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.slyfoxdevelopment.example2.utils.Constants.LOGGED_IN_USER;
import static com.slyfoxdevelopment.example2.utils.Constants.LOGGED_IN_USER_ID;

public class HomeScreen extends AppCompatActivity {
    public static final String TAG =  "HomeScreen: ";
    int logged_in_user_id;
    String logged_in_user;
    TextView logged_in_user_placeholder, logged_in_user_current_balance;

    Double results =0.0;

    HomeViewModel homeViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        logged_in_user_placeholder = findViewById(R.id.logged_in_user_placeholder);
        logged_in_user_current_balance = findViewById(R.id.logged_in_user_current_balance);

        TextView logged_in_user_current_balance = findViewById(R.id.logged_in_user_current_balance);



        final Observer<List<Budget>> allBudgetObserver = new Observer< List< Budget > >() {
            @Override
            public void onChanged(List< Budget > budgets) {
                Double income = 0.0, expense = 0.0;

                for(Budget budget: budgets){

                    if(budget.getType().equalsIgnoreCase("income")){
                        income += Double.parseDouble(budget.getAmount());
                    }

                    if(budget.getType().equalsIgnoreCase("expense")){
                        expense += Double.parseDouble(budget.getAmount());
                    }

                    results = income - expense;

                    logged_in_user_current_balance.setText("Current Balance: " +Double.toString(Math.round(results * 100D) / 100D));
                }
            }
        };

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        homeViewModel.mLiveDateAllBudgets.observe(this, allBudgetObserver);





        Bundle extras = getIntent().getExtras();

        if(extras != null){
            logged_in_user =  extras.getString(LOGGED_IN_USER);
            logged_in_user_id = extras.getInt(LOGGED_IN_USER_ID);
            Log.i(TAG, "onCreate: " + logged_in_user_id + " " + logged_in_user);
            logged_in_user_placeholder.setText("Logged in as: " + logged_in_user);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_terms_and_conditions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            logOutUser();
        }


        return super.onOptionsItemSelected(item);
    }

    private void logOutUser() {
        homeViewModel.logOutUser();
        Intent intent = new Intent(this, SplashScreen.class);
        startActivity(intent);
    }
}
