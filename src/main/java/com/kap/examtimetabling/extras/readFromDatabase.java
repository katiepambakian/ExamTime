/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kap.examtimetabling.extras;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author katiepambakian
 */
public class readFromDatabase {
    

    //the read from database will apply the command given by the user
    public static List<String> readDatabase(String SQL, String columnLabel){
        List<String> contense = new ArrayList<>();
         // details required to connect to the database
        String url = "jdbc:postgresql://localhost/postgres";
        String user = "postgres";
        String password = "";
        
        // estabilishes the connection to query data or return the error
        try (Connection conn = connect(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)){
            
            contense = returnContense(rs, columnLabel);
            
        }
        catch (SQLException ex){
            JOptionPane.showMessageDialog(null, 
                                  ex.getMessage(), 
                                  "Error", 
                                  JOptionPane.WARNING_MESSAGE);
        }
        return contense;
        
    }
    public static Connection connect(String url, String user, String password) {
        Connection conn = null;
        try{
            // creates a connection
            conn = DriverManager.getConnection(url, user, password);
        } catch(SQLException e){
            // print the error if it did not work
            JOptionPane.showMessageDialog(null, 
                                  e.getMessage(), 
                                  "Error", 
                                  JOptionPane.WARNING_MESSAGE);
        }
        //returns the connection so it can be used to access/write data
        return conn;
    }
    private static List<String> returnContense(ResultSet rs, String columnLabel) throws SQLException {
        // displays the timetable in a nice format
        List<String> contense = new ArrayList<>();
        
        while (rs.next()){
            contense.add(rs.getString(columnLabel));
        }
        return contense;
    }

}
