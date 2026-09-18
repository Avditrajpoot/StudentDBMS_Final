package com.vityarthi.dbms.dao;
import com.vityarthi.dbms.config.DatabaseConnection;
import java.sql.*; import java.util.*;
public class EnrollmentDAO {
 public void enroll(int studentId,int courseId)throws SQLException{try(Connection c=DatabaseConnection.getConnection();PreparedStatement p=c.prepareStatement("INSERT INTO enrollments(student_id,course_id) VALUES(?,?)")){p.setInt(1,studentId);p.setInt(2,courseId);p.executeUpdate();}}
 public void setGrade(int enrollmentId,String grade)throws SQLException{try(Connection c=DatabaseConnection.getConnection();PreparedStatement p=c.prepareStatement("UPDATE enrollments SET grade=? WHERE id=?")){p.setString(1,grade);p.setInt(2,enrollmentId);p.executeUpdate();}}
 public List<Object[]> findAll()throws SQLException{String q="SELECT e.id,e.student_id,s.name,e.course_id,c.name,e.grade FROM enrollments e JOIN students s ON s.id=e.student_id JOIN courses c ON c.id=e.course_id ORDER BY e.id";return query(q,null);}
 public List<Object[]> findForStudent(int studentId)throws SQLException{String q="SELECT e.id,c.code,c.name,c.credits,e.grade FROM enrollments e JOIN courses c ON c.id=e.course_id WHERE e.student_id=? ORDER BY c.code";return query(q,studentId);}
 private List<Object[]> query(String q,Integer id)throws SQLException{List<Object[]> out=new ArrayList<>();try(Connection c=DatabaseConnection.getConnection();PreparedStatement p=c.prepareStatement(q)){if(id!=null)p.setInt(1,id);try(ResultSet r=p.executeQuery()){while(r.next()){int n=r.getMetaData().getColumnCount();Object[] row=new Object[n];for(int i=0;i<n;i++)row[i]=r.getObject(i+1);out.add(row);}}}return out;}
 public void printStudentEnrollments(int studentId)throws SQLException{for(Object[] r:findForStudent(studentId))System.out.println(Arrays.toString(r));}
}
