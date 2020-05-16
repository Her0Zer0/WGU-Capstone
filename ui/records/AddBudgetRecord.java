package com.slyfoxdevelopment.example2.ui.records;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.slyfoxdevelopment.example2.R;
import com.slyfoxdevelopment.example2.data.Account;
import com.slyfoxdevelopment.example2.data.Budget;
import com.slyfoxdevelopment.example2.data.Categories;
import com.slyfoxdevelopment.example2.data.Label;
import com.slyfoxdevelopment.example2.utils.Converters;
import com.slyfoxdevelopment.example2.utils.FormUtils;

import java.io.File;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.slyfoxdevelopment.example2.utils.Constants.BUDGET_ID_KEY;
import static com.slyfoxdevelopment.example2.utils.Constants.BUDGET_IS_NEW;

public class AddBudgetRecord extends AppCompatActivity {

    EditText budget_amount, budget_notes;
    TextView budget_created;
    Spinner budget_type_spinner, budget_category_spinner, budget_label_spinner;
    ImageButton budget_date_range, budget_created_date_range;
    Button budget_save_btn;
    AddBudgetRecordViewModel mViewModel;
    List< Label > labelList = new ArrayList< Label >();
    List<String> labelListSpinnerData = new ArrayList<>();

    List< Categories > categoryList = new ArrayList< Categories>();
    List<String> categoryListSpinnerData = new ArrayList<>();

    Account loggedInUser = FormUtils.getLoggedInUserAccount();

    boolean mNewBudget =true;

    public static final String TAG = "AddBudgetRecord: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget_record);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Record");

        //EditText
        budget_amount = findViewById(R.id.budget_amount);
        budget_notes = findViewById(R.id.budget_notes);

        //TextView
