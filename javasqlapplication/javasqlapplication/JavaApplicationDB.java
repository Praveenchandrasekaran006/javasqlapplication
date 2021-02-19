package javasqlapplication;


import java.sql.*;
import java.util.*;
import java.io.*;
public class JavaApplicationDB {
	
	public static void getSubject(Connection con) throws Exception
	{
		PreparedStatement pst = con.prepareStatement("select Subject.subject_number,Subject.subject_name  from Subject inner join StudentsGrade on StudentsGrade.subject_number = Subject.subject_number where StudentsGrade.student_id = ?;");
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the student Id");
		int id = in.nextInt();
		
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			int subject_number = rs.getInt("Subject.subject_number");
			String subject_name = rs.getString("Subject.subject_name");
			System.out.println("The registered subject is "+subject_name);
			System.out.println("The registered subject number is "+subject_number);
		}
		
		pst.close();
		con.close();
		
	}
	
	public static void getGrade(Connection con)throws Exception
	{
		PreparedStatement pst = con.prepareStatement("select StudentsGrade.grade from StudentsGrade inner join StudentsTable on StudentsTable.student_id = StudentsGrade.student_id where StudentsTable.student_id = ?; ");
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the student Id");
		int id = in.nextInt();
		
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			int grade = rs.getInt("StudentsGrade.grade");
			System.out.println("The Student grade is "+grade);
		}
		
		pst.close();
		con.close();
		
	}
	
	public static void updateValues(Connection con) throws Exception
	{
		System.out.println("press \"1\" for updating student ID");
		System.out.println("press \"2\" for updating student Name");
		System.out.println("press \"3\" for updating student Department");
		
		Scanner in = new Scanner(System.in);
		int temp = in.nextInt();
		
		if(temp == 1)
		{
			PreparedStatement pst = con.prepareStatement("update StudentsTable set student_id = ? where student_name = ?;");
			
			Scanner input = new Scanner(System.in);
			System.out.println("Enter the name of student: ");
			String name = input.nextLine();
			System.out.println("Enter the student Id to be updated: ");
			int id = input.nextInt();
			pst.setInt(1, id);
			pst.setString(2,name);
			pst.executeUpdate();
			
			System.out.println("student Id updated Successfully ");
			pst.close();
			con.close();
		}
		else if(temp == 2)
		{
			PreparedStatement pst = con.prepareStatement("update StudentsTable set student_name = ? where student_id = ?;");
			
			Scanner input = new Scanner(System.in);
			System.out.println("Enter the Id of student: ");
			int id = Integer.parseInt(input.nextLine());
			System.out.println("Enter the student Name to be updated: ");
			String name = input.nextLine();
			pst.setString(1, name);
			pst.setInt(2,id);
			pst.executeUpdate();
			
			System.out.println("student Name updated Successfully ");
			pst.close();
			con.close();
		}
		else
		{
			PreparedStatement pst = con.prepareStatement("update StudentsTable set department = ? where student_id = ?;");
			
			Scanner input = new Scanner(System.in);
			System.out.println("Enter the Id of student: ");
			int id = Integer.parseInt(input.nextLine());
			System.out.println("Enter the Department Name to be updated: ");
			String dept = input.nextLine();
			pst.setString(1, dept);
			pst.setInt(2,id);
			pst.executeUpdate();
			
			System.out.println("Department Name updated Successfully ");
			pst.close();
			con.close();
		}
		
		
		
	}
	
	public static void getValues(Connection con) throws Exception
	{
		PreparedStatement pst = con.prepareStatement("select * from StudentsTable where student_id = (?) ");
		
		System.out.println("Enter the Student Id");
		Scanner in = new Scanner(System.in);
		pst.setInt(1, in.nextInt());
		ResultSet rs = pst.executeQuery();
		
		System.out.println("The mentioned Student details is...");
		while(rs.next())
		{
			int id = rs.getInt("student_id");
			String name = rs.getString("student_name");
			String dept = rs.getString("department");
			System.out.println("Student Id = "+id);
			System.out.println("Student Name = "+name);
			System.out.println("Student Department = "+dept);
			
		}
		
		
		pst.close();
		con.close();
	}
	
	public static void insertValues(Connection con) throws SQLException
	{
		PreparedStatement pst = con.prepareStatement("insert into StudentsTable values (?,?,?);");
		
		System.out.println("Enter the details of new Student...");
		System.out.println("Enter the Student ID");
		Scanner in = new Scanner(System.in);
		int id = Integer.parseInt(in.nextLine());
		System.out.println("Enter the Student Name");
		String name = in.nextLine();
		System.out.println("Enter the Department");
		String dept = in.nextLine();
		
		pst.setInt(1, id);
		pst.setString(2, name);
		pst.setString(3, dept);
		pst.executeUpdate();
		
		System.out.println("Values Updated Successfully");
		
		pst.close();
		con.close();
		
	}
	
	public static void main(String args[]) throws Exception
	{
		Properties p = new Properties();
		InputStream is = new FileInputStream("databasedetails.txt");
		p.load(is);
		String url = p.getProperty("url");
		String uname = p.getProperty("uname");
		String pass = p.getProperty("pass");
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url,uname,pass);
		
		System.out.println("Press \"1\" for inserting the students data");
		System.out.println("Press \"2\" for getting the students data");
		System.out.println("Press \"3\" for updating the students data");
		System.out.println("Press \"4\" for getting the Student\'s registered subject");
		System.out.println("Press \"5\" for getting the students grade");
		
		Scanner input = new Scanner(System.in);
		int temp = input.nextInt();
		
		if(temp == 1)
			insertValues(con);
		else if(temp == 2)
			getValues(con);
		else if(temp == 3)
			updateValues(con);
		else if(temp == 4)
			getSubject(con);
		else if(temp == 5)
			getGrade(con);
		
		
	}

}


