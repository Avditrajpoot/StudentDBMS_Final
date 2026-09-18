package com.vityarthi.dbms.dao;
import com.vityarthi.dbms.config.DatabaseConnection;
import com.vityarthi.dbms.model.Student;
import java.sql.*; import java.util.*;
public class StudentDAO {
 public void add(Student s) throws SQLException { String q="INSERT INTO students(roll_no,name,email,department,semester) VALUES(?,?,?,?,?)"; try(Connection c=DatabaseConnection.getConnection(); PreparedStatement p=c.prepareStatement(q)){p.setString(1,s.rollNo());p.setString(2,s.name());p.setString(3,s.email());p.setString(4,s.department());p.setInt(5,s.semester());p.executeUpdate();} }
 public List<Student> findAll() throws SQLException { List<Student> out=new ArrayList<>(); String q="SELECT * FROM students ORDER BY id"; try(Connection c=DatabaseConnection.getConnection(); Statement s=c.createStatement(); ResultSet r=s.executeQuery(q)){while(r.next())out.add(map(r));} return out; }
 public Optional<Student> findById(int id) throws SQLException { String q="SELECT * FROM students WHERE id=?"; try(Connection c=DatabaseConnection.getConnection(); PreparedStatement p=c.prepareStatement(q)){p.setInt(1,id);try(ResultSet r=p.executeQuery()){return r.next()?Optional.of(map(r)):Optional.empty();}} }
 public boolean update(Student s) throws SQLException { String q="UPDATE students SET roll_no=?,name=?,email=?,department=?,semester=? WHERE id=?"; try(Connection c=DatabaseConnection.getConnection(); PreparedStatement p=c.prepareStatement(q)){p.setString(1,s.rollNo());p.setString(2,s.name());p.setString(3,s.email());p.setString(4,s.department());p.setInt(5,s.semester());p.setInt(6,s.id());return p.executeUpdate()>0;} }
 public boolean delete(int id) throws SQLException { try(Connection c=DatabaseConnection.getConnection(); PreparedStatement p=c.prepareStatement("DELETE FROM students WHERE id=?")){p.setInt(1,id);return p.executeUpdate()>0;} }
 private Student map(ResultSet r)throws SQLException{return new Student(r.getInt("id"),r.getString("roll_no"),r.getString("name"),r.getString("email"),r.getString("department"),r.getInt("semester"));}
}
