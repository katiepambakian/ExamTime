/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.kap.UserInterface;

import com.kap.examtimetabling.extras.readFromDatabase;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author katiepambakian
 */
public class search extends javax.swing.JFrame {

    /**
     * Creates new form search
     */
    public search() {
        initComponents();
        start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        parent = new javax.swing.JPanel();
        MainMenu = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        studentsPerExam = new javax.swing.JPanel();
        searchBar = new javax.swing.JPanel();
        examName = new javax.swing.JTextField();
        searchBt = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Subject = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        room = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        examnamelookuptable = new javax.swing.JTable();
        studentsPerTimeperiod = new javax.swing.JPanel();
        searchBar1 = new javax.swing.JPanel();
        date = new javax.swing.JTextField();
        searchBt1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        StartTime = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        roomTf = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        examseries = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        timeperiodstudents = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SearchMenu");
        setPreferredSize(new java.awt.Dimension(394, 605));
        setResizable(false);

        parent.setMaximumSize(new java.awt.Dimension(394, 605));
        parent.setPreferredSize(new java.awt.Dimension(394, 605));
        parent.setSize(new java.awt.Dimension(394, 605));
        parent.setLayout(new java.awt.CardLayout());

        MainMenu.setBackground(new java.awt.Color(0, 51, 102));
        MainMenu.setMaximumSize(new java.awt.Dimension(394, 605));
        MainMenu.setMinimumSize(new java.awt.Dimension(394, 605));
        MainMenu.setPreferredSize(new java.awt.Dimension(394, 605));

        jButton1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jButton1.setText("Students for an exam");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jButton2.setText("All Students in a timeperiod");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Helvetica Neue", 1, 34)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("GENERATE REGISTER");

        javax.swing.GroupLayout MainMenuLayout = new javax.swing.GroupLayout(MainMenu);
        MainMenu.setLayout(MainMenuLayout);
        MainMenuLayout.setHorizontalGroup(
            MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainMenuLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(MainMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                .addContainerGap())
        );
        MainMenuLayout.setVerticalGroup(
            MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainMenuLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(34, 34, 34)
                .addComponent(jButton2)
                .addContainerGap(408, Short.MAX_VALUE))
        );

        parent.add(MainMenu, "card4");

        studentsPerExam.setBackground(new java.awt.Color(255, 255, 255));
        studentsPerExam.setPreferredSize(new java.awt.Dimension(394, 605));

        searchBar.setBackground(new java.awt.Color(0, 51, 102));

