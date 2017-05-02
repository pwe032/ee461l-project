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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class UserIngredientsActivity extends AppCompatActivity {
    RecipeDatabase rdb;
    TableLayout table;
    private ArrayList<String> userIngredients;
    EditText ingredientsTypeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ingredients);
        table = (TableLayout) findViewById(R.id.tlGridTable);
        rdb = new RecipeDatabase();     //TODO: get this part done/to work without making RecipeDatabase public
        userIngredients = new ArrayList<String>();
        ingredientsTypeEditText = (EditText) findViewById(R.id.ingredientsTypeEditText);
    }


public ArrayList<String> sort(ArrayList<String> userIngredients){//}, ArrayList<String> userIngredientsNum) {
    String tmp;
    String tmpNum;
    for (int i = 0;i < userIngredients.size();i++)
    {
        tmp = userIngredients.get(i);
        tmpNum = userIngredients.get(i);
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
        String ingredientsTypeText = ingredientsTypeEditText.getText().toString();
        ingredientsTypeText = ingredientsTypeText.trim();
        if(!(userIngredients.contains(ingredientsTypeText))){            //only add if ingredient isn't already in there
            userIngredients.add(ingredientsTypeText);                    //add ingredient to use ingredient list
        }

        sort(userIngredients);


        //now update the table view of the user ingredients
        table = (TableLayout)findViewById(R.id.tlGridTable);
        table.removeAllViews();                                                 //clear the table

        for (int i = 0; i < userIngredients.size(); i++) {           //add back the new table
            TableRow row = new TableRow(this);
            String iNameString = userIngredients.get(i);
            TextView iNameTextView = new TextView(this);
            iNameTextView.setText("" + iNameString);
            row.addView(iNameTextView);
            table.addView(row);
        }

        System.out.println(ingredientsTypeText);
        ingredientsTypeEditText.getText().clear();
    }

    public void deleteButtonOnClick(View view){

        String ingredientsTypeText = ingredientsTypeEditText.getText().toString();

        ingredientsTypeText = ingredientsTypeText.trim();

        int index = userIngredients.indexOf(ingredientsTypeText);
        userIngredients.remove(ingredientsTypeText);            //delete the ingredient to the user ArrayList of ingredients



        //now update the table view of the user ingredients
        table = (TableLayout)findViewById(R.id.tlGridTable);
        table.removeAllViews();                                                 //clear the table

        for (int i = 0; i < userIngredients.size(); i++) {           //add back the new table
            TableRow row = new TableRow(this);
            String iNameString = userIngredients.get(i);
            TextView iNameTextView = new TextView(this);
            iNameTextView.setText("" + iNameString);
            row.addView(iNameTextView);
            table.addView(row);
        }

        ingredientsTypeEditText.getText().clear();
    }

    public ArrayList<String> getUserIngredients(){
        return userIngredients;
    }

}
