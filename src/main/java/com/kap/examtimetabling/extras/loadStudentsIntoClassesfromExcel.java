/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kap.examtimetabling.extras;

import com.kap.examtimetabling.domain.Subject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
public class loadStudentsIntoClassesfromExcel {
    public static void main(String[] args) throws IOException {
        getSubjectStudentList();
    }

    public static void getSubjectStudentList() throws IOException{
        ArrayList<String> contenseOfFile = getContenceOfFile();
        for (String i : contenseOfFile){
            //System.out.println(i);
        }
        //Hashtable<String, ArrayList<String>> studies = getDictionary(contenseOfFile);
        //List<Subject> subjectStudentList = addToClasses(studies);
        //writeToDatabase(subjectStudentList);
       //return subjectStudentList;
    }
    
    private static void writeToDatabase(List<Subject> subjectStudentList){
        
        for (int i=1; i <(subjectStudentList.size());i++){
            
        }
        
    }
    
    private static Hashtable<String, ArrayList<String>> getDictionary(ArrayList<String> contenseOfFile){
        //list of subject names - strings
        //String[] subjectNames = {"0","1","2","3","4","5"
        //,"6","7","8","9","10","11","12"
       // ,"13","14","15","16","17","18","19"};
        String[] subjectNames = {"physics","biology","classics","psychology","re","maths"
        ,"politics","chemistry","further_maths","geography","spanish","computing","buisness"
        ,"economics","art","english","food","french","pe","history"};
       
        //instantiating all of the subject lists - these will hold the students
        ArrayList<String> physics= new ArrayList<>();
        ArrayList<String> biology= new ArrayList<>();
        ArrayList<String> classics= new ArrayList<>();
        ArrayList<String> psychology = new ArrayList<>();
        ArrayList<String> re= new ArrayList<>();
        ArrayList<String> maths= new ArrayList<>();
        ArrayList<String> politics= new ArrayList<>();
        ArrayList<String> chemistry= new ArrayList<>();
        ArrayList<String> further_maths= new ArrayList<>();
        ArrayList<String> geography= new ArrayList<>();
        ArrayList<String> spanish= new ArrayList<>();
        ArrayList<String> computing= new ArrayList<>();
        ArrayList<String> buisness= new ArrayList<>();
        ArrayList<String> economics= new ArrayList<>();
        ArrayList<String> art= new ArrayList<>();
        ArrayList<String> english= new ArrayList<>();
        ArrayList<String> food= new ArrayList<>();
        ArrayList<String> french= new ArrayList<>();
        ArrayList<String> pe= new ArrayList<>();
        ArrayList<String> history= new ArrayList<>();
        
        
        //add all the student lists to an array
        //added in the same order that the subejct names are in
        ArrayList<ArrayList<String>> subjects = new ArrayList<>();
        subjects.add(physics);
        subjects.add(biology);
        subjects.add(classics);
        subjects.add(psychology);
        subjects.add(re);
        subjects.add(maths);
        subjects.add(politics);
        subjects.add(chemistry);
        subjects.add(further_maths);
        subjects.add(geography);
        subjects.add(spanish);
        subjects.add(computing);
        subjects.add(buisness);
        subjects.add(economics);
        subjects.add(art);
        subjects.add(english);
        subjects.add(food);
        subjects.add(french);
        subjects.add(pe);
        subjects.add(history);
        
        //create dictionary to hold subject and students
        //this will allow the name of the subject to be found and the student to be added easily
        Hashtable<String, ArrayList<String>> studies = new Hashtable<String, ArrayList<String>>();
        
        //add the subjects to the dictionary
        for (int z=0; z<(subjectNames.length);z++){
            studies.put(subjectNames[z], subjects.get(z));
        }
        
        //loop for the every item in contense (row of sheet)
        for (int i=0; i<(contenseOfFile.size()-1); i++) {
            //get the element from content
            String temp = contenseOfFile.get(i);
            //split it into indiviual items
            String parts[] = temp.split(",");            
            
            //get the students name
            String studentName = parts[0];
            
            //adds the students name to the subject list 
            for(int x=1; x<((parts.length)); x++){
                //get the array depending on the subject name
                // then adds the student to the array
                (studies.get(parts[x])).add(studentName);
                
            }
        }
        return studies;
    }
    
    private static ArrayList<String> getContenceOfFile() throws IOException {
        //the path to the excel file where the list is
        String path = "/Users/katiepambakian/Programming Project/teacherTimetable1.xlsx";
        
        //create the file object with the path as parameters
        File excelFile = new File(path);
        FileInputStream fis = new FileInputStream(excelFile);
        
        //create an XSSF workbook object for XLSX excel file
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        
        //get the first sheet of the excel file
        XSSFSheet sheet = workbook.getSheetAt(0);
        
        //iterate each row of the sheet
        Iterator<Row> rowIt = sheet.iterator();
        
        //create a new array to hold the contense of the whole file
        ArrayList<String> contense = new ArrayList<>();
        
        //keep looping until the file has finished i.e no more rows
        while(rowIt.hasNext()){
            //...select the next row in the file
            Row row = rowIt.next();
            
            
            //iterate through all the not null cells on the row
            Iterator<Cell> cellIterator = row.cellIterator();
            
            //a variable to hold the new items
            String contentOfCell = "";
            
            //keep looping until the row has finished i.e. no more columns in the row 
            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                //makes the cell value a string
                String value = cell.toString();
                //checks if the cell is empty
                if (value.isEmpty()){
                    //there is nothing else to add so the while loop breaks
                    break;
                }else{
                    //adds the cell value to the array
                    contentOfCell = contentOfCell + value + ",";
                }
                  
            }
            //adds all the items in the row to the contense fo the file 
            //it must be converted to a string first
            contense.add(contentOfCell);
        }
        
        workbook.close();
        fis.close();
        
        return contense;
    }

    private static List<Subject> addToClasses(Hashtable<String, ArrayList<String>> studies) {
        
        List<Subject> subjectClassList = new ArrayList<>();
        for (String i : studies.keySet()){
            //subjectClassList.add(new Subject(i, studies.get(i)));
            //String SQL = "INSERT INTO \"ExamTime\".studies (studentid, subjectid) VALUES (1, 2);";
           
            //writeToDatabase.writeToDatabase(SQL);
        }

        return subjectClassList;
    }
}
