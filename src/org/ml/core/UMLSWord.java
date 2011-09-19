package org.ml.core;

import java.util.ArrayList;
import java.util.HashMap;

public class UMLSWord implements Comparable <UMLSWord>{

    private Integer mlId;
    private String CUI;
    private String label;
    private String type;
    private String STN;
    private ArrayList<UMLSWord> synonyms;
    private String definition;
    private HashMap <String, ArrayList<Synonym>> suiList;
    private int synonymCount ;
    private int wordCount;
    private ArrayList<String> SABList;

    public UMLSWord(String cui){
            setCUI(cui);
    }

    public UMLSWord(String cui, String label){
            setLabel(label);
            this.wordCount = countWords(label);
            setCUI(cui);
    }

    public void setCUI(String cUI) {
            CUI = cUI;
    }
    public String getCUI() {
            return CUI;
    }
    public void setSynonyms(ArrayList<UMLSWord> synonyms) {
            this.synonyms = synonyms;
    }
    public ArrayList<UMLSWord> getSynonyms() {
            return synonyms;
    }

    public void setDefinition(String definition) {
            this.definition = definition;
    }

    public String getDefinition() {
            return definition;
    }

    public void setLabel(String label) {
            this.label = label;
    }

    public String getLabel() {
            return label;
    }

    public void setMlId(Integer mlId) {
            this.mlId = mlId;
    }

    public Integer getMlId() {
            return mlId;
    }

    public void setType(String type) {
            this.type = type;
    }

    public String getType() {
            return type;
    }

    public void setSTN(String STN) {
            this.STN = STN;
    }

    public String getSTN() {
            return STN;
    }

    public int getSynonymCount() {
         return synonymCount;
    }

    public int getWordCount() {
         return wordCount;
    }


    public ArrayList<String> getSABList() {
        return SABList;
    }

    public void setSABList(ArrayList<String> SABList) {
        this.SABList = SABList;
    }

    public HashMap<String, ArrayList<Synonym>> getSuiList() {
        return suiList;
    }

    public void setSuiList(HashMap<String, ArrayList<Synonym>> SuiList) {
        this.suiList = SuiList;
        this.synonymCount = countSynonyms(SuiList);
    }

    private int countWords(String string){
        return string.split(" ").length;
    }

    private int countSynonyms(HashMap<String, ArrayList<Synonym>> SuiList){
        int count = 0;
        for (ArrayList<Synonym> auiList : SuiList.values()){
            count = count + auiList.size();
        }
        return count;
    }

    public int compareTo(UMLSWord w) {

            if(wordCount == w.getWordCount()){
                return (synonymCount > w.getSynonymCount() ? -1 :
                (synonymCount == w.getSynonymCount() ? 0 : 1));
            }
            else
                return (wordCount > w.getWordCount() ? -1 : 1);
    }
}
