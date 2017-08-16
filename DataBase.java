package Sudoko;

import java.sql.*;

public class DataBase {
	private static final String user="atul04";
	private static final String passwd="atul04";
	private static Connection con=null;
	private static Statement smt=null;
	
	public void dataConnection()
	{
		try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e)
		{ 
			e.printStackTrace();
		}
		
		try
		{
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",user,passwd);
			smt=con.createStatement();
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		if(con==null)
		{
			System.out.println("Please Try Again");
		}
	}
	
	public void DisplayScore(String[] nameList , String[] pointsList )
	{
		if(con!=null)
		{
			try
			{
				String sql="SELECT * FROM SUDOKUGAME ORDER BY WIN DESC";
				
				smt=con.createStatement();
				
				ResultSet rst= smt.executeQuery(sql);
				int i=0;
				
				while(rst.next())
				{
					nameList[i] = rst.getString(1);
					pointsList[i] = rst.getString(2);
				
					i+=1;
				}
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public int count()
	{
		if(con!=null)
		{
			try
			{
				String sql = "SELECT count(NAME) FROM SUDOKUGAME";
				
				smt = con.createStatement();
				
				ResultSet rst = smt.executeQuery(sql);
				
				rst.next();
				
				return (rst.getInt(1));
				
			}catch(SQLException e)
			{
				e.printStackTrace();
				return 0;
			}
		}
		else
			return 0;
	}
	
	public void close()
	{
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void upsert(String winner)
	{
		String sql1="MERGE INTO SUDOKUGAME USING dual ON(NAME='"+winner+"')"+"\n"
				+ "WHEN MATCHED THEN UPDATE SET WIN=WIN+1 \n"
				+ "WHEN NOT MATCHED THEN INSERT\n"
				+ "VALUES('"+winner+"',1)";
		
		try {
			smt=con.createStatement();
			smt.executeQuery(sql1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
