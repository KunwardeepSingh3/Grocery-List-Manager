package edu.qc.seclass.glm;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddItemsActivity extends AppCompatActivity {

    public DatabaseHandler db = new DatabaseHandler(this);
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items_screen);
        TextView tv = (TextView) findViewById(R.id.addItemScreenTextView);
        final SearchView search = (SearchView) findViewById(R.id.addItemSearchBar);
        final Spinner spin = (Spinner) findViewById(R.id.addItemSpinner);


        Intent activityThatCalled = getIntent();
        final String listName = activityThatCalled.getStringExtra("listName");
        tv.setText(listName);

        db.populate();

        final ArrayList<String> s = db.showAllItemsFromGroceryTable();
        final ArrayList<String> s2 = db.getItemsFromTable(listName);

        final ListView lv = (ListView) findViewById(R.id.addItemListView);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                s);
        lv.setAdapter(adapter);

        final ListView lv2 = (ListView) findViewById(R.id.addItemListView2);
        adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                s2);
        lv2.setAdapter(adapter2);


        // *** searchFunction
        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                search.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                int position = spin.getSelectedItemPosition();
                ArrayList<String> theList = getTypeList(position);

                ArrayList<String> listToShow = db.searchBar(theList,newText);
                ArrayList<String> itemsToRemove = db.getItemsFromTable(listName);
                db.removeItemsFromList(listToShow,itemsToRemove);
                if(listToShow.size() <= 0) {
                    listToShow.add("Click here to add item to DB");
                }
                if(position != 0) {
                    listToShow = db.sortByType(listToShow);
                }
                adapter.clear();
                adapter.addAll(listToShow);
                return true;
            }


        };

        search.setOnQueryTextListener(queryTextListener);

        // *** viewByType
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.clear();
                ArrayList<String> items = getTypeList(position);
                ArrayList<String> itemsToRemove = db.getItemsFromTable(listName);
                db.removeItemsFromList(items, itemsToRemove);
                if(position != 0) {
                    items = db.sortByType(items);
                }
                search.clearFocus();
                adapter.addAll(items);

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                adapter.clear();

                ArrayList<String> allItems = s;
                // code here to remove items from allItems that's already in adapter2 (I will have to do this with the DB)

                adapter.addAll(s);

            }
        });


        // *** addToDB
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = lv.getItemAtPosition(position).toString();
                if(!s.equals("Click here to add item to DB")) {
                    db.addItemToTableByName(lv.getItemAtPosition(position).toString(), listName);
                    // Need to use spinner to set and adjust values
                    adapter2.insert(lv.getItemAtPosition(position).toString(), 0);
                    adapter.remove(lv.getItemAtPosition(position).toString());
                }else{
                    final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddItemsActivity.this);
                    final View promptView =  (LayoutInflater.from(AddItemsActivity.this)).inflate(R.layout.add_item_to_db,null);
                    alertBuilder.setView(promptView);

                    final Dialog dialog = alertBuilder.create();
                    dialog.show();

                    Button backBtn = (Button) promptView.findViewById(R.id.doNotAddToDB);
                    backBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    final Spinner spin = (Spinner) promptView.findViewById(R.id.createItemForDBSpinner);
                    final EditText editText1 = (EditText) promptView.findViewById(R.id.enterNameOfNewItem);
                    Button addItemBtn = (Button) promptView.findViewById(R.id.addToDB);

                    addItemBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String itemName = editText1.getText().toString();
                            int positionOfItem = spin.getSelectedItemPosition();
                            String typeName1 = spin.getItemAtPosition(positionOfItem).toString();

                            Item item = new Item(itemName,typeName1);

                            if(db.containsItem(item)){
                                editText1.setError("Item is already in the list");
                            }else if(!db.isValidListName(itemName)){
                                editText1.setError("Item can only contain letters, number and apostrophe");
                            }
                            else {
                                db.createItemToAddToDB(itemName,typeName1);
                                // Toast to show item created
                                dialog.dismiss();
								Toast.makeText(getApplicationContext(), itemName + " is created under the type " + typeName1,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });






                }

            }
        });

        // *** deleteItem
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                db.deleteItemByNameAndTable(lv2.getItemAtPosition(position).toString(), listName);
                adapter.insert(lv2.getItemAtPosition(position).toString(), 0);
                adapter2.remove(lv2.getItemAtPosition(position).toString());
            }
        });

    }



    public ArrayList<String> getTypeList(int position){
        ArrayList<String> ans = new ArrayList<>();
        switch(position){
            case 0: {
                List<Item> allGroceries = db.getAllGroceries();

                for(Item x: allGroceries){
                    ans.add(x.getName());
                }

                break;
            }
            case 1:{
                List<Item> allGroceries = db.getAllGroceries();
                for(Item x: allGroceries){
                    if(x.getItemType().equals(Constants.FRUIT_TYPE_NAME)) {
                        ans.add(x.getName());
                    }
                }
                break;
            }
            case 2:{
                List<Item> allGroceries = db.getAllGroceries();
                for(Item x: allGroceries){
                    if(x.getItemType().equals(Constants.VEGETABLE_TYPE_NAME)) {
                        ans.add(x.getName());
                    }
                }
                break;
            }
            case 3:{
                List<Item> allGroceries = db.getAllGroceries();
                for(Item x: allGroceries){
                    if(x.getItemType().equals(Constants.MEAT_TYPE_NAME)) {
                        ans.add(x.getName());
                    }
                }
                break;
            }
            case 4:{
                List<Item> allGroceries = db.getAllGroceries();
                for(Item x: allGroceries){
                    if(x.getItemType().equals(Constants.DAIRY_TYPE_NAME)) {
                        ans.add(x.getName());
                    }
                }
                break;
            }
            case 5:{
                List<Item> allGroceries = db.getAllGroceries();
                for(Item x: allGroceries){
                    if(x.getItemType().equals(Constants.FROZEN_TYPE_NAME)) {
                        ans.add(x.getName());
                    }
                }
                break;
            }
            case 6:{
                List<Item> allGroceries = db.getAllGroceries();
                for(Item x: allGroceries){
                    if(x.getItemType().equals(Constants.BAKERY_TYPE_NAME)) {
                        ans.add(x.getName());
                    }
                }
                break;
            }
            case 7:{
                List<Item> allGroceries = db.getAllGroceries();
                for(Item x: allGroceries){
                    if(x.getItemType().equals(Constants.BEVERAGE_TYPE_NAME)) {
                        ans.add(x.getName());
                    }
                }
                break;
            }
        }
        return ans;
    }


    public void goBackToListPage(View v){
        Intent getIntent = new Intent(AddItemsActivity.this, ListPageActivity.class);
        TextView listNameTextView = (TextView) findViewById(R.id.addItemScreenTextView);
        String listName = listNameTextView.getText().toString();
        getIntent.putExtra("listName",listName);
        startActivity(getIntent);
        finish();
    }





}
