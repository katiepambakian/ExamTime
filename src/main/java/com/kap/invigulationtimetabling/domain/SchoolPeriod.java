/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kap.invigulationtimetabling.domain;

import com.kap.examtimetabling.domain.Exam;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author katiepambakian
 */
public class SchoolPeriod {
    
    //id of the period
    private Integer id;
    //length of period
    private Integer length;
    
    //start time
    private LocalTime start;
    //end time
    private LocalTime end;
    
    //data of the invigulation
    private String day;
    
    //holds all the exams happening in the period
    private List<Exam> examsInPeriod = new ArrayList<>();
    
    //teachers frees
    private List<Teacher> teachersFree = new ArrayList<>();
    
    //constructors
    
    public SchoolPeriod(){
        
    }
    public SchoolPeriod(Integer id, Integer lengthP, LocalTime startP, LocalTime endP, String dayP){
        this.id = id;
        this.length = lengthP;
        this.start = startP;
        this.end = endP;
        this.day = dayP;
    }
    
    public SchoolPeriod(Integer id, Integer lengthP, LocalTime startP, LocalTime endP, String dayP, List<Exam> examsinPeriodP){
        this.id = id;
        this.length = lengthP;
        this.start = startP;
        this.end = endP;
        this.day = dayP;
        this.examsInPeriod = examsinPeriodP;
    }
    
    //getters
    public Integer getID(){
        return this.id;
    }
    public Integer getLength(){
        return length;
    }
    
    public LocalTime getStart(){
        return start;
    }
    
    public LocalTime getEnd(){
        return end;
    }
    
    public String getDay(){
        return day;
    }
    
    public List<Exam> examsInPeriodAsList(){
        return examsInPeriod;
    }
    
    public String examsInPeriodAsString(){
        return examsInPeriod.toString();
    }
    
    public List<Teacher> getTeachersFreeInPeriod(){
        return teachersFree;
    }
    
    //setters 
    
    public void addTeacher(Teacher teacher){
        this.teachersFree.add(teacher);
    }
    public void addExam(Exam exam){
        this.examsInPeriod.add(exam);
    }
    
    
}
