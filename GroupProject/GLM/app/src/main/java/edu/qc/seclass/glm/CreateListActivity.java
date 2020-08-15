package edu.qc.seclass.glm;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CreateListActivity extends AppCompatActivity {

    public DatabaseHandler db = new DatabaseHandler(this);
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list_screen);
        // Here I might have to change the code here
        db.createTable(Constants.PLACE_HOLDER_DB_NAME);

        final ListView lv = (ListView) findViewById(R.id.createListScreenListView);
        final ListView lv2 = (ListView) findViewById(R.id.createListScreenListView2);
        final Spinner spin = (Spinner) findViewById(R.id.spinner);
        final SearchView search = (SearchView) findViewById(R.id.createListSearchView);

        Intent activityThatCalled = getIntent();
        String listName = activityThatCalled.getStringExtra("listName");
        final ArrayList<String> s = db.showAllItemsFromGroceryTable();


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                s);
        lv.setAdapter(adapter);

        final ArrayList<String> s2 = new ArrayList<>();

        adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                s2);
        lv2.setAdapter(adapter2);

        db.populate();

        searchFunction(search,spin);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    adapter.clear();
                    ArrayList<String> items = getTypeList(position);
                    ArrayList<String> itemsToRemove = db.getItemsFromTable(Constants.PLACE_HOLDER_DB_NAME);
                    db.removeItemsFromList(items, itemsToRemove);
                    if(position != 0) {
                        items = db.sortByType(items);
                    }
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

        addToDB(lv);

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                db.deleteItemByNameAndTable(lv2.getItemAtPosition(position).toString(),Constants.PLACE_HOLDER_DB_NAME);
                adapter.insert(lv2.getItemAtPosition(position).toString(),0);
                adapter2.remove(lv2.getItemAtPosition(position).toString());
            }
        });

    }

    public void searchFunction(final SearchView search, final Spinner spin){
        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                search.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                int position = spin.getSelectedItemPosition();
                ArrayList<String> theList = getTypeList(position);
                ArrayList<String> listToShow = db.searchBar(theList,newText);
                ArrayList<String> itemsToRemove = db.getItemsFromTable(Constants.PLACE_HOLDER_DB_NAME);
                db.removeItemsFromList(listToShow,itemsToRemove);
                if(position != 0) {
                    listToShow = db.sortByType(listToShow);
                }


                if(listToShow.size() <= 0){
                    listToShow.add("Click here to add item to DB");
                }
                adapter.clear();

                adapter.addAll(listToShow);
                return false;
            }

        };
        search.setOnQueryTextListener(queryTextListener);
    }

    public void addToDB(final ListView lv){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = lv.getItemAtPosition(position).toString();
                if(!s.equals("Click here to add item to DB")) {
                    db.addItemToTableByName(lv.getItemAtPosition(position).toString(), Constants.PLACE_HOLDER_DB_NAME);
                    // Need to use spinner to set and adjust values
                    adapter2.insert(lv.getItemAtPosition(position).toString(), 0);
                    adapter.remove(lv.getItemAtPosition(position).toString());
                }else{
                    final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CreateListActivity.this);
                    final View promptView =  (LayoutInflater.from(CreateListActivity.this)).inflate(R.layout.add_item_to_db,null);
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
                                // Toast item created, also include itemname and type name
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), itemName + " is created under the type " + typeName1,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

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





    // Not using this yet
    public void createList(View v){
        final EditText enterListName = (EditText) findViewById(R.id.enterListName);
        Intent activityThatCalled = getIntent();
        String ans = enterListName.getText().toString();

        if(ans.equals("")) {
            enterListName.setError("Please Enter a List Name");
        }else if(ans.equals("\'")){
            enterListName.setError("Cannot start with \"\'\" (Apostrophe)");
        }
        else if(!db.isValidListName(ans)){
            enterListName.setError("List name can only contain letters, numbers, and apostrophe. \n" +
                    "And list cannot start with numbers");

        } else if(db.checkDuplicateName(ans)){
            enterListName.setError("List name already used");
        }else{
            activityThatCalled.putExtra("listname", ans);
            db.renameTable(Constants.PLACE_HOLDER_DB_NAME, ans);
            setResult(RESULT_OK, activityThatCalled);
            finish();
        }

    }

    public void goBackToHome(View v){
        Intent getIntent = new Intent(CreateListActivity.this, HomePageActivity.class);
        db.deleteTableByName(Constants.PLACE_HOLDER_DB_NAME);
        startActivity(getIntent);
        finish();
    }


}
