package com.estate.servlet.tools.mgen;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.constants.InputMaps;
import com.estate.servlet.Utils;
import com.teag.estate.MGTrustTool;

public class MgenToolServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6209996523982006297L;

	

	/**
	 * Constructor of the object.
	 */
	public MgenToolServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/mgen/tool.jsp";

		String pageView = request.getParameter("pageView");
		
		MGTrustTool mgen = (MGTrustTool) session.getAttribute("mgen");
		
		// Handle tool form data
		if(pageView.equalsIgnoreCase("PROCESS")) {
			int yearsPerGeneration = Utils.getIntegerParameter(request, "yearsPerGeneration", 25);
			mgen.setYearsPerGeneration(yearsPerGeneration);
			double inflationRate = Utils.getPercentParameter(request, "inflationRate", .03);
			mgen.setInflationRate(inflationRate);
			int term = Utils.getIntegerParameter(request, "term", 25);
			mgen.setTerm(term);
			double payoutRate = Utils.getPercentParameter(request, "payoutRate", .06);
			mgen.setPayoutRate(payoutRate);
			double trusteeRate = Utils.getPercentParameter(request, "trusteeRate", .01);
			mgen.setTrusteeRate(trusteeRate);
			String trustState = Utils.getStringParameter(request, "trustState", "CA");
			mgen.setTrustState(trustState);
			double combinedIncomeTaxRate = Utils.getPercentParameter(request, "combinedIncomeTaxRate", .35);
			mgen.setCombinedIncomeTaxRate(combinedIncomeTaxRate);
			double capitalGainsRate = Utils.getPercentParameter(request, "capitalGainsRate", .15);
			mgen.setCapitalGainsRate(capitalGainsRate);
			boolean lawOfPerpetuity = Utils.getBooleanParameter(request, "lawOfPerpetuity", true);
			mgen.setLawOfPerpetuity(lawOfPerpetuity);
			double mgenIncomeTax = Utils.getPercentParameter(request, "delawareCombinedTaxRate", .35);
			mgen.setDelawareCombinedTaxRate(mgenIncomeTax);
			double mgenCapGains = Utils.getPercentParameter(request, "delawareCapitalGainsRate", 0);
			mgen.setDelawareCapitalGainsRate(mgenCapGains);
			String mgenTrustState = Utils.getStringParameter(request, "mgenTrustState", "South Dakota");
			mgen.setMgenTrustState(mgenTrustState);
		}
		
		Map<String, String> mgenStates = new LinkedHashMap<String, String>();
		mgenStates.put("Deleware","DE");
		mgenStates.put("Wisconsin", "WI");
		mgenStates.put("South Dakota","SD");
		mgenStates.put("Alaska","AK");
		mgenStates.put("Idaho", "ID");
	
		request.setAttribute("states",InputMaps.states);
		request.setAttribute("mgenStates", mgenStates);
		
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
		// We put this here so we only need to do it once!
		
	}

}
