/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ml.core;
import java.util.ArrayList;
import java.util.HashMap;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.graph.*;
import java.io.StringWriter;
import org.apache.commons.lang.StringEscapeUtils;
import com.hp.hpl.jena.xmloutput.impl.Basic;
import java.util.Iterator;
import java.util.ListIterator;
/**
 *
 * @author adivecha
 */
public class RdfUmlsModel {

  private Model model ;
  private int stmtOrder;

   public RdfUmlsModel(){
       model = ModelFactory.createDefaultModel();
      }

   public void addUmlsWord(UMLSWord word){

       String umlsBase = "http://link.informatics.stonybrook.edu/umls/";
       String umlsCUI = umlsBase + "CUI/";
       String rdfsBase = "http://www.w3.org/2000/01/rdf-schema#";
       Property hasLabel = model.createProperty(rdfsBase + "label");
       Property hasType = model.createProperty(umlsBase + "hasSemanticType");
       Property hasSTN = model.createProperty(umlsBase + "hasSTY");
       Property hasDefinition = model.createProperty(rdfsBase + "comment"); // what is the ns for definition ?
       Property synonymList = model.createProperty(umlsBase + "synonymList");
       Property hasSynonym = model.createProperty(umlsBase + "hasSynonym");
       Property wordCount = model.createProperty(umlsBase + "wordCount");
       Property auiCount = model.createProperty(umlsBase + "AUICount");
       Resource umlsCuiResource = model.createResource(umlsCUI + word.getCUI());
       Resource umlsSTYResource = model.createResource(umlsBase + "STY/" + word.getSTN());

       //Properties for SUIs and AUIS

       Property hasSui = model.createProperty(umlsBase + "hasSUI");
       Property hasAui = model.createProperty(umlsBase + "hasAUI");
       Property hasSource = model.createProperty(umlsBase + "hasSource");

       if(word.getLabel()!= null)
            umlsCuiResource.addProperty(hasLabel, word.getLabel());
       
       if(word.getType()!=null)
            umlsCuiResource.addProperty(hasType, word.getType());

       if(word.getSTN()!=null)
            umlsCuiResource.addProperty(hasSTN, umlsSTYResource);

       if(word.getDefinition()!=null)
            umlsCuiResource.addProperty(hasDefinition, word.getDefinition());

       umlsCuiResource.addLiteral(auiCount, word.getSynonymCount());
       umlsCuiResource.addLiteral(wordCount, word.getWordCount());

       if(word.getSynonyms()!= null){

               ArrayList<UMLSWord> synonymnsList = word.getSynonyms();
               Resource blankNode = model.createResource(synonymList);
               for(int i = 0 ; i < synonymnsList.size(); i++) {
                   blankNode.addProperty(hasSynonym, synonymnsList.get(i).getLabel());
               }
       }
       
       
       if(word.getSuiList()!= null){

           Resource suiNodeList = model.createResource(synonymList);
           umlsCuiResource.addProperty(synonymList, suiNodeList);
           HashMap <String, ArrayList<Synonym>> suiList = word.getSuiList();
           Iterator suiIterator = suiList.keySet().iterator();
           while(suiIterator.hasNext()){
               String sui = suiIterator.next().toString();
               Resource suiNode = model.createResource(umlsBase + "SUI/" + sui);        // **** Please check the URI for SUI
               suiNodeList.addProperty(hasSui, suiNode);
               ArrayList<Synonym> auiList = suiList.get(sui);
               ListIterator auiIterator = auiList.listIterator();
               while(auiIterator.hasNext()){
                   Synonym auiObject = (Synonym)auiIterator.next();
                   Resource auiNode = model.createResource(umlsBase + "AUI/" + auiObject.getAui());   // **** Please check the URI for AUI
                   auiNode.addProperty(hasSource, auiObject.getSource());
                   suiNode.addProperty(hasAui, auiNode);
               }

           }

       }
       //adds order number to the umlsCuiResource while doing addUmlsWord
      
   }

   public String toNTriplesDisplay(){
       StringEscapeUtils sv = new StringEscapeUtils();
       StringWriter sw = new StringWriter();
       model.write(sw, "N-TRIPLE");
       return sv.escapeHtml(sw.toString());
   }

    public String toRdfXmlDisplay(){
        StringEscapeUtils sv = new StringEscapeUtils();
       StringWriter sw = new StringWriter();
       model.write(sw, "RDF/XML");
       return sv.escapeXml(sw.toString());
   }


    public String toNTriples(){
       StringWriter sw = new StringWriter();
       model.write(sw, "N-TRIPLE");
       return sw.toString();
   }

    public String toRdfXml(){
       StringWriter sw = new StringWriter();
       model.write(sw, "RDF/XML");
       return sw.toString();
   }

}
