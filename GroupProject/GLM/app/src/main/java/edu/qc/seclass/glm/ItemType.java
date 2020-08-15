package edu.qc.seclass.glm;

public class ItemType {

    private String typeName;

    public ItemType(){}

    public ItemType(String typeName){
        this.typeName = typeName;
    }

    public void setItemType(String typeName){
        this.typeName = typeName;
    }

    public String getItemType(){
        return typeName;
    }
}
