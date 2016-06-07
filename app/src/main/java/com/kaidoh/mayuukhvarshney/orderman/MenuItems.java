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
    private Integer OrderId;
    private Integer Quantity;


    public void setId(String id){
        this.Id= id;
    }
    public void setQuantity(Integer quant){
        this.Quantity= quant;
    }
    public void setCategoryId(String catID){
        this.CategoryId=catID;

    }
    public void setOrderId(Integer order){
        this.OrderId= order;
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

    public Integer getOrderId(){
        return this.OrderId;
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
    public Integer getQuantity(){
        return this.Quantity;
    }
    public Float getPrice(){
        return this.Price;
    }
}
