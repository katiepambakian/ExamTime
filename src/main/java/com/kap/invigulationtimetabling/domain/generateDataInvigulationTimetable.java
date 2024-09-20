/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kap.invigulationtimetabling.domain;

import com.kap.examtimetabling.domain.ExamTimetable;
import com.kap.examtimetabling.domain.Timeslot;
import com.kap.examtimetabling.extras.readFromDatabase;
import static com.kap.invigulationtimetabling.InvigulationTimetablingApp.schoolid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author katiepambakian
 */
public class generateDataInvigulationTimetable {
    
    private static List<SchoolPeriod> schoolPeriodList;
    private static List<List<SchoolPeriod>> schoolTimetable;
    private static List<Teacher> teacherList;
    private static float lenOfPeriod;
    private static String schoolid;
    private static float percentageInvigulation;
    
    public static List<SchoolPeriod> getSchoolPeriods(){
        return schoolPeriodList;
        
    }
    public static List<List<SchoolPeriod>> getSchoolTimetable(){
        return schoolTimetable;
    }
    
    public static List<Teacher> getTeachersList(){
        return teacherList;
    }
    
    public static float getlenOfPeriod(){
        return lenOfPeriod;
    }

    public static void main(ExamTimetable solution, int examperiodid, String schoolidP, float percentageInvigulationP) throws IOException{
        //clear the content of the variables
        
        schoolid = schoolidP;
        percentageInvigulation = percentageInvigulationP;
        List<Timeslot> timeslots = solution.getTimeslotList();
        calculateLenOfPeriod(timeslots);
        //get the school periods from the database
        generateSchoolPeriod(schoolidP);
        //arrange the school periods into timetable
        generateSchoolTimetable();
        //get the teachers from the database
        generateTeachers2();
        
    }
    
