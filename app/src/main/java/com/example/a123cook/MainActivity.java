package com.example.a123cook;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    public DrawerLayout mDrawer;
    private Toolbar toolbar;
    public NavigationView nvDrawer;

    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    private ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
    }

    public void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        String selection;
        Class fragmentClass;

        LayoutInflater inflater = getLayoutInflater();
        RelativeLayout container = (RelativeLayout) findViewById(R.id.content_frame);

        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                Intent search = new Intent(this, SearchActivity.class);
                startActivity(search);
                break;
            case R.id.nav_second_fragment:
                Intent ingredients = new Intent(this, UserIngredientsActivity.class);      //MyIngredients
                startActivity(ingredients);
                break;
            case R.id.nav_third_fragment:
                Intent suggestions = new Intent(this, SearchRecipesActivity.class);        //SuggestedRecipes
                startActivity(suggestions);
                break;
            case R.id.nav_fourth_fragment:
                Intent message = new Intent(this, MessageActivity.class);
                startActivity(message);
                break;
            case R.id.nav_fifth_fragment:
                Intent account = new Intent(this, AccountActivity.class);
                startActivity(account);
                break;
            case R.id.nav_sixth_fragment:
                Intent signOut = new Intent(this, SignOutActivity.class);
                startActivity(signOut);
                break;
            case R.id.nav_seventh_fragment:
                Intent makeRecipe = new Intent(this, MakeRecipeActivity.class);
                startActivity(makeRecipe);
                break;
            default:
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

}

