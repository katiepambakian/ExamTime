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
public class Subject {
    Long id;
    private String subject;
    //this needs to be the list of the student objects
    List<Student> students = new ArrayList<>();
    
    //empty constructors 
    public Subject(){
        
    }
    //constructor with just subject name
    public Subject(String subject){
        this.subject = subject;
    }

    //constructor with subject name and list of students
    public Subject(String subject, List<Student> students){
        this.subject = subject;
        this.students = students;
    }
    // getters
    public String getSubject(){
        return subject;
    }

    public List<Student> getStudents(){
        return students;
    }
    // method to add a new student to the subject
    public void addStudent(Student student){
        this.students.add(student);
    }
    
}