    private static void calculateLenOfPeriod(List<Timeslot> timeslots) {
        
        //variables to hold the first and last period
        LocalDate first = timeslots.get(0).getDate();
        LocalDate last = timeslots.get(1).getDate();
        for (int i=2; i<timeslots.size();i++){
            //if the timeslot comes before the first timeslot
            if (timeslots.get(i).getDate().isBefore(first)){
                //i comes before the first data therefore is first
                first = timeslots.get(i).getDate();
            }
            else if (timeslots.get(i).getDate().isAfter(last)){
                //the timeslot occurs after the last timeslot therefore is last
                last = timeslots.get(i).getDate();
            }
        }
        //variable which will count the number of days between the two dates
        float firstweek = 0f;
        float lastweek = 0f;

        //need to round the days to work out the exact number of weeks
        //get any days which occur before the first full week
        switch (first.getDayOfWeek().toString()){
            case "MONDAY" -> {
                //if the start day is monday then no adjustment is needed
                break;
            }
            case "TUESDAY" -> {
                //include the days in the first week 
                firstweek += 4;
                //jump to the next monday
                first.plusDays(6);
            }
            case "WEDNESDAY" -> {
                //the days in the first week
                firstweek +=3;
                //move the counter on the the next monday
                first.plusDays(5);
            }
            case "THURSDAY" -> {
                //the days in the first week
                firstweek +=2;
                //move the counter on the the next monday
                first.plusDays(4);
            }
            case "FRIDAY" -> {
                //the days in the first week
                firstweek +=1;
                //move the counter on the the next monday
                first.plusDays(3);
            }
            default -> {
            
            }
        }
        //get any days which occur after the first full week
        switch (last.getDayOfWeek().toString()){
            case "MONDAY" -> {
                //if the last day is a monday
                //add monday to the total
                lastweek += 1;
                //go back to the last sunday
                last.minusDays(1);
            }
            case "TUESDAY" -> {
                //add monday to the total
                lastweek += 2;
                //go back to the last sunday
                last.minusDays(2);
            }
            case "WEDNESDAY" -> {
                //add monday to the total
                lastweek += 3;
                //go back to the last sunday
                last.minusDays(3);
            }
            case "THURSDAY" -> {
                //add monday to the total
                lastweek += 4;
                //go back to the last sunday
                last.minusDays(4);
            }
            case "FRIDAY" -> {
                //add monday to the total
                lastweek += 5;
                //go back to the last sunday
                last.minusDays(5);
            }
            default -> {
                
            }
        }
            
        Integer daysbetween = daysBetween(first, last);
        //get the number of days between 
        float weeksbetween = daysbetween/7;
        //calculate the fraction of the weeks before and after
        float extraDays = (firstweek + lastweek)/5;
        
        lenOfPeriod = weeksbetween + extraDays;
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
    
    private static void generateSchoolPeriod(String schoolid){
        //get the ids of the schoool periods in order in which they occur
        //get the schoolperiods in order
        String SQL = String.format("select * " +
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
        
        
        //gets the schoolperiod ids -> need to be converted into integers
        String columnLabel = "schoolperiodsid";
        List<String> periodIDList = (readFromDatabase.readDatabase(SQL, columnLabel));
        //gets the start time as string -> needs to be put into local time format
        String columnLabel1 = "starttime";
        List<String> startTimeList = (readFromDatabase.readDatabase(SQL, columnLabel1));
        //gets the end time as string -> needs to be put into local time format
        String columnLabel2 = "endtime";
        List<String> endTimeList = (readFromDatabase.readDatabase(SQL, columnLabel2));
        //gets the lengths as a string -> need to be converted into integers
        String columnLabel3 = "length";
        List<String> lengthList = (readFromDatabase.readDatabase(SQL, columnLabel3));
        //gets the day of the week as a string
        String columnLabel4 = "dayofweek";
        List<String> dayOfWeekList = (readFromDatabase.readDatabase(SQL, columnLabel4));
        
        List<SchoolPeriod> SchoolPeriods = new ArrayList<>();
        
        for (int i=0;i<periodIDList.size();i++){
            Integer id = Integer.valueOf(periodIDList.get(i));
            //get the start time in local time format
            LocalTime startTime = LocalTime.parse(startTimeList.get(i), DateTimeFormatter.ISO_LOCAL_TIME);
            //get the end time in local time format
            LocalTime endTime = LocalTime.parse(endTimeList.get(i), DateTimeFormatter.ISO_LOCAL_TIME);
            Integer length = Integer.valueOf(lengthList.get(i));
            String dayOfWeek = dayOfWeekList.get(i);
            SchoolPeriods.add(new SchoolPeriod(id, length, startTime, 
                    endTime, dayOfWeek));
            
        }
        generateDataInvigulationTimetable.schoolPeriodList = SchoolPeriods;
     
    }
    
    //creates a 2D array which replicates the school timetable
    private static void generateSchoolTimetable(){
        List<SchoolPeriod> schoolPeriods = generateDataInvigulationTimetable.schoolPeriodList;
        //the days and times of the school day
        List<String> daysOfWeek = getDaysOfWeek();
        List<LocalTime> timesOfDay = getStartTimesOfDay();
        
        //a 2D array that will hold the schoolTimetable - index [day][time]
        List<List<SchoolPeriod>> schoolTimetableLocal = new ArrayList<List<SchoolPeriod>>();
        //add the days to the array
        schoolTimetableLocal.add(new ArrayList<SchoolPeriod>());
        schoolTimetableLocal.add(new ArrayList<SchoolPeriod>());
        schoolTimetableLocal.add(new ArrayList<SchoolPeriod>());
        schoolTimetableLocal.add(new ArrayList<SchoolPeriod>());
        schoolTimetableLocal.add(new ArrayList<SchoolPeriod>());
        
        //...for every day
        Integer index = 0;
        
        for (String day : daysOfWeek){
            
            //get the list of periods in that day
            //temporary list to hold the periods in that day
            List<SchoolPeriod> schoolperiodsOnDay = new ArrayList<>();
            
            for (SchoolPeriod period: schoolPeriods){
                
                //for every period on the day add it to the temporary list
                if ((period.getDay()).equals(day)){
                    schoolperiodsOnDay.add(period);
                }
            }

            //...for every time in the day
            for (int x=0;x<timesOfDay.size();x++){
                //the period on the day at the right timet that we are trying to find
                SchoolPeriod item = null;
                //while we are still looking for the period we need
                Boolean found = false;
                Integer counter = 0;
                //conver the day into the local time format so it can be compaired
                while (found == false){
                 
                    //it the school period is at the time we need return the item, if it is not keep looking
                    //gets the start time of the period and makes it a string
                    //then compairs it to the period we are looking for
                    
                    if (((schoolperiodsOnDay.get(counter)).getStart()).equals(timesOfDay.get(x)))
                    {
                        found = true;
                        item = schoolperiodsOnDay.get(counter);  
                    }
                    counter +=1;
                }
                //now we have found the period we are looking for we can add it to the list
                schoolTimetableLocal.get(index).add(item);
            }
            
            index += 1;
        }
        
        generateDataInvigulationTimetable.schoolTimetable = schoolTimetableLocal;
        
    }
    
    private static List<LocalTime> getStartTimesOfDay(){

        List<LocalTime> times = new ArrayList<>();

        for (SchoolPeriod period : schoolPeriodList){
            if (!(times.contains(period.getStart()))){
                
                times.add(period.getStart());
            }
        }
        //sort them 
        boolean swapped;
        for (int i = 0; i < times.size() - 1; i++)
        {
            swapped = false;
            for (int j = 0; j < times.size() - i - 1; j++)
            {
                if (times.get(j+1).isBefore(times.get(j)))
                {
                    // swap arr[j] and arr[j+1]
                    LocalTime temp = times.get(j);
                    times.set(j, times.get(j+1));
                    times.set(j+1, temp);
                    swapped = true;
                }
            }
 
            // IF no two elements were
            // swapped by inner loop, then break
            if (swapped == false)
                break;
        }
        
        
        return times;
    }
    
    private static List<String> getDaysOfWeek(){
        
        List<String> days = new ArrayList<>();

        for (SchoolPeriod period : schoolPeriodList){
            if (!(days.contains(period.getDay()))){
                days.add(period.getDay());
            }
        }
           
        
        return days;
    }
    
    //generates and list of Teacher objects and figures out their frees and invigulation allowance
    private static void generateTeachers2() throws IOException{
        
        //get a list of the teachers from the database
        String SQL = String.format("SELECT * FROM \"ExamTime\".teachers WHERE schoolid='%s'", schoolid);
        List<String> teacherids = readFromDatabase.readDatabase(SQL, "teacherid");
        List<String> timetables = readFromDatabase.readDatabase(SQL, "timetable");
        List<String> daysWorking = readFromDatabase.readDatabase(SQL, "daysworking");
        
        //make a new list for the teacher objects
        List<Teacher> teachersList = new ArrayList<>();
        
        
        //these values will be got from the user
        Integer periodsInDay = 8;
        
        //iterate through all the teachers
        for (int i=0;i<teacherids.size();i++){
            //make the teacher object with the teacher id and the number of days they are working
            teachersList.add(new Teacher(Integer.valueOf(teacherids.get(i)), Integer.parseInt(daysWorking.get(i))));
        }
        int counter = 0;
        //iterate through each of the teachers
        for (Teacher teacher : teachersList){
            //calculate the teachers frees - pass in the teacher object just created and
            //their timetable path from database
            getTeachersFrees(timetables.get(counter), teacher);
            teacher.calculateInvigulation(periodsInDay, percentageInvigulation, lenOfPeriod);
            counter +=1;
        }
        
        generateDataInvigulationTimetable.teacherList = teachersList;
        
        
        
    }
    
    //work out when the teacher is free and add it to the teachers free list and the timeperiods teacher list
    private static void getTeachersFrees(String path, Teacher teacher) throws IOException {
        List<List<SchoolPeriod>> schoolTimetableHashTable = schoolTimetable;
        //create the file object with the path as parameters
        File excelFile = new File(path);
        //create an XSSF workbook object for XLSX excel file
        try (FileInputStream fis = new FileInputStream(excelFile); //create an XSSF workbook object for XLSX excel file
                XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            
            //get the first sheet of the excel file
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            for (int x = 3; x<11; x++){
                //iterate through all of the periods
                Row row = sheet.getRow(x);
                for (int i=0; i<6; i++){
                    //iterate through all of the days
                    Cell cell = row.getCell(i);
                    if (cell == null || cell.getCellType() == CellType.BLANK){
                        //the cell is empty therefore the teacher is free
                        //x = row = period -> this need to be subtracted two as it starts at 2
                        //column = day
                        //get the school periods [day][period]
                        SchoolPeriod period = schoolTimetableHashTable.get(i-1).get(x-3);
                        
                        //adds the School periods to the teachers free list
                        teacher.addFrees(period);
                        //adds teachers that are free to the correct School periods teacher free list
                        period.addTeacher(teacher);
                    }
                }
            }
        }
        
        
        
    }
    
    //generates and list of Teacher objects and figures out their frees and invigulation allowance
    /*
    private static void generateTeachers(){
        //list of the teachers names
        List<List<SchoolPeriod>> schoolTimetableHashTable = schoolTimetable;
        
        List<String> teacherNames = Arrays.asList("Mr Smith", "Mrs Bell", "Mr Maxwell", "Mrs Mosley", 
                "Miss White", "Mr Parkins", "Mrs Parks", "Mrs Pambakian", "Miss Greene", "Mr Stark", "Mrs Pollington", "Mr McCarthy",
                "Mr Turing", "Miss Sherlock");
        //list describing when the teachers are free
        String[][] frees = {
            {"Mr Smith, Miss White, Mr Maxwell, Mrs Bell, Mrs Pollington, Mr McCarthy", "Mr Maxwell, Mr Parkins, Mr Smith, Mrs Pambakian, Mrs Pollington, Mr McCarthy", "Mr Smith, Mrs Mosley, Mrs Bell, Miss White, Mrs Pollington, Mr McCarthy", "Mr Maxwell, Mrs Bell, Mr Smith, Mrs Parks, Mrs Pollington, Mr McCarthy", "Miss White, Mr Parkins, Mrs Parks, Mrs Pambakian, Mr Turing, Miss Sherlock", "Mr Smith, Miss White, Mrs Mosley, Mrs Bell, Mr Turing, Miss Sherlock", "Mrs Bell, Miss White, Mr Parkins, Mrs Parks, Mr Turing, Miss Sherlock", "Mr Parkins, Mr Smith, Mrs Pambakian, Miss Greene, Mr Turing, Miss Sherlock"},
            {"Mrs Bell, Mr Parkins, Mrs Parks, Miss Greene, Mrs Pollington, Mr McCarthy", "Mr Maxwell, Mrs Parks, Miss Greene, Mrs Pambakian, Mr Turing, Miss Sherlock",  "Mrs Mosley, Mrs Parks, Miss Greene, Mrs Pambakian, Mrs Pollington, Mr McCarthy","Mr Parkins, Miss White, Mr Maxwell, Mrs Parks, Mr Turing, Miss Sherlock", "Mr Maxwell, Miss Greene, Mrs Pambakian, Mrs Bell, Mr Turing, Miss Sherlock" ,"Miss White, Mrs Bell, Mr Parkins, Mrs Parks, Mr Turing, Miss Sherlock","Miss White, Mrs Bell, Miss Greene, Mrs Pambakian, Mrs Pollington, Mr McCarthy","Mr Smith, Mrs Parks, Miss Greene, Mrs Pambakian, Mrs Pollington, Mr McCarthy"},
            {"Mr Smith, Mrs Bell, Mr Parkins, Miss Greene, Mrs Pollington, Mr McCarthy", "Mr Parkins, Mrs Bell, Mr Maxwell, Mr Smith, Mrs Pollington, Mr McCarthy","Mrs Mosley, Mr Parkins, Mr Smith, Miss Greene, Mr Turing, Miss Sherlock", "Mrs Mosley, Miss Greene, Mrs Pambakian, Mr Stark, Mr Turing, Miss Sherlock","Mr Parkins, Miss Greene, Mrs Pambakian, Mr Stark, Mr Turing, Miss Sherlock","Mr Smith, Mrs Bell, Miss Greene, Mrs Pambakian, Mrs Pollington, Mr McCarthy","Mr Smith, Miss Greene, Mrs Pambakian, Mr Stark, Mr Turing, Miss Sherlock","Mr Maxwell, Mrs Parks, Miss Greene, Mrs Pambakian, Mrs Pollington, Mr McCarthy"},
            {"Mr Maxwell, Mr Smith, Miss Greene, Mrs Pambakian, Mrs Pollington, Mr McCarthy", "Mrs Mosley, Mrs Bell, Mr Parkins, Mr Smith, Mrs Pollington, Mr McCarthy","Mrs Mosley, Mr Smith, Mrs Bell, Mr Parkins, Mrs Pollington, Mr McCarthy","Mr Parkins, Miss Greene, Mrs Pambakian, Mr Stark, Mr Turing, Miss Sherlock","Mrs Bell, Mrs Parks, Miss Greene, Mrs Pambakian, Mr Turing, Miss Sherlock","Mr Smith, Mr Maxwell, Mrs Mosley, Mr Parkins, Mr Turing, Miss Sherlock","Mr Parkins, Mr Maxwell, Mrs Parks, Mrs Pambakian, Mr Turing, Miss Sherlock","Mrs Mosley, Miss Greene, Mrs Pambakian, Mr Stark, Mrs Pollington, Mr McCarthy"},
            {"Mrs Bell, Miss White, Mr Smith, Mrs Parks, Mrs Pollington, Mr McCarthy", "Miss White, Mr Smith, Mrs Bell, Mrs Pambakian, Mrs Pollington, Mr McCarthy","Mrs Mosley, Mr Smith, Mrs Bell, Mr Maxwell, Mr Turing, Miss Sherlock","Mr Parkins, Mr Maxwell, Miss Greene, Mrs Pambakian, Mr Turing, Miss Sherlock","Mr Maxwell, Miss Greene, Mrs Pambakian, Mr Stark, Mr Turing, Miss Sherlock","Mr Smith, Mr Maxwell, Miss White, Mrs Mosley, Mr Turing, Miss Sherlock", "Mrs Mosley, Miss Greene, Mrs Pambakian, Mr Stark, Mr Turing, Miss Sherlock","Mrs Bell, Miss White, Mr Smith, Mrs Parks, Mrs Pollington, Mr McCarthy"}
        };
        
        //makes a list of teacher objects
        //the index of the teacher object will be the same as that of their name
        List<Teacher> teachersList = new ArrayList<>();
        Integer id = 49;
        for (String i: teacherNames){
            teachersList.add(new Teacher(id++, 5));
        }
   
        teachersList.get(4).setDaysWorking(3);
        teachersList.get(9).setDaysWorking(3);
        
        //adds teachers that are free to the correct School periods teacher free list
        //adds the School periods to the teachers free list
        
        //iterate through all of the days
        for (int i=0; i<schoolTimetableHashTable.size();i++){
            //iterate through all of the timeperiods
            for (int x=0; x<schoolTimetableHashTable.get(i).size();x++){
                //get the teachers free at that time period
                String available = frees[i][x];
                //get the period which this free is in
                SchoolPeriod period = schoolTimetableHashTable.get(i).get(x);
                //slit the teachers so they are on their own
                String[] teachers = available.split(", ");
                //iterate through all the teachers in the period
                for (String teacher: teachers){
                    // find the index of the teacher
                    Integer positionOfTeacher = teacherNames.indexOf(teacher);
                    //add the free period to the teacher class
                    teachersList.get(positionOfTeacher).addFrees(period);
                    //add the teacher to the time period
                    period.addTeacher(teachersList.get(positionOfTeacher));
                }
            }
            
        }
        Integer periodsInDay = 8;
        
        //to be changed by the user
        
        teachersList = calculateTeachersInvigulations(teachersList, periodsInDay, percentageInvigulation, lenExamPerid);

        generateDataInvigulationTimetable.teacherList = teachersList;
        
    }
    
    //calculates how much invigulation the teachers can have
    private static List<Teacher> calculateTeachersInvigulations(List<Teacher> teacherList, Integer periodsInDay, float percentageInvigulation, float lenExamPerid){
        //calculates how many invigulation periods each teacher should cover
        for (Teacher t : teacherList){
            t.calculateInvigulation(periodsInDay, percentageInvigulation, lenExamPerid);
        }
        return teacherList;
    }
*/
    
    
    
}
