package com.example.limayeneha.todo;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity implements EditItemDialog.OnFragmentInteractionListener {

    private final int REQUEST_CODE = 20;
    ItemsAdapter itemsAdapter;
    ListView lvItems;
    List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ItemsAdapter(this, (ArrayList<Item>) itemList);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }



    private void setupListViewListener() {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showEditDialog(position, itemList.get(position));

            }
        });
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View view, int position, long id) {
                        itemList.get(position).delete();
                        readItems();
                        itemsAdapter.refreshAdapter(itemList);
                        return true;
                    }
                });
    }

    private void showEditDialog(int position, Item item) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialog editNameDialogFragment = EditItemDialog.newInstance(position, item);
        editNameDialogFragment.show(fm, "fragment_edit_item");
    }


    public void readItems() {
        itemList = SQLite.select().
                from(Item.class).queryList();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            readItems();

            itemsAdapter.refreshAdapter(itemList);

        }
    }

    public void addANewTask(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onFragmentInteraction(int position, Item item) {

        readItems();

        itemsAdapter.refreshAdapter(itemList);
    }
}
