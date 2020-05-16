package com.slyfoxdevelopment.example2.ui.catitem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.slyfoxdevelopment.example2.R;
import com.slyfoxdevelopment.example2.data.Account;
import com.slyfoxdevelopment.example2.data.Categories;
import com.slyfoxdevelopment.example2.ui.login.HomeScreen;
import com.slyfoxdevelopment.example2.ui.records.AddBudgetRecord;
import com.slyfoxdevelopment.example2.utils.FormUtils;

import static com.slyfoxdevelopment.example2.utils.Constants.CATEGORY_ID_KEY;
import static com.slyfoxdevelopment.example2.utils.Constants.CATEGORY_IS_NEW;

public class CategoryEdit extends AppCompatActivity {

    EditText category_edit_title;
    Button category_edit_save_btn;
    CategoryEditViewModel mViewModel;
    boolean mNewCatitem = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Category");

        category_edit_title = findViewById(R.id.category_edit_title);
        category_edit_save_btn = findViewById(R.id.category_edit_save_btn);

        initViewModel();



        category_edit_save_btn.setOnClickListener(view -> {
            String title = category_edit_title.getText().toString();
            Account account = FormUtils.getLoggedInUserAccount();

            if(!title.isEmpty()){

                Categories category = new Categories(title.substring(0,1).toUpperCase() + title.substring(1).toLowerCase(), account.getId());
                mViewModel.insertCategory(category);

                Intent intent = new Intent(getApplicationContext(), CategoryList.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void initViewModel() {

        mViewModel = ViewModelProviders.of(this).get(CategoryEditViewModel.class);

        mViewModel.mCategoriesMutableLiveData.observe(this, new Observer< Categories >() {
            @Override
            public void onChanged(Categories categories) {
                if(categories != null){
                    category_edit_title.setText(categories.getTitle());
                }
            }
        });

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            int cat_id = extras.getInt(CATEGORY_ID_KEY);
            mNewCatitem = extras.getBoolean(CATEGORY_IS_NEW);
            mViewModel.loadCategory(cat_id);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater =  getMenuInflater();
        if(!mNewCatitem){
            menuInflater.inflate(R.menu.menu_edit, menu);
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

        mViewModel.deleteCurrentCatItem();
        Intent intent = new Intent(this, CategoryList.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
