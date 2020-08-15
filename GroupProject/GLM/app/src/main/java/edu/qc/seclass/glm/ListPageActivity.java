package edu.qc.seclass.glm;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ListPageActivity  extends AppCompatActivity {

    public DatabaseHandler db = new DatabaseHandler(this);
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);
        final ListView lv = (ListView) findViewById(R.id.listPageListView);

        Intent activityThatCalled = getIntent();
        final String listName = activityThatCalled.getStringExtra("listName");
        final TextView listNameTextView = (TextView) findViewById(R.id.listName);
        final Spinner spin = (Spinner) findViewById(R.id.activityListSpinner);
        Button renameButton = (Button) findViewById(R.id.rename_list);
        listNameTextView.setText(listName);
        ArrayList<String> s = db.getItemsFromTable(db.changeStringForDB(listName));



        adapter = new ListPageAdapter(this,
                db.changeStringForDB(listName),
                s);
        Log.e("TAG", s.toString());
        lv.setAdapter(adapter);


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.clear();
                ArrayList<String> list = db.showAllItemsFromTableName(listName);

                switch (position){
                    case 0:
                        adapter.addAll(list);
                        break;
                    case 1:
                        list = db.returnAllOfSpecificType(list, Constants.FRUIT_TYPE_NAME);
                        adapter.addAll(list);
                        break;
                    case 2:
                        list = db.returnAllOfSpecificType(list, Constants.VEGETABLE_TYPE_NAME);
                        adapter.addAll(list);
                        break;
                    case 3:
                        list = db.returnAllOfSpecificType(list, Constants.MEAT_TYPE_NAME);
                        adapter.addAll(list);
                        break;
                    case 4:
                        list = db.returnAllOfSpecificType(list, Constants.DAIRY_TYPE_NAME);
                        adapter.addAll(list);
                        break;
                    case 5:
                        list = db.returnAllOfSpecificType(list, Constants.FROZEN_TYPE_NAME);
                        adapter.addAll(list);
                        break;
                    case 6:
                        list = db.returnAllOfSpecificType(list, Constants.BAKERY_TYPE_NAME);
                        adapter.addAll(list);
                        break;
                    case 7:
                        list = db.returnAllOfSpecificType(list, Constants.BEVERAGE_TYPE_NAME);
                        adapter.addAll(list);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
       renameTable(renameButton,listNameTextView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final TextView listNameTextView = (TextView) findViewById(R.id.listName);
        final ListView lv = (ListView) findViewById(R.id.listPageListView);
        final Spinner spin = (Spinner) findViewById(R.id.activityListSpinner);
        final String listName = listNameTextView.getText().toString();
        adapter.clear();
        ArrayList<String> list = db.getItemsFromTable(db.changeStringForDB(listName));
        adapter = new ListPageAdapter(ListPageActivity.this,listName,list);
        lv.setAdapter(adapter);

        sortListAccordingToSpinner(spin,listName);

    }

    public void sortListAccordingToSpinner(final Spinner spin, final String listName){

                adapter.clear();
                ArrayList<String> list = db.showAllItemsFromTableName(listName);
                int position = spin.getSelectedItemPosition();


                switch (position){
                    case 0:
                        adapter.addAll(list);
                        break;
                    case 1:
                        list = db.returnAllOfSpecificType(list, Constants.FRUIT_TYPE_NAME);
                        adapter.addAll(list);
                        break;
                    case 2:
                        list = db.returnAllOfSpecificType(list, Constants.VEGETABLE_TYPE_NAME);
                        adapter.addAll(list);
                        break;
                    case 3:
                        list = db.returnAllOfSpecificType(list, Constants.MEAT_TYPE_NAME);
                        adapter.addAll(list);
                        break;
                    case 4:
                        list = db.returnAllOfSpecificType(list, Constants.DAIRY_TYPE_NAME);
                        adapter.addAll(list);
                        break;
                    case 5:
                        list = db.returnAllOfSpecificType(list, Constants.FROZEN_TYPE_NAME);
                        adapter.addAll(list);
                        break;
                    case 6:
                        list = db.returnAllOfSpecificType(list, Constants.BAKERY_TYPE_NAME);
                        adapter.addAll(list);
                        break;
                    case 7:
                        list = db.returnAllOfSpecificType(list, Constants.BEVERAGE_TYPE_NAME);
                        adapter.addAll(list);
                        break;
                }


    }


    public void renameTable(final Button renameButton, final TextView listNameTextView){
        renameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View view = (LayoutInflater.from(ListPageActivity.this)).inflate(R.layout.for_rename_list_button,null);
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ListPageActivity.this);
                alertBuilder.setView(view);

                final Dialog dialog = alertBuilder.create();
                dialog.show();

                final EditText newNameForTable = (EditText) view.findViewById(R.id.enterNewNameForTable);
                String text = newNameForTable.getText().toString();
                Button rnBtn = (Button) view.findViewById(R.id.renameTable);
                Button backBtn = (Button) view.findViewById(R.id.doNotRenameTable);


                rnBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = newNameForTable.getText().toString();

                        if(text.equals("")) {
                            newNameForTable.setError("Please Enter a List Name");
                        }else if(text.equals("\'")){
                            newNameForTable.setError("Cannot start with \"\'\" (Apostrophe)");
                        }
                        else if(!db.isValidListName(text)){
                            newNameForTable.setError("List name can only contain letters, numbers, and apostrophe. \n" +
                                    "And list cannot start with numbers");

                        } else if(db.checkDuplicateName(text)){
                            newNameForTable.setError("List name already used");
                        }else{
                            db.renameTable(listNameTextView.getText().toString(), text);
                            listNameTextView.setText(text);
                            dialog.dismiss();
                            onResume();


                            Toast.makeText(getApplicationContext(), "Renamed Table",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });
    }

    public void deleteMyList(View v){
        TextView listNameTextView = (TextView) findViewById(R.id.listName);
        String toDelete = listNameTextView.getText().toString();
        db.deleteTableByName(toDelete);
        goBackToHome(v);
	Toast.makeText(getApplicationContext(), toDelete + " is deleted",Toast.LENGTH_SHORT).show();
    }

    public void goToAddItemsActivity(View v){
        Intent getIntent = new Intent(ListPageActivity.this, AddItemsActivity.class);
        TextView listNameTextView = (TextView) findViewById(R.id.listName);
        String listName = listNameTextView.getText().toString();
        getIntent.putExtra("listName", listName);
        startActivity(getIntent);


        finish();
    }

    public void goBackToHome(View v){
        Intent getIntent = new Intent(ListPageActivity.this, HomePageActivity.class);
        startActivity(getIntent);
        finish();
    }

    public void clearAllCheck(View v){
        TextView listNameTV = (TextView) findViewById(R.id.listName);
        String listName = listNameTV.getText().toString();
        ArrayList<String> s = db.getItemsFromTable(listName);
        adapter = new ListPageAdapter(this,
                listName,
                s);
        int count = adapter.getCount();
        if(count > 0) {
            db.setAllIsSelectedToFalse(db.changeStringForDB(listName));
        }
        adapter.notifyDataSetChanged();
        onResume();
        Toast.makeText(ListPageActivity.this, "Checks cleared",Toast.LENGTH_SHORT).show();
    }


}
