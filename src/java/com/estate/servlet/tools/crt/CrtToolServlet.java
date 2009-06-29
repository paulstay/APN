package com.estate.servlet.tools.crt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.estate.CrtTool;

public class CrtToolServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8172120992214311552L;

	/**
	 * Constructor of the object.
	 */
	public CrtToolServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/crt/tool.jsp";
		String pageView = request.getParameter("pageView");
		
		// Process the form elements
		if(pageView.equals("PROCESS")){
			CrtTool crt = (CrtTool) session.getAttribute("crt");
			
			double irsRate = Utils.getPercentParameter(request, "irsRate", .05);
			crt.setIrsRate(irsRate);
			
			Date irsDate = Utils.getDateParameter(request, "irsDate", new Date());
			crt.setIrsDate(irsDate);
			
			int frequency = Utils.getIntegerParameter(request, "frequency", 1);
			crt.setFrequency(frequency);
			
			int uniLag = Utils.getIntegerParameter(request, "uniLag", 0);
			crt.setUniLag(uniLag);
			
			double investmentReturn = Utils.getPercentParameter(request, "investmentReturn", .05);
			crt.setInvestmentReturn(investmentReturn);

			String payoutOption = Utils.getStringParameter(request, "payoutOption", "L");
			crt.setPayoutOption(payoutOption);
			
			double payoutRate = Utils.getPercentParameter(request, "payoutRate", .05);
			
			if(payoutRate > crt.getMaxPayout()) {
				crt.setPayoutRate(crt.getMaxPayout());
				request.setAttribute("errorPayout", " * payout exceeded maximum, reset to maximum");
			} else 	if(payoutRate < .05){
				crt.setPayoutRate(.05);
				request.setAttribute("errorPayout", "* payout must be at least 5%, reset to minimum");
			} else {
				crt.setPayoutRate(payoutRate);
			}
			
			double payoutGrowth = Utils.getPercentParameter(request, "payoutGrowth", 0);
			double payoutIncome = Utils.getPercentParameter(request, "payoutIncome", 0);
			
			// Need to add a check here to make sure these add up to payout rate!
			crt.setPayoutGrowth(payoutGrowth);
			crt.setPayoutIncome(payoutIncome);
			
			double adjustedGrossIncome = Utils.getDoubleParameter(request, "adjustedGrossIncome", 0);
			crt.setAdjustedGrossIncome(adjustedGrossIncome);
			
			double taxRate = Utils.getPercentParameter(request, "taxRate", .35);
			crt.setTaxRate(taxRate);
			
			double estateTaxRate = Utils.getPercentParameter(request, "estateTaxRate", 0);
			crt.setEstateTaxRate(estateTaxRate);
			
			double capitalGainsRate = Utils.getPercentParameter(request, "capitalGainsRate", 0);
			crt.setCapitalGainsRate(capitalGainsRate);
			
			double lifePremium = Utils.getDoubleParameter(request, "lifePremium", 0);
			crt.setLifePremium(lifePremium);
			
			double lifeDeathBenefit = Utils.getDoubleParameter(request, "lifeDeathBenefit", 0);
			crt.setLifeDeathBenefit(lifeDeathBenefit);
			
			session.setAttribute("crt", crt);
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
