package com.vityarthi.dbms;
import com.vityarthi.dbms.config.DatabaseConnection;
import com.vityarthi.dbms.ui.ConsoleUI;
import com.vityarthi.dbms.ui.DashboardFrame;
import javax.swing.*;
public class Main {
 public static void main(String[] args){
  if(args.length>0 && "console".equalsIgnoreCase(args[0])){try(DatabaseConnection.getConnection()){new ConsoleUI().start();}catch(Exception e){System.err.println("Cannot connect to MySQL: "+e.getMessage());}return;}
  SwingUtilities.invokeLater(()->{try(DatabaseConnection.getConnection()){UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());new DashboardFrame().setVisible(true);}catch(Exception e){JOptionPane.showMessageDialog(null,"Cannot connect to MySQL.\n\n"+e.getMessage()+"\n\nSet DB_URL, DB_USER and DB_PASSWORD before starting the application.","Database Connection Error",JOptionPane.ERROR_MESSAGE);}});
 }
}
