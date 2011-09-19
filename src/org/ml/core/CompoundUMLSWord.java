package org.ml.core;

import java.util.ArrayList;

public class CompoundUMLSWord {

	private Integer id;
	private String originalText;
	private ArrayList<UMLSWord> words = new ArrayList<UMLSWord>();
	
	public CompoundUMLSWord(Integer id, String text){
		setId(id);
		setOriginalText(text);
	}
	
	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}
	public String getOriginalText() {
		return originalText;
	}
	public void setWords(ArrayList<UMLSWord> words) {
		this.words = words;
	}
	public ArrayList<UMLSWord> getWords() {
		return words;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	} 
}
