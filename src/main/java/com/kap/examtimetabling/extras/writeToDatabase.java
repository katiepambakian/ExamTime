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
public class writeToDatabase {
    
    //make a connection to the database
    private static Connection connect(String url, String user, String password) {
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
    //the method within the class that will be called from other classes
    //takes the SQL statement/query as a parameter
    public static void writeToDatabase(String SQL){
        String url = "jdbc:postgresql://localhost/postgres";
        String user = "postgres";
        String password = "";
        
        // estabilishes the connection to query data or return the error
        try (Connection conn = connect(url, user, password);
                Statement stmt = conn.createStatement();){
                stmt.executeUpdate(SQL);
        }
        catch (SQLException ex){
            //error message if the connection/query failed
            JOptionPane.showMessageDialog(null, 
                                  ex.getMessage(), 
                                  "Error", 
                                  JOptionPane.WARNING_MESSAGE);
        }
    }
   
    
}
