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
			Scanner sc=new Scanner(System.in);
			String username="";
			String password="";
			System.out.println("If you have an account already, type \"login\", if you would like to create a new account, type \"make account\":");
			String s = sc.nextLine();
			if (s.equals("make account")) username=makeAccount();
			else{
				//check that username and password pair exist in table of users
				while (true) {
					System.out.println("Please enter your username:");
					username = sc.nextLine();
					System.out.println("Please enter your password:");
					password = sc.nextLine();
					String checkLogin="SELECT * FROM users WHERE username=\"" + username + "\" AND password=\"" + password + "\"";
					ResultSet rset2 = stmt.executeQuery(checkLogin);
					if (rset2.next()) break;
					System.out.println("invalid login credentials");
				}
			}
			
			//Select the columns containing the word list, in English and French
			//Select the column containing each word's frequency ranking for the user that is logged in
			String strSelect = "SELECT freq_" + username +", englishTr, frenchTr FROM wordList WHERE freq_" + username + "=0 ORDER BY RAND()";
			System.out.println(strSelect);
			ResultSet rset = stmt.executeQuery(strSelect);

			boolean flag=false;
			while(rset.next()) {   // Move the cursor to the next word, return false if no more words
				String fr = rset.getString("frenchTr");
				String en = rset.getString("englishTr");
				String[] enArr=en.split(", ");
				System.out.println("Enter the correct english translation for: " + fr );
				String guess=sc.nextLine();
				//check if user translated the word correctly
				for (int i=0; i<enArr.length; i++) {
					if (enArr[i].equalsIgnoreCase(guess)) {
						//if right, increase the word's frequency ranking by one so that it will appear less often
						//frequency ranking ranges from 0 (most frequent) to 4 (least frequent)
						System.out.println("Correct!");
						String strUpdate = ("UPDATE wordList SET freq_" + username +" = freq_" + username + "+1 "
											+ "WHERE TRIM(frenchTr)=\"" + fr + "\" AND " + "freq_" + username +"<4");
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
	
	public static String makeAccount() {
		String newUser="";
		String newPass="";
		try (
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "username", "password");
			Statement stmt = conn.createStatement();
			) {
			Scanner sc = new Scanner(System.in);
			while (true) {
				System.out.println("Enter your new username:");
				newUser = sc.nextLine();
				//check if username already exists
				String checkExists = "SELECT * FROM users WHERE username=\"" + newUser + "\"";
				ResultSet r = stmt.executeQuery(checkExists);
				if (!r.next()) break;
				System.out.println("that username is taken");
			}
			while (true) {
				System.out.println("Enter your new password:");
				newPass = sc.nextLine();
				System.out.println("Confirm password:");
				//make sure password is entered correctly
				String newPassConfirm = sc.nextLine();
				if (newPass.equals(newPassConfirm)) break;
				System.out.println("Passwords didn't match, try again");
			}
			//add new user to table of users
			String createAcc = "INSERT INTO users (username, password) VALUES (\"" + newUser + "\", \"" + newPass + "\")";
			stmt.executeUpdate(createAcc);
			
			//add new column to wordlist table, setting the frequency ranking of every word to 0 for this new user
			String newCol="ALTER TABLE wordlist ADD freq_"+newUser + " INT ";
			stmt.executeUpdate(newCol);
			String setZero = "UPDATE wordlist SET freq_"+newUser + "=0";
			stmt.executeUpdate(setZero);
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		return newUser;
	}
}
