package com.example.limayeneha.todo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by limayeneha on 2/9/17.
 */

public class ItemsAdapter extends ArrayAdapter<Item> {

    private List<Item> dataitem;
    private Activity activity;
    Item item;

    public ItemsAdapter(Activity activity, ArrayList<Item> items) {
        super(activity, 0, items);
        this.dataitem = items;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_view, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvItemName);
        TextView tvDueDate = (TextView) convertView.findViewById(R.id.tvDueDate);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
//        // Populate the data into the template view using the data object
        tvName.setText(item.taskName);
        tvDueDate.setText(item.dueDate);
        tvPriority.setText(item.priority);
        if(item.priority.equalsIgnoreCase("HIGH")) {
            tvPriority.setTextColor(Color.RED);
        } else if(item.priority.equalsIgnoreCase("MEDIUM")) {
            tvPriority.setTextColor(Color.BLUE);
        } else {
            tvPriority.setTextColor(Color.GREEN);
        }

        // Return the completed view to render on screen
        return convertView;
    }

    public synchronized void refreshAdapter(List<Item> dataitems) {

        dataitem.clear();
        dataitem.addAll(dataitems);
        notifyDataSetChanged();
    }

}
