package srs;
import java.sql.*; 
import java.util.*;

public class MySQLConnect {  
	
	public static void main(String[] args) {
		try (
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "username", "password");
			Statement stmt = conn.createStatement();
			Statement stmt2 = conn.createStatement();
			) {
			//Execute a SQL SELECT query, the query result is returned in a 'ResultSet' object.
			String strSelect = "SELECT * FROM DATA ORDER BY RAND()" ;
			ResultSet rset = stmt.executeQuery(strSelect);

			//Process the ResultSet by scrolling the cursor forward via next()
			Scanner sc=new Scanner(System.in);
			boolean flag=false;
			while(rset.next()) {   // Move the cursor to the next row, return false if no more row
				String fr = rset.getString("frenchTr");
				String en = rset.getString("englishTr");
				String[] enArr=en.split(", ");
				System.out.println("Enter the correct english translation for: " + fr );
				String guess=sc.nextLine();
				for (int i=0; i<enArr.length; i++) {
					if (enArr[i].equalsIgnoreCase(guess)) {
						System.out.println("Correct!");
						String strUpdate = ("UPDATE data SET ranking = ranking+1 WHERE TRIM(frenchTr)=\"" + fr + "\"");
						System.out.println("The SQL query is: " + strUpdate);
						stmt2.executeUpdate(strUpdate);
						flag=true;
						break;
					}
				}
				if (!flag) {
					System.out.println("Not quite");
					System.out.println("Correct answer(s): " + en);
				}
				flag=false;
			}
		} 
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
}
