package srs;
import java.sql.*; 
import java.util.*;

public class MySQLConnect {  
	
	public static void main(String[] args) {
		try (
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "username", "password");
			Statement stmt = conn.createStatement();
			) {
			//Execute a SQL SELECT query, the query result is returned in a 'ResultSet' object.
			String strSelect = "select * from data";
			ResultSet rset = stmt.executeQuery(strSelect);

			//Process the ResultSet by scrolling the cursor forward via next()
			Scanner sc=new Scanner(System.in);
			boolean flag=false;
			while(rset.next()) {   // Move the cursor to the next row, return false if no more row
				String fr = rset.getString("frenchTr");
				String en = rset.getString("englishTr");
				String[] enArr=en.split(", ");
				int rank = rset.getInt("ranking");
				System.out.println("Enter the correct english translation for: " + fr );
				String guess=sc.nextLine();
				for (int i=0; i<enArr.length; i++) {
					if (enArr[i].equalsIgnoreCase(guess)) {
						System.out.println("Correct!");
						if (rank<5) rank++;
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