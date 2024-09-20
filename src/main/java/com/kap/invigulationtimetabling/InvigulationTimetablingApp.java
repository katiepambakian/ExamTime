/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kap.invigulationtimetabling;

import com.kap.UserInterface.AdminHomeScreen;
import com.kap.UserInterface.LoginScreen;
import com.kap.examtimetabling.ExamTimetablingApp;
import com.kap.examtimetabling.domain.Exam;
import com.kap.examtimetabling.domain.Timeslot;
import com.kap.examtimetabling.domain.ExamTimetable;
import com.kap.examtimetabling.domain.Room;
import com.kap.examtimetabling.extras.readFromDatabase;
import com.kap.examtimetabling.extras.writeToDatabase;
import com.kap.invigulationtimetabling.domain.InvigulationTimer;
import com.kap.invigulationtimetabling.domain.SchoolPeriod;
import com.kap.invigulationtimetabling.domain.Teacher;
import com.kap.invigulationtimetabling.domain.generateDataInvigulationTimetable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author katiepambakian
 */
public class InvigulationTimetablingApp {
    
    public static int examperiodid;
    public static String schoolid;
    private static Set<Timeslot> timeslots;
    private static Integer durationexam;
    private static Integer durationinvigulation;
    private static String pathexam;
    private static String pathinvigulation;
    private static float percentageInvigulation;
    
    //the start time variable
    private static Instant offsetClock;
    
