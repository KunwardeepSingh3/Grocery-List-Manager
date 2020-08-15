package edu.qc.seclass.glm;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.nio.file.Watchable;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ListPageAdapter extends ArrayAdapter<String> {
    private String listName;
    private ArrayList<String> list;
    private Context cxt;
    DatabaseHandler db;


    public ListPageAdapter(@NonNull Context context, String listName, ArrayList<String> resource) {
        super(context,0, resource);
        cxt = context;
        list = resource;
        this.listName = listName;
        db = new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final String itemName = list.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_for_list_page_activity,parent,false);
        }

        final TextView itemNameTV = (TextView) convertView.findViewById(R.id.itemName);
        final EditText itemQty = (EditText) convertView.findViewById(R.id.qtyNum);
        final LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.linearLayout);
        final ImageView checkedImage = (ImageView)convertView.findViewById(R.id.checked1);
        final ImageView checkBox = (ImageView) convertView.findViewById(R.id.checkbox1);

        itemNameTV.setText(itemName);

        itemQty.setText(db.getQuantity(listName,itemName));

        // *** changeQuantity
        itemQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                db.changeQuantity(listName,itemName,itemQty.getText().toString());
            }
        });


        // *** checkItem
        if(db.isSelected(db.changeStringForDB(listName),itemName)){
            checkedImage.setVisibility(View.VISIBLE);
        }else{
            checkedImage.setVisibility(View.INVISIBLE);
        }

        itemNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.isSelected(listName,itemName)){
                    db.changeToFalse(listName,itemName);
                    if(!db.isSelected(listName,itemName)) {
                        checkedImage.setVisibility(View.INVISIBLE);
                        notifyDataSetChanged();
                    }

                }else{
                    db.changeToTrue(listName,itemName);
                    if(db.isSelected(listName,itemName)) {
                        checkedImage.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                    }
                }
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.isSelected(listName,itemName)){
                    db.changeToFalse(listName,itemName);
                    if(!db.isSelected(listName,itemName)) {
                        checkedImage.setVisibility(View.INVISIBLE);
                        notifyDataSetChanged();
                    }

                }else{
                    db.changeToTrue(listName,itemName);
                    if(db.isSelected(listName,itemName)) {
                        checkedImage.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                    }
                }
            }
        });

        return convertView;
    }

    public void uncheckCheckMarks(){
        ArrayList<String> items = db.getItemsFromTable(listName);
            int listViewCount = this.getCount();

        for(int i = 0; i < listViewCount; i++){
                View v = getView(i,null,null);
                ImageView checkedImage = (ImageView) v.findViewById(R.id.checked1);
                checkedImage.setVisibility(View.INVISIBLE);
        }
    }

    public void setListName(String s){
        listName = s;
    }



}
