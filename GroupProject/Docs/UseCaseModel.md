# Use Case Model

**Author**: Team7

## 1 Use Case Diagram
![Use Case Model](https://github.com/qc-se-spring2020/370Spring20Team7/blob/master/GroupProject/Docs/ReferencePictures/Copy%20of%20use%20case%20diagram.png)

## 2 Use Case Descriptions

### Use case: Create grocery list ###

- *Requirements: This should allow user to create a groceryList object.*

- *Pre-conditions: There should be a class called groceryList such that an object can be instantiated. We must have a working database to store the list.*

- *Post-conditions: An empty list with given name must be created and visible on the home screen.*

- *Scenarios: The User must click on &quot;Create new list&quot; button on the home screen of the application, resulting in the creation of a new list with the given name.*

### Use case: Save grocery list ###

- *Requirements: Should allow user to manually save the created list or does it automatically, immediately some changes are made to it.*

- *Pre-conditions: A list must be created, or some modifications has to be made in order to save it.*

- *Post-conditions: The new modifications must be visible in the list.*

- *Scenarios: user will select a grocery list and make some changes to it. Every time a modification is made, list will be automatically saved and updated in the GUI.*

### Use case: Rename Grocery List ###

- *Requirements: This should allow user to rename an existing list.*

- *Pre-conditions: There should be at least one list successfully created or selected to name/rename it.*

- *Post-conditions: The chosen name by the user must be valid and not used before.*

- *Scenarios: The user must select a list to be renamed and then click &quot;rename list&quot; button, which will open an input text box and allow user to enter the name. Once user enters the name, it displays the list with a new name and if the chosen name is invalid or already exists, it asks user to enter the name again.*

### Use case: Select Grocery List ###

- *Requirements: This should allow user to select the Grocery list.*

- *Pre-conditions: There should be at least one list that is already created.*

- *Post-conditions: User should be able to make changes to the selected list and the check box must be marked to show the selected list.*

- *Scenarios: User should click on one of the existing lists and it should mark it selected.*

### Use case: Delete grocery list ###

- *Requirements: The user must be able to completely delete the list, along with its contents.*

- *Pre-conditions: User must select at least one list to be deleted.*

- *Post-conditions: The user must not be able to see the deleted list on the screen anymore and it should be cleared from the memory as well.*

- *Scenarios: User will go the screen with all the lists and will select at least one list to be deleted. User will tap on the options menu and click &quot;delete list&quot; button. Deleted list is no longer available.*

### Use case: Add Item ###

- *Requirements: This should allow user to add an item to a list if it is already not there.*

- *Pre-conditions: The grocery list should be selected, and item should be available in the database.*

- *Post-conditions: The item list should be updated with added new item and valid quantity.*

- *Scenarios: Once user selects a list and taps &quot;add item&quot; button, user can either search the particular item or pick an item from the hierarchical list using dropdown menu. This will be accessed from the database.*

### Use case: Add Item to database ###

- *Requirements: This should allow user to add an item to the database if it is not already there.*

- *Pre-conditions: user must search the database first and in order to add it to the database, it must not be already present.*

- *Post-conditions: After adding an Item to the database, user must be able to search for that item or pick from the drop-down menu of the hierarchical list.*

- *Scenarios: User first searches for an item, if no results are shown and the item is not in the application database, it allows user to add that item into the database by clicking &quot;add new item&quot; button. It should ask user for Item type, item name and submit it. The item is now available to be added to the list.*

### Use case: Delete Item ###

- *Requirements: User must be able to delete the unwanted item from the list.*

- *Pre-conditions: user must select the item and the item needs to be deleted must be present in the list.*

- *Post-conditions: The list should be automatically saved immediately after deleting an item and the undesired deleted item is no longer available in the list.*

- *Scenarios: User will select the grocery list and will delete the undesired item by clicking &quot;delete item&quot; button.*

### Use case: Check items ###

- *Requirements: User will be able to check mark either all of the items or few items.*

- *Pre-conditions: There should be at least few items available in the list to check them off.*

- *Post-conditions: a check mark should be visible in front of all the items mark checked.*

- *Scenarios: User will select a list and will simply click on the items in the list to mark them checked and a check mark will be visible in front of them.*

### Use case: clear checked item ###
 
- *Requirements: This will allow user to uncheck the check marked items in the grocery list.*

- *Pre-conditions: Items must be check marked in the list in order to clear them off.*

- *Post-conditions: No check marks should be seen in front of the items.*

- *Scenarios: In the list of check marked items, user can simply uncheck them by selecting each item and then clicking &quot;uncheck&quot; button.*

### Use case: Change quantity ###

- *Requirements: The user should be able to change and update the quantity of an item in the grocery list.*

- *Pre-conditions: there should be at least one item in the list.*

- *Post-conditions: Item quantity should be updated and saved on the list.*

- *Scenarios: A user selects an item in the grocery list and can change the quantity of the item by simply clicking &quot;change quantity&quot; button to a valid positive whole number.*
