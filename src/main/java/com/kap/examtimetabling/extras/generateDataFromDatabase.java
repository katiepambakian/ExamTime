/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kap.examtimetabling.extras;

import com.kap.examtimetabling.domain.Exam;
import com.kap.examtimetabling.domain.Room;
import com.kap.examtimetabling.domain.Student;
import com.kap.examtimetabling.domain.Subject;
import com.kap.examtimetabling.domain.Timeslot;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author katiepambakian
 */
public class generateDataFromDatabase {
    //Attributes
    
    //for each othe lists the name/id and the object have the same index
    //holds a list of the student ids
    private static List<String> studentid = new ArrayList<>();
    //holds the list of the names of the subects
    private static List<String> subjectNames = new ArrayList<>();
    
    //holds a list of the student objects
    private static final List<Student> studentList = new ArrayList<>();
    //holds the list of subjet objects
    private static final List<Subject> subjectList = new ArrayList<>();
    
    //list of all the exam objects
    private static List<Exam> examList = new ArrayList<>();
    
    //list of all the timeslot objects
    private static List<Timeslot> timeslotList = new ArrayList<>();
    
    //list of all the room objects
    private static List<Room> roomList = new ArrayList<>();
    
    public static void generateData(int examperiodid, String schoolid){
        //clear the content of the lists
        studentid.clear();
        subjectNames.clear();
        studentList.clear();
        subjectList.clear();
        examList.clear();
        roomList.clear();
        timeslotList.clear();
        
        //method that runs all of the functions
        loadSubjects();
        loadStudents(examperiodid);
        //must come after subject and students
        loadStudies(examperiodid);
        //must come after studies
        loadExams(examperiodid);
        loadTimeslots(examperiodid);
        loadRooms(examperiodid);
    }
    
    //checked
    private static void loadSubjects(){
        //qerys the database and gets a list of subject names 
        String SQL = "SELECT * FROM \"ExamTime\".subjects";
        String columnLabel = "subjectname";
        subjectNames = (readFromDatabase.readDatabase(SQL, columnLabel));
        //make a new subject class for each and add it to an array
        //the position in the array of classes will be the same as the index in the array of names above
        for (int i=0; i<(subjectNames.size());i++){
            subjectList.add(new Subject(subjectNames.get(i)));
        }
        
    }
    //checked and working
    private static void loadStudents(int examperiod){
        //qerys the database and gets a list of students ids 
        //get the student id's in the exam period
        String SQL = String.format("SELECT * FROM \"ExamTime\".sitting where examperiodid = %d;", examperiod);
        String columnLabel = "studentid";
        studentid = (readFromDatabase.readDatabase(SQL, columnLabel));
        
        //make a new student class with id attribute
        //add it to a list (the index of the class in the list will be the same as the index of the student in the list
        
        for (int i=0; i<(studentid.size());i++){
            studentList.add(new Student(studentid.get(i)));
        }
        

    }
    
    private static void loadStudies(int examperiod){
        //in the studies table there will be some students not in the exam period we want
        
        //read the subject name from the subject list, find the appropriate class (I can look through the list of subject names to find the index of the class in the subject class list)
        //then find the appropriate corresponding student and their class. 
        //Then write the student object to the studentList subject class attribute. 
        
        String SQL = String.format("SELECT studies.studentid, studies.subjectname " +
            "FROM \"ExamTime\".studies " +
            "join \"ExamTime\".sitting on sitting.studentid = studies.studentid " +
            "join \"ExamTime\".students on studies.studentid = students.studentid " +
            "where sitting.examperiodid =%d;", examperiod);
        //load subject name from database and store in list
        String columnLabel = "subjectname";
        List<String> subject = (readFromDatabase.readDatabase(SQL, columnLabel));
        

        //load student id from database and store in list
        String columnLabel2 = "studentid";
        List<String> id = (readFromDatabase.readDatabase(SQL, columnLabel2));
        
        
        //find the index of the subjectName for each of the names in subject in the subjectName class
        for (int i = 0; i<subject.size(); i++){
            //get the index of the subject
            Integer subjectindex = subjectNames.indexOf(subject.get(i));
            //get the subject class
            Subject subjectClass = subjectList.get(subjectindex);
            
            //get the index of the student
            Integer studentindex = studentid.indexOf(id.get(i));
            //get the student class
            Student studentClass = studentList.get(studentindex);
            System.out.println(subjectList.get(subjectindex)+" student: "+studentList.get(studentindex).getStudentId());
            //write the student to the subject student list
            subjectClass.addStudent(studentClass);
            System.out.println(subjectClass.getStudents());
        }
        
        for (Subject s:subjectList){
            System.out.println(s.getSubject());
            System.out.println(s.getStudents());
        }
        
    }
    
