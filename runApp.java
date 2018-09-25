package srs;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class runApp {
	
	public static void main (String[] args) {
		ArrayList<verb> v=loadVerbDb("verb_db.txt");
		Random r = new Random();
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Please enter your name:");
		String name=sc.nextLine();
		System.out.println("Welcome " + name + "!");
		int count=0;
		for (int i=0; i<10; i++) {
			System.out.println(" Please enter the correct english translation:");
			verb test=v.get(r.nextInt(v.size()));
			System.out.println(test.getFrenchTr());
			String guess=sc.nextLine();
			String[] s=test.getEnglishTr().split(", ");
			boolean flag=false;
			for (int j=0; j<s.length; j++) {
				if (guess.equals(s[j])) {
					System.out.println("correct");
					flag=true;
					count++;
					break;
				}
			}
			if (!flag) {
				System.out.println("not quite");
				System.out.println("Correct response: " + test.getEnglishTr());
			}
			flag=false;
		}
		System.out.println("Your score is " + count +"/10");
	}
	
	public static ArrayList<verb> loadVerbDb(String filename) {
		try {
			ArrayList<verb> verbs = new ArrayList<verb>();
			FileReader fr=new FileReader(filename);
			BufferedReader br=new BufferedReader(fr);
			String currentLine="potato";
			while ((currentLine=br.readLine()) !=null) {
				String[] s=currentLine.split("\t");
				verb v=new verb(s[0], s[1]);
				verbs.add(v);
			}
			br.close();
			fr.close();
			return verbs;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}