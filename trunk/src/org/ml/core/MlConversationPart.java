package org.ml.core;

import java.util.ArrayList;
import java.util.HashMap;

public class MlConversationPart {
	
	private String plainText;
	private String processedText;
	private Integer relevance = 1;
	
	private ArrayList<MlWord> words = new ArrayList<MlWord>();
	private ArrayList<WordNetWord> wordNetWords = new ArrayList<WordNetWord>();
	private HashMap<String,UMLSWord> UMLSWords = new HashMap<String,UMLSWord>();
	
	
	public MlConversationPart(String text){
		plainText = text;
	}
	
	public boolean doesSentenceContainSimpleWordForm(String label){
		label = label.toLowerCase();
		for(WordNetWord word : wordNetWords)
			if(word.getSimpleForm().toLowerCase().startsWith(label) || label.startsWith(word.getSimpleForm().toLowerCase()))
				return true;
		return false;
	}
		
	public String getPlainText() {
		return plainText;
	}

	public void setRelevance(Integer relevance) {
		this.relevance = relevance;
	}

	public Integer getRelevance() {
		return relevance;
	}
	
	public void setWords(ArrayList<MlWord> words) {
		this.words = new ArrayList<MlWord>(words);
	}

	public ArrayList<MlWord> getWords() {
		return words;
	}

	public void addWord(MlWord word) {
		words.add(word);
	}

	public void setWordNetWords(ArrayList<WordNetWord> wordNetWords) {
		this.wordNetWords = wordNetWords;
	}

	public ArrayList<WordNetWord> getWordNetWords() {
		return wordNetWords;
	}

	public void setUMLSWords(HashMap<String,UMLSWord> uMLSWords) {
		UMLSWords = uMLSWords;
	}

	public HashMap<String,UMLSWord> getUMLSWords() {
		return UMLSWords;
	}

	public void setProcessedText(String processedText) {
		this.processedText = processedText;
	}

	public String getProcessedText() {
		return processedText;
	}

}
