package com.estate.servlet.tools.mgen;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.estate.MGTrustTool;
import com.teag.webapp.EstatePlanningGlobals;


public class MgenServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5646569103079656956L;

	/**
	 * Constructor of the object.
	 */
	public MgenServlet() {
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

		doPost(request, response);
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
		String path = "/WEB-INF/jsp/estatetools/mgen/description.jsp";

		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		// Initialize the MGEN tool here if we are starting for the first time, otherwise
		// We just need to get it from the session.
		
		MGTrustTool mgen = (MGTrustTool) session.getAttribute("mgen");
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session.getAttribute("epg");
		
		if(mgen == null) {
			mgen = new MGTrustTool();
			mgen.setMgenTrustState("SD");
			mgen.setCapitalGainsRate(.20);
			mgen.setDelawareCapitalGainsRate(.2);
			mgen.setCombinedIncomeTaxRate(.35);
			mgen.setDelawareCombinedTaxRate(.396);
			mgen.setCurrentYear(cal.get(Calendar.YEAR));
			mgen.setGrantor(true);
			mgen.setUseCrummey(false);
			mgen.setUseLifeInsurance(false);
			mgen.setYearsPerGeneration(25);
			mgen.setInflationRate(.03);
			mgen.setPayoutRate(.03);
			mgen.setTrusteeRate(.01);
			mgen.setTurnoverRate(.75);
			mgen.setLifeDeathBenefit(0);
			mgen.setLifePremium(0);
			mgen.setTerm(10);
			mgen.setLawOfPerpetuity(false);
			mgen.setId(-epg.getScenarioId());
		}

		mgen.setLifeExpectancy((int)Math.max(epg.getClientLifeExp(),epg.getSpouseLifeExp()));
		
		session.setAttribute("mgen", mgen);
		
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
