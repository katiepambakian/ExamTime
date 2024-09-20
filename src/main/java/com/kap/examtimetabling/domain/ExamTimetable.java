/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kap.examtimetabling.domain;

import java.util.List;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

/**
 *
 * @author katiepambakian
 */
@PlanningSolution
public class ExamTimetable {
    //connect the @PlanningVariable with the @ValueRangeProvider,
    //by matching the value of the id property with the value of the 
    //valueRangeProviderRefs property of the @PlanningVariable 
    //annotation in the Exam class.
    @ValueRangeProvider(id = "timeslotRange")
    @ProblemFactCollectionProperty
    private List<Timeslot> timeslotList;
    @ValueRangeProvider(id = "roomRange")
    @ProblemFactCollectionProperty
    private List<Room> roomList;
    @PlanningEntityCollectionProperty
    private List<Exam> examList;
    
    //variable holding the score of the solution
    //specifies the quality of the solution
    @PlanningScore
    private HardSoftScore score;
    
    //constructors
    public ExamTimetable(){
        
    }
    public ExamTimetable(List<Timeslot> timeslotList, List<Room> roomList, List<Exam> examList){
        this.timeslotList = timeslotList;
        this.roomList = roomList;
        this.examList = examList;
    }
    //getters
    public List<Timeslot> getTimeslotList(){
        return timeslotList;
    }
    public List<Room> getRoomList(){
        return roomList;
    }
    public List<Exam> getExamList(){
        return examList;
    }
    public HardSoftScore getScore(){
        return score;
    }
    

    
}
