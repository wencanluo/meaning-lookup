package org.ml.engine;

import org.ml.bo.StanfordTaggerBO;
import org.ml.bo.UmlsBO;
import org.ml.bo.WordNetBO;
import org.ml.core.MlConversationPart;
import org.ml.util.Global;
import org.ml.util.LogHelper;

public class MlDisambiguator {

	public static final Boolean DIFFERENT_TYPES_IN_NEIGHBORING_WORDS = true;
	
	public static MlConversationPart disambiguate(String text){
		
		MlConversationPart convPart = buildConversationPart(text);
		if(convPart.getProcessedText().equals(""))
			return convPart;
		try {
			StanfordTaggerBO.getPennTaggedSentences(convPart);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		WordNetBO.getWordNetWords(convPart);
		UmlsBO.getExactUmlsWords(convPart);
		if(convPart.getWords().size() > 1)
			UmlsBO.getWholePhraseUmlsWord(convPart);
		
		UmlsBO.getMuliplePartsUmlsWords(convPart);
		
		return convPart;
		
	}
	
	private static MlConversationPart buildConversationPart(String text){
		MlConversationPart convPart = new MlConversationPart(text);
		text = MlDisambiguator.removePunctuationFromText(text);
		text = MlDisambiguator.removeStopWordsFromText(text);
		convPart.setProcessedText(text);
		return convPart;
	}
	
	public static String removeStopWordsFromText(String text){
		text = " "+ text +" ";
		for(String stopWord : Global.STOP_WORDS.split(","))
			text = text.replaceAll("(?<=\\s+)(?i)"+ stopWord + "(?=\\s+)", " ");
		text = text.trim();
		return text;
	}
	
	public static String removePunctuationFromText(String text){
		text = text.replaceAll("\\(|\\)|\\||\\[|\\]|\\{|]]}", " ");
		text = text.replaceAll("\\,|\\;|\\:|\"", " ");
		return text;
	}
	
	public static boolean doesConversationContainSentence(MlConversationPart convPart, String sentence){
		LogHelper.debug("Check if sentence contains the word:"+sentence);
		String simpleForm;
		for(String word : sentence.split("\\s+")){
			simpleForm = WordNetBO.getSimpleVersionOfWord(word);
			if(simpleForm != null)
				if(!convPart.doesSentenceContainSimpleWordForm(simpleForm))
					return false;
		}
		return true;
	}
}
