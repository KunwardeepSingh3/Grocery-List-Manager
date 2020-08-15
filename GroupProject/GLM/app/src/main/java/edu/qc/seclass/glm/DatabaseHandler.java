package edu.qc.seclass.glm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context ctx;
    private ArrayList<Item> itemsList;
    private ArrayList<Item> fruitList;
    private ArrayList<Item> veggiesList;
    private ArrayList<Item> meatList;
    private ArrayList<Item> dairyList;
    private ArrayList<Item> frozenList;
    private ArrayList<Item> bakeryList;
    private ArrayList<Item> beveragesList;


    public DatabaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
        itemsList = new ArrayList<>();
        fruitList = new ArrayList<>();
        veggiesList = new ArrayList<>();
        meatList = new ArrayList<>();
        dairyList = new ArrayList<>();
        frozenList = new ArrayList<>();
        bakeryList = new ArrayList<>();
        beveragesList = new ArrayList<>();


    }

    public ArrayList<String> turnItemListToStringList(ArrayList<Item> list){
        ArrayList<String> ans = new ArrayList<>();

        for(Item x: list){
            ans.add(x.getName());
        }
        return ans;
    }

    public ArrayList<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(ArrayList<Item> itemsList) {
        this.itemsList = itemsList;
    }

    public ArrayList<Item> getFruitList() {
        return fruitList;
    }

    public void setFruitList(ArrayList<Item> fruitList) {
        this.fruitList = fruitList;
    }

    public ArrayList<Item> getVeggiesList() {
        return veggiesList;
    }

    public void setVeggiesList(ArrayList<Item> veggiesList) {
        this.veggiesList = veggiesList;
    }

    public ArrayList<Item> getMeatList() {
        return meatList;
    }

    public void setMeatList(ArrayList<Item> meatList) {
        this.meatList = meatList;
    }

    public ArrayList<Item> getDairyList() {
        return dairyList;
    }

    public void setDairyList(ArrayList<Item> dairyList) {
        this.dairyList = dairyList;
    }

    public ArrayList<Item> getFrozenList() {
        return frozenList;
    }

    public void setFrozenList(ArrayList<Item> frozenList) {
        this.frozenList = frozenList;
    }

    public ArrayList<Item> getBakeryList() {
        return bakeryList;
    }

    public void setBakeryList(ArrayList<Item> bakeryList) {
        this.bakeryList = bakeryList;
    }

    public ArrayList<Item> getBeveragesList() {
        return beveragesList;
    }

    public void setBeveragesList(ArrayList<Item> beveragesList) {
        this.beveragesList = beveragesList;
    }

    public void createTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String CREATE_USER_TABLE = "CREATE TABLE " + tableName + "("
                + Constants.KEY_ID_GL
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.KEY_ITEM_ID_KEY + " INTEGER, "
                + Constants.KEY_ITEM_NAME + " TEXT,"
                + Constants.KEY_ITEM_TYPE + " TEXT,"
                + Constants.KEY_QTY_NUMBER + " TEXT, "
                + Constants.KEY_isSelected + " TEXT, "
                + "FOREIGN KEY(" + Constants.KEY_ITEM_ID_KEY + ") REFERENCES "
                + Constants.TABLE_NAME
                + "(" + Constants.KEY_ID + "));";

        db.execSQL(CREATE_USER_TABLE);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_GROCERY_TABLE2 = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_ITEM + " TEXT,"
                + Constants.KEY_TYPE_NAME + " TEXT);";

        db.execSQL(CREATE_GROCERY_TABLE2);
        populate();
    }

    public void createItemsTBL() {
        SQLiteDatabase db = this.getWritableDatabase();

        String CREATE_GROCERY_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_ITEM + " TEXT,"
                + Constants.KEY_TYPE_NAME + " TEXT);";

        db.execSQL(CREATE_GROCERY_TABLE);
        populate();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    public void deleteTableByName(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + changeStringForDB(tableName));
        Log.d("Table deleted", changeStringForDB(tableName));

    }


    public List<String> showAllColumns(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db = getReadableDatabase();
        Cursor dbCursor = db.query(tableName, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        List<String> ans = Arrays.asList(columnNames);
        return ans;

    }

    //Delete Item
    public void deleteItemByID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ?",
                new String[]{String.valueOf(id)});

        db.close();
    }



    // This might not work because it will have problem with ID ***
    public void deleteAllItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        int x = getGroceriesCount();
        for (int i = 1; i <= x; i++) {
            db.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ?",
                    new String[]{String.valueOf(i)});
        }
        db.close();
    }

    public void renameTable(String originalTableName, String newTableName) {
        // Check if original is in the list
        if (containsTable(changeStringForDB(originalTableName))) {
            SQLiteDatabase db = this.getWritableDatabase();

            String changeTableName = "ALTER TABLE " + changeStringForDB(originalTableName) + " RENAME TO " + changeStringForDB(newTableName);
            db.execSQL(changeTableName);

            db.close();
        } else {
            // Eventually make this a toast
            Log.e("M", "Table Does Not exist");
        }

    }


    public void populate() {

        String[] fruits = new String[]{
                "Apple", "Banana", "Blackberry", "Blueberry", "Cherry",
                "Grapefruit", "Grape", "Melon", "Kiwifruit", "Lemon",
                "Lychee", "Mandarin", "Mango", "Orange", "Watermelon",
                "Peach", "Pear", "Dragonfruit", "Pineapple", "Strawberry"
        };

        String[] vegetables = new String[]{
                "Beans", "Broccoli", "Cabbage", "Carrot",
                "Cauliflower", "Celery", "Corn", "Cucumber", "Eggplant",
                "Kale", "Lettuce", "Mushrooms", "Okra", "Onion",
                "Peas", "Pepper", "Potato", "Pumpkin", "Radishes"
        };

        String[] meat = new String[]{
                "Bacon", "Chicken Wings", "Chicken Legs",
                "Chicken Breast", "Chicken Thighs", "Ground Beef",
                "Ham", "Hot Dogs", "Lamb", "Pork", "Steak",
                "Turkey Legs", "Turkey Thighs", "Turkey Breast"
        };

        String[] dairy = new String[]{
                "Butter", "Cheese", "Cream",
                "Eggs", "Milk", "Yogurt"
        };

        String[] frozenGoods = new String[]{
                "Ice Cream", "Pie Shells",
                "Pizza", "Veggie Burger",
                "Waffles"
        };

        String[] bakeryGoods = new String[]{
                "Bagel", "Bread", "Cake",
                "Cookies", "Donuts", "Muffins",
                "Rolls", "Tortillas"
        };

        String[] beverages = new String[]{
                "Beer", "Coffee",
                "Juice", "Lemonade", "Soda",
                "Tea", "Water"
        };

        for (int i = 0; i < fruits.length; i++) {
            fruitList.add(new Item(Constants.FRUIT_TYPE_NAME, fruits[i], "0", 0));
        }

        for (int i = 0; i < vegetables.length; i++) {
            veggiesList.add(new Item(Constants.VEGETABLE_TYPE_NAME, vegetables[i], "0", 0));
        }

        for (int i = 0; i < meat.length; i++) {
            meatList.add(new Item(Constants.MEAT_TYPE_NAME, meat[i], "0", 0));
        }
        for (int i = 0; i < dairy.length; i++) {
            dairyList.add(new Item(Constants.DAIRY_TYPE_NAME, dairy[i], "0", 0));
        }
        for (int i = 0; i < frozenGoods.length; i++) {
            frozenList.add(new Item(Constants.FROZEN_TYPE_NAME, frozenGoods[i], "0", 0));
        }
        for (int i = 0; i < bakeryGoods.length; i++) {
            bakeryList.add(new Item(Constants.BAKERY_TYPE_NAME, bakeryGoods[i], "0", 0));
        }
        for (int i = 0; i < beverages.length; i++) {
            beveragesList.add(new Item(Constants.BEVERAGE_TYPE_NAME, beverages[i], "0", 0));
        }

        itemsList.addAll(fruitList);
        itemsList.addAll(veggiesList);
        itemsList.addAll(meatList);
        itemsList.addAll(dairyList);
        itemsList.addAll(frozenList);
        itemsList.addAll(bakeryList);
        itemsList.addAll(beveragesList);

        for(Item x:itemsList){
            addItem(x);
        }

    }

    public void deleteAllTables() {
        ArrayList<String> allNames = showAllTables();
        SQLiteDatabase db = this.getWritableDatabase();
        for (String x : allNames) {
            x = changeStringForDB(x);
            if (!x.equals("android_metadata")) {
                if (!x.equals("sqlite_sequence"))
                    db.execSQL("DROP TABLE IF EXISTS " + x);
            }
        }

        db.close();

    }

    public boolean containsTable(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                if (s.equals(c.getString(c.getColumnIndex("name")))) {
                    db.close();
                    return true;
                }
                c.moveToNext();
            }
        }

        db.close();
        return false;
    }

    public ArrayList<String> searchBar(ArrayList<String> groList, String word) {
        ArrayList<String> newList = new ArrayList<String>();

        for (String curVal : groList) {
            if ( curVal.toUpperCase().contains(word.toUpperCase()) )
                newList.add(curVal);
        }

        return newList;
    }



    public ArrayList<String> sortByType(ArrayList<String> groceryList) {

        ArrayList<String> ans = new ArrayList<>();

        ArrayList<String> bakery = new ArrayList<>();
        ArrayList<String> beverage = new ArrayList<>();
        ArrayList<String> dairy = new ArrayList<>();
        ArrayList<String> frozen = new ArrayList<>();
        ArrayList<String> fruit = new ArrayList<>();
        ArrayList<String> meat = new ArrayList<>();
        ArrayList<String> veggie = new ArrayList<>();


        for (int i = 0; i < groceryList.size(); i++) {
            String temp = getType(groceryList.get(i));
            if (temp.equals(Constants.BAKERY_TYPE_NAME)) {
                bakery.add(groceryList.get(i));
            } else if (temp.equals(Constants.BAKERY_TYPE_NAME)) {
                beverage.add(groceryList.get(i));
            } else if (temp.equals(Constants.DAIRY_TYPE_NAME)) {
                dairy.add(groceryList.get(i));
            } else if (temp.equals(Constants.FROZEN_TYPE_NAME)) {
                frozen.add(groceryList.get(i));
            } else if (temp.equals(Constants.FRUIT_TYPE_NAME)) {
                fruit.add(groceryList.get(i));
            } else if (temp.equals(Constants.MEAT_TYPE_NAME)) {
                meat.add(groceryList.get(i));
            } else if (temp.equals(Constants.VEGETABLE_TYPE_NAME)) {
                veggie.add(groceryList.get(i));
            }
        }

        // Maybe add an additional sorting function for the indivdual array list ***
        Collections.sort(fruit);
        ans.addAll(fruit);
        Collections.sort(meat);
        ans.addAll(meat);
        Collections.sort(veggie);
        ans.addAll(veggie);
        Collections.sort(bakery);
        ans.addAll(bakery);
        Collections.sort(beverage);
        ans.addAll(beverage);
        Collections.sort(dairy);
        ans.addAll(dairy);
        Collections.sort(frozen);
        ans.addAll(frozen);

        return ans;
    }

    public void deleteItemByNameAndTable(String itemName, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(changeStringForDB(tableName), Constants.KEY_ITEM_NAME + " = ?",
                new String[]{String.valueOf(changeStringForDB(itemName))});
        db.close();
    }

    public String changeStringForOutput(String str){

        String newStr = "";

        if(str.contains("Apostrophe")){
           str = replaceApostropheWithSymbol(str);
        }

        for(int i = 0; i < str.length(); i++){
            if( str.charAt(i) == ('_') ) {
                newStr += ' ';
            }
            else
                newStr += str.charAt(i);
        }


        return newStr;

    }

    private String replaceApostropheWithSymbol(String str) {

        if(str.length() <= 10){
            return str;
        }

        int beginIndex = 0;
        int endIndex = -1;
        String word = "Apostrophe";
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == 'A'){
                if(str.length() >= i + 10 && restOfWordMatches(str, i)){
                    beginIndex = i;
                    endIndex = i + 9;
                }
            }
        }

        str = removeTheWord(str,beginIndex,endIndex);

        return str;

    }

    private String removeTheWord(String str, int beginIndex, int endIndex) {
        if(endIndex == -1){
            return str;
        }
        StringBuilder ans = new StringBuilder("");

        for(int i = 0; i < str.length(); i++){
            if(i == beginIndex){
                ans.append("\'");
            }else if(i > beginIndex && i <= endIndex){
                //do nothing
            }else{
                ans.append(str.charAt(i));
            }
        }
        return ans.toString();
    }

    private boolean restOfWordMatches(String str, int i) {
        if(str.charAt(i) == 'A'
                && str.charAt(i + 1) == 'p'
                && str.charAt(i + 2) == 'o'
                && str.charAt(i + 3) == 's'
                && str.charAt(i + 4) == 't'
                && str.charAt(i + 5) == 'r'
                && str.charAt(i + 6) == 'o'
                && str.charAt(i + 7) == 'p'
                && str.charAt(i + 8) == 'h'
                && str.charAt(i + 9) == 'e'){
            return true;
        }
        return false;
    }

    public String changeStringForDB(String str){

        String newStr = "";

        for(int i = 0; i < str.length(); i++){
            if( str.charAt(i) == (' ') ) {
                newStr += '_';
            }
            else if( str.charAt(i) == ('\'') ){
                newStr += "Apostrophe";
            }
            else
                newStr += str.charAt(i);
        }

        return newStr;
    }

    public ArrayList<String> removeItemsFromList(ArrayList<String> theList, ArrayList<String> itemsToRemoveFromTheList){

        for(String str : itemsToRemoveFromTheList){
            if(theList.contains(str) )
                theList.remove(str);
        }

        return theList;
    }

    public static boolean isValidListName(String str) {
        //if string is valid
        if (!Character.isLetter(str.charAt(0))) {
            return false;
        }
        // Traverse the string for the rest of the characters
        for (int i = 1; i < str.length(); i++) {

            if (!((str.charAt(i) >= 'a' && str.charAt(i) <= 'z')
                    || (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')
                    || (str.charAt(i) >= '0' && str.charAt(i) <= '9')
                    || str.charAt(i) == '\''
                    || str.charAt(i) == ' ')) {
                return false;
            }
        }
        // String is a valid identifier
        return true;
    }
    public boolean checkDuplicateName(String name){
        ArrayList<String> allNames = showAllTables();
        if(allNames.contains(name)){
            return true;
        }
        return false;
    }














    //// Adding items to table or GroceryTBL ****
    public void addItemToTableByName(String itemName, String tableName){
        Item item = getItemFromDBByName(itemName);
        addItemToTable(item,changeStringForDB(tableName));
        Log.e("Added to DB", item.toString());

    }
    public void addItemToTable(Item item, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean duplicateName = false;

        Cursor cursor = db.query(changeStringForDB(tableName), new String[] {
                        Constants.KEY_ID_GL, Constants.KEY_ITEM_NAME,
                        Constants.KEY_ITEM_TYPE, Constants.KEY_QTY_NUMBER,
                        Constants.KEY_isSelected}
                , null, null, null, null, Constants.KEY_ID_GL + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Item item1 = new Item();
                String item1Name = cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_NAME));
                item1Name = changeStringForOutput(item1Name);
                item1.setName(item1Name);
                if (item.getName().equals(item1.getName())) {
                    duplicateName = true;
                }
            } while (cursor.moveToNext());
        }
        if (!duplicateName) {
            ContentValues values = new ContentValues();
            values.put(Constants.KEY_ITEM_NAME, changeStringForDB(item.getName()));
            values.put(Constants.KEY_ITEM_TYPE, changeStringForDB(item.getItemType()));
            values.put(Constants.KEY_QTY_NUMBER, changeStringForDB(item.getQuantity()));
            values.put(Constants.KEY_isSelected, changeStringForDB(item.getIsSelected()));


            //Insert the row
            db.insert(tableName, null, values);

            Log.d("Saved!!", item.getName() + " Saved to DB");
        } else {
            Log.e("Duplicate Name", "Did not save to DB");
        }
    }

    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean duplicateName = false;

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
                Constants.KEY_ID, Constants.KEY_ITEM,
                Constants.KEY_TYPE_NAME}, null, null, null, null, Constants.KEY_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Item item1 = new Item();
                String item1Name = cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM));
                item1Name = changeStringForOutput(item1Name);
                item1.setName(item1Name);
                if (item.getName().equals(item1.getName())) {
                    duplicateName = true;
                }
            } while (cursor.moveToNext());
        }
        if (!duplicateName) {
            ContentValues values = new ContentValues();


            values.put(Constants.KEY_ITEM, changeStringForDB(item.getName()));
            values.put(Constants.KEY_TYPE_NAME, changeStringForDB(item.getItemType()));

            //Insert the row
            db.insert(Constants.TABLE_NAME, null, values);

            Log.d("Saved!!", item.getName() + " to DB");
        } else {

            Log.e("Duplicate Name", item.getName() + "  did not save to DB");
        }

    }

    public boolean containsItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean duplicateName = false;

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
                Constants.KEY_ID, Constants.KEY_ITEM,
                Constants.KEY_TYPE_NAME}, null, null, null, null, Constants.KEY_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Item item1 = new Item();
                String item1Name = cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM));
                item1Name = changeStringForOutput(item1Name);
                item1.setName(item1Name);
                if (item.getName().equals(item1.getName())) {
                    duplicateName = true;
                }
            } while (cursor.moveToNext());
        }

        return duplicateName;

    }


    public void createItemToAddToDB(String itemName, String itemType) {
        Item item = new Item(itemName, itemType);
        // Also add it to the appropriate list (fruits, beverage, bakery...)

        if (itemType.equals(Constants.FRUIT_TYPE_NAME))
            fruitList.add(item);
        else if (itemType.equals(Constants.VEGETABLE_TYPE_NAME))
            veggiesList.add(item);
        else if (itemType.equals(Constants.MEAT_TYPE_NAME))
            meatList.add(item);
        else if (itemType.equals(Constants.DAIRY_TYPE_NAME))
            dairyList.add(item);
        else if (itemType.equals(Constants.FROZEN_TYPE_NAME))
            frozenList.add(item);
        else if (itemType.equals(Constants.BAKERY_TYPE_NAME))
            bakeryList.add(item);
        else if (itemType.equals(Constants.BEVERAGE_TYPE_NAME))
            beveragesList.add(item);

        itemsList.add(item);

        addItem(item);
    }

    //Updated Item
    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_ITEM, changeStringForDB(item.getName()));
        values.put(Constants.KEY_TYPE_NAME, changeStringForDB(item.getItemType()));


        //update row
        return db.update(Constants.TABLE_NAME, values, Constants.KEY_ID + "=?", new String[]{String.valueOf(item.getId())});
    }


















    // Get/Show functions items from table *****

    public Item getItemFromDBByName(String itemName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[] {Constants.KEY_ID, Constants.KEY_ITEM, Constants.KEY_TYPE_NAME},
                Constants.KEY_ITEM + "=?",
                new String[]{itemName}, null, null, null, null);

        Item item = new Item("", "", "1", 0);
        if (cursor != null) {
            cursor.moveToFirst();
            item.setName(changeStringForOutput(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM))));
            item.setItemType(changeStringForOutput(cursor.getString(cursor.getColumnIndex(Constants.KEY_TYPE_NAME))));
        }


        db.close();
        return item;
    }
    public ArrayList<String> getItemsFromTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> arrItemNames = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT * FROM " + changeStringForDB(tableName), null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                arrItemNames.add(changeStringForOutput(c.getString(c.getColumnIndex(Constants.KEY_ITEM_NAME))));
                c.moveToNext();
            }
        }
        db.close();

        sortByType(arrItemNames);

        return arrItemNames;
    }

    //Get a Item
    public Item getItemByID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
                        Constants.KEY_ID, Constants.KEY_ITEM, Constants.KEY_TYPE_NAME},
                Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();


        Item item = new Item();
        item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
        item.setName(changeStringForOutput(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM))));
        item.setItemType(changeStringForOutput(cursor.getString(cursor.getColumnIndex(Constants.KEY_TYPE_NAME))));


        return item;
    }

    // This does not work
    public Item getItemFromTableByID(int id, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(tableName, new String[] {
                        Constants.KEY_ID_GL, Constants.KEY_ITEM_ID_KEY,
                        Constants.KEY_ITEM_NAME, Constants.KEY_ITEM_TYPE,
                        Constants.KEY_QTY_NUMBER, Constants.KEY_isSelected
                        },
                Constants.KEY_ID_GL + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();


        Item item = new Item();
        item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID_GL))));
        item.setName(changeStringForOutput(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM))));
        item.setItemType(changeStringForOutput(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_TYPE))));
        item.setQuantity(changeStringForOutput(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER))));
        item.setIsSelected(changeStringForOutput(cursor.getString(cursor.getColumnIndex(Constants.KEY_isSelected))));

        return item;
    }

    public Item getItemFromTableByName(String itemName, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(tableName,
                new String[] {
                        Constants.KEY_ID_GL, Constants.KEY_ITEM_ID_KEY,
                        Constants.KEY_ITEM_NAME, Constants.KEY_ITEM_TYPE,
                        Constants.KEY_QTY_NUMBER, Constants.KEY_isSelected
                },
                Constants.KEY_ITEM_NAME + "=?",
                new String[]{itemName}, null, null, null, null);

        Item item = new Item("test", "test", "1", 0);
        if (cursor != null) {
            cursor.moveToFirst();
            item.setName(changeStringForOutput(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_NAME))));
            item.setItemType(changeStringForOutput(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_TYPE))));
        }


        db.close();
        return item;
    }

    //Get all Groceries
    public List<Item> getAllGroceries() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Item> groceryList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
                Constants.KEY_ID, Constants.KEY_ITEM,
                Constants.KEY_TYPE_NAME}, null, null, null, null, Constants.KEY_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                item.setName(changeStringForOutput(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM))));
                item.setItemType(changeStringForOutput(cursor.getString(cursor.getColumnIndex(Constants.KEY_TYPE_NAME))));

                // Add to the groceryList
                groceryList.add(item);

            } while (cursor.moveToNext());
        }
        return groceryList;
    }




    //Get count
    public int getGroceriesCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }
    public ArrayList<String> showAllTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> arrTblNames = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                arrTblNames.add(changeStringForOutput(c.getString(c.getColumnIndex("name"))));
                c.moveToNext();
            }
        }
        db.close();
        return arrTblNames;
    }

    public ArrayList<String> showAllItemsFromGroceryTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> arrItemNames = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME, null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                arrItemNames.add(changeStringForOutput(c.getString(c.getColumnIndex(Constants.KEY_ITEM))));
                c.moveToNext();
            }
        }
        db.close();
        return arrItemNames;
    }

    public ArrayList<String> showAllItemsFromTableName(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> arrItemNames = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT * FROM " + changeStringForDB(tableName), null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String nameToAdd = (c.getString(c.getColumnIndex(Constants.KEY_ITEM_NAME)));
                arrItemNames.add(changeStringForOutput(nameToAdd));
                c.moveToNext();
            }
        }
        db.close();
        return arrItemNames;
    }

    public String getQuantity(String tableName, String itemName){

        if(!containsTable(changeStringForDB(tableName))){
            return "00";
        }
        tableName = changeStringForDB(tableName);
        String ans = "";

        SQLiteDatabase db = this.getReadableDatabase();

        String quantityQuery = "SELECT " + Constants.KEY_QTY_NUMBER+ " FROM " + tableName +
                " WHERE " + Constants.KEY_ITEM_NAME  + " = '" + itemName + "';";

        Cursor c = db.rawQuery(quantityQuery, null);

        if( c != null){
            c.moveToFirst();
        }

        ans = c.getString(c.getColumnIndex(Constants.KEY_QTY_NUMBER));

        db.close();
        return ans;
    }


    public void changeQuantity(String tableName, String itemName, String quantity){
        if(!containsTable(tableName)){
            return;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        tableName = changeStringForDB(tableName);


        String updateQuantity = "UPDATE " + tableName +
                " SET " + Constants.KEY_QTY_NUMBER + " = '" + quantity + "' "
                + " WHERE " + Constants.KEY_ITEM_NAME + " = '" + itemName + "';";

        db.execSQL(updateQuantity);

        db.close();
    }

    public void changeToTrue(String tableName, String itemName){
        tableName = changeStringForDB(tableName);
        if (!containsTable(tableName)) {
            return;
        }

        SQLiteDatabase db = this.getReadableDatabase();

        String updateIsSelected = "UPDATE " + tableName +
                " SET " + Constants.KEY_isSelected + " = '1'"
                + " WHERE " + Constants.KEY_ITEM_NAME + " = '" + itemName + "';";

        db.execSQL(updateIsSelected);
        db.close();
    }
    public void changeToFalse(String tableName, String itemName){
        SQLiteDatabase db = this.getReadableDatabase();
        String updateIsSelected = "UPDATE " + tableName +
                " SET " + Constants.KEY_isSelected + " = '0'"
                + " WHERE " + Constants.KEY_ITEM_NAME + " = '" + itemName + "';";

        db.execSQL(updateIsSelected);
        db.close();
    }

    public boolean isSelected(String tableName, String itemName){
        tableName = changeStringForDB(tableName);
        if(!containsTable(tableName)){
            return false;
        }
        String ans = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String quantityQuery = "SELECT " + Constants.KEY_isSelected+ " FROM " + tableName +
                " WHERE " + Constants.KEY_ITEM_NAME  + " = '" + itemName + "';";
        Cursor c = db.rawQuery(quantityQuery, null);
        if( c != null){
            c.moveToFirst();
        }
        ans = c.getString(c.getColumnIndex(Constants.KEY_isSelected));
        db.close();
        return ans.equals("1");


    }

    public void setAllIsSelectedToFalse(String tableName){
        SQLiteDatabase db = this.getReadableDatabase();

        String updateIsSelected = "UPDATE " + tableName +
                " SET " + Constants.KEY_isSelected + " = '" + "0" + "' " + ";";

        db.execSQL(updateIsSelected);

        db.close();
    }

    public ArrayList<String> showIsSelectedsFromTableName(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> arrItemNames = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT * FROM " + tableName, null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String nameToAdd = (c.getString(c.getColumnIndex(Constants.KEY_isSelected)));
                arrItemNames.add(changeStringForOutput(nameToAdd));
                c.moveToNext();
            }
        }
        db.close();
        return arrItemNames;
    }

    public String getType(String itemName){
        String typeName = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME, null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                if( itemName.equalsIgnoreCase((c.getString(c.getColumnIndex(Constants.KEY_ITEM)))) )
                    typeName = (c.getString(c.getColumnIndex(Constants.KEY_TYPE_NAME)));
                c.moveToNext();
            }
        }
        db.close();
        return typeName;
    }

    public ArrayList<String> getTypeList(int position){
        ArrayList<String> ans = new ArrayList<>();
        switch(position){
            case 0: {
                List<Item> allGroceries = getAllGroceries();

                for(Item x: allGroceries){
                    ans.add(x.getName());
                }

                break;
            }
            case 1:{
                List<Item> allGroceries = getAllGroceries();
                for(Item x: allGroceries){
                    if(x.getItemType().equals(Constants.FRUIT_TYPE_NAME)) {
                        ans.add(x.getName());
                    }
                }
                break;
            }
            case 2:{
                List<Item> allGroceries = getAllGroceries();
                for(Item x: allGroceries){
                    if(x.getItemType().equals(Constants.VEGETABLE_TYPE_NAME)) {
                        ans.add(x.getName());
                    }
                }
                break;
            }
            case 3:{
                List<Item> allGroceries = getAllGroceries();
                for(Item x: allGroceries){
                    if(x.getItemType().equals(Constants.MEAT_TYPE_NAME)) {
                        ans.add(x.getName());
                    }
                }
                break;
            }
            case 4:{
                List<Item> allGroceries = getAllGroceries();
                for(Item x: allGroceries){
                    if(x.getItemType().equals(Constants.DAIRY_TYPE_NAME)) {
                        ans.add(x.getName());
                    }
                }
                break;
            }
            case 5:{
                List<Item> allGroceries = getAllGroceries();
                for(Item x: allGroceries){
                    if(x.getItemType().equals(Constants.FROZEN_TYPE_NAME)) {
                        ans.add(x.getName());
                    }
                }
                break;
            }
            case 6:{
                List<Item> allGroceries = getAllGroceries();
                for(Item x: allGroceries){
                    if(x.getItemType().equals(Constants.BAKERY_TYPE_NAME)) {
                        ans.add(x.getName());
                    }
                }
                break;
            }
            case 7:{
                List<Item> allGroceries = getAllGroceries();
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

    public ArrayList<String> returnAllOfSpecificType(ArrayList<String> list, String itemType) {
        ArrayList<String> ans = new ArrayList<>();

        for(String x: list){
            String type = getType(x);
            if(type.equals(itemType)){
                ans.add(x);
            }
        }
        return ans;
    }
}