    public static void main() throws Exception{
        //get the variables from the main method
        try{
            schoolid = AdminHomeScreen.getSchoolID();
            examperiodid = Integer.parseInt(AdminHomeScreen.getSeriesID());
            durationexam = AdminHomeScreen.getdurationExam();
            durationinvigulation = AdminHomeScreen.getdurationInvigulation();
            pathexam = AdminHomeScreen.getpathExam();
            pathinvigulation = AdminHomeScreen.getpathInvigulation();
            percentageInvigulation = AdminHomeScreen.getPercentageInvigulation();
                    
            //generate an examtimetable
            ExamTimetable solution = ExamTimetablingApp.generateExamTimetable(examperiodid, schoolid, durationexam);
            
            JOptionPane.showMessageDialog(null, 
                              "Examtimetable complete, now solving invigulation timetable", 
                              "UPDATE!", 
                              JOptionPane.INFORMATION_MESSAGE);

            generateDataInvigulationTimetable.main(solution, examperiodid, schoolid, percentageInvigulation);

            //generate the teachers and school timetable
            List<List<SchoolPeriod>> schoolTimetable = generateDataInvigulationTimetable.getSchoolTimetable();

            Map<Timeslot, List<Room>> roomsAndPeriods = getTimeslotsAndRoomsInUse(solution);

            getSchoolPeriodsInUse(schoolTimetable, roomsAndPeriods);

            numInvigulatorsReq(roomsAndPeriods);
            timeslots = roomsAndPeriods.keySet();
            
            //start the assigning process
            
            //make the offset clock
            Clock baseClock = Clock.systemDefaultZone();
            offsetClock = Clock.offset(baseClock, Duration.ofSeconds(durationinvigulation)).instant();
            
            assignInvigulators();
            deleteInvigulationsInTimePeriod();
            finalAssigning();

            //output to users
            oututToUserExamTimetable(pathexam);
            oututToUserInvigulationTimetable(pathinvigulation);

                    
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, 
                              "An error has occured,\n please make sure all infomation has been entered correctly\n" + ex, 
                              "ERROR", 
                              JOptionPane.WARNING_MESSAGE);
        }
    }
    
    
    //add the school periods to the timeslots
    public static void getSchoolPeriodsInUse(List<List<SchoolPeriod>> schoolTimetable, Map<Timeslot, List<Room>> roomsAndPeriodsInUse){
        Set<Timeslot> timeslotList = roomsAndPeriodsInUse.keySet();
        
        for (Timeslot timeslot : timeslotList){

            //gets the day of the week as a string (it will be in all caps
            String day = (timeslot.getDate()).getDayOfWeek().toString();
            
            //list of all the school periods on the same day
            List<SchoolPeriod> sameDay = new ArrayList<>();
            
            //get the appropriate day from the school timetable
            switch (day) {
                case "MONDAY" -> sameDay = schoolTimetable.get(0);
                case "TUESDAY" -> sameDay = schoolTimetable.get(1);
                case "WEDNESDAY" -> sameDay = schoolTimetable.get(2);
                case "THURSDAY" -> sameDay = schoolTimetable.get(3);
                case "FRIDAY" -> sameDay = schoolTimetable.get(4);
                default -> {
                }
            }
            
            //get the start and end time of the timeslot
            LocalTime timeslotstart = timeslot.getStartTime();
            LocalTime timeslotend = timeslot.getEndTime();
            
            //get the longest exam 
            List<Exam> exams = timeslot.getExams();
            
            Exam longest = exams.get(0);
            for (Exam e: exams){
                //if e is longer than the current longest
                if (e.getLength() > longest.getLength()){
                    //make e the new longest
                    longest = e;
                }
            }
            LocalTime examStart = timeslotstart;
            LocalTime examEnd = timeslotstart.plusMinutes(longest.getLength());
            
            //see if the school schoolPeriod is contained/overlaps with these
            for (SchoolPeriod schoolperiod: sameDay){
                
                LocalTime schoolPeriodStart = schoolperiod.getStart();
                LocalTime schoolPeriodEnd = schoolperiod.getEnd();
                
                if (// if the timeslot starts before the school schoolPeriod ends
                        examStart.isBefore(schoolPeriodEnd) &&
                        //the timeslot ends after the school schoolPeriod starts
                        examEnd.isAfter(schoolPeriodStart)){
                    
                    //add the timeslot to the school schoolPeriod array in the Timeslot class
                    timeslot.addschoolPeriod(schoolperiod);
                }

            }
            
        }
        
    }
    
    //get the timeslots in use and which rooms are used during those timeslots-> hashtable
    public static Map<Timeslot, List<Room>> getTimeslotsAndRoomsInUse(ExamTimetable timetable){
        //gets the list of exams which are featured in the timetable and therefore need to be invigulated
        List<Exam> examList = timetable.getExamList();
        
        //create a dictionary which will store the timeslot and the rooms used in that schoolPeriod
        Map<Timeslot, List<Room>> roomsAndExams = new HashMap<Timeslot, List<Room>>();
        
        for (Exam exam : examList){
            //get the timeslot the exam occurs in
            Timeslot timeslot = exam.getTimeslot();
            
            //add the exam to the timeslot list for latter use
            timeslot.addExams(exam);
            
            //get the room it happens in
            Room room = exam.getRoom();
            
            //if the timeslot is already in the dictionary
            if (roomsAndExams.containsKey(timeslot)){
                //reate a new list to hold rooms
                List<Room> roomList = new ArrayList<>();
                //add the rooms already known to be in use in the timeslot
                roomList.addAll(roomsAndExams.get(timeslot));
                
                //if the room is not in the room list
                if (!(roomList.contains(room))){
                    //add the room to the list
                    roomList.add(room);
                    //overwrite the roomlist in the hash table
                    roomsAndExams.replace(timeslot, roomList);
                }
                //otherwise ignore it
                
            //if the timeslot is not in the dictionary                  
            }else{
                //append the timeslot and room to the hashtable
                roomsAndExams.put(timeslot, Arrays.asList(room));
            }
        }
    
        return roomsAndExams;
    }
    
    public static void numInvigulatorsReq(Map<Timeslot, List<Room>> roomsAndPeriods){
        //calculate the number of invigulators required for each of the timeslots
        
        //get a list of the timeslots
        Set<Timeslot> timeslotList = roomsAndPeriods.keySet();
        //iterate through all the timeslots
        for (Timeslot timeslot : timeslotList){
            Integer numinvigulator = 0;
            
            //get the rooms used in the timeperiod
            List<Room> roomList = roomsAndPeriods.get(timeslot);
          
            //for each of the rooms...
            for (Room room : roomList){
                //...get the number fo invigulators required and add it to the total
                numinvigulator = numinvigulator + room.getNumInvigulators();
            }
            
            //set the number of invigulators required in the timeslot class in each school schoolPeriod
            timeslot.setnumInvigulators(numinvigulator);
            
        }
    }
    
    public static Boolean checkTeacherValid(Teacher teacher, SchoolPeriod period){
        return (teacher.checkAllowed() && teacher.checkAvailable(period));
    }
    
    
    
    
    //the teachers are not being added to the teacher free list in the school schoolPeriod

    //for each schoolPeriod i need the 
    public static Boolean assignInvigulators() throws Exception{
        if (!offsetClock.isBefore(Clock.systemDefaultZone().instant())){
            //get timeslots - > use the public variable
            //for each of the timeslots periods 
            for (Timeslot timeslot : timeslots){
                //todo-the weeks should change e.g new week reset the counter
                //get the list of school perids and the teachers invigulating them
                //at the start the teacher lists will be empty
                Map<SchoolPeriod, List<Teacher>> schoolPeriodsAndCover = timeslot.getschoolPeriodsandCover();
                //for each of the school periods in the timeslots
                for (SchoolPeriod schoolPeriod : timeslot.getSchoolPeriods()){

                    //get the teachers that are free
                    List<Teacher> freeteachers =schoolPeriod.getTeachersFreeInPeriod();
                    //shuffle the list of teachers so they are in a random order -> fair
                    Collections.shuffle(freeteachers);
                    //keep adding more teachers to the teachers list until all the spots in that 
                    //schoolPeriod have been filled

                    freeteachers = makePriorityQueue(freeteachers);

                    while (schoolPeriodsAndCover.get(schoolPeriod).size() < timeslot.getnumInvigulators()){
                        //get the list of teachers that are free in that schoolPeriod
                        //try each of the teachers
                        for (Teacher teacher : freeteachers){
                            if (checkTeacherValid(teacher, schoolPeriod)){
                                //the teacher is valid in that slot
                                //add the slot and increment their invigulations
                                teacher.addInvigulationPeriod(schoolPeriod);
                                timeslot.addTeacher(schoolPeriod, teacher);
                                //call again
                                if (assignInvigulators()){
                                    return true;
                                }else{
                                    //remove the schoolPeriod and decement the invigulation 
                                    teacher.removeInviguationPeriod(schoolPeriod);
                                    //remove the teacher from the timeslot
                                    timeslot.removeTeacher(schoolPeriod, teacher);
                                }
                            }
                        }
                        return false;
                    } 

                }

            }
            return true;
        }else{
            throw new Exception("No solution found");
        }
    }

    private static void printSolution() {
                
        for (Timeslot t : timeslots){
            Map<SchoolPeriod, List<Teacher>> schoolPeriodsAndCover = t.getschoolPeriodsandCover();
            System.out.println("number of required invigulators  : " + t.getnumInvigulators());
            
            for (SchoolPeriod period : schoolPeriodsAndCover.keySet()){
                System.out.println(t.getTeachersForPeriod(period));
                System.out.println("\n\nThe school Period is : " + period.getStart() + period.getDay() + " id "+period.getID());

            }
        }
        
    }
    //old function
    private static void storeResult(){
        //need to store results as Period - Teacher - room - exam
        for (Timeslot t: timeslots){
            //iterate through each of the timeslots

            //get the exams taking place in the timeperiod
            List<Exam> exam = t.getExams();
            
            //get list of rooms that need covering for each of the periods
            List<Room> rooms = new ArrayList<>();
            
            for (Exam e : exam){
                rooms.add(e.getRoom());
            }
            //assign teachers to each of the rooms so that all rooms have right number of invigulators

            //get the school periods and teachers that are being used to cover each slot
            Map<SchoolPeriod, List<Teacher>> schoolPeriodsAndCover = t.getschoolPeriodsandCover();
            //get a list of all the school periods
            Set<SchoolPeriod> periods =  schoolPeriodsAndCover.keySet();
            
            //iterate through the periods
            for (SchoolPeriod p : periods){
                //list of teachers for that period
                List<Teacher> teachers = schoolPeriodsAndCover.get(p);
                //a counter that will hold the position that we are as we iterate through the arry
                Integer counter = 0;
                
                //iterate through the rooms for each period 
                for(Room r : rooms){
                    List<Teacher> tech = new ArrayList<>();
                    //and assign teachers 
                    for (int i= 0; i<=r.getNumInvigulators(); i++){
                        tech.add(teachers.get(counter));
                        counter +=1;
                    }
                    
                }
            }
            
                
        }
    }

    private static List<Teacher> makePriorityQueue(List<Teacher> teachers) {
        //get the number of teachers in the array
        int size = teachers.size();
        boolean swapped;
        //loop through each element
        for (int i=0; i< size-1 ; i++){
            swapped = false;
            //loop to compare adjacent teachers
            for (int j=0; j<(size-i-1);j++){
                //compare the adgacent elements
                //get the percentage avaiable for the teachers
                //the order should be smaller percentage - larger percentage
                
                if (teachers.get(j).getpercentageOfAvalibleOccuiped() > 
                        teachers.get(j+1).getpercentageOfAvalibleOccuiped()){
                    //teacher in wrong order (larger percentage - smaller percentage)
                    //swap the elements
                    Teacher temp = teachers.get(j);
                    teachers.set(j, teachers.get(j+1));
                    teachers.set(j+1, temp);

                    //swap has occured
                    swapped = true;
                }
            }
            //if there was no swapping in the pass then the list is 
            //sorted so no further comparision needed
            if (!swapped){
                break;
            }
        }
        return teachers;    
    }
   
    private static void finalAssigning(){
        deleteInvigulationsInTimePeriod();
        //iterate through each of the exams
        for (Timeslot timeslot: timeslots){
            
            Integer timeslotid = timeslot.getID();
            //get the exams occuring
            List<Exam> examList = timeslot.getExams();
            
            //get all the school periods that need covering
            List<SchoolPeriod> schoolPeriods = timeslot.getSchoolPeriods();
            
            for (SchoolPeriod p: schoolPeriods){
                
                //get the list of teachers covering each period
                List<Teacher> teacher = timeslot.getTeachersForPeriod(p);

                //calculate the total number of invigulators for the period
                int numInvigulatorsForperiod = teacher.size();

                //conter to hold position through teacher list
                int counter = 0;
                //iterate through each of the exams and assign the teachers for the rooms
                for (Exam e: examList){
                    List<Teacher> teacherForRoom = new ArrayList<>();
                    Room room= e.getRoom(); 
                    
                    if ((numInvigulatorsForperiod >= room.getNumInvigulators())){
                        //there are sufficient invigulators
                    
                        for (int i=0; i<room.getNumInvigulators(); i++){
                            teacherForRoom.add(teacher.get(counter));
                            counter +=1;
                            numInvigulatorsForperiod -=1;
                        }
                        
                        for (Teacher t : teacherForRoom){
                            String SQL = String.format("INSERT INTO \"ExamTime\".invigulation (teacherid, roomid, examid, schoolperiodsid, timeslotid, examperiodid) "
                                    + "VALUES (%d, %d, %d, %d, %d, %d);",
                                    t.getId(), room.getID(),e.getId(), p.getID(),timeslotid, examperiodid);
                            writeToDatabase.writeToDatabase(SQL);
                        }
                    }else{
                            throw new RuntimeException("Insufficient teachers");
                            
                        }
                }
            }
        }
    }

    private static void deleteInvigulationsInTimePeriod() {
        String SQL = String.format("DELETE FROM \"ExamTime\".invigulation WHERE examperiodid =%s;", examperiodid);
        writeToDatabase.writeToDatabase(SQL);
    }
    
    //output the exam timetable as an excel file
    private static void oututToUserExamTimetable(String path) throws IOException{
        // tree map to hold the date to be writen to the excel file
        Map<Integer, Object[]> studentData = new TreeMap<Integer, Object[]>();
        
        //sort the timeslots so that they are in time order
        List<Timeslot> timeslotList = new ArrayList<>(timeslots);
        timeslotList = sortTimeslots(timeslotList);
        
        Integer id = 0;
        //header for the spreadsheet
        studentData.put(id++, new Object[] {"Date", "Start Time", "Subject","Exam","Length"});
        
        //iterate through each of the timeslots and make them into objects
        for (Timeslot t : timeslotList){
            //get the list of the exams
            List<Exam> examList = t.getExams();
            
            //create a new list that will be writen to the spreadsheet
            List<String> listToAppend = new ArrayList<>();
            
            //write the timeslot and first item
            
            //add the timeslot details to the array
            listToAppend.add(t.getDate().toString());
            listToAppend.add(t.getStartTime().toString());
            
            listToAppend.add(examList.get(0).getSubject());
            listToAppend.add(examList.get(0).getName());
            listToAppend.add(examList.get(0).getLengthToString());
            
            //write to the spreadsheet
            Object[] firstArray = listToAppend.toArray();
            studentData.put(id++, firstArray);
                        
            //add the rest of the exams starting at the second one
            if (examList.size()>1){
                for (int i=1; i<examList.size()-1;i++){
                    //create a new list that will be writen to the spreadsheet
                    List<String> toadd = new ArrayList<>();
                    toadd.add("");
                    toadd.add("");
                    toadd.add(examList.get(i).getSubject());
                    toadd.add(examList.get(i).getName());
                    toadd.add(examList.get(i).getLengthToString());
                    
                    //write to the spreadsheet
                    Object[] addArray = toadd.toArray();
                    studentData.put(id++, addArray);
                }
            }
            
            
        }

        writeToExcelFile(studentData, path, "ExamTimetable");
        
        
    }
    
    private static void oututToUserInvigulationTimetable(String path) throws IOException{
        //hashtabe to store the invigulation data and an index to access it
        Map<Integer, Object[]> invigulationData = new TreeMap<Integer, Object[]>();
        //an index for the table
        Integer i=0;
        
        //header for the spreadsheet
        invigulationData.put(i++, new Object[]{"Date", "Day", "Start Time", "End Time","Exam", "Room", "Teacher"});
        
        //get the schoolperiods in order
        String SQL = String.format("select schoolperiodsid " +
            "from \"ExamTime\".schoolperiods " +
            "where schoolid ='%s' " +
            "ORDER BY " +
            "CASE " +
            "WHEN dayofweek = 'Monday' THEN 1 " +
            "WHEN dayofweek = 'Tuesday' THEN 2 " +
            "WHEN dayofweek = 'Wednesday' THEN 3 " +
            "WHEN dayofweek = 'Thursday' THEN 4 " +
            "WHEN dayofweek = 'Friday' THEN 5 " +
            "WHEN dayofweek = 'Saturday' THEN 6 " +
            "WHEN dayofweek = 'Sunday' THEN 7 " +
            "END asc, starttime asc;",schoolid);
        //get the ids of the schoool periods in order in which they occur
        List<String> schoolperiodsid = readFromDatabase.readDatabase(SQL, "schoolperiodsid");
        
        //for each school periods get the teachers invigulating -> group same exams together
        for (String id : schoolperiodsid){
            
            String invigulation = String.format("select \"ExamTime\".teachers.username, "
                    + "\"ExamTime\".rooms.roomname, \"ExamTime\".exams.\"name\", \"ExamTime\".timeslots.\"date\", "
                    + "\"ExamTime\".schoolperiods.endtime,\"ExamTime\".schoolperiods.starttime, "
                    + "\"ExamTime\".schoolperiods.dayofweek " +
                "from \"ExamTime\".invigulation " +
                "join \"ExamTime\".teachers on teachers.teacherid = invigulation.teacherid " +
                "join \"ExamTime\".rooms on rooms.roomid = invigulation.roomid " +
                "join \"ExamTime\".exams on exams.examid = invigulation.examid " +
                "join \"ExamTime\".timeslots on invigulation.timeslotid = timeslots.timeslotid " +
                "join \"ExamTime\".schoolperiods on schoolperiods.schoolperiodsid = invigulation.schoolperiodsid " +
                "where \"ExamTime\".invigulation.schoolperiodsid = %s and \"ExamTime\".invigulation.examperiodid = %s " +
                "order by \"name\" asc;", id, examperiodid);
            List<String> username = readFromDatabase.readDatabase(invigulation, "username");
            List<String> room = readFromDatabase.readDatabase(invigulation, "roomname");
            List<String> examname = readFromDatabase.readDatabase(invigulation, "name");
            List<String> date = readFromDatabase.readDatabase(invigulation, "date");
            List<String> endtime = readFromDatabase.readDatabase(invigulation, "endtime");
            List<String> starttime = readFromDatabase.readDatabase(invigulation, "starttime");
            List<String> dayofweek = readFromDatabase.readDatabase(invigulation, "dayofweek");
            
            //create a list that will make the object to write to the spreadsheet

            if (!username.isEmpty()){
                //create the first entry to the database, this will contain the day of the week 
                List<String> listToAppend = new ArrayList<>();
                listToAppend.add(date.get(0));
                listToAppend.add(dayofweek.get(0));
                listToAppend.add(starttime.get(0));
                listToAppend.add(endtime.get(0));
                listToAppend.add(examname.get(0));
                listToAppend.add(room.get(0));
                listToAppend.add(username.get(0));

                Object[] invigulationObject = listToAppend.toArray();
                invigulationData.put(i++, invigulationObject);

                //iterate through all the exams starting at the second one
                // first checking there is a second one 
                if (examname.size()>1){
                    for (int counter=1; counter<examname.size()-1;counter++){
                        List<String> itemsToAdd = new ArrayList<>();
                        itemsToAdd.add("");
                        itemsToAdd.add("");
                        itemsToAdd.add("");
                        itemsToAdd.add("");
                        itemsToAdd.add(examname.get(counter));
                        
                        itemsToAdd.add(room.get(counter));
                        
                        itemsToAdd.add(username.get(counter));
                        
                        Object[] examArray = itemsToAdd.toArray();
                        invigulationData.put(i++, examArray);
                    }
                }
            }
            
            
        }
        writeToExcelFile(invigulationData, path, "InvigulationTimetable");
        
    }
    
    private static void writeToExcelFile(Map<Integer, Object[]> studentData, String path, String sheet) 
            throws FileNotFoundException, IOException{
        //create the excel sheet to write to 
        // workbook object
        XSSFWorkbook workbook = new XSSFWorkbook();
        // spreadsheet object
        XSSFSheet spreadsheet = workbook.createSheet(sheet);
        // row object
        XSSFRow row;
        
        int rowid = 0;
        //get all the entries in the hashtable
        Set<Integer> keyid = studentData.keySet();
        
        // writing the data into the sheets...
        for (Integer key :keyid){
            //create a row for each entry in the hash table
            row = spreadsheet.createRow(rowid++);
            //get the details
            Object[] objectArr = studentData.get(key);
            int cellid = 0;
            //for each object have a separate entry into the spreadsheet
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
            }
        }
        
        try ( // .xlsx is the format for Excel Sheets...
        // writing the workbook into the file...
                FileOutputStream out = new FileOutputStream(new File(path))) {
            workbook.write(out);
        }
    }
    
    
    
    private static List<Timeslot> sortTimeslots(List<Timeslot> timeslotList){
        int n=timeslotList.size();
        
        Boolean swapped = true;
        
        //iterate through all the timeslots
        for (int i=0; i<n;i++){
            swapped = false;
            for (int j = 0; j < n - i - 1; j++){
                // see which day they occur on
                
                // see if they occur on the same day
                // get the date it is on
                if (timeslotList.get(j).getDate().equals(timeslotList.get(j+1).getDate())){
                    //comapaire the time to see whcih comes first in the day
                    //time of the first one
                    LocalTime start1 = timeslotList.get(j).getStartTime();
                    LocalTime start2 = timeslotList.get(j+1).getStartTime();
                    if (start2.isBefore(start1)){
                        //the second one occurs first so they need to switch
                        Timeslot temp1 = timeslotList.get(j);
                        Timeslot temp2 = timeslotList.get(j+1);
                        
                        timeslotList.set(j, temp2);
                        timeslotList.set(j+1, temp1);
                        swapped = true;
                    }//otherwise the two times are in the correct order
                } else{
                    //the timeslots are on different days
                    if (timeslotList.get(j+1).getDate().isBefore(timeslotList.get(j).getDate())){
                        //the second timeslot occurs before the first timeslot
                        Timeslot temp1 = timeslotList.get(j);
                        Timeslot temp2 = timeslotList.get(j+1);
                        
                        timeslotList.set(j, temp2);
                        timeslotList.set(j+1, temp1);
                        swapped = true;
                    }//otherwise the second timeslot is after the first one
                }
            }
            if (swapped == false)
                break;
        }
        return timeslotList;
    }
    
    
    /*
    public static void main(String[] args) throws IOException {
        
        //generate an examtimetable
        ExamTimetable solution = ExamTimetablingApp.generateExamTimetable(examperiodid, schoolid, durationexam);
        generateDataInvigulationTimetable.main(solution, examperiodid, schoolid);
        //generate the teachers and school timetable
        
        List<SchoolPeriod> schoolPeriodList = generateDataInvigulationTimetable.getSchoolPeriods();
        List<List<SchoolPeriod>> schoolTimetable = generateDataInvigulationTimetable.getSchoolTimetable();
        List<Teacher> teacherList = generateDataInvigulationTimetable.getTeachersList();
        
        Map<Timeslot, List<Room>> roomsAndPeriods = getTimeslotsAndRoomsInUse(solution);
        getSchoolPeriodsInUse(schoolTimetable, roomsAndPeriods);
        
        numInvigulatorsReq(roomsAndPeriods);
        timeslots = roomsAndPeriods.keySet();
        assignInvigulators();
        deleteInvigulationsInTimePeriod();
        finalAssigning();
        //printSolution();
        //output to users
        oututToUserExamTimetable(pathexam);
        oututToUserInvigulationTimetable(pathinvigulation);
    }
*/
    
    
}