        searchBt.setText("SEARCH");
        searchBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Exam name");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Subject");

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("REGISTER GENERATOR");

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Room Name (optional)");

        jButton3.setText("BACK");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchBarLayout = new javax.swing.GroupLayout(searchBar);
        searchBar.setLayout(searchBarLayout);
        searchBarLayout.setHorizontalGroup(
            searchBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchBarLayout.createSequentialGroup()
                .addGroup(searchBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchBarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(searchBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(searchBt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(searchBarLayout.createSequentialGroup()
                                .addGroup(searchBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(searchBarLayout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addGroup(searchBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(examName, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(searchBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(Subject, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2)))
                                    .addGroup(searchBarLayout.createSequentialGroup()
                                        .addComponent(jButton3)
                                        .addGap(33, 33, 33)
                                        .addComponent(jLabel3)))
                                .addGap(0, 11, Short.MAX_VALUE))))
                    .addGroup(searchBarLayout.createSequentialGroup()
                        .addGroup(searchBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(searchBarLayout.createSequentialGroup()
                                .addGap(109, 109, 109)
                                .addComponent(room, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(searchBarLayout.createSequentialGroup()
                                .addGap(127, 127, 127)
                                .addComponent(jLabel4)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        searchBarLayout.setVerticalGroup(
            searchBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchBarLayout.createSequentialGroup()
                .addGroup(searchBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchBarLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel3))
                    .addGroup(searchBarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(searchBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(searchBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(examName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Subject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(room, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchBt)
                .addGap(15, 15, 15))
        );

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        examnamelookuptable.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        examnamelookuptable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STUDENT", "ACCESS", "ROOM", "Exam"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(examnamelookuptable);

        javax.swing.GroupLayout studentsPerExamLayout = new javax.swing.GroupLayout(studentsPerExam);
        studentsPerExam.setLayout(studentsPerExamLayout);
        studentsPerExamLayout.setHorizontalGroup(
            studentsPerExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(searchBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        studentsPerExamLayout.setVerticalGroup(
            studentsPerExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentsPerExamLayout.createSequentialGroup()
                .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        parent.add(studentsPerExam, "card3");

        studentsPerTimeperiod.setBackground(new java.awt.Color(255, 255, 255));
        studentsPerTimeperiod.setMaximumSize(new java.awt.Dimension(394, 605));
        studentsPerTimeperiod.setMinimumSize(new java.awt.Dimension(394, 605));
        studentsPerTimeperiod.setPreferredSize(new java.awt.Dimension(394, 605));

        searchBar1.setBackground(new java.awt.Color(0, 51, 102));

        searchBt1.setText("SEARCH");
        searchBt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBt1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("DATE");

        jLabel6.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("START TIME");

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("REGISTER GENERATOR");

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Room Name (optional)");

        jButton4.setText("BACK");
        jButton4.setToolTipText("");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Examseries id (optional)");

        javax.swing.GroupLayout searchBar1Layout = new javax.swing.GroupLayout(searchBar1);
        searchBar1.setLayout(searchBar1Layout);
        searchBar1Layout.setHorizontalGroup(
            searchBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchBar1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchBt1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(searchBar1Layout.createSequentialGroup()
                        .addGroup(searchBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(searchBar1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(searchBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(date, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel8)
                                    .addComponent(roomTf))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(searchBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(StartTime, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel10)
                                    .addComponent(examseries)))
                            .addGroup(searchBar1Layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addGap(33, 33, 33)
                                .addComponent(jLabel7)))
                        .addGap(0, 11, Short.MAX_VALUE)))
                .addContainerGap())
        );
        searchBar1Layout.setVerticalGroup(
            searchBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchBar1Layout.createSequentialGroup()
                .addGroup(searchBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchBar1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel7))
                    .addGroup(searchBar1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(searchBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(searchBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StartTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(searchBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(searchBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roomTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(examseries, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(searchBt1)
                .addGap(15, 15, 15))
        );

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));

        timeperiodstudents.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        timeperiodstudents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STUDENT", "ACCESS", "ROOM", "EXAM"
            }
        ));
        jScrollPane2.setViewportView(timeperiodstudents);

        javax.swing.GroupLayout studentsPerTimeperiodLayout = new javax.swing.GroupLayout(studentsPerTimeperiod);
        studentsPerTimeperiod.setLayout(studentsPerTimeperiodLayout);
        studentsPerTimeperiodLayout.setHorizontalGroup(
            studentsPerTimeperiodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(searchBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        studentsPerTimeperiodLayout.setVerticalGroup(
            studentsPerTimeperiodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentsPerTimeperiodLayout.createSequentialGroup()
                .addComponent(searchBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE))
        );

        parent.add(studentsPerTimeperiod, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(parent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(parent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtActionPerformed
        // TODO add your handling code here:
        //check that the boxes where not left empty
        
        String r = room.getText();
        String exam = examName.getText();
        String subject = Subject.getText();
        
        //check that neither box has been left empty
        if (!(exam.equals("") || subject.equals(""))){
            if (r.equals("")){
                
                //room has been left empty - nos specied which rooom
                
                //get the details from the database
                String SQl = String.format("select \"ExamTime\".students.firstname, " +
                    "\"ExamTime\".students.lastname, \"ExamTime\".rooms.roomname , \"ExamTime\".exams.\"name\" " +
                    "from \"ExamTime\".studies " +
                    "join \"ExamTime\".students on studies.studentid = students.studentid " +
                    "join \"ExamTime\".exams on studies.subjectname = exams.subjectname " +
                    "join \"ExamTime\".rooms on rooms.roomid = exams.roomid " +
                    "where studies.subjectname = '%s' and exams.\"name\" = '%s';", subject, exam);
                List<String> firstname = (readFromDatabase.readDatabase(SQl, "firstname"));
                List<String> lastname = (readFromDatabase.readDatabase(SQl, "lastname"));
                List<String> roomname = (readFromDatabase.readDatabase(SQl, "roomname"));
                List<String> name = (readFromDatabase.readDatabase(SQl, "name"));
                
                //create table model
                DefaultTableModel model = (DefaultTableModel) examnamelookuptable.getModel();
                //clear table
                model.setRowCount(0);
                
                //write the details to the table
                for (int i = 0; i<firstname.size();i++){
                    Object[] row = {(firstname.get(i)+" "+ lastname.get(i)), "", roomname.get(i), name.get(i)};
                    model.addRow(row);
                }
                
            }else{
                //the room has been speicied
                
                //get the details from the database
                String SQl = String.format("select \"ExamTime\".students.firstname, " +
                    "\"ExamTime\".students.lastname, \"ExamTime\".rooms.roomname , \"ExamTime\".exams.\"name\" " +
                    "from \"ExamTime\".studies " +
                    "join \"ExamTime\".students on studies.studentid = students.studentid " +
                    "join \"ExamTime\".exams on studies.subjectname = exams.subjectname " +
                    "join \"ExamTime\".rooms on rooms.roomid = exams.roomid " +
                    "where studies.subjectname = '%s' and exams.\"name\" = '%s' and rooms.roomname = '%s';", subject, exam, r);
                List<String> firstname = (readFromDatabase.readDatabase(SQl, "firstname"));
                List<String> lastname = (readFromDatabase.readDatabase(SQl, "lastname"));
                List<String> roomname = (readFromDatabase.readDatabase(SQl, "roomname"));
                List<String> name = (readFromDatabase.readDatabase(SQl, "name"));
                
                //create table model
                DefaultTableModel model = (DefaultTableModel) examnamelookuptable.getModel();
                //clear table
                model.setRowCount(0);
                
                //write the details to the table
                for (int i = 0; i<firstname.size();i++){
                    Object[] row = {(firstname.get(i)+" "+ lastname.get(i)), "", roomname.get(i), name.get(i)};
                    model.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_searchBtActionPerformed

    private void searchBt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBt1ActionPerformed
        // TODO add your handling code here:
        //check that the boxes where not left empty
        
        String r = roomTf.getText();
        String d = date.getText();
        String start = StartTime.getText();
        String examseriesID=examseries.getText();
        String newexamseriesID;
        //if the examseries has not been filled set it to a wield card (display all)
        if (examseriesID.equals("")){
            newexamseriesID = "notnull";
        }else{
            newexamseriesID = "="+examseriesID;
        }
        
        //check that neither box has been left empty
        if (!(d.equals("") || start.equals(""))){
            //make the date into the date format
            try{
                LocalDate tdate = (LocalDate.parse(d));
                DateTimeFormatter formaterDate = DateTimeFormatter.ofPattern("yyy-MM-dd");
                String dateFormated = tdate.format(formaterDate);

                DateTimeFormatter formater = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalTime startUnfomrated = (LocalTime.parse(start));
                String startFormated = startUnfomrated.format(formater);
              
                
                if (r.equals("")){
                    //room has been left empty - nos specied which rooom
                    try{
                        //get the timeperiod id
                        String SQl1 = String.format("select \"ExamTime\".timeslots.timeslotid from \"ExamTime\".timeslots where starttime = '%s' and "
                                + "\"date\" = '%s' and examperiodid %s;", startFormated, dateFormated, newexamseriesID);
                        List<String> timeslotid = (readFromDatabase.readDatabase(SQl1, "timeslotid"));
                        
                        if (!timeslotid.isEmpty()){
                            for (String timeslot: timeslotid){
                                //get the details from the database
                                String SQl = String.format("""
                                                           select "ExamTime".students.firstname, "ExamTime".students.lastname , 
                                                           "ExamTime".rooms.roomname, "ExamTime".exams."name" 
                                                           from "ExamTime".studies 
                                                           join "ExamTime".students on studies.studentid = students.studentid
                                                           join "ExamTime".exams on studies.subjectname = exams.subjectname
                                                           join "ExamTime".rooms on rooms.roomid = exams.roomid
                                                           where "ExamTime".exams.timeslotid =%s and exams.examperiodid %s
                                                           order by roomname;""", timeslot, newexamseriesID);
                                
                                List<String> firstname = (readFromDatabase.readDatabase(SQl, "firstname"));
                                List<String> lastname = (readFromDatabase.readDatabase(SQl, "lastname"));
                                List<String> roomname = (readFromDatabase.readDatabase(SQl, "roomname"));
                                List<String> name = (readFromDatabase.readDatabase(SQl, "name"));
                                
                                //create table model
                                DefaultTableModel model = (DefaultTableModel) timeperiodstudents.getModel();
                                //clear table
                                model.setRowCount(0);

                                //write the details to the table
                                for (int i = 0; i<firstname.size();i++){
                                    Object[] row = {(firstname.get(i)+" "+ lastname.get(i)), "",roomname.get(i), name.get(i)};
                                    
                                    model.addRow(row);
                                }
                            }
                        }
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null, 
                                  ex, 
                                  "Error", 
                                  JOptionPane.WARNING_MESSAGE);
                        
                    }


                }else{
                    //the room has been speicied

                    //get the details from the database
                    //get the timeperiod id
                    String SQl1 = String.format("select \"ExamTime\".timeslots.timeslotid " +
                        "from \"ExamTime\".timeslots " +
                        "where starttime = '%s' and \"date\" = '%s';", start, d);
                    List<String> timeslotid = (readFromDatabase.readDatabase(SQl1, "timeslotid"));


                    //get the details from the database
                    String SQl = String.format("select \"ExamTime\".students.firstname, " +
                        "\"ExamTime\".students.lastname, \"ExamTime\".rooms.roomname , \"ExamTime\".exams.\"name\" " +
                        "from \"ExamTime\".studies " +
                        "join \"ExamTime\".students on studies.studentid = students.studentid " +
                        "join \"ExamTime\".exams on studies.subjectname = exams.subjectname " +
                        "join \"ExamTime\".rooms on rooms.roomid = exams.roomid " +
                        "where exams.timeslotid =%s and rooms.roomname ='%s'", timeslotid.get(0), r);

                    List<String> firstname = (readFromDatabase.readDatabase(SQl, "firstname"));
                    List<String> lastname = (readFromDatabase.readDatabase(SQl, "lastname"));
                    List<String> roomname = (readFromDatabase.readDatabase(SQl, "roomname"));
                    List<String> name = (readFromDatabase.readDatabase(SQl, "name"));

                    //create table model
                    DefaultTableModel model = (DefaultTableModel) timeperiodstudents.getModel();
                    //clear table
                    model.setRowCount(0);

                    //write the details to the table
                    for (int i = 0; i<firstname.size();i++){
                        Object[] row = {(firstname.get(i)+" "+ lastname.get(i)), "", roomname.get(i), name.get(i)};
                        model.addRow(row);
                    }
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, 
                                  "Please make sure that the data and time are in the correct format", 
                                  "Formatting Error", 
                                  JOptionPane.WARNING_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null, 
                                  "Please enter all the data", 
                                  "Error", 
                                  JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_searchBt1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        parent.removeAll();
        parent.add(studentsPerExam);
        parent.repaint();
        parent.revalidate();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        parent.removeAll();
        parent.add(studentsPerTimeperiod);
        parent.repaint();
        parent.revalidate();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        parent.removeAll();
        parent.add(MainMenu);
        parent.repaint();
        parent.revalidate();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        parent.removeAll();
        parent.add(MainMenu);
        parent.repaint();
        parent.revalidate();
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainMenu;
    private javax.swing.JTextField StartTime;
    private javax.swing.JTextField Subject;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField date;
    private javax.swing.JTextField examName;
    private javax.swing.JTable examnamelookuptable;
    private javax.swing.JTextField examseries;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel parent;
    private javax.swing.JTextField room;
    private javax.swing.JTextField roomTf;
    private javax.swing.JPanel searchBar;
    private javax.swing.JPanel searchBar1;
    private javax.swing.JButton searchBt;
    private javax.swing.JButton searchBt1;
    private javax.swing.JPanel studentsPerExam;
    private javax.swing.JPanel studentsPerTimeperiod;
    private javax.swing.JTable timeperiodstudents;
    // End of variables declaration//GEN-END:variables
    public void start(){
        parent.removeAll();
        parent.add(MainMenu);
        parent.repaint();
        parent.revalidate();
    }
}
