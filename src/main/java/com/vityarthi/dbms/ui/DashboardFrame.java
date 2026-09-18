package com.vityarthi.dbms.ui;

import com.vityarthi.dbms.config.DatabaseConnection;
import com.vityarthi.dbms.dao.CourseDAO;
import com.vityarthi.dbms.dao.EnrollmentDAO;
import com.vityarthi.dbms.dao.StudentDAO;
import com.vityarthi.dbms.model.Course;
import com.vityarthi.dbms.model.Student;
import com.vityarthi.dbms.service.ReportService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DashboardFrame extends JFrame {
    private final StudentDAO studentDAO = new StudentDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private final ReportService reportService = new ReportService();

    private final CardLayout cards = new CardLayout();
    private final JPanel content = new JPanel(cards);
    private final JLabel status = new JLabel("Ready");
    private final JLabel studentCount = statValue();
    private final JLabel courseCount = statValue();
    private final JLabel enrollmentCount = statValue();

    public DashboardFrame() {
        setTitle("VITyarthi Student DBMS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 700));
        setSize(1200, 760);
        setLocationRelativeTo(null);
        build();
        refreshDashboard();
    }

    private void build() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(245, 247, 250));
        root.add(sidebar(), BorderLayout.WEST);
        root.add(mainPanel(), BorderLayout.CENTER);
        setContentPane(root);
    }

    private JPanel sidebar() {
        JPanel side = new JPanel();
        side.setPreferredSize(new Dimension(230, 0));
        side.setBackground(new Color(25, 32, 44));
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBorder(new EmptyBorder(24, 16, 20, 16));

        JLabel title = new JLabel("VITyarthi DBMS");
        title.setForeground(Color.WHITE); title.setFont(new Font("SansSerif", Font.BOLD, 22));
        JLabel sub = new JLabel("Student Management");
        sub.setForeground(new Color(170, 181, 198)); sub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        side.add(title); side.add(Box.createVerticalStrut(4)); side.add(sub); side.add(Box.createVerticalStrut(28));

        addNav(side, "Dashboard", () -> show("dashboard"));
        addNav(side, "Students", () -> show("students"));
        addNav(side, "Courses", () -> show("courses"));
        addNav(side, "Enrollments", () -> show("enrollments"));
        addNav(side, "Reports", () -> show("reports"));
        side.add(Box.createVerticalGlue());
        JLabel db = new JLabel("MySQL • JDBC • Java 17");
        db.setForeground(new Color(145, 157, 175)); db.setFont(new Font("SansSerif", Font.PLAIN, 11)); side.add(db);
        return side;
    }

    private void addNav(JPanel side, String text, Runnable action) {
        JButton b = new JButton(text);
        b.setAlignmentX(Component.LEFT_ALIGNMENT); b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        b.setHorizontalAlignment(SwingConstants.LEFT); b.setForeground(new Color(225,230,238));
        b.setBackground(new Color(25,32,44)); b.setBorder(new EmptyBorder(10, 14, 10, 10));
        b.setFocusPainted(false); b.setFont(new Font("SansSerif", Font.PLAIN, 14));
        b.addActionListener(e -> action.run()); side.add(b); side.add(Box.createVerticalStrut(4));
    }

    private JPanel mainPanel() {
        JPanel p = new JPanel(new BorderLayout(0, 12)); p.setBorder(new EmptyBorder(20, 22, 14, 22)); p.setBackground(new Color(245,247,250));
        JPanel header = new JPanel(new BorderLayout()); header.setOpaque(false);
        JLabel h = new JLabel("Student Database Management System"); h.setFont(new Font("SansSerif", Font.BOLD, 25)); h.setForeground(new Color(25,32,44));
        status.setForeground(new Color(95,105,120)); status.setFont(new Font("SansSerif", Font.PLAIN, 12));
        header.add(h, BorderLayout.WEST); header.add(status, BorderLayout.EAST); p.add(header, BorderLayout.NORTH);

        content.add(dashboardPanel(), "dashboard"); content.add(studentsPanel(), "students"); content.add(coursesPanel(), "courses"); content.add(enrollmentsPanel(), "enrollments"); content.add(reportsPanel(), "reports");
        p.add(content, BorderLayout.CENTER); return p;
    }

    private JPanel dashboardPanel() {
        JPanel p = base(); p.setLayout(new BorderLayout(0,18));
        JPanel stats = new JPanel(new GridLayout(1,3,14,0)); stats.setOpaque(false);
        stats.add(statCard("TOTAL STUDENTS", studentCount)); stats.add(statCard("TOTAL COURSES", courseCount)); stats.add(statCard("ENROLLMENTS", enrollmentCount));
        p.add(stats, BorderLayout.NORTH);
        JTextArea info = new JTextArea("Use the navigation to manage students, courses and enrollments.\n\n" +
                "Workflow: Student → Course → Enrollment → Grade → Reports\n\n" +
                "The application uses JDBC with prepared statements and relational constraints in MySQL.");
        info.setEditable(false); info.setFont(new Font("SansSerif", Font.PLAIN, 15)); info.setForeground(new Color(70,80,95)); info.setBackground(Color.WHITE); info.setBorder(new EmptyBorder(24,24,24,24)); p.add(info, BorderLayout.CENTER); return p;
    }

    private JPanel studentsPanel() {
        JPanel p = base(); p.setLayout(new BorderLayout(0,12));
        JButton add = primary("+ Add Student", e -> addStudent());
        JPanel top = new JPanel(new BorderLayout()); top.setOpaque(false); top.add(section("Students"), BorderLayout.WEST); top.add(add, BorderLayout.EAST); p.add(top, BorderLayout.NORTH);
        JTable table = table(); JScrollPane scroll = new JScrollPane(table); p.add(scroll, BorderLayout.CENTER);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT)); bottom.setOpaque(false);
        JButton edit = secondary("Edit Selected", e -> editStudent(table)); JButton del = secondary("Delete Selected", e -> deleteStudent(table));
        bottom.add(edit); bottom.add(del); p.add(bottom, BorderLayout.SOUTH);
        p.putClientProperty("table", table); return p;
    }

    private JPanel coursesPanel() {
        JPanel p = base(); p.setLayout(new BorderLayout(0,12));
        JPanel top = new JPanel(new BorderLayout()); top.setOpaque(false); top.add(section("Courses"), BorderLayout.WEST); top.add(primary("+ Add Course", e -> addCourse()), BorderLayout.EAST); p.add(top, BorderLayout.NORTH);
        JTable table = table(); p.add(new JScrollPane(table), BorderLayout.CENTER); p.putClientProperty("table", table); return p;
    }

    private JPanel enrollmentsPanel() {
        JPanel p = base(); p.setLayout(new BorderLayout(0,12));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT)); top.setOpaque(false); top.add(section("Enrollments"));
        JTextField studentId = new JTextField(8); top.add(new JLabel("Student ID:")); top.add(studentId);
        top.add(primary("Load", e -> loadEnrollments(studentId, p))); top.add(primary("+ Enroll", e -> enroll())); top.add(secondary("Set Grade", e -> setGrade(p)));
        p.add(top, BorderLayout.NORTH); JTable table = table(); p.add(new JScrollPane(table), BorderLayout.CENTER); p.putClientProperty("table", table); return p;
    }

    private JPanel reportsPanel() {
        JPanel p = base(); p.setLayout(new BorderLayout(0,12));
        JPanel top = new JPanel(new BorderLayout()); top.setOpaque(false); top.add(section("Reports & Analytics"), BorderLayout.WEST); top.add(primary("Refresh", e -> loadReports(p)), BorderLayout.EAST); p.add(top, BorderLayout.NORTH);
        JTextArea report = new JTextArea(); report.setEditable(false); report.setFont(new Font("Monospaced", Font.PLAIN, 14)); report.setBorder(new EmptyBorder(20,20,20,20)); p.add(new JScrollPane(report), BorderLayout.CENTER); p.putClientProperty("report", report); return p;
    }

    private void addStudent() {
        JTextField roll = new JTextField(), name = new JTextField(), email = new JTextField(), dept = new JTextField(), sem = new JTextField();
        if (form("Add Student", new String[]{"Roll No", "Name", "Email", "Department", "Semester"}, new JTextField[]{roll,name,email,dept,sem})) try {
            studentDAO.add(new Student(0,roll.getText().trim(),name.getText().trim(),email.getText().trim(),dept.getText().trim(),Integer.parseInt(sem.getText().trim()))); ok("Student added successfully."); refreshStudents(); refreshDashboard();
        } catch(Exception ex){ error(ex); }
    }

    private void editStudent(JTable t) {
        int r=t.getSelectedRow(); if(r<0){warn("Select a student first.");return;} int id=(int)t.getValueAt(r,0);
        try { Student s=studentDAO.findById(id).orElseThrow(); JTextField roll=new JTextField(s.rollNo()), name=new JTextField(s.name()), email=new JTextField(s.email()), dept=new JTextField(s.department()), sem=new JTextField(String.valueOf(s.semester()));
            if(form("Edit Student",new String[]{"Roll No","Name","Email","Department","Semester"},new JTextField[]{roll,name,email,dept,sem})){studentDAO.update(new Student(id,roll.getText().trim(),name.getText().trim(),email.getText().trim(),dept.getText().trim(),Integer.parseInt(sem.getText().trim())));ok("Student updated.");refreshStudents();refreshDashboard();}
        }catch(Exception ex){error(ex);}
    }

    private void deleteStudent(JTable t){int r=t.getSelectedRow();if(r<0){warn("Select a student first.");return;}int id=(int)t.getValueAt(r,0);if(confirm("Delete this student? Enrollments will also be removed."))try{studentDAO.delete(id);ok("Student deleted.");refreshStudents();refreshDashboard();}catch(Exception e){error(e);}}

    private void addCourse(){JTextField code=new JTextField(),name=new JTextField(),credits=new JTextField();if(form("Add Course",new String[]{"Course Code","Course Name","Credits"},new JTextField[]{code,name,credits}))try{courseDAO.add(new Course(0,code.getText().trim(),name.getText().trim(),Integer.parseInt(credits.getText().trim())));ok("Course added.");refreshCourses();refreshDashboard();}catch(Exception e){error(e);}}

    private void enroll(){JTextField sid=new JTextField(),cid=new JTextField();if(form("Enroll Student",new String[]{"Student ID","Course ID"},new JTextField[]{sid,cid}))try{enrollmentDAO.enroll(Integer.parseInt(sid.getText().trim()),Integer.parseInt(cid.getText().trim()));ok("Enrollment created.");refreshEnrollments();refreshDashboard();}catch(Exception e){error(e);}}

    private void setGrade(JPanel panel){JTable t=(JTable)panel.getClientProperty("table");int r=t.getSelectedRow();if(r<0){warn("Select an enrollment first.");return;}int id=(int)t.getValueAt(r,0);String grade=JOptionPane.showInputDialog(this,"Grade (e.g. A, B+, C):");if(grade!=null&&!grade.isBlank())try{enrollmentDAO.setGrade(id,grade.trim());refreshEnrollments();ok("Grade updated.");}catch(Exception e){error(e);}}

    private void loadEnrollments(JTextField sid,JPanel p){try{int id=Integer.parseInt(sid.getText().trim());JTable t=(JTable)p.getClientProperty("table");List<Object[]> rows=enrollmentDAO.findForStudent(id);fill(t,new String[]{"Enrollment ID","Course Code","Course","Credits","Grade"},rows);}catch(Exception e){error(e);}}

    private void loadReports(JPanel p){try{JTextArea a=(JTextArea)p.getClientProperty("report");a.setText(reportService.buildReport());}catch(Exception e){error(e);}}

    private void refreshStudents(){try{JPanel p=(JPanel)content.getComponent(1);fill((JTable)p.getClientProperty("table"),new String[]{"ID","Roll No","Name","Email","Department","Semester"},studentRows(studentDAO.findAll()));status.setText("Students refreshed");}catch(Exception e){error(e);}}
    private void refreshCourses(){try{JPanel p=(JPanel)content.getComponent(2);fill((JTable)p.getClientProperty("table"),new String[]{"ID","Code","Course","Credits"},courseRows(courseDAO.findAll()));status.setText("Courses refreshed");}catch(Exception e){error(e);}}
    private void refreshEnrollments(){try{JPanel p=(JPanel)content.getComponent(3);JTable t=(JTable)p.getClientProperty("table");fill(t,new String[]{"Enrollment ID","Student ID","Student","Course ID","Course","Grade"},enrollmentDAO.findAll());status.setText("Enrollments refreshed");}catch(Exception e){error(e);}}
    private void refreshDashboard(){try{int[] c=reportService.counts();studentCount.setText(String.valueOf(c[0]));courseCount.setText(String.valueOf(c[1]));enrollmentCount.setText(String.valueOf(c[2]));}catch(Exception e){status.setText("Database not connected");}}

    private void show(String card){cards.show(content,card);try{switch(card){case "students"->refreshStudents();case "courses"->refreshCourses();case "enrollments"->refreshEnrollments();case "reports"->loadReports((JPanel)content.getComponent(4));case "dashboard"->refreshDashboard();}}catch(Exception e){error(e);}}

    private boolean form(String title,String[] labels,JTextField[] fields){JPanel p=new JPanel(new GridLayout(labels.length,2,10,10));p.setBorder(new EmptyBorder(10,10,0,10));for(int i=0;i<labels.length;i++){p.add(new JLabel(labels[i]+":"));p.add(fields[i]);}return JOptionPane.showConfirmDialog(this,p,title,JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE)==JOptionPane.OK_OPTION;}
    private JPanel base(){JPanel p=new JPanel();p.setBackground(Color.WHITE);p.setBorder(new EmptyBorder(18,18,18,18));return p;}
    private JLabel section(String s){JLabel l=new JLabel(s);l.setFont(new Font("SansSerif",Font.BOLD,21));l.setForeground(new Color(25,32,44));return l;}
    private JTable table(){JTable t=new JTable();t.setRowHeight(30);t.setFont(new Font("SansSerif",Font.PLAIN,13));t.getTableHeader().setFont(new Font("SansSerif",Font.BOLD,12));t.setAutoCreateRowSorter(true);return t;}
    private JButton primary(String s,java.awt.event.ActionListener a){JButton b=new JButton(s);b.addActionListener(a);b.setFocusPainted(false);return b;}
    private JButton secondary(String s,java.awt.event.ActionListener a){JButton b=new JButton(s);b.addActionListener(a);b.setFocusPainted(false);return b;}
    private JLabel statValue(){JLabel l=new JLabel("-");l.setFont(new Font("SansSerif",Font.BOLD,30));l.setForeground(new Color(30,45,65));return l;}
    private JPanel statCard(String label,JLabel value){JPanel p=new JPanel();p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));p.setBackground(Color.WHITE);p.setBorder(new EmptyBorder(18,20,18,20));JLabel l=new JLabel(label);l.setFont(new Font("SansSerif",Font.BOLD,11));l.setForeground(new Color(100,110,125));p.add(l);p.add(Box.createVerticalStrut(7));p.add(value);return p;}
    private void fill(JTable t,String[] cols,List<Object[]> rows){DefaultTableModel m=new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};for(Object[] row:rows)m.addRow(row);t.setModel(m);}
    private List<Object[]> studentRows(List<Student> x){return x.stream().map(s->new Object[]{s.id(),s.rollNo(),s.name(),s.email(),s.department(),s.semester()}).toList();}
    private List<Object[]> courseRows(List<Course> x){return x.stream().map(c->new Object[]{c.id(),c.code(),c.name(),c.credits()}).toList();}
    private void ok(String s){JOptionPane.showMessageDialog(this,s,"Success",JOptionPane.INFORMATION_MESSAGE);}
    private void warn(String s){JOptionPane.showMessageDialog(this,s,"Input Required",JOptionPane.WARNING_MESSAGE);}
    private void error(Exception e){String msg=e.getMessage()==null?e.toString():e.getMessage();status.setText("Operation failed");JOptionPane.showMessageDialog(this,msg,"Error",JOptionPane.ERROR_MESSAGE);}
    private boolean confirm(String s){return JOptionPane.showConfirmDialog(this,s,"Confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION;}
}
