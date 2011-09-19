package org.ml.web;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public BaseServlet() {
        super();
    }
	
    protected String xmlCleaner(String xml) {
		Pattern p = Pattern.compile("[a-zA-Z]+(?=(_-pcsubclass.*?>))");
		Matcher m = p.matcher(xml);
		String xmlclean = xml;
		while(m.find()){
			System.out.println(m.group());
			xmlclean = xmlclean.replaceAll("<org.apache.openjpa.enhance.(.*)_-"+m.group()+"_-pcsubclass.*>", "<"+m.group()+">");
			xmlclean = xmlclean.replaceAll("</org.apache.openjpa.enhance.(.*)_-"+m.group()+"_-pcsubclass.*>", "</"+m.group()+">");
		}
		return xmlclean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	abstract public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException;

	public void forward(HttpServletRequest request, HttpServletResponse response, String link){
		try{
			RequestDispatcher dispatcher = request.getRequestDispatcher(link);
			dispatcher.forward(request, response);		  
		}catch(ServletException se){
		}catch(IOException ie){}
	}

}

