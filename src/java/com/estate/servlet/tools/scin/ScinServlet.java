package com.estate.servlet.tools.scin;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.constants.ToolTableTypes;
import com.teag.estate.SCINTool;
import com.teag.webapp.EstatePlanningGlobals;

public class ScinServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 747358598401880375L;

	/**
	 * Constructor of the object.
	 */
	public ScinServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = "/WEB-INF/jsp/estatetools/scin/description.jsp";
		HttpSession session = request.getSession();
		SCINTool scin;
		
		try {
			scin = (SCINTool) session.getAttribute("scin");
		} catch (Exception e) {
			scin = null;
		}
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session.getAttribute("epg");
		
		if(scin == null){
			scin = new SCINTool();
			scin.setEndBegin(0);
			scin.setIrsDate(new Date());
			scin.setToolTableId(ToolTableTypes.SCIN.id());
			// This needs to be the scenario ID and negative for setup
			scin.setId(-epg.getScenarioId());
		}
		
		session.setAttribute("scin", scin);
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);

	}

	/**
	 * Returns information about the servlet, such as 
	 * author, version, and copyright. 
	 *
	 * @return String information about this servlet
	 */
	@Override
	public String getServletInfo() {
		return "This is my default servlet created by Eclipse";
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}

}
