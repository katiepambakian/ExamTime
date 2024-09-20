/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kap.examtimetabling.domain;

import com.kap.examtimetabling.extras.generateDataFromDatabase;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
/**
 *
 * @author katiepambakian
 */
@PlanningEntity
public class Exam {
    // variables for the Exam class
    // unique identifier
    private Integer id;
    //the name of the exam
    private String name;
    //lenght of the exam
    private Integer length;
    
    //reference to the subject class - to get the name and students
    private Subject subjectClass;
    
    // references to other classes
    @PlanningVariable(valueRangeProviderRefs = "timeslotRange")
    private Timeslot timeslot;
    @PlanningVariable(valueRangeProviderRefs = "roomRange")
    private Room room;
    
    // constructors
    public Exam(){
        
    }
    public Exam(Integer id, String name, Subject subjectClass, Integer length){
        this.id = id;
        this.name  = name;
        this.subjectClass = subjectClass;
        this.length = length;
    }
    public Exam(Integer id, String name, Subject subjectClass){
        this.id = id;
        this.name  = name;
        this.subjectClass = subjectClass;
    }
    
    // getters
    public Integer getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getSubject(){
        return subjectClass.getSubject();
    }
    
    //gets the name of the subject and returns it as a string
    public String getStudentstoString(){
        return (subjectClass.getStudents()).toString();
    }
    //returns a list of the student ids as strings
    public List<String> getStudentsList(){
        List<String> temp = new ArrayList<>();
        
        for (int i = 0; i < subjectClass.getStudents().size();i++){
            temp.add((subjectClass.getStudents().get(i)).getStudentId());
        }
        return temp;
    }

   
    public Timeslot getTimeslot(){
        return timeslot;
    }
    public Room getRoom(){
        return room;
    }
    
    public String getDate(){
        return (timeslot.getDate()).toString();
    }
    public LocalDate getDateInDateForm(){
        return timeslot.getDate();
    }
    
    public Integer getLength(){
        return length;
    }
    public String getLengthToString(){
        return length.toString();
    }
    
    public Integer getTimeslotLength(){
        return timeslot.getLength();
    }
    
    public boolean timePeriodLongEnough(){
        //check if the time period is long enough
        if (getLength()>getTimeslotLength()){
            //the timeslot is too small
            return false;
        }else{
            //the time period is long enough
            return true;
        }
    }
    
    //returns the number of days between the two dates/exams
    public static int daysBetween(LocalDate date1, LocalDate date2){
        int timebetween = 0;

        //make sure date 1 is before date 2
        if (date2.isBefore(date1)){
            //if not swap them around
            LocalDate temp = date2;
            date2 = date1;
            date1 = temp;
        }
        
        int monthofDay1 = date1.getMonthValue();
        int monthofDay2 = date2.getMonthValue();
        int dayofDay1 = date1.getDayOfMonth();
        int dayofDay2 = date2.getDayOfMonth();
        
        //check if in same month
        if (monthofDay1 == monthofDay2){
            timebetween = dayofDay2 -  dayofDay1;
        }else{
            // gets the number of days left in the first month + the number of days in the last month
            timebetween = (date1.lengthOfMonth() - dayofDay1) + dayofDay2;

            // increment the month of date 1 to get the next month -> getting closer to month 2
            // while there is still a gap
            monthofDay1 = monthofDay1 + 1;
            int counter = 1;
            while (!((monthofDay1) >= (monthofDay2))){
                // add the number of days in that month to the counter
                timebetween = timebetween + (date1.plusMonths(counter)).lengthOfMonth();
                monthofDay1 = monthofDay1 + 1;
                counter  = counter +1;
            }
        }
        
        return timebetween;
    }

    // setters - as these are the parts that will change
    public void setTimeslot(Timeslot timeslot){
        this.timeslot = timeslot;
    }
    public void setRoom(Room room){
        this.room = room;
    }
    


}
