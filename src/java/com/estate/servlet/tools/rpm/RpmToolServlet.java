package com.estate.servlet.tools.rpm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.estate.RpmTool;

public class RpmToolServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 815891426486578874L;

	/**
	 * Constructor of the object.
	 */
	public RpmToolServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/rpm/tool.jsp";
		
		RpmTool rpm = (RpmTool) session.getAttribute("rpm");
		
		String pageView = request.getParameter("pageView");
		
		if(pageView.equalsIgnoreCase("process")){
			int term = Utils.getIntegerParameter(request, "term", 10);
			rpm.setTerm(term);
			double stateIncomeTaxRate = Utils.getPercentParameter(request, "stateIncomeTaxRate", 0);
			rpm.setStateIncomeTaxRate(stateIncomeTaxRate);
			double lifePremium = Utils.getDoubleParameter(request, "lifePremium", 0);
			rpm.setLifePremium(lifePremium);
			double lifeDeathBenefit = Utils.getDoubleParameter(request, "lifeDeathBenefit", 0);
			rpm.setLifeDeathBenefit(lifeDeathBenefit);
			double securitiesGrowth = Utils.getPercentParameter(request, "securitiesGrowth", .05);
			rpm.setSecuritiesGrowth(securitiesGrowth);
			double securitiesIncome = Utils.getPercentParameter(request, "securitiesIncome", 0.02);
			rpm.setSecuritiesIncome(securitiesIncome);
			double securitiesTurnover = Utils.getPercentParameter(request, "securitiesTurnover", .75);
			rpm.setSecuritiesTurnover(securitiesTurnover);
			session.setAttribute("rpm", rpm);
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request,response);

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