//        budget_recurring = findViewById(R.id.budget_recurring);
        budget_created = findViewById(R.id.budget_created);


        //Spinner
        budget_type_spinner = findViewById(R.id.budget_type_spinner);
        budget_category_spinner = findViewById(R.id.budget_category_spinner);
        budget_label_spinner = findViewById(R.id.budget_label_spinner);

        //ImageButton
        budget_date_range = findViewById(R.id.budget_date_range);
        budget_created_date_range = findViewById(R.id.budget_created_date_range);

        //Button
        budget_save_btn = findViewById(R.id.budget_save_btn);

        initViewModel();

        initLabalSpinnerData();

        initCategorySpinnerData();

        budget_created_date_range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormUtils.makeCalendarDialog(AddBudgetRecord.this, budget_created);
            }
        });

        //set up category spinner
        initCategorySpinner();

        //set up label spinner
        initLabelSpinner();

        //set up budget type
        initBudgetType();

        budget_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAndReturn();
            }
        });




    }


    private void saveAndReturn() {

        String amount = budget_amount.getText().toString().trim();
        String budget_type = budget_type_spinner.getSelectedItem().toString();
        String label_title = budget_label_spinner.getSelectedItem().toString();
        String label_color = "";
        String category = budget_category_spinner.getSelectedItem().toString();
        String notes = budget_notes.getText().toString();
        String created = budget_created.getText().toString();
        Date created_date;

        String abc = "abcdefghijklmnopqrstuvwxyzABCEDFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+\\|/?><,`~";


        int error_count = 0;





        if(amount.isEmpty()){
            error_count++;
            budget_amount.setHintTextColor(getResources().getColor(R.color.colorWarningText));
        }


        if(!amount.isEmpty()){

            String[] string_array = abc.split("");
            String[] testingStr = amount.split("");

            for(int i = 0; i < testingStr.length; i++){

                for(int j = 0; j < string_array.length; j++){

                    if( testingStr[i].equalsIgnoreCase(string_array[j]) && !testingStr[i].isEmpty()){
                        Log.i(TAG, "saveAndReturn: matching " + testingStr[i] + " " + string_array[j]);

                        error_count++;
                        budget_amount.setHintTextColor(getResources().getColor(R.color.colorWarningText));
                        budget_amount.setHint("Amount cannot contain letters");
                        break;
                    }
                }

            }


        }


        if(!label_title.isEmpty()){
            for(Label label : labelList){
                if(label.getTitle() == label_title){
                    label_color = label.getColor();
                    break;
                }
            }
        }


        if(created.isEmpty()){
            created_date = new Date();
        }else{
            created_date = Converters.convertToDate(created);
        }



        if(error_count == 0){
            //do the deed
            //Log.i(TAG, "saveAndReturn: Amount = " + amount + " Type = " + budget_type + " Label = " + label_title + " Color = " + label_color + " ReDate = " + recurring_date );

            Budget budget = new Budget(amount, budget_type, label_title, label_color, category, created_date, notes, loggedInUser.getId());

            mViewModel.saveBudget(budget);
            Intent intent = new Intent(getApplicationContext(), BudgetList.class);
            startActivity(intent);
            finish();

        }else{
            Toast.makeText(this, "Please set an amount before continuing", Toast.LENGTH_SHORT).show();
        }

    }

    private void initBudgetType() {
        //budget_type
        String[] budget_type = getResources().getStringArray(R.array.budget_type);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, budget_type);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        budget_type_spinner.setAdapter(typeAdapter);
        budget_type_spinner.setSelection(0);
    }

    private void initLabelSpinner() {
        ArrayAdapter<String> labelAdapter = new ArrayAdapter<String>(this, R.layout.spinner, R.id.spinner_default, labelListSpinnerData);
        labelAdapter.setDropDownViewResource(R.layout.spinner);
        budget_label_spinner.setAdapter(labelAdapter);
        budget_label_spinner.setSelection(0);

    }

    private void initCategorySpinner() {
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, R.layout.spinner, R.id.spinner_default, categoryListSpinnerData);
        categoryAdapter.setDropDownViewResource(R.layout.spinner);
        budget_category_spinner.setAdapter(categoryAdapter);
        budget_category_spinner.setSelection(0);
    }


    private void initLabalSpinnerData(){

        labelListSpinnerData.clear();

        labelListSpinnerData.add("No Label");

        final Observer<List<Label>> labelObserver = new Observer< List< Label > >() {
            @Override
            public void onChanged(List< Label > labels) {
                //we may still need to have the full label
                labelList.clear();
                labelList.addAll(labels);

                //now we grab the strings for our spinner
                for(Label label: labels){
                    labelListSpinnerData.add(label.getTitle());
                    Log.i(TAG, "onChanged: " + label.getAccount_id() + " " +  loggedInUser.getUserName() +  " " +loggedInUser.getId() );
                }
            }
        };

        mViewModel.mLiveLabelList.observe(this, labelObserver);
    }


    private void initCategorySpinnerData(){
         categoryListSpinnerData.clear();
        categoryListSpinnerData.add("Uncategorized");

        final Observer<List<Categories>> categoryObserver = new Observer< List< Categories > >() {
            @Override
            public void onChanged(List< Categories > categories) {
                for(Categories cat: categories){
                    if(cat.getAccount_id() == loggedInUser.getId()){
                        categoryListSpinnerData.add(cat.getTitle());
                    }
                }
            }
        };

        mViewModel.mLiveCategoriesList.observe(this, categoryObserver);
    }

    private void initViewModel() {

        mViewModel = ViewModelProviders.of(this).get(AddBudgetRecordViewModel.class);

        mViewModel.mBudgetMutableLiveData.observe(this, new Observer< Budget >() {
            @Override
            public void onChanged(Budget budget) {
                if(budget != null){
                    budget_amount.setText(budget.getAmount());

                    /**
                     * Budget Type set
                     */

                    setBudgetSpinner(budget.getType());

                    /**
                     * Label Spinner set
                     */
                    setLabelSpinner(budget.getLabel_title());

                    /**
                     * Category spinner set
                     */
                    setCategorySpinner(budget.getCategory());

                    budget_notes.setText(budget.getNotes());

                    budget_created.setText(Converters.convertDateForView(budget.getCreated_date()));

                }
            }
        });

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            int budget_id = extras.getInt(BUDGET_ID_KEY);
            mNewBudget = extras.getBoolean(BUDGET_IS_NEW);
            mViewModel.loadRecord(budget_id);
        }
    }

    private void setBudgetSpinner(String str){
        String[] budget_type = getResources().getStringArray(R.array.budget_type);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, budget_type);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        budget_type_spinner.setAdapter(typeAdapter);
        if(str != null){
            int pos = typeAdapter.getPosition(str);
            budget_type_spinner.setSelection(pos);
        }

    }

    private void setLabelSpinner(String str){
        List<String> labelList = new ArrayList<>();

        labelList.add("No Label");
        for(Label label: mViewModel.mLabelsList){
            labelList.add(label.getTitle());
        }


        ArrayAdapter<String> labelAdapter = new ArrayAdapter<String>(AddBudgetRecord.this, R.layout.spinner, R.id.spinner_default, labelList);
        labelAdapter.setDropDownViewResource(R.layout.spinner);
        budget_label_spinner.setAdapter(labelAdapter);

//        Log.i(TAG, "setLabelSpinner: " + labelAdapter.getItem(1));
        if(str != null){
            int labelPos = labelAdapter.getPosition(str);
            budget_label_spinner.setSelection(labelPos);
        }
    }

    private void setCategorySpinner(String str){
        List<String> catList = new ArrayList<>();


        catList.add("Uncategorized");
        for(Categories cat : mViewModel.mCategoriesList){
            catList.add(cat.getTitle());
        }

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, R.layout.spinner, R.id.spinner_default, catList);
        categoryAdapter.setDropDownViewResource(R.layout.spinner);
        budget_category_spinner.setAdapter(categoryAdapter);
        if(str != null){
            int pos = categoryAdapter.getPosition(str);
            budget_category_spinner.setSelection(pos);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater =  getMenuInflater();
        if(!mNewBudget){
            menuInflater.inflate(R.menu.menu_edit, menu);
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_edit_delete){
            deleteAndFinish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAndFinish() {

        mViewModel.deleteCurrentBudget();
        Intent intent = new Intent(this, BudgetList.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        finish();
        return true;
    }

}


