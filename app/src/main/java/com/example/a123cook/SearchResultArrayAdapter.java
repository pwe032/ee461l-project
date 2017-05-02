package com.example.a123cook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class SearchResultArrayAdapter extends ArrayAdapter<String>{
    private final Context context;
    private final ArrayList<String> values;

    public SearchResultArrayAdapter(Context context, ArrayList<String> values) {
        super(context, R.layout.activity_searchresults, values);
        this.context = context;
        this.values = values;
    }

    @Override


    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_searchresults, parent, false);
        //create View components that make up the recipe post
        TextView foodName = (TextView) rowView.findViewById(R.id.textfield); // Recipe post has a food name
        foodName.setText(values.get(position));
        return rowView;
    } //end of getView()

} //end of ProfileArrayAdapter
