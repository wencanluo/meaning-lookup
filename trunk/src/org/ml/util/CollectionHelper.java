package org.ml.util;


import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CollectionHelper {
	
	public static HashMap sortHashMapOnValues(Map<String, Integer> cuis){
		  List list = new LinkedList(cuis.entrySet());
		     Collections.sort(list, new Comparator() {
		          public int compare(Object o1, Object o2) {
		               return ((Comparable) ((Map.Entry) (o1)).getValue())
		              .compareTo(((Map.Entry) (o2)).getValue());
		          }
		     });
		// logger.info(list);
		HashMap result = new HashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
		     Map.Entry entry = (Map.Entry)it.next();
		     result.put(entry.getKey(), entry.getValue());
		     }
		return result;
	}
	
	public static String getStringOfValuesFromHashMap(HashMap list){
		int i = 0 ; 
		String stringValue = "";
		Iterator<String> it = list.keySet().iterator(); 
		while(it.hasNext() && i < Global.MAX_NUMBER_OF_UMLS_CUIS){  
			stringValue += it.next()+",";
			i++;
		}
		return stringValue;
	}

}
