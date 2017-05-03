package com.example.a123cook;

import android.content.Intent;
import android.media.midi.MidiDevice;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class SearchActivity extends MainActivity {

        public String Entry;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);
            mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            nvDrawer = (NavigationView) findViewById(R.id.nvView);
            setupDrawerContent(nvDrawer);
        }

        public void startSearchActivity(View view){
            EditText name = (EditText) findViewById(R.id.edit_message);
            Entry = name.getText().toString();
            Intent resultsPage = new Intent(this, SearchResultsActivity.class); //CHNAGE TO MESSAGE ACT
            resultsPage.putExtra("Name", Entry);
            startActivity(resultsPage);
        }




}
