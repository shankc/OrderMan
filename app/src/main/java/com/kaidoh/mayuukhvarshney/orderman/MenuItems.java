package com.kaidoh.mayuukhvarshney.orderman;

/**
 * Created by mayuukhvarshney on 02/06/16.
 */
public class MenuItems {
    private String Id;
    private String CategoryId;
    private String ItemName;
    private String ItemDescription;
    private Float Price;
    private String Image;

    public void setId(String id){
        this.Id= id;
    }
    public void setCategoryId(String catID){
        this.CategoryId=catID;

    }
    public void setItemDescription(String desc){
        this.ItemDescription=desc;
    }
    public void setPrice(Float price){
        this.Price=price;
    }
    public void setImage(String img){
        this.Image= img;
    }
    public void setItemName(String itname){
        this.ItemName= itname;
    }
    public String getId(){
        return this.Id;
    }
    public String getCategoryId(){
        return this.CategoryId;
    }
    public String getItemDescription(){
        return this.ItemDescription;
    }
    public String getItemName(){
        return this.ItemName;
    }
    public String getImage(){
        return this.Image;

    }
    public Float getPrice(){
        return this.Price;
    }
}
