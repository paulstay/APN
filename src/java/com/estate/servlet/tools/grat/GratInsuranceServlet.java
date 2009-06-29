package com.estate.servlet.tools.grat;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.view.GratInsuranceView;
import com.teag.estate.GratTool;
import com.teag.webapp.EstatePlanningGlobals;

public class GratInsuranceServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8185696986159320270L;

	/**
	 * Constructor of the object.
	 */
	public GratInsuranceServlet() {
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
		GratTool grat = (GratTool) session.getAttribute("grat");
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session
		.getAttribute("epg");
		GratInsuranceView gv = new GratInsuranceView();

		if(grat.isUseLife()) {
			
			grat.setClientAge(epg.getClientAge());
			grat.setSpouseAge(epg.getSpouseAge());
			grat.calculate();
			grat.buildCFTable();
			
			gv.setTerm(grat.getClientTermLength()-1);
			gv.setGratValue(grat.getCfTotalValue()[(int)grat.getClientTermLength()-1]);
			gv.setEstateTaxes(gv.getGratValue()*grat.getEstateTaxRate());
			gv.setDeathBenefit(grat.getLifeDeathBenefit());
			gv.setPremium(grat.getLifeInsPremium());
			gv.setInsuranceCost(gv.getTerm() * gv.getPremium());
			gv.setCashValue(grat.getLifeCashValue());
			gv.setCostTax(gv.getInsuranceCost() - gv.getCashValue());
			gv.setCostProtection(gv.getInsuranceCost()-gv.getCashValue());
			gv.setPercentCost(1.0 - gv.getCostProtection());
		}
		session.setAttribute("gv",gv);
		
		// Forward to the discription page.
		RequestDispatcher dispatch = request
				.getRequestDispatcher("/WEB-INF/jsp/estatetools/grat/insurance.jsp");
		
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
