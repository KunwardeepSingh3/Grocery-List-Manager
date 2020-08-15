Grocery Store

Here we have the UML diagram for a grocery store/ multiple grocery stores

1. There's a grocery store class which has a many-to-many relationship with ItemTypes and Users. So many items and be in many stores and many users can go into many stores

2. The store computes the price from with money/price class

3. In itemTypes there are many items per specific types. Item inherits itemTypes and expand up on those itemTypes. For example and itemType might be cereal and all cereals will come from that class as items

4. Users class uses the List class for all the users list. The User can check off items in the list using the parallel array
