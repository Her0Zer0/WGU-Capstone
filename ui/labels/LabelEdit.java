package com.slyfoxdevelopment.example2.ui.labels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.slyfoxdevelopment.example2.R;
import com.slyfoxdevelopment.example2.data.Account;
import com.slyfoxdevelopment.example2.data.Label;
import com.slyfoxdevelopment.example2.ui.records.AddBudgetRecord;
import com.slyfoxdevelopment.example2.utils.FormUtils;

import petrov.kristiyan.colorpicker.ColorPicker;

import static com.slyfoxdevelopment.example2.utils.Constants.LABEL_ID_KEY;
import static com.slyfoxdevelopment.example2.utils.Constants.LABEL_IS_NEW;

public class LabelEdit extends AppCompatActivity {

    ImageButton label_edit_color_btn;
    Button label_edit_save_btn;
    TextView label_edit_color;
    EditText label_edit_title;
    LabelEditViewModel mViewModel;
    boolean mNewLabel=true;

    public static final String TAG = "LabelEdit: ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Label");
        label_edit_title = findViewById(R.id.label_edit_title);
        label_edit_color = findViewById(R.id.label_edit_color);
        label_edit_save_btn = findViewById(R.id.label_edit_save_btn);
        label_edit_color_btn = findViewById(R.id.label_edit_color_btn);

        initViewModel();
        label_edit_color.setBackgroundColor(-11419154);

        //ColorPicker Class Reference: https://github.com/kristiyanP/colorpicker
        //set label color picker
        label_edit_color_btn.setOnClickListener(view -> {

            ColorPicker colorPicker = new ColorPicker(this);
            colorPicker.show();

            colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                @Override
                public void onChooseColor(int position, int color) {
                    if(position != -1){
                        label_edit_color.setBackgroundColor(color);
                    }

                    Log.i(TAG, "onChooseColor: Pos: " + position + " Color: " + color);
                }

                @Override
                public void onCancel() {

                }
            });

        });

        //set the onsave action

        label_edit_save_btn.setOnClickListener(view -> {
            String title = label_edit_title.getText().toString();
            ColorDrawable color = (ColorDrawable) label_edit_color.getBackground();
            int colorId = color.getColor();
            Account account = FormUtils.getLoggedInUserAccount();
            int error = 0;

            if(title.isEmpty()){
                error++;
            }

            //we should always have a default color

            Log.i(TAG, "onCreate: " + title + " " + 1*colorId + " " + error);

            if(error == 0){
                //save to the system
                Label label = new Label(title.toLowerCase(), Integer.toString(colorId), account.getId());
                mViewModel.insertLabel(label);

                Intent intent = new Intent(getApplicationContext(), LabelList.class);
                startActivity(intent);

                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

                finish();
            }else{
                Toast.makeText(this, "Please add a title first", Toast.LENGTH_SHORT).show();
            }




        });

    }

    private void initViewModel() {

        mViewModel = ViewModelProviders.of(this).get(LabelEditViewModel.class);

        mViewModel.mLabelMutableLiveData.observe(this, new Observer< Label >() {
            @Override
            public void onChanged(Label label) {
                if(label != null){
                    label_edit_title.setText(label.getTitle());
                    label_edit_color.setBackgroundColor( Integer.parseInt( label.getColor() ) );
                }
            }
        });

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            int label_id = extras.getInt(LABEL_ID_KEY);
            mNewLabel = extras.getBoolean(LABEL_IS_NEW);
            mViewModel.loadLabel(label_id);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater =  getMenuInflater();
        if(!mNewLabel){
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

        mViewModel.deleteCurrentLabel();
        Intent intent = new Intent(this, LabelList.class);
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
