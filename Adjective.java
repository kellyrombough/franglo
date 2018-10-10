package srs;

public class Adjective extends Word {
	//fields for adjective's alternative spellings (masculine/feminine, singlular/plural)
	String fs;
	String mpl;
	String fpl;
	
	//constructor
	public Adjective(String frenchTr, String englishTr, String fs, String mpl, String fpl) {
		super(frenchTr, englishTr);
		this.fs=fs;
		this.mpl=mpl;
		this.fpl=fpl;
	}
}
