/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kap.examtimetabling.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author katiepambakian
 */
public class Student {
    private String id;
    private List<String> accessArrangements = new ArrayList<>();
    private List<Exam> exams = new ArrayList<>();
    
    //empty constructors 
    public Student(){
        
    }
    //constructor with only id
    public Student(String id){
        this.id = id;
    }

    public Student(String id, List<String> accessArrangements){
        this.id = id;
        this.accessArrangements = accessArrangements;
    }
    // getters
    public String getStudentId(){
        return id;
    }

    public List<String> getAccessArrangements(){
        return accessArrangements;
    }
    // method to add a new accessarrangment to the student
    public void addAccess(String access){
        this.accessArrangements.add(access);
    }
    //method to add a new exam to the student
    public void addExam(Exam exam){
        this.exams.add(exam);
    }
}
