package com.estate.servlet.tools.crt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.estate.CrtTool;
import com.teag.webapp.EstatePlanningGlobals;

public class CrtServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2415512089400392456L;

	/**
	 * Constructor of the object.
	 */
	public CrtServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/crt/description.jsp";

		// Check to see if we have a CRT loaded in the session
		
		HttpSession session = request.getSession();
		
		CrtTool crt = (CrtTool) session.getAttribute("crt");	// WPT is really the CRT, 
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session.getAttribute("epg");

		// If crt is null, than this is probably the first time here, so create it.
		if(crt == null) {
			crt = new CrtTool();
			crt.setId(-epg.getScenarioId());
			crt.setToolType("CRUT");
			crt.setTerm(15);
			crt.setStartTerm(0);
			crt.setInvestmentReturn(.08);
			crt.setPayoutRate(.07);
			crt.setPayoutOption("P");
			crt.setPayoutGrowth(.04);
			crt.setPayoutIncome(.03);
			crt.setAdjustedGrossIncome(0);
			Date now = new Date();
			crt.setIrsDate(now);
			crt.setIrsRate(.05);
			crt.setLifePremium(0);
			crt.setLifeDeathBenefit(0);
			crt.setEstateTaxRate(.55);
			crt.setCapitalGainsRate(.15);
			crt.setTaxRate(.35);
			crt.setFrequency(1);
			crt.setUniLag(0);
		}

		crt.setClientAge(epg.getClientAge());
		crt.setSpouseAge(epg.getSpouseAge());
		crt.setFinalDeath(25);

		crt.initialCalc();
		
		session.setAttribute("crt", crt);
		
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
