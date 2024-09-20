/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kap.invigulationtimetabling.domain;

import com.kap.examtimetabling.domain.Exam;
import static java.lang.Math.round;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 *
 * @author katiepambakian
 */

public class Teacher {
    
    
    //id to identify and instance
    private Integer id;
    //location of the teachers timetable in HTML form
    private String timetable;
    //maximum number of invigulation periods they can have
    private Integer maxInvigulation;
    //current number of invigulation they are doing
    private Integer currentInvigulations = 0;
    
    private int daysWorking=0;
    
    // list of the frees
    private List<SchoolPeriod> frees = new ArrayList<>();
    
    //list of invigulations
    private List<SchoolPeriod> invigulationPeriods = new ArrayList<>();
    
    private Float percentageOccuiped = 0f;
    
    //constructors
    public Teacher(){
        
    }
    //constructors
    public Teacher(Integer id){
        this.id = id;
    }
    public Teacher(Integer id, int daysWorking){
        this.id = id;
        this.daysWorking = daysWorking;
    }
    public Teacher(Integer id, List<SchoolPeriod> frees, int daysWorking){
        this.id = id;
        this.frees = frees;
        this.daysWorking = daysWorking;
    }
    public Teacher(Integer idP, Integer maxInvigulationP, Integer currentInvigulations){
        this.id = idP;
        this.maxInvigulation = maxInvigulationP;
        this.currentInvigulations = currentInvigulations;
    }
    public Teacher(Integer idP, Integer maxInvigulationP, List<SchoolPeriod> freesP){
        this.id = idP;
        this.maxInvigulation = maxInvigulationP;
        this.frees = freesP;
    }
    public Teacher(Integer idP, Integer maxInvigulationP, String timetableP, 
            List<SchoolPeriod> freesP){
        this.id = idP;
        this.maxInvigulation = maxInvigulationP;
        this.timetable = timetableP;
        this.frees = freesP;
    }
    
    //setters

    // set the timetable
    public void setTimetable(String timetable){
        //gets the location of the timetable
        this.timetable = timetable;
    }
    
    public void setFrees(List<SchoolPeriod> freesP){
        this.frees = freesP;
    }
    
    public void setCurrentInvigulations(Integer current){
        this.currentInvigulations = current;
    }
    public void setMaxInvigulations(Integer max){
        this.maxInvigulation = max;
    }
    
    public void setDaysWorking(int days){
        this.daysWorking = days;
    }
    
    //getters
    public Integer getId(){
        return id;
    }
    public Integer getMaxInvigulations(){
        return maxInvigulation;
    }
    public Integer getCurrentInvigulations(){
        return currentInvigulations;
    }
    public List<SchoolPeriod> getFrees(){
        return this.frees;
    }
    
    
    
    //methods 
    
    //checks to see if the teacher has exceeded the max number of invigulations or if they still can
    public Boolean checkAllowed(){
        //if the teacher is still availble returns true
        // if the teacher is not availbale returns false
        return maxInvigulation > currentInvigulations;
    }
    public Boolean checkAvailable(SchoolPeriod period){
        //returns true if the teacher is avaible 
        //i.e. in that period they are free
        //they are not already invigulating in that timeperiod
        //if the teacher is free in this period it will return true
        Boolean free = this.frees.contains(period);
        //if the teacher is already invigulating this will return true
        Boolean alreadyInvigulate = this.invigulationPeriods.contains(period);
        return free && !alreadyInvigulate;
    }
    public void incementCurrentInvigulations(){
        this.currentInvigulations = currentInvigulations+1;
    }
    public void decementCurrentInvigulations(){
        this.currentInvigulations = currentInvigulations-1;
    }
    
    
    
    public void addFrees(SchoolPeriod period){
        this.frees.add(period);
    }
    
    //calculate number of invigulation periods
    //takes the number of days in period and the proportion of frees that should be invigulations as a percentage
    public void calculateInvigulation(Integer periodsInDay, Float percentageInvigulation, Float numberOfWeeksInExamPeriod){
        //gets the proporiton of their time that is free
        float numFree = frees.size();
        float numDayWorking = daysWorking;
        float proportionFree = (numFree/(numDayWorking * periodsInDay));
        //work out the number of invigulations they should have relative to their free time in one week
        float invigulationsPerWeek = (proportionFree * (percentageInvigulation/100)) * 
                (periodsInDay * daysWorking);
        //get the number of ivigulations they should have across the whole exam period
        int totalInvigulations = round(invigulationsPerWeek *numberOfWeeksInExamPeriod);
        this.maxInvigulation = totalInvigulations;
    }
    
    public void addInvigulationPeriod(SchoolPeriod period){
        invigulationPeriods.add(period);
        this.currentInvigulations = currentInvigulations+1;
    }
    
    public void removeInviguationPeriod(SchoolPeriod period) {
        invigulationPeriods.remove(period);
        this.currentInvigulations = currentInvigulations-1;
    }
    public Float getpercentageOfAvalibleOccuiped(){
        this.percentageOccuiped = (Float.valueOf(currentInvigulations)/
                Float.valueOf(maxInvigulation))*100;
        return percentageOccuiped;
    }
    
}
