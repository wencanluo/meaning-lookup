package org.ml.bo;

import java.util.ArrayList;
import java.util.Collection;
import org.ml.core.MlConversationPart;
import org.ml.core.MlWord;
import org.ml.util.Global;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class StanfordTaggerBO {
		
	public static MlConversationPart getPennTaggedSentences(MlConversationPart convPart) throws Exception{
		MaxentTagger tagger = new MaxentTagger(Global.STANFORD_LOCATION);
		Collection<Word> words = new ArrayList<Word>();
		for(String word : convPart.getProcessedText().split("\\s+"))
			words.add(new Word(word)); //words.add(new Word(word.toLowerCase()));
		int i = 0;
		for(TaggedWord stanfordWord: tagger.tagSentence( new Sentence(words)))
			convPart.addWord(new MlWord(i++, stanfordWord.word(),stanfordWord.tag()));
		return convPart;
	}
	


}
