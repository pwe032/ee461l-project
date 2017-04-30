package com.example.a123cook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class SearchActivity extends AppCompatActivity {

        public String Entry;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);
        }

        public void startSearchActivity(View view){
            EditText name = (EditText) findViewById(R.id.edit_message);
            Entry = name.getText().toString();
            Intent resultsPage = new Intent(this, SearchResultsActivity.class); //CHNAGE TO MESSAGE ACT
            resultsPage.putExtra("Name", Entry);
            startActivity(resultsPage);
        }




}
