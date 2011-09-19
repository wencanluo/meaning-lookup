package org.ml.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ml.engine.MlDisambiguator;
import org.ml.util.LogHelper;

public class MeaningServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public MeaningServlet() {
        super();
    }

	public void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
				
		String action = request.getParameter("action");
		String folder = "";
		if(request.getParameter("folder")!=null)
			folder = request.getParameter("folder") + "/";
		String backPage = "index.jsp";
		if(request.getParameter("back_page")!=null)
			backPage = request.getParameter("back_page")+".jsp";
		
		if(action.equals("find_meaning")){
			LogHelper.debug(this, "Find Meaning");
			String text = (String) request.getParameter("textToProcess");
			if(text==null) 
				forward(request,response,"error/default_error.jsp");
			else{ 
				request.getSession().setAttribute("textToProcess", request.getParameter("textToProcess"));
				request.getSession().setAttribute("conPart", MlDisambiguator.disambiguate(text));
				request.getSession().setAttribute("newSearch", "true");
				if(request.getParameter("verbose")!=null)
					forward(request,response,folder + backPage + "?verbose=true");
				else
					forward(request,response,folder + backPage);
			}
		}else
		if(action.equals("reset_search")){
			request.getSession().removeAttribute("textToProcess");
			request.getSession().removeAttribute("conPart");
			forward(request,response,folder + backPage);
		}else
		if(action.equals("show_syns")){
			forward(request,response,folder + backPage + "?syns="+request.getParameter("syns"));	
		}else
		if(action.equals("hide_syns")){
			forward(request,response,folder + backPage);	
		}
		
		
	}
	
}
