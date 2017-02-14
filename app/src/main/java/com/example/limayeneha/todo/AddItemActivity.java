package com.example.limayeneha.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.limayeneha.todo.R.id.dpDatePicker;
import static com.example.limayeneha.todo.R.id.etEditItem;

public class AddItemActivity extends AppCompatActivity {

    String setPriority;
    String itemName;
    String dueDate;
    DatePicker dpDueDate;
    EditText etAddItem;
    Spinner priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        etAddItem = (EditText) findViewById(R.id.etAddItem);
        dpDueDate = (DatePicker) findViewById(R.id.dpDatePicker);

        priority = (Spinner) findViewById(R.id.spinPriority);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Priorities);
        priority.setAdapter(spinnerAdapter);
        priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                setPriority = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_item_menu, menu);
        return true;
    }

    static final String[] Priorities = new String[] { "LOW", "MEDIUM",
            "HIGH" };

    public String getDateFromDatePicker(){
        int day = dpDueDate.getDayOfMonth();
        int month = dpDueDate.getMonth();
        int year =  dpDueDate.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        String formattedDate = new SimpleDateFormat("MM-dd-yy").format(calendar.getTime());

        return formattedDate;
    }

    public void saveTask(MenuItem menuitem) {

        if(etAddItem!=null) itemName = etAddItem.getText().toString();
        if(dpDueDate!=null) dueDate = getDateFromDatePicker();
        if(itemName.length()<1 || setPriority.isEmpty()) {
            Toast.makeText(this, "Enter missing information.", Toast.LENGTH_SHORT).show();
        } else {
            Item item = new Item(itemName, 1, setPriority, dueDate);
            item.save();

            Intent data = new Intent(this, MainActivity.class);
            setResult(RESULT_OK, data);


            finish();
        }

    }

    public void cancelTask(MenuItem item) {
        finish();
    }
}
