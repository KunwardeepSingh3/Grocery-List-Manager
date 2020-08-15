package edu.qc.seclass.glm;

public class Item extends ItemType{

    private String name;
    private String quantity;
    private int id;
    private String isSelected;


    public Item() {
        super("Fruit");
        name = "Apple";
        quantity = "0";
        id = 0;
        isSelected = "0";
    }

    public Item(String itemType, String name, String quantity, int id) {
        super(itemType);
        this.name = name;
        this.quantity = quantity;
        this.id = id;
        isSelected = "0";
    }

    public Item(String itemName, String itemType){
        super(itemType);
        this.name = itemName;
    }

    public Item(String itemType, String name, String quantity, String dateItemAdded, int id) {
        super(itemType);
        this.name = name;
        this.quantity = quantity;
        this.id = id;
        this.isSelected = "0";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected){
        this.isSelected = isSelected;
    }

    public String toString(){
        return "itemName = " + name + "\n" ;
    }


}
