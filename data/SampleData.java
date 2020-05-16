package com.slyfoxdevelopment.example2.data;

import com.slyfoxdevelopment.example2.utils.FormUtils;
import com.slyfoxdevelopment.example2.utils.PasswdUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleData {

    static int accountId = 1;
    static String salt = PasswdUtils.getSalt(30);
    static String mySecurePassword = PasswdUtils.generateSecurePassword("1234", salt);

    public static Date getDate(int diff){
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MONTH, diff);
        return cal.getTime();
    }

    public static Account setAccount(){
        return new Account(accountId,"tester", mySecurePassword, "John", "Doe", salt, getDate(-5), false);
    }

    public static List< Label> setLabels(){
        List<Label> labels = new ArrayList<>();
        labels.add(new Label(1,"food", "-14654801", accountId));
        labels.add(new Label(2,"gas", "-10896368", accountId));
        labels.add(new Label(3,"school", "-740056", accountId));
        labels.add(new Label(4,"electric", "-7583749", accountId));
        labels.add(new Label(5,"water", "-3246217", accountId));
        labels.add(new Label(6,"truck parts", "-11419154", accountId));
        return labels;
    }

    public static List<Categories> setCategories(){
        List<Categories> categories = new ArrayList<>();

        categories.add(new Categories(1,"Utilities", accountId));
        categories.add(new Categories(2,"Groceries", accountId));
        categories.add(new Categories(3,"Automotive", accountId));
        categories.add(new Categories(4,"Other", accountId));

        return categories;
    }

    public static List<Budget> setBudgets(){
        List<Budget> budgets = new ArrayList<>();
        //income
        budgets.add( new Budget(1, "1500.00","Income","", "", "", getDate(-5), "Starting with my pay raise", accountId) );
        //5 months ago
        budgets.add( new Budget(2, "124.00","Expense","food", "-14654801", "Groceries", getDate(-5), "Groceries from Dollar General", accountId) );
        budgets.add( new Budget(3, "60.00","Expense","gas", "-10896368", "Automotive", getDate(-5),  "Exxon station at the end of town", accountId) );
        budgets.add( new Budget(4, "24.00","Expense","water", "-3246217", "Utilities", getDate(-5), "", accountId) );
        budgets.add( new Budget(5, "98.75","Expense","electric", "-7583749", "Utilities", getDate(-5), "", accountId) );


        //4 months ago
        budgets.add( new Budget(6, "240.00","Expense","food", "-14654801", "Groceries", getDate(-4),  "Caught a sale on little pizzas at the local Wal-Mart", accountId) );
        budgets.add( new Budget(7, "54.50","Expense","gas", "-10896368", "Automotive", getDate(-4),  "Circle K", accountId) );
        budgets.add( new Budget(8, "28.00","Expense","water", "-3246217", "Utilities", getDate(-4), "", accountId) );
        budgets.add( new Budget(9, "88.73","Expense","electric", "-7583749", "Utilities", getDate(-4),  "", accountId) );
        budgets.add( new Budget(10, "100.50","Expense","gas", "-10896368", "Automotive", getDate(-4),  "Took a trip to see my family 243 miles away", accountId) );


        //3 months ago
        budgets.add( new Budget(11, "60.00","Expense","food", "-14654801", "Groceries", getDate(-3),  "Didn't have to buy much this time because of all the pizza surplus", accountId) );
        budgets.add( new Budget(12, "54.50","Expense","gas", "-10896368", "Automotive", getDate(-3),  "Circle K", accountId) );
        budgets.add( new Budget(13, "25.38","Expense","water", "-3246217", "Utilities", getDate(-3),  "", accountId) );
        budgets.add( new Budget(14, "73.25","Expense","electric", "-7583749", "Utilities", getDate(-3),  "", accountId) );
        budgets.add( new Budget(15, "100.50","Expense","truck parts", "-11419154", "Automotive", getDate(-3),  "Took a trip to see my family 243 miles away", accountId) );


        //2 months ago
        budgets.add( new Budget(16, "74.00","Expense","food", "-14654801", "Groceries", getDate(-2),  "More little pizzas", accountId) );
        budgets.add( new Budget(17, "58.50","Expense","gas", "-10896368", "Automotive", getDate(-2),  "Exxon station at the end of town", accountId) );
        budgets.add( new Budget(18, "24.00","Expense","water", "-3246217", "Utilities", getDate(-2), "", accountId) );
        budgets.add( new Budget(19, "73.05","Expense","electric", "-7583749", "Utilities", FormUtils.isAfterYesterday(-18),  "", accountId) );

        budgets.add( new Budget(20, "7.00","Expense","no label", "", "", FormUtils.isAfterYesterday(-5),  "Gas station food", accountId) );
        budgets.add( new Budget(21, "12.00","Expense","gas", "-10896368", "Automotive", FormUtils.isAfterYesterday(-4),  "Gas station food", accountId) );
        budgets.add( new Budget(22, "42.50","Expense","gas", "-10896368", "Automotive", FormUtils.isAfterYesterday(-3),  "Exxon station at the end of town", accountId) );
        budgets.add( new Budget(23, "32.00","Expense","water", "-3246217", "Utilities", FormUtils.isAfterYesterday(-4),  "", accountId) );
        budgets.add( new Budget(24, "48.05","Expense","electric", "-7583749", "Utilities", FormUtils.isAfterYesterday(-1),  "", accountId) );


        return budgets;
    }

}
