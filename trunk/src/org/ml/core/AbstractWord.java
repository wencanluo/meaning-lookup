package org.ml.core;

public abstract class AbstractWord {
	//Penn Treebank conventions
	public static final String ADJECTIVE = "JJ";
	public static final String ADJECTIVE_COMPERATIVE = "JJR";	//ENDING WITH -ER
	public static final String ADJECTIVE_SUPERLATIVE = "JJS";	//ENDING WITH -EST
	public static final String ADVERB = "RB";					//ENDING WITH -LY
	public static final String ADVERB_COMPERATIVE = "RBR";		//ENDING WITH -ER
	public static final String ADVERB_SUPERLATIVE = "RBS";
	public static final String ARTICLE = "DT";
	public static final String CARDINAL_NUMBER = "CD";
	public static final String COMMON_NOUN_PLURAL = "NNS";
	public static final String COMMON_NOUN_SINGULAR = "NN";
	public static final String COMPERATIVE_ADJECTIVE = "JJR";
	public static final String COMPERATIVE_ADVERB = "RBR";
	public static final String CONJUNCTION_COORDINATING = "CC";
	public static final String CONJUNCTION_SUBORDINATING = "IN";
	public static final String COORDINATIONG_CONJUNCTION = "CC";
	public static final String DETERMINER = "DT";
	public static final String EXISTENTIAL_THERE = "EX";
	public static final String FOREIGN_WORD = "FW";
	public static final String GERUND = "VBG";
	public static final String INTERJECTION = "UH";
	public static final String LIST_ITEM_MARKER = "LS";
	public static final String MODAL_VERB = "MD";
	public static final String NEGATION = "RB";
	public static final String NOUN_PLURAL = "NNS";
	public static final String NOUN_SINGULAR = "NN";
	public static final String PARTICLE = "RP";
	public static final String POSSESSIVE_ENDING = "POS";
	public static final String PREDETERMINER = "PDT";
	public static final String PREPOSITION_OR_SUBORDINATING_CONJUNCTION = "IN";
	public static final String PRONOUN_PERSONAL = "PRP";
	public static final String PRONOUN_POSSESSIVE = "PRP$";
	public static final String PROPER_NOUN_PLURAL = "NNPS";
	public static final String PROPER_NOUN_SINGULAR = "NNP";
	public static final String SYMBOL = "SYM";
	public static final String TO = "TO";
	public static final String VERB_BASE_FORM = "VB";
	public static final String VERB_PAST_TENSE = "VBD";
	public static final String VERB_PRESENT_TENSE = "VBP";
	public static final String VERB_PRESENT_TENSE_3RD_PERSON_SINGULAR = "VBZ";
	public static final String VERB_PRESENT_TENSE_NON_3RD_PERSON_SINGULAR = "VBP";
	public static final String VERB_PARTICIPLE_PAST = "VBN";
	public static final String VERB_PARTICIPLE_PRESENT = "VBG";
	public static final String WH_DETERMINER = "WDT";
	public static final String WH_PRONOUN_POSSESSIVE = "WP$";
	public static final String WH_ADVERB = "WRB";
	
	private String tag;
	private String label;
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String content) {
		this.label = content;
	}
	
}
