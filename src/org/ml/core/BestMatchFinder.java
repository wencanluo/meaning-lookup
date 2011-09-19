/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ml.core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author adivecha
 */
public class BestMatchFinder {

    private String words;
    private ArrayList<UMLSWord> umlsWords;


    public BestMatchFinder(String words, ArrayList<UMLSWord> umlsWords){
        this.words = words;
        this.umlsWords = umlsWords;
    }


    public ArrayList<UMLSWord> findBestMatches(){


        HashMap<String,Boolean> taggedWords = new HashMap<String,Boolean>();
        String[] splitWords;

            splitWords = words.split(" ");
    

        
        ArrayList<UMLSWord> bestMatches = new ArrayList<UMLSWord>();

        for(String wordEntity : splitWords){
            taggedWords.put(wordEntity, Boolean.FALSE);
        }

        for(UMLSWord word : umlsWords){

            int flag = 0;
            String[] splitLabel = word.getLabel().split(" ");
            for(String singleWord : splitLabel){
                if(taggedWords.containsKey(singleWord)){
                    if(taggedWords.get(singleWord) == Boolean.FALSE){
                       taggedWords.put(singleWord,Boolean.TRUE);
                       flag = 1;
                    }
                }


            }
          if (flag == 1){
              bestMatches.add(word);
          }
        }

            return bestMatches;
    }





}
