package com.vityarthi.dbms.dao;
import com.vityarthi.dbms.config.DatabaseConnection; import com.vityarthi.dbms.model.Course; import java.sql.*; import java.util.*;
public class CourseDAO {
 public List<Course> findAll()throws SQLException{List<Course>o=new ArrayList<>();try(Connection c=DatabaseConnection.getConnection();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT * FROM courses ORDER BY code")){while(r.next())o.add(new Course(r.getInt("id"),r.getString("code"),r.getString("name"),r.getInt("credits")));}return o;}
 public void add(Course x)throws SQLException{try(Connection c=DatabaseConnection.getConnection();PreparedStatement p=c.prepareStatement("INSERT INTO courses(code,name,credits) VALUES(?,?,?)")){p.setString(1,x.code());p.setString(2,x.name());p.setInt(3,x.credits());p.executeUpdate();}}
}
