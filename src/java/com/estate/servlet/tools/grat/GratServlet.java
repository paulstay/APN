package com.estate.servlet.tools.grat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.estate.GratTool;
import com.teag.webapp.EstatePlanningGlobals;

public class GratServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6585639017148934211L;

	/**
	 * Constructor of the object.
	 */
	public GratServlet() {
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
		
		GratTool tool = (GratTool) session.getAttribute("grat");
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session
				.getAttribute("epg");

		// If null than we need to create it and put in some default values
		if(tool == null) {
			SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
			Date now = new Date();

			tool = new GratTool();
			tool.setId(-epg.getScenarioId());
			tool.setNumTrusts(2);
			tool.setClientAge(epg.getClientAge());
			tool.setSpouseAge(epg.getSpouseAge());
			tool.setClientPriorGifts(0.0);
			tool.setSpousePriorGifts(0.0);
			tool.setClientStartTerm(1);
			tool.setClientTermLength( 10);
			tool.setSpouseStartTerm(1);
			tool.setSpouseTermLength( 15);
			tool.setEstateTaxRate(.55);
			tool.setIncomeTaxRate(.35);		// For now just include Fed.
			tool.setCalcType("A");
			tool.setRemainderInterest(0);
			tool.setAfrDate(df.format(now));
			tool.setAfrRate(.05);
			tool.setAnnuity(0);
			tool.setAnnuityFreq(4);
			tool.setAnnuityIncrease(0.0);
			tool.setAnnuityPaymentRate(0);
			tool.setTotalValue(0);
			tool.setDiscountedValue(0);
			tool.setWeightedGrowth(0);
			tool.setWeightedIncome(0);
			tool.setFinalDeath(25);
			tool.setUseLife(false);
		}
		session.setAttribute("grat",tool);
		
		// Forward to the discription page.
		RequestDispatcher dispatch = request
				.getRequestDispatcher("/WEB-INF/jsp/estatetools/grat/description.jsp");
		
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
