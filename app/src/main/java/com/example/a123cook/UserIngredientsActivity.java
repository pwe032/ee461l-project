package com.example.a123cook;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class UserIngredientsActivity extends AppCompatActivity {
    RecipeDatabase rdb;
    TableLayout table;
    AutoCompleteTextView autoCompleteTextView;
    private ArrayList<String> userIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ingredients);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, INGREDIENTS);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView);
        textView.setAdapter(adapter);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        table = (TableLayout) findViewById(R.id.tlGridTable);
        rdb = new RecipeDatabase();     //TODO: get this part done/to work without making RecipeDatabase public
        userIngredients = new ArrayList<String>();
    }

    private static final String[] INGREDIENTS = new String[]{      //TODO: create more autocomplete ingredients
            "Eggs", "Bacon", "Rice", "Salt", "Pepper", "Olive Oil", "Cheese", "Dough"
    };

//    public void searchButtonOnClick(View view) {
//        ArrayList<Integer> ingredientNumberArrList = new ArrayList<Integer>();
//        ArrayList<String> ingredientNameArrList = new ArrayList<String>();
//
//        for(int i = 0; i < 6; i++){
//            ingredientNumberArrList.add(i);
//            ingredientNameArrList.add("hello");
//        }
//
//        table = (TableLayout)findViewById(R.id.tlGridTable);
//
////        TableLayout table = (TableLayout) findViewById(R.id.myTableLayout);
//        for (int i = 0; i < ingredientNumberArrList.size(); i++) {
//            TableRow row = new TableRow(this);
//            Integer iNumInt = ingredientNumberArrList.get(i);
//            String iNameString = ingredientNameArrList.get(i);
//
//            TextView iNumTextView = new TextView(this);
//            iNumTextView.setText("" + iNumInt.toString());
//
//            TextView iNameTextView = new TextView(this);
//            iNameTextView.setText("" + iNameString);
//
//            row.addView(iNumTextView);
//            row.addView(iNameTextView);
//            table.addView(row);
//        }

//public void autoCompleteKeyboardOnClick(View view) {
//    if(view.requestFocus()){
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
//    }
//}


public ArrayList<String> sort(ArrayList<String> userIngredients) {
    String tmp;
    for (int i = 0;i < userIngredients.size();i++)
    {
        tmp = userIngredients.get(i);
        for (int j = 0;j < userIngredients.size();j++)
        {
            if (i == j) continue;
            int x = tmp.compareTo(userIngredients.get(j));
            if (x < 0)
            {
                tmp = userIngredients.get(j);
                userIngredients.set(j, userIngredients.get(i));
                userIngredients.set(i, tmp);
            }
        }
    }
    return userIngredients;
}


//    }

    public void addButtonOnClick(View view){
        String autoCompleteText = autoCompleteTextView.getText().toString();
//        Ingredient i = new Ingredient("User", autoCompleteText);
//        ingre.addRecipeIngredients("User", autoCompleteText);       //add the ingredient to the user ArrayList of ingredients

        if(!(userIngredients.contains(autoCompleteText))){            //only add if ingredient isn't already in there
            userIngredients.add(autoCompleteText);                    //add ingredient to use ingredient list
        }

        sort(userIngredients);


        //now update the table view of the user ingredients
        table = (TableLayout)findViewById(R.id.tlGridTable);
        table.removeAllViews();                                                 //clear the table

        for (int i = 0; i < userIngredients.size(); i++) {           //add back the new table
            TableRow row = new TableRow(this);
//            Integer iNumInt = ingredientNumberArrList.get(i);
            String iNameString = userIngredients.get(i);

//            TextView iNumTextView = new TextView(this);
//            iNumTextView.setText("" + iNumInt.toString());

            TextView iNameTextView = new TextView(this);
            iNameTextView.setText("" + iNameString);

//            row.addView(iNumTextView);
            row.addView(iNameTextView);
            table.addView(row);
        }
    }

    public void deleteButtonOnClick(View view){
        String autoCompleteText = autoCompleteTextView.getText().toString();
//        Ingredient i = new Ingredient("User", autoCompleteText);
        userIngredients.remove(autoCompleteText);            //delete the ingredient to the user ArrayList of ingredients
        sort(userIngredients);




        //now update the table view of the user ingredients
        table = (TableLayout)findViewById(R.id.tlGridTable);
        table.removeAllViews();                                                 //clear the table

        for (int i = 0; i < userIngredients.size(); i++) {           //add back the new table
            TableRow row = new TableRow(this);
//            Integer iNumInt = ingredientNumberArrList.get(i);
            String iNameString = userIngredients.get(i);

//            TextView iNumTextView = new TextView(this);
//            iNumTextView.setText("" + iNumInt.toString());

            TextView iNameTextView = new TextView(this);
            iNameTextView.setText("" + iNameString);

//            row.addView(iNumTextView);
            row.addView(iNameTextView);
            table.addView(row);
        }
    }

    public ArrayList<String> getUserIngredients(){
        return userIngredients;
    }
}
