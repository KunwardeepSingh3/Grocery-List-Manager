# Individual Designs

## Design 1
![AmitCharran's Design](https://github.com/qc-se-spring2020/370Spring20Team7/blob/master/GroupProject/Design-Individual/AmitCharran/design.pdf)

### Pros  
+ Hierarchical structure of item types and item 

### Cons
- Do not need Grocery Store class
- no relation between user and list
- no relation between list and item type
- not enough function

## Design 2
![chevano's Design](https://github.com/qc-se-spring2020/370Spring20Team7/blob/master/GroupProject/Design-Individual/chevano/design.pdf)

### Pros
+ have most required classes
+ having a relationship set up between all classes

### Cons
- Store class is not needed
- Not strictly UML

## Design 3
![Jae-File's Design](https://github.com/qc-se-spring2020/370Spring20Team7/blob/master/GroupProject/Design-Individual/Jae-File/design.pdf)

### Pros  
+ has a function that takes care DB 
+ 1-to-M between itemType to Item

### Cons 
- ambigous functions   
- Used Customer instead of User
- having a class representing a database

## Design 4
![KunwardeepSingh3's design](https://github.com/qc-se-spring2020/370Spring20Team7/blob/master/GroupProject/Design-Individual/KunwardeepSingh3/design.pdf)

### Pros
+ usage of appropriate UML design tools
+ checkMark: boolean

### Cons 
- No hierarchical structure item and item type

## Design 5
![yhuang306's design](https://github.com/qc-se-spring2020/370Spring20Team7/blob/master/GroupProject/Design-Individual/yhuang306/design.pdf)

### Pros
+ relationship are clear

### Cons
- no user class
- hierarchicalList class

# Team Design
![Team Design](https://github.com/qc-se-spring2020/370Spring20Team7/blob/master/GroupProject/Design-Team/design-team.pdf)

### The commonalities between the team-individual designs and our group design:
* Idea of the classes were similar to the individual ones
* Idea of most functions were similar to group design functions

### The differences between the team-individual designs and our group design:
* We did not use relationships between any one classes, we had a combination of all of our ideas
* Attributes for final design differ between all individual designs
* Eliminates all unnecessary classes

#### Item extends ItemType because we want one ItemType to be associated with every item. There is an aggregate connection between Item and GroceryList because GroceryList is composed of Items. The User class is dependent on GroceryList class because users need to have a list for our application.

# Summary
* We learned to coordinate our timing
* We learned to agree more because if we agree more we will be able to work more and get more work done
* We improved our UML design skills
* We used each other’s strengths as our advantage
