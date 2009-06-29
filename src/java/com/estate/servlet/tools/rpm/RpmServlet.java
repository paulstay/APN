package com.estate.servlet.tools.rpm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.estate.RpmTool;
import com.teag.webapp.EstatePlanningGlobals;

public class RpmServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2953923882569074991L;

	/**
	 * Constructor of the object.
	 */
	public RpmServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/rpm/description.jsp";
		HttpSession session = request.getSession();
		
		RpmTool rpm = (RpmTool) session.getAttribute("rpm");
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session.getAttribute("epg");
		
		if( rpm == null )  {
			rpm = new RpmTool();
			rpm.setSecuritiesGrowth(.06);
			rpm.setSecuritiesIncome(.02);
			rpm.setSecuritiesTurnover(.75);
			rpm.setLifeDeathBenefit(0);
			rpm.setLifePremium(0);
			rpm.setStateIncomeTaxRate(0);
			rpm.setId(-epg.getScenarioId());
		}

		rpm.setClientAge(epg.getClientAge());
		rpm.setSpouseAge(epg.getSpouseAge());
		session.setAttribute("rpm", rpm);
		
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
