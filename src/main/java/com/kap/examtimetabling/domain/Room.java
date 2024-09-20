/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kap.examtimetabling.domain;

/**
 *
 * @author katiepambakian
 */
public class Room {
    
    // variables 
    private Integer id;
    private String name;
    private Integer capacity;
    private Integer numInvigulators;
    
    //default constructor
    public Room(){
        
    }
    
    //constructor with parameters 
    public Room(Integer id, String name, Integer capacity, Integer numInvigulators){
        this.id =id;
        this.name = name;
        this.capacity = capacity;
        this.numInvigulators = numInvigulators;
    }
    
    //---- getters ----
    
    //returns the name
    public String getName(){
        return name;
    }
    
    //returns the max number of people 
    //the room can fit
    public Integer getCapacity(){
        return capacity; 
    }
    
    //returns the number of invigulators 
    // required for the room
    public Integer getNumInvigulators(){
        return numInvigulators; 
    }
    
    public Integer getID(){
        return id;
    }

}
