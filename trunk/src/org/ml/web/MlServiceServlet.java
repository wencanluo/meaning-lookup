/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ml.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ml.bo.UmlsBO;
import org.ml.core.MlConversationPart;
import org.ml.core.RdfUmlsModel;
import org.ml.core.UMLSWord;
import org.ml.engine.MlDisambiguator;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.ml.core.BestMatchFinder;

/**
 *
 * @author adivecha
 */
public class MlServiceServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            if(request.getParameter("textToProcess").length() > 512 ){
                response.sendError(response.SC_REQUEST_ENTITY_TOO_LARGE, "Text to Process Exceeded 512 Characters. Please try again");
            }

            else{
                RdfUmlsModel rdfModel = new RdfUmlsModel();
            if (request.getParameter("textToProcess") != null) {
                String textToProcess = request.getParameter("textToProcess");
                MlConversationPart conPart = MlDisambiguator.disambiguate(textToProcess);
                UMLSWord umlsword = null;
                ArrayList<JsonWord> jsonWordList = new ArrayList<JsonWord>();
                ArrayList<JsonWord> bestJsonWordList = new ArrayList<JsonWord>();
                ArrayList<UMLSWord> umlsWordList = new ArrayList<UMLSWord>();
                for (String key : UmlsBO.getUMLSWordsKeysSortedByNumberOfWords(((MlConversationPart) conPart))) {
                        umlsword = conPart.getUMLSWords().get(key);
                        umlsWordList.add(umlsword);
                        rdfModel.addUmlsWord(umlsword);
                }
                Collections.sort(umlsWordList);
                
                for(UMLSWord umlsWord : umlsWordList){
                    jsonWordList.add(new JsonWord(umlsWord));
                }

                 ArrayList<UMLSWord> bestMatch = new ArrayList<UMLSWord>();
                if(request.getParameter("textToProcess") != null) {
                    BestMatchFinder bestMatchFinder = new BestMatchFinder(request.getParameter("textToProcess"),umlsWordList);
                    bestMatch = bestMatchFinder.findBestMatches();
                 }

                for(UMLSWord umlsWord : bestMatch){
                    bestJsonWordList.add(new JsonWord(umlsWord));
                }

                if(request.getParameter("format") == null ? "json" == null : request.getParameter("format").equals("json")){
                    Gson gson = new Gson();  
                     response.setContentType("application/json");
                     //for (JsonWord jsonWordOutput : jsonWordList)
                     HashMap<String, ArrayList<JsonWord>> jsonOutputMap = new HashMap<String, ArrayList<JsonWord>>();
                     jsonOutputMap.put("Best Match", bestJsonWordList);
                     jsonOutputMap.put("All", jsonWordList);
                     out.println(gson.toJson(jsonOutputMap));
                }
                else if(request.getParameter("format") == null ? "rdf/xml" == null : request.getParameter("format").equals("rdf/xml"))
                {
                    response.setContentType("application/rdf+xml");
                    out.println(rdfModel.toRdfXml());
                }
                else{
                    response.setContentType("text/plain");
                    out.println(rdfModel.toNTriples());
                }
            }
          }
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

        class JsonWord{
        private String CUI;
        private String label;
        private String type;
        private ArrayList<UMLSWord> synonyms;
        private String definition;
        private ArrayList<String> SABList;

        public JsonWord(UMLSWord umlsWord){
            this.CUI = umlsWord.getCUI();
            this.label = umlsWord.getLabel();
            this.type = umlsWord.getType();
            this.definition = umlsWord.getDefinition();
            this.synonyms = umlsWord.getSynonyms();
            this.SABList=  umlsWord.getSABList();
        }
    }


}
