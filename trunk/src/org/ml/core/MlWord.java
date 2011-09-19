package org.ml.core;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "meaning")
public class MlWord extends AbstractWord{

	public static final int UNDEFINED = 0;
	public static final int NOUN = 1;
	public static final int VERB = 2;
	public static final int ADJECTIVE = 3;
	public static final int PRONOUN = 4;
	public static final int ADVERB = 5;
	public static final int AUXILIARY_VERB = 6;
	public static final int PREPOSITION = 7;
	public static final int ARTICLE = 8;
	public static final int DEMONTRATIVE = 9;
	public static final int QUANTIFIER = 10;
	public static final int CONJUNCTION = 11;
	
	public static HashMap<Integer,String> pos = new HashMap<Integer,String>();
	
	static{
		pos.put(UNDEFINED, "undefined");
		pos.put(NOUN, "noun");
		pos.put(VERB, "verb");
		pos.put(ADJECTIVE, "adjective");
		pos.put(PRONOUN, "pronoun");
		pos.put(ADVERB, "adverb");
		pos.put(AUXILIARY_VERB, "auxiliary_verb");
		pos.put(PREPOSITION, "preposition");
		pos.put(ARTICLE, "article");
		pos.put(DEMONTRATIVE, "demonstrative");
		pos.put(QUANTIFIER, "quantifier");
		pos.put(CONJUNCTION, "conjunction");
	}
				
	private Integer vaguePOSType;
	private Integer id;
	
	public MlWord(Integer id , String label, String tag){
		setId(id);
		setLabel(label);
		setTag(tag);
		determineGlobalType();
	}
	
	public void determineGlobalType(){
		if(getTag().equals(VERB_BASE_FORM)||getTag().equals(VERB_PAST_TENSE)||getTag().equals(VERB_PRESENT_TENSE)||
				getTag().equals(VERB_PARTICIPLE_PAST)||getTag().equals(VERB_PARTICIPLE_PRESENT))
			setVaguePOSType(VERB);
		else
		if(getTag().equals(NOUN_PLURAL)||getTag().equals(NOUN_SINGULAR))
			setVaguePOSType(NOUN);
		else
		if(getTag().equals(ADJECTIVE)||getTag().equals(ADJECTIVE_COMPERATIVE)||getTag().equals(ADJECTIVE_SUPERLATIVE))
			setVaguePOSType(ADJECTIVE);
		else
			setVaguePOSType(UNDEFINED);
	}
	
	public Integer getVaguePOSType() {
		return vaguePOSType;
	}
	public void setVaguePOSType(Integer type) {
		this.vaguePOSType = type;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

}
