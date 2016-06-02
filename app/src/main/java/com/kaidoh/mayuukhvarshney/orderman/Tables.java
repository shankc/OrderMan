package com.kaidoh.mayuukhvarshney.orderman;

/**
 * Created by mayuukhvarshney on 02/06/16.
 */
public class Tables {

    private  String Id;
    private String Name;
    private Boolean Status;
    private String Capacity;

    public void setId(String ID){
        this.Id=ID;
    }
    public void setName(String name){
        this.Name=name;
    }
    public void setStatus(Boolean stat){
        this.Status=stat;
    }
    public void setCapacity(String cap){
        this.Capacity= cap;
    }


    public String getId(){
        return this.Id;
    }
    public String getName(){
        return this.Name;
    }
    public Boolean getStatus(){
        return this.Status;
    }
    public String getCapacity(){
        return this.Capacity;
    }

}
