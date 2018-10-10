package srs;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class runApp {
	
	private static ArrayList<Verb>[] wordBank = new ArrayList[5];
	
	public static void main (String[] args) {
		ArrayList<Verb> v=loadVerbDb("verb_db.txt");
		wordBank[0]=v;
		wordBank[1]=new ArrayList<Verb>();
		wordBank[2]=new ArrayList<Verb>();
		wordBank[3]=new ArrayList<Verb>();
		wordBank[4]=new ArrayList<Verb>();
		Random r = new Random();
		Scanner sc=new Scanner(System.in);
		System.out.println("Please enter your name:");
		String name=sc.nextLine();
		System.out.println("Welcome " + name + "!");
		loopWords(1);
		loopWords(2);
		loopWords(3);
		loopWords(4);
		loopWords(5); 
	}
	
	public static ArrayList<Verb> loadVerbDb(String filename) {
		try {
			ArrayList<Verb> verbs = new ArrayList<Verb>();
			FileReader fr=new FileReader(filename);
			BufferedReader br=new BufferedReader(fr);
			String currentLine="potato";
			while ((currentLine=br.readLine()) !=null) {
				String[] s=currentLine.split("\t");
				Verb v=new Verb(s[0], s[1]);
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
	
	public static void moveWord(Verb v, int oldList, int newList) {
		wordBank[newList].add(v);
		wordBank[oldList].remove(v);
	}
	
	public static void resize(ArrayList<Verb> list) {
		//make a new ArrayList not including all the empty elements
	}
	
	public static void loopWords(int k) {
		System.out.println("starting round" + k);
		Random r=new Random();
		Scanner sc=new Scanner(System.in);
		
		for (int i=1; i<5; i++) {
			int list=0;
			int rand=r.nextInt(101);
			if (rand<6) list=4;
			else if(rand<12) list=3;
			else if(rand<25) list=2;
			else if(rand<50) list=1;
			System.out.println(list);
			//ensure that the chosen list isn't empty
			while (wordBank[list].size()==0) {
				rand=r.nextInt(101);
				if (rand<6) list=4;
				else if(rand<12) list=3;
				else if(rand<25) list=2;
				else if(rand<50) list=1;
				else list=0;
			}
			int word=r.nextInt(wordBank[list].size());
			System.out.println(" Please enter the correct english translation:");
			Verb test=wordBank[list].get(word);
			System.out.println(test.getFrenchTr());
			String guess=sc.nextLine();
			String[] s=test.getEnglishTr().split(", ");
			boolean flag=false;
			for (int j=0; j<s.length; j++) {
				if (guess.equals(s[j])) {
					System.out.println("correct");
					flag=true;
					if (list<4) moveWord(test, list, list+1);
					break;
				}
			}
			if (!flag) {
				System.out.println("not quite");
				System.out.println("Correct response: " + test.getEnglishTr());
			}
			flag=false;
		}
	}
}