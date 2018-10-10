package srs;

public class Word {
	
	String frenchTr;
	String englishTr;
	//constructor
	public Word (String frenchTr, String englishTr) {
		this.englishTr=englishTr;
		this.frenchTr=frenchTr;
	}
		
	public String getEnglishTr() {
	return this.englishTr;
	}
		
	public String getFrenchTr() {
		return this.frenchTr;
	}

}
