package com.kaidoh.mayuukhvarshney.orderman;

/**
 * Created by mayuukhvarshney on 03/06/16.
 */
public class Waiting {
    private Integer Id;
    private String Name;
    private Integer NoPersons;

    public void setId(int id){
        this.Id= id;
    }
    public void setName(String name){
        this.Name=name;

    }
    public void setNoPersons(int Persons){
        this.NoPersons=Persons;
    }

    public Integer getId(){
        return this.Id;
    }
    public String getName(){
        return this.Name;
    }
    public int getNoPersons(){
        return this.NoPersons;
    }
}
