/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kap.examtimetabling.domain;

import com.kap.invigulationtimetabling.domain.SchoolPeriod;
import com.kap.invigulationtimetabling.domain.Teacher;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author katiepambakian
 */
public class Timeslot {
    
    //variables
    //unique identifier
    Integer id;
    // input date like LocalDate.of(year, month, day)
    private LocalDate date;
    // input times like LocalTime.of(hour, minuet, second)
    private LocalTime startTime;
    private LocalTime endTime;
    
    private Integer length;
    
    private List<Exam> exams = new ArrayList<>();
    
    private Map<SchoolPeriod, List<Teacher>> schoolPeriodsAndCover = new HashMap<SchoolPeriod, List<Teacher>>();
    //the number of invigulators required per school period
    private Integer numRequiredInvigulators;
    //private Integer currentNumInvigulators;
    //empty constructor
    public Timeslot(){
        
    }
    //constructor with parameters
    public Timeslot(Integer id, LocalDate date, LocalTime startTime, LocalTime endTime, Integer length){
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;   
        this.length = length;
    }
    //constructor with parameters
    public Timeslot(Integer id, LocalDate date, LocalTime startTime, LocalTime endTime){
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;   
    }
    //---- setters ----
    
    public void setnumInvigulators(Integer num){
        this.numRequiredInvigulators = num;
    }
    
    public void setschoolPeriodsAndCover(Map<SchoolPeriod, List<Teacher>> x){
        this.schoolPeriodsAndCover = x;
    }

    //---- getters ----
    /*
    public Integer getcurrentNumInvigulators(){
        return this.currentNumInvigulators;
    }
    */
    public Map<SchoolPeriod, List<Teacher>> getschoolPeriodsandCover(){
        return this.schoolPeriodsAndCover;
    }
    
    public List<SchoolPeriod> getSchoolPeriods(){
        return new ArrayList<>(schoolPeriodsAndCover.keySet());
    }
    
    public List<Teacher> getTeachersForPeriod(SchoolPeriod period){
        return schoolPeriodsAndCover.get(period);
    }
    
    public Integer getnumInvigulators(){
        return this.numRequiredInvigulators;
    }
    
    public List<Exam> getExams(){
        return exams;
    }
    
    //returns the name
    public LocalDate getDate(){
        return date;
    }
    public Integer getID(){
        return id;
    }
    
    //returns the max number of people 
    //the room can fit
    public LocalTime getStartTime(){
        return startTime; 
    }
    
    //returns the end time of
    //the exam period
    public LocalTime getEndTime(){
        return endTime; 
    }
    
    public Integer getLength(){
        return length;
    }
    
    //----methods----
    
    
    //returns the lenght of a timeperiod
    //in minuets
    public Float getLengthMinuets(){
        // get the hours and minuets of start time
        Integer starthours = startTime.getHour();
        Integer startminuets = startTime.getMinute();
        // convert into 24 hour clock
        Integer start = starthours*100 + startminuets;
        //get the hours and minuets of the end time
        Integer endhours = endTime.getHour();
        Integer endminuets = endTime.getMinute();
        // convert into 24 hour clock
        Integer end = endhours*100 + endminuets;
        //length between them
        Float time = (float)end - (float)start;
        //get the time in minuets
        time = (time/100)*60;

        return time;
    }
    
    //returns the lenght of a timeperiod
    //in hours
    public Float getLengthHours(){
        // get the hours and minuets of start time
        Integer starthours = startTime.getHour();
        Integer startminuets = startTime.getMinute();
        // convert into 24 hour clock
        Integer start = starthours*100 + startminuets;
        //get the hours and minuets of the end time
        Integer endhours = endTime.getHour();
        Integer endminuets = endTime.getMinute();
        // convert into 24 hour clock
        Integer end = endhours*100 + endminuets;
        //length between them
        Float length = (float)end - (float)start;
        //get the time in minuets
        length = (length/100);
        return length;
    }
    
    public void addschoolPeriod(SchoolPeriod x){
        this.schoolPeriodsAndCover.put(x, new ArrayList<>());
    }
    
    
    //gets the time period and the teacher to add
    public void addTeacher(SchoolPeriod period, Teacher teacher){
        List<Teacher> teachersCovering = this.schoolPeriodsAndCover.get(period);
        //adds the teacher to the list of teachers
        teachersCovering.add(teacher);
        //re-appends the list of teachers with the new teacher
        this.schoolPeriodsAndCover.replace(period, teachersCovering);
        
    }
    
    //gets the time period and the teacher to add
    public void removeTeacher(SchoolPeriod x, Teacher y){
        List<Teacher> teachers = this.schoolPeriodsAndCover.get(x);
        //adds the teacher to the list of teachers
        teachers.remove(y);
        //re-appends the list of teachers with the new teacher
        this.schoolPeriodsAndCover.replace(x, teachers);
    }

    public void addExams(Exam x){
        (this.exams).add(x);
    }

}