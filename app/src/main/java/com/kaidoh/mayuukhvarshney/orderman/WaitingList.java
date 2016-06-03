package com.kaidoh.mayuukhvarshney.orderman;

/**
 * Created by Ramesh on 6/2/2016.
 */
public class WaitingList {
    private Integer Id;
    private String Name;
    private Integer NoOfPersons;

    public void setId(Integer id){
        this.Id = id;
    }

    public void setName(String name){
        this.Name = name;
    }

    public void setNoOfPersons(Integer noOfPersons){
        this.NoOfPersons = noOfPersons;
    }

    public Integer getId(){
        return this.Id;
    }

    public String getName(){
        return this.Name;
    }

    public Integer getNoOfPersons(){
        return this.NoOfPersons;
    }

}
