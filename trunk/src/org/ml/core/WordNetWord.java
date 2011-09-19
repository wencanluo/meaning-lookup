package org.ml.core;

import java.util.ArrayList;

import edu.smu.tspell.wordnet.Synset;


public class WordNetWord extends MlWord {
	
	private ArrayList<Synset> synsets;
	private Integer sysnsetId = null;
	
	public WordNetWord(MlWord word){
		super(word.getId(), word.getLabel(),word.getTag());
		synsets = new ArrayList<Synset>();
	}
	
	public String getSimpleForm(){
		if(synsets.size() == 0)
			return getLabel();
		else
			return synsets.get(0).getWordForms()[0];
	}
	
	public ArrayList<Synset> getSynsets() {
		return synsets;
	}
	public void setSynsets(ArrayList<Synset> synsets) {
		this.synsets = synsets;
	}
	
	public Integer getSysnsetId() {
		return sysnsetId;
	}

	public void setSysnsetId(Integer sysnsetId) {
		this.sysnsetId = sysnsetId;
	}
	

}
