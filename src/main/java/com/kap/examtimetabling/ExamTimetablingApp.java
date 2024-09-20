/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.kap.examtimetabling;
//imports libraries which will allow for the date 
import java.time.DayOfWeek;
import java.time.LocalTime;

//importing the classes in other packages of this project
import com.kap.examtimetabling.domain.Exam;
import com.kap.examtimetabling.domain.Room;
import com.kap.examtimetabling.domain.Timeslot;
import com.kap.examtimetabling.domain.ExamTimetable;
import com.kap.examtimetabling.domain.Subject;
import com.kap.examtimetabling.extras.generateDataFromDatabase;
import static com.kap.examtimetabling.extras.loadStudentsIntoClassesfromExcel.getSubjectStudentList;
import com.kap.examtimetabling.extras.readFromDatabase;
import com.kap.examtimetabling.extras.writeToDatabase;

//import the constraint provider
import com.kap.examtimetabling.solver.ExamTimetableConstraintProvider;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author katiepambakian
 */
public class ExamTimetablingApp {
    
    public static int examPeriodid;
    public static String schoolid;
    
    // logger to see what optaplanner is doing
    private static final Logger LOGGER = LoggerFactory.getLogger(ExamTimetablingApp.class);

    public static void main(String[] args) throws Exception {
        
        SolverFactory<ExamTimetable> solverFactory = SolverFactory.create(new SolverConfig()
            //registers the planning solution
            .withSolutionClass(ExamTimetable.class)
            // registers the planning entity
            .withEntityClasses(Exam.class)
             //registers the constraint provider
            .withConstraintProviderClass(ExamTimetableConstraintProvider.class)
            // this will stop the program after 10 seconds 
            .withTerminationSpentLimit(Duration.ofSeconds(10))
        );  
        
        ExamTimetable problem = generateDate();
        
        
        Solver<ExamTimetable> solver = solverFactory.buildSolver();
        ExamTimetable solution = solver.solve(problem);
        
        sendToDatabase(solution);
        
        printTimetable(solution);
        
        
    }
    //main method
    public static ExamTimetable generateExamTimetable(Integer examPeriodidP, String schoolidP, Integer duration) throws IOException{

            examPeriodid = examPeriodidP;
            schoolid = schoolidP;

            SolverFactory<ExamTimetable> solverFactory = SolverFactory.create(new SolverConfig()
                //registers the planning solution
                .withSolutionClass(ExamTimetable.class)
                // registers the planning entity
                .withEntityClasses(Exam.class)
                 //registers the constraint provider
                .withConstraintProviderClass(ExamTimetableConstraintProvider.class)
                // this will stop the program after 10 seconds 
                .withTerminationSpentLimit(Duration.ofSeconds(duration))
            );  

            ExamTimetable problem = generateDate();

            Solver<ExamTimetable> solver = solverFactory.buildSolver();
            ExamTimetable solution = solver.solve(problem);

            sendToDatabase(solution);
            printTimetable(solution);
            return solution;
        
        
    }

    private static ExamTimetable generateDate() throws IOException{
        generateDataFromDatabase.generateData(examPeriodid, schoolid);
        List<Timeslot> timeslotList = generateDataFromDatabase.getTimeslots();
        List<Exam> examList = generateDataFromDatabase.getExams();
        List<Room> roomList = generateDataFromDatabase.getRooms();
        
        return new ExamTimetable(timeslotList, roomList, examList);
    }
    
    private static void sendToDatabase(ExamTimetable timetable){
        //get the list of timeslots and exams from the solution
        List<Exam> examList = timetable.getExamList();
        
        //iterate through all of the exams to get the timeslot and room they have been assigned to
        for (Exam x: examList){
            //the timeslot for the exam
            Timeslot timeslot = x.getTimeslot();
            //the room of the exams
            Room room = x.getRoom();
            
            //update the database with the room and timeslot for each exam
            String SQL = String.format("UPDATE \"ExamTime\".exams SET roomid=%d, timeslotid=%d WHERE examid=%d;",
                    room.getID(), timeslot.getID(), x.getId());
            //send the query to the database
            writeToDatabase.writeToDatabase(SQL);
        }
    }

    private static void printTimetable(ExamTimetable timeTable){
        //display timetable in readable format
        LOGGER.info("");
        List<Room> roomList = timeTable.getRoomList();
        List<Exam> lessonList = timeTable.getExamList();
        Map<Timeslot, Map<Room, List<Exam>>> lessonMap = lessonList.stream()
                .filter(exam -> exam.getTimeslot() != null && exam.getRoom() != null)
                .collect(Collectors.groupingBy(Exam::getTimeslot, Collectors.groupingBy(Exam::getRoom)));
        LOGGER.info("|            | " + roomList.stream()
                .map(room -> String.format("%-10s", room.getName())).collect(Collectors.joining(" | ")) + " |");
        LOGGER.info("|" + "------------|".repeat(roomList.size() + 1));
        for (Timeslot timeslot : timeTable.getTimeslotList()) {
            List<List<Exam>> cellList = roomList.stream()
                    .map(room -> {
                        Map<Room, List<Exam>> byRoomMap = lessonMap.get(timeslot);
                        if (byRoomMap == null) {
                            return Collections.<Exam>emptyList();
                        }
                        List<Exam> cellLessonList = byRoomMap.get(room);
                        if (cellLessonList == null) {
                            return Collections.<Exam>emptyList();
                        }
                        return cellLessonList;
                    })
                    .collect(Collectors.toList());
            LOGGER.info("| " + String.format("%-10s",
                    timeslot.getDate().toString() + " " + timeslot.getStartTime()) + " | "
                    + cellList.stream().map(cellLessonList -> String.format("%-10s",
                            cellLessonList.stream().map(Exam::getSubject).collect(Collectors.joining(", "))))
                            .collect(Collectors.joining(" | "))
                    + " |");
            LOGGER.info("|            | "
                    + cellList.stream().map(cellLessonList -> String.format("%-10s",
                            cellLessonList.stream().map(Exam::getLengthToString).collect(Collectors.joining(", "))))
                            .collect(Collectors.joining(" | "))
                    + " |");
            LOGGER.info("|            | "
                    + cellList.stream().map(cellLessonList -> String.format("%-10s",
                            cellLessonList.stream().map(Exam::getName).collect(Collectors.joining(", "))))
                            .collect(Collectors.joining(" | "))
                    + " |");
            LOGGER.info("|" + "------------|".repeat(roomList.size() + 1));
        }
        List<Exam> unassignedLessons = lessonList.stream()
                .filter(lesson -> lesson.getTimeslot() == null || lesson.getRoom() == null)
                .collect(Collectors.toList());
        if (!unassignedLessons.isEmpty()) {
            LOGGER.info("");
            LOGGER.info("Unassigned lessons");
            for (Exam exam : unassignedLessons) {
                LOGGER.info("  " + exam.getSubject() + " - " + exam.getRoom() + " - " + exam.getSubject() + " - " + exam.getLength().toString());
            }
        }
    }
    
}