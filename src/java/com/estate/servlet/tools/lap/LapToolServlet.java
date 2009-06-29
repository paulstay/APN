package com.estate.servlet.tools.lap;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.estate.LiquidAssetProtectionTool;

public class LapToolServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1711558756074211066L;

	/**
	 * Constructor of the object.
	 */
	public LapToolServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/lap/tool.jsp";
		String pageView = request.getParameter("pageView");

		LiquidAssetProtectionTool lap = (LiquidAssetProtectionTool) session.getAttribute("lap");
		
		if(pageView.equalsIgnoreCase("process")) {
			String description = Utils.getStringParameter(request, "description", "Lap Plan");
			lap.setDescription(description);
			double annuityMonthlyIncome = Utils.getDoubleParameter(request, "annuityMonthlyIncome", 0);
			lap.setAnnuityMonthlyIncome(annuityMonthlyIncome);
			double annuityExclusionRatio = Utils.getPercentParameter(request, "annuityExclusionRate", .75);
			lap.setAnnuityExclusionRatio(annuityExclusionRatio);
			double incomeTaxRate = Utils.getPercentParameter(request, "incomeTaxRate", .35);
			lap.setIncomeTaxRate(incomeTaxRate);
			double estateTaxRate = Utils.getPercentParameter(request, "estateTaxRate", .55);
			lap.setEstateTaxRate(estateTaxRate);
			double lifeFaceValue = Utils.getDoubleParameter(request, "lifeFaceValue", 0);
			lap.setLifeFaceValue(lifeFaceValue);
			double lifePremium = Utils.getDoubleParameter(request, "lifePremium", 0);
			lap.setLifePremium(lifePremium);
			boolean overFunded = Utils.getBooleanParameter(request, "overFunded", true);
			lap.setOverFunded(overFunded);
		}

		session.setAttribute("lap", lap);
		
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
