package com.vityarthi.dbms.service;
import com.vityarthi.dbms.config.DatabaseConnection; import java.sql.*;
public class ReportService {
 public int[] counts()throws SQLException{String q="SELECT (SELECT COUNT(*) FROM students),(SELECT COUNT(*) FROM courses),(SELECT COUNT(*) FROM enrollments)";try(Connection c=DatabaseConnection.getConnection();Statement s=c.createStatement();ResultSet r=s.executeQuery(q)){r.next();return new int[]{r.getInt(1),r.getInt(2),r.getInt(3)};}}
 public String buildReport()throws SQLException{StringBuilder b=new StringBuilder();int[] c=counts();b.append("SYSTEM SUMMARY\n==============================\nStudents    : ").append(c[0]).append("\nCourses     : ").append(c[1]).append("\nEnrollments : ").append(c[2]).append("\n\nDEPARTMENT-WISE STUDENTS\n==============================\n");String q="SELECT department,COUNT(*) total FROM students GROUP BY department ORDER BY total DESC,department";try(Connection cn=DatabaseConnection.getConnection();Statement s=cn.createStatement();ResultSet r=s.executeQuery(q)){while(r.next())b.append(String.format("%-25s %d%n",r.getString(1),r.getInt(2)));}return b.toString();}
 public void dashboard()throws SQLException{System.out.println(buildReport());}
 public void departmentReport()throws SQLException{}
}
