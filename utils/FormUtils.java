package com.slyfoxdevelopment.example2.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import com.slyfoxdevelopment.example2.data.Account;
import com.slyfoxdevelopment.example2.data.SampleData;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FormUtils extends Activity {

    private static String abc = "abcdefghijklmnopqrstuvwxyzABCEDFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+\\|/?><,`~";


    private static Account mAccount;

    public static void setLoggedInUserAccount(Account account){
        mAccount = account;
    }

    public static Account getLoggedInUserAccount(){
        return mAccount;
    }


    public static void makeCalendarDialog(Context context, TextView textView){

            Calendar c;
            DatePickerDialog dpd;

            c = Calendar.getInstance();
            int dd = c.get(Calendar.DAY_OF_MONTH);
            int mm = c.get(Calendar.MONTH);
            int yyyy = c.get(Calendar.YEAR);

            dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                    textView.setText( ( mMonth + 1 ) + "/" + mDay + "/" + mYear);
                }
            }, yyyy, mm, dd);
            dpd.show();
    }

    public static Date getDateMonth(int diff){
        return SampleData.getDate(diff);
    }

    public static Date isAfterYesterday(int diff){
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, diff);
        return cal.getTime();
    }


}






