package srs;

public class Verb extends Word{
	String[] presentConj;
	
	//constructor
	public Verb(String frenchTr, String englishTr) {
		super(frenchTr, englishTr);
		//TODO: initialize conjugations
	}
	public String getPresentConj(int i) {
		return this.presentConj[i];
	}
}