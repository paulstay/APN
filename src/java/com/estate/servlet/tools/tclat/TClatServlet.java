package com.estate.servlet.tools.tclat;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.estate.TClatTool;
import com.teag.webapp.EstatePlanningGlobals;

public class TClatServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6359779080261619783L;

	/**
	 * Constructor of the object.
	 */
	public TClatServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/tclat/description.jsp";
		
		TClatTool tclat = (TClatTool) session.getAttribute("tclat");
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session.getAttribute("epg");
		
		if(tclat == null) {
			tclat = new TClatTool();
			tclat.setId(-epg.getScenarioId());
			tclat.setAfrRate(.05);			// We really don't know what the rate will be just guess large.
			tclat.setIncomeTaxRate(.35); // This is the default for Federal
			tclat.setAfrRate(.05);		// IRS 7520 rate
			tclat.setAfrDate("10/1/2009");
			tclat.setAnnuityFreq(4);	// Payments frequency
			tclat.setAnnuityType(0);	// end begin 0= end 1 = begin
			tclat.setFinalDeath(25);	// We really don't need this.
			tclat.setTerm(20);
			tclat.setInclusive("Y");
		}
		
		session.setAttribute("tclat", tclat);
		
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
