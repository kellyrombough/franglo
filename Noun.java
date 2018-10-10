package srs;

public class Noun extends Word {
	char gender;
	
	//constructor
	public Noun(String frenchTr, String englishTr, char gender) {
		super(frenchTr, englishTr);
		if(gender=='m' || gender=='f' || gender=='M' || gender=='F') this.gender=gender;
		else throw new IllegalArgumentException("noun gender must be masculine(m) or feminine(f)");
	}
}
