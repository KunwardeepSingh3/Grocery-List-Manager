### *GroceryListManager* Design Info
###### Yajie Zhang
1. The application must allow users to add items to a list, delete items from a list, and change the quantity of items in the list. I have methods: 
	* `addItem(String itemName, int quantities)` — allow user add items to a list
	* `deleteItem( )` — allow user to delete items from a list
	* `changeQuantities( ) `— allow user to change quantity of items in the list
	
2.  The application must contain a database (DB) of items and corresponding item types. To do that, I have multiple types of items aggregate to be the database.

3. Users must be able to add items to a list by picking them from a hierarchical list, where the first level is the item type, and the second level is the name of the actual item. After adding an item, users must be able to specify a quantity for that item. In order to satisfy the hierarchy requirement, I have the database consisted from multiple types of items, and each type is consisted from multiple items of the same type. And I have methods: 
	* `setQuantities()` — allow user to specify item quantity

4. Users must also be able to specify an item by typing its name. If a match cannot be found, the application must ask the user to select a type for the item and then save the new item, together with its type, in its DB. I did methods under GroceryDB class:
	* `findItemByName(String name)` — allow user to find an item by its name
	* `addItemDB(String type, String name)`— allow user to add new item with its type and name to database

5. Lists must be saved automatically and immediately after they are modified. I made `autoSave()` method to do that.

6.  Users must be able to check off items in a list (without deleting them). I made `checkedOff()` method to satisfy it.

7.  Users must also be able to clear all the check-off marks in a list at once. `clearChecked()` method will do the work.
8.  Check-off marks for a list are persistent and must also be saved immediately. This function is included in `autoSave()` method.

9. The application must present the items in a list grouped by type, so as to allow users to shop for a specific type of products at once. `autoSortByType()` method will automatically sort items by their type, thus to satisfy this requirement.

10.  The application must support multiple lists at a time. Therefore, the application must provide the users with the ability to create, (re)name, select, and delete lists. Methods `createList(String list);  deleteList(); renameList(); selectList()` do that.

11. The User Interface (UI) must be intuitive and responsive. Not considered because it does not affect the design directly.