    private static void loadExams(int examPeriod){
        //specifying the table and is used by each query
        String SQL = String.format("SELECT * FROM \"ExamTime\".exams WHERE examperiodid = %d;", examPeriod);
        
        //query the database and get a list of 
        // examdids
        String columnLabel = "examid";
        List<String> examid = (readFromDatabase.readDatabase(SQL, columnLabel));
        
        //exam names
        String columnLabel2 = "name";
        List<String> examName= (readFromDatabase.readDatabase(SQL, columnLabel2));
        
        //exam lengths
        String columnLabel3 = "length";
        List<String> examlength= (readFromDatabase.readDatabase(SQL, columnLabel3));
        
        //subject names
        String columnLabel4 = "subjectname";
        List<String> subjectName= (readFromDatabase.readDatabase(SQL, columnLabel4));
        //check to see if there are any empty subjects so their exams will be skips and objects not made
        
        List<Subject> empty = checkEmpty();
        
        for (int i = 0; i < examid.size();i++){
            
            //get the index of the subject
            Integer subjectindex = subjectNames.indexOf(subjectName.get(i));
            //get the subject class
            Subject subjectClass = subjectList.get(subjectindex);
            
            //make sure it is not the class which is empty - if the class is not empty
            if (!(empty.contains(subjectClass))){
                //not empty
                //gets the lenght of the exam as an integer
                Integer length = Integer.valueOf(examlength.get(i));

                //gets the id of the exam as an integer
                Integer id = Integer.valueOf(examid.get(i));

                //gets the name of the exam
                String name = examName.get(i);
                //makes a new exam object and adds it to the list
                examList.add(new Exam(id, name, subjectClass, length));
            }
        }
        
    }
    
    private static List<Subject> checkEmpty(){
        //makes a new list to hold empty subject classes
        List<Subject> empty = new ArrayList();
        //checks to see if each of the classes has students in
        for (int i = 0; i<subjectList.size(); i++){
            if (subjectList.get(i).getStudents().size() < 1){
                //subject is empty/no students so the subject class is added to the list
                empty.add(subjectList.get(i));
            }
        }
        return empty;
    }
    
    private static void loadRooms(int examPeriod){
        String SQL = String.format("SELECT * FROM \"ExamTime\".rooms WHERE examperiodid = %d", examPeriod);
        //get the room id
        String columnLabel = "roomid";
        List<String> roomid = (readFromDatabase.readDatabase(SQL, columnLabel));
        //get the room name
        String columnLabel2 = "roomname";
        List<String> name = (readFromDatabase.readDatabase(SQL, columnLabel2));
        //get the capacity
        String columnLabel3 = "capacity";
        List<String> capacity = (readFromDatabase.readDatabase(SQL, columnLabel3));
        //get the number of invigulators required
        String columnLabel4 = "numinvigulatorsrequired";
        List<String> invigulators = (readFromDatabase.readDatabase(SQL, columnLabel4));
        
        for (int i=0;i<roomid.size();i++){
            Integer id = Integer.valueOf(roomid.get(i));
            Integer capacityValue = Integer.valueOf(capacity.get(i));
            Integer invigulatorsNumber = Integer.valueOf(invigulators.get(i));
            //create new room and add it to the list
            roomList.add(new Room(id,name.get(i), capacityValue, 
                    invigulatorsNumber));
        }
        
    }
    private static void loadTimeslots(int examperiod){
        //specifying the table and is used by each query
        String SQL = String.format("SELECT * FROM \"ExamTime\".timeslots WHERE examperiodid = %d", examperiod);
        
        //id of timeslot
        String columnLabel = "timeslotid";
        List<String> timeslotid = (readFromDatabase.readDatabase(SQL, columnLabel));
        
        //date of timeslot
        String columnLabel2 = "date";
        List<String> dateList= (readFromDatabase.readDatabase(SQL, columnLabel2));
        
        //start time
        String columnLabel3 = "starttime";
        List<String> starttime= (readFromDatabase.readDatabase(SQL, columnLabel3));
        
        //end time
        String columnLabel4 = "endtime";
        List<String> endtime= (readFromDatabase.readDatabase(SQL, columnLabel4));
        
        String columnLabel5 = "length";
        List<String> lengthList= (readFromDatabase.readDatabase(SQL, columnLabel5));
        
        
        for (int i = 0; i < timeslotid.size();i++){
            //get the id as an integer
            Integer id = Integer.valueOf(timeslotid.get(i));
            
            //get the date of the timeslot and make it into local date and
            //make it into a local date format
            LocalDate date = LocalDate.parse(dateList.get(i), 
                    DateTimeFormatter.ISO_DATE);
            
            //get the start time in local time format
            LocalTime startTime = LocalTime.parse(starttime.get(i), 
                    DateTimeFormatter.ISO_LOCAL_TIME);
            //get the end time in local time format
            LocalTime endTime = LocalTime.parse(endtime.get(i), 
                    DateTimeFormatter.ISO_LOCAL_TIME);
            
            //get the length as an integer
            Integer length = Integer.valueOf(lengthList.get(i));
            
            //create new timeslot and add it to the list
            timeslotList.add(new Timeslot(id, date, startTime, endTime, length));
        }
        
    }

    public static List<Timeslot> getTimeslots(){
        return timeslotList;
    }
    
    public static List<Exam> getExams(){
        return examList;
    }
    public static List<Room> getRooms(){
        return roomList;
    }

}
