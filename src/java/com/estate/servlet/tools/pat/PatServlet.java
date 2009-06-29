package com.estate.servlet.tools.pat;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.estate.PrivateAnnuityTool;
import com.teag.webapp.EstatePlanningGlobals;

public class PatServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3203049791200021010L;

	/**
	 * Constructor of the object.
	 */
	public PatServlet() {
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
		HttpSession session = request.getSession();
		String path = "/WEB-INF/jsp/estatetools/pat/description.jsp";
		PrivateAnnuityTool pat = (PrivateAnnuityTool) session.getAttribute("pat");
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session.getAttribute("epg");
		
		if(pat == null) {
			pat = new PrivateAnnuityTool();
			pat.setAfrDate(new Date());
			pat.setAfrRate(.05);
			pat.setIncomeTaxRate(.35);
			pat.setEstateTaxRate(.55);
			pat.setCapitalGainsRate(.15);
			pat.setAnnuityFreq(1);
			pat.setId(-epg.getScenarioId());
			pat.setUseBoth("C");
			pat.setDescription("Private Annuity");
		}
		
		session.setAttribute("pat", pat);
		
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
