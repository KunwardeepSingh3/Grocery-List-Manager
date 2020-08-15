# Test Plan

**Team 7**:

## 1 Testing Strategy

### 1.1 Overall strategy

#### Unit-testing strategies
We will be doing manual unit testing. We will test all the functions our selves, to make sure it matches what we want.

#### Integration-testing strategies
Our Q/A tester will go over the app, report in bugs.

#### System-testing strategies
Our Q/A tester will test our app with multiple devices.

#### Regression-testing strategies
When new methods are introduced we will manually test them and see if they reach our requirements.

### 1.2 Test Selection

#### We will choose the Black-box testing since it could be done without understanding the source code. The techniques we will be using are:

1. *Decision Table Testing* used to test system behavior for different input combinations. For example, if we want to verify app can be open or not, we will have condition of *app is install* and *app can be open*, the T & T condition will have the homepage be displayed as an action, while the other 3 condition will have homepage not displayed as an action. It could be used on almost all of the test cases.
2. *Boundary Value Analysis* used to find the errors at boundaries of input domain rather than finding those errors in the center of input. For example, when we change the quantity of items, since the min value for a quantity will be 0, so we enter a -1 and 0 to see if the program will accept the quantity as -1 or not. Same as when the quantity is 0. 

### 1.3 Adequacy Criterion

The quality of our test cases will be assess by using a combination of functional coverage to ensure that the behavior of our program meets the requirement specification, and structural coverage (code coverage) to measure the quality of testing/verification and completeness.

Our choice of code coverage will be decision coverage (branch coverage), which is a testing method that aims to ensure every possible branch from each decision point is executed at least once and thereby ensuring that all reachable code is executed.

We will also be using condition coverage to decision coverage but has better sensitivity to the control flow.

The testing techniques used will differ at each level. Unit testing will use white-box technique to test the internal structure of the application, while integration, system, and acceptance testing will use black-box technique to test the functionality of the application.

### 1.4 Bug Tracking
Inside the app there will be section to report bug.

### 1.5 Technology

#### We intend to build and use the following technologies for testing: 

We will do manual testing since it is a small simple app.


## 2 Test Cases

|Test Name|Purpose|Pre-condtion|Steps|Expected Result|Actual Result|P/F|
|-------|------|------|------|-----|-------|-----|
|TC01|verify app can be opened|app is installed|1.look for app icon|1.app icon does show on the screen|as expected|P
||||2. click the app icon |2. app opens and show the home screen of the app|as expected|P
|TC02|verify list creation|app is opened|1.click "+" on app home screen|1.switch to create list screen|as expected|P
||||2. type the name of the list|2. smooth typing|as expected|P
||||3. click "Create"|3. new list successfully created and appeared on the home screen|as expected|P
|TC03|verify rename list|exising lists, list opened|1. click "rename button" in the list |1. rename window pop up|as expected|P|
||||2. click "back" |2. back to the list without name change|as expected|p|
||||3. click "rename" again to pop up rename window, type the new name |3. smooth typing|as expected|P
||||4. click "rename" |4. the name of the list changed successfully|as expected|P
|TC04|verify list deletion|multiple lists already created|1.click a list to open that list|1. list opened|as expected|P
||||2. click "Delete"|2. the list disapeared from home screen|as expected|P
|TC05|verify select list & go back arrow|existing lists|1. click the name of a list|1. switch to list screen for the selected list|as expected|P
||||2. click back arrow icon|2. switch back to home screen|as expected|P
|TC06|verify add item to the list from DB item type|Opened list, knowing the item is in the DB|1. click "Edit Item" |1. switch to add item screen|as expected|P
||||2. choose item type from drop down|2. items under the chosen type showed underneath|as expected|P
||||3. select an item name|3. the item is pushed onto added item list|as expected|P
||||4. click "Done"|4. switch back to list screen, the item successfully added to the list|as expected|P
|TC07|verify add item to the list by search item by name|Opened list, knowing the item is in the DB|1. click "Edit Items"|1. switch to add item screen|as expected|P
||||2. click the maginify glass on the top|2. Search bar appeared, start typing bar blinks next to the magnifying glass|as expected|P
||||3. type the item name|3. show all similar item name under search bar|as expected|P
||||4. select the wanted item|4. item push onto added item list|as expected|P
||||5. click "Done"|6. switch back to list screen, the item is added to the list|as expected|P
|TC08|verify change item quantities|Opened list, and items are in the list|1. click the field has quantity number|1. blink line indicating able to type |as expected|P
||||2. type in quantity number |2. quantity changed|as expected|P
|TC09|verify item deletion|on list screen, list has items|1. click "edit items"|1. switch to addItem screen|as expected|P
||||2. click unwanted items on added item list|2. item disappeared from the added item list|as expected|P
||||3. click "Done"|3. switch back to list screen, item disappeared from the list|as expected|P
|TC10|verify check-off item|on list screen, list has items|1. click empty rectangle next to the item name|1. a check mark appeared in the clicked rectangle|as expected|P
||||2. click a rectangle has the check mark inside|2. the check mark disappeared from the rectangle|as expected|P
|TC11|verify clear all/check all button|on the list screen, some items already checked off|1. click the "Clear" button|1. all check marks for the items in the list disappeared|as expected|P
|TC12|verify cancel add item|on the list screen|1. click "Edit Items"|1. switch to add item screen|as expected|P
||||2. click "Done"|2. switch back to list screen, item did not add|as expected|P
|TC13|verify adding item to DB|on add item screen, the item name which is not in DB already typed|1. type the name of item is not in DB|1. show the msg "click here to add to DB"|as expected|P
||||2. click where is the msg showed|2. pop up addItemtoDB window|as expected|P
||||3. choose "Item type" from drop down|3. Item type shows the type|as expected|P
||||4. click "back"|4. switch back to additem screen without new item added to DB|as expected|P
||||5. repeat step 1–3, type item name |4. item added to DB corresponding type successfully|as expected|P
TC14|verify autosave|opened app, made bunch of changes|1. hard close the app|1. app is closed, not even in back refresh|as expected|P
||||2. reopen the app, check if all changes are still there|2. all changes are still there|as expected|P
TC15|verify UI responsive|has finished app coded in responsive fashion|1. install the app to different phone with different screen size|1. selected phone all installed the app without problem|as expected|P
||||2. open the app on different phone|2. app scales up and down nicely|as expected|P
|TC16|verify bug report|app opened|1. click the bug icon| bug report window poped up|as expected|P
||||2.type in subject and bug report|2. smooth typing|as expected|P
||||3.click "send"|3.report sent|as expected|P
||||4.repeat step 1-2, click "back"|4. report not sent| as expected|P
|TC17|verify sort by type in list|listscreen open, have bunch of items in it|1. choose a type from drop down|1.list only displayed all items from that type|as expected|P
