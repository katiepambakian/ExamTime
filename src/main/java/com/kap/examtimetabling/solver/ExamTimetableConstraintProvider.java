/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kap.examtimetabling.solver;
import java.time.LocalDate;
import com.kap.examtimetabling.domain.Exam;
import com.kap.examtimetabling.domain.Timeslot;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;
import static org.optaplanner.core.api.score.stream.Joiners.greaterThan;
import static org.optaplanner.core.api.score.stream.Joiners.lessThan;
/**
 *
 * @author katiepambakian
 */
public class ExamTimetableConstraintProvider implements ConstraintProvider{
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory){
        return new Constraint[]{
            //hard constraints
            
            //make sure the same subject is not in the same timeslot
            subjectConflict(constraintFactory),
            //make sure the students do not sit an exam at the same time
            studentConflict(constraintFactory),
            //todo - make sure that rooms do not exceed there capacity
            roomConflict(constraintFactory),
            //make sure subjects are not on the same day
            sameSubjectOnSameDay(constraintFactory),
            //the timeperiod can fit the exam in
            timeslotLength(constraintFactory),
            //soft constraints
            sameSubjectExamProximity(constraintFactory)
        };
    }
    private Constraint subjectConflict(ConstraintFactory constraintFactory){
        // you can only have one subject per exam slot
        // selects a lesson and pairs it with another one
        return constraintFactory
                .forEach(Exam.class)
                .join(Exam.class,
                        // ... in the same timeslot ...
                        Joiners.equal(Exam::getTimeslot),
                        // ... with the same subject ...
                        Joiners.equal(Exam::getSubject),
                        // ... and the pair is unique (different id, no reverse pairs) ...
                        Joiners.lessThan(Exam::getId))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Subject conflict");
    }
    private Constraint studentConflict(ConstraintFactory constraintFactory){
        // students can only sit one exam at the same time
        // selects an exam and pairs it with another one
        return constraintFactory
                .forEach(Exam.class)
                .join(Exam.class,
                        //...in the same timeslot ...
                        Joiners.equal(Exam::getTimeslot))
                //...has the same students ...
                .filter((exam1, exam2) -> {
                    if(exam1.getStudentsList().size() > exam2.getStudentsList().size()){
                    // this will return true if the same students are in the same timeslot
                        for(String i: exam1.getStudentsList()){
                            if ((exam2.getStudentsList().contains(i))){
                                return true;
                            }
                        }
                        return false;
                    }else{
                        for(String i: exam2.getStudentsList()){
                            if ((exam1.getStudentsList().contains(i))){
                                return true;
                            }
                        }
                        return false;
                    }
                })     
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Student conflict");
    }
    
    private Constraint roomConflict(ConstraintFactory constraintFactory){
    // exams can only be in one room at onces
    // TODO - allow more than one exam in the room at once 
    // as long as the total is not exceeded and everyone sitting one exam
    // is in the same room
    return constraintFactory
            .forEach(Exam.class)
            .join(Exam.class,
                    //...in the same timeslot ...
                    Joiners.equal(Exam::getTimeslot),
                    //...has the same students ...
                    Joiners.equal(Exam::getRoom))
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("Room conflict");
    }
    
    //makes sure that the same subjects exams are not close to each other
    private Constraint sameSubjectExamProximity(ConstraintFactory constraintFactory){
        return constraintFactory
                .forEach(Exam.class)
                .join(Exam.class)
                .filter((exam1, exam2) -> {
                    // this will return true if the exams are adacate distances appart
                    LocalDate date1 = exam1.getDateInDateForm();
                    LocalDate date2 = exam2.getDateInDateForm();
                    return !(Exam.daysBetween(date1, date2) < 3);
                })
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Exams are too close together");
    }
    
    private Constraint timeslotLength(ConstraintFactory constraintFactory){
        return constraintFactory
                .forEach(Timeslot.class)
                .join(Exam.class,
                        greaterThan(Timeslot::getLength, Exam::getLength))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Exam is too long for timeperiod");
    }
    
    //prevents subjects from being on the same day
    private Constraint sameSubjectOnSameDay(ConstraintFactory constraintFactory){
        return constraintFactory
                .forEach(Exam.class)
                .join(Exam.class,
                        //gets the subject on the same day
                        Joiners.equal(Exam::getSubject),
                        Joiners.equal(Exam::getDate),
                        Joiners.lessThan(Exam::getId))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Exams from same subject on the same day");
                    
    }
    
     

}
