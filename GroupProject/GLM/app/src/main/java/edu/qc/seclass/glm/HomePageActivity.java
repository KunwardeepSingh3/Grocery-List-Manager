package edu.qc.seclass.glm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ImageButton;


import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {

    public DatabaseHandler db = new DatabaseHandler(this);



    // List adapter
    ArrayAdapter<String> adapter;
    private static final int ADD_ITEM_SCREEN_REQUEST_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // db.deleteAllTables();
        Log.e("showTables", db.showAllTables().toString());
        if(!db.containsTable(Constants.TABLE_NAME)){
            db.createItemsTBL();
        }
        if(db.containsTable(Constants.PLACE_HOLDER_DB_NAME)){
            db.deleteTableByName(Constants.PLACE_HOLDER_DB_NAME);
        }
        //db.populate();

        showList();

//         Get reference of widgets from XML layout
        final Button addBtn = (Button) findViewById(R.id.AddButton);

        final ListView lv = (ListView) findViewById(R.id.activityMainListView);

        viewList(lv);

        ImageButton reportBugBtn = (ImageButton) findViewById(R.id.reportBug);
        reportBugBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = (LayoutInflater.from(HomePageActivity.this)).inflate(R.layout.bug_report_page, null);
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(HomePageActivity.this);
                alertBuilder.setView(view);

                final Dialog dialog = alertBuilder.create();
                dialog.show();
                Button backBtn = (Button) view.findViewById(R.id.bugReportBack);
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button reportBtn = (Button) view.findViewById(R.id.sendBugReport);

                reportBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Bug Reported",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        db.close();
    }

    public void viewList(final ListView lv){
        final ArrayList<String> listStorage = db.showAllTables();
        removeUnwantedFromList(listStorage);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listStorage);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent listPageIntent = new Intent(HomePageActivity.this, ListPageActivity.class);
                listPageIntent.putExtra("listName", lv.getItemAtPosition(position).toString());
                String l = lv.getItemAtPosition(position).toString();

                startActivity(listPageIntent);
            }
        });
    }

    public void onResume() {
        super.onResume();
        showList();

    }
    public void showList(){
        // Holds the list names for the listView in HomePageActivity
        ArrayList<String> listStorage = db.showAllTables();
        removeUnwantedFromList(listStorage);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listStorage);


        final ListView lv = (ListView) findViewById(R.id.activityMainListView);
        lv.setAdapter(adapter);


    }



    public void removeUnwantedFromList(ArrayList<String> listStorage){
        listStorage.remove("android metadata");
        listStorage.remove("sqlite sequence");
        listStorage.remove(Constants.TABLE_NAME);
    }

    // Not using this yet
    public void goToCreateListActivity(View v){
        Intent getIntent = new Intent(HomePageActivity.this, CreateListActivity.class);
        startActivityForResult(getIntent, ADD_ITEM_SCREEN_REQUEST_CODE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DatabaseHandler db = new DatabaseHandler(this);
        if (requestCode == ADD_ITEM_SCREEN_REQUEST_CODE) {
            if(resultCode == RESULT_OK)
            {
                String result = "default Value";
                result = data.getStringExtra("listname");
                Toast.makeText(getApplicationContext(), result + " is created",Toast.LENGTH_SHORT).show();  

                showList();


            }
            if (resultCode == RESULT_CANCELED) {
                // Make a toast that result was cancelled
            }
        }
    }//onActivityResultpublic void deleteMyList(){

}
