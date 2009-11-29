package com.estate.servlet.client.setup;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.bean.CashFlowBean;
import com.teag.bean.ClientBean;

public class CashFlowInitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2707461580067257506L;

	/**
	 * Constructor of the object.
	 */
	public CashFlowInitServlet() {
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
		String path = "/WEB-INF/jsp/client/setup/cashflow/index.jsp";
		
		ClientBean client = (ClientBean) session.getAttribute("client");

		CashFlowBean cashFlow = new CashFlowBean();
		cashFlow.setOwnerId(client.getPrimaryId());
		cashFlow.initialize();

		/// If the pageView is update, than we need to add in properties from the form.
		String pageView = request.getParameter("pageView");
		
		if(pageView != null && pageView.equalsIgnoreCase("update")){
			int finalDeath = Utils.getIntegerParameter(request, "finalDeath", 25);
			cashFlow.setFinalDeath(finalDeath);
			double socialSecurity = Utils.getDoubleParameter(request, "socialSecurity", 0);
			cashFlow.setSocialSecurity(socialSecurity);
			double socialSecurityGrowth = Utils.getPercentParameter(request, "socialSecurityGrowth", .03);
			cashFlow.setSocialSecurityGrowth(socialSecurityGrowth);
			double inflation = Utils.getPercentParameter(request, "inflation", .03);
			cashFlow.setInflation(inflation);
			double stateTaxRate = Utils.getPercentParameter(request, "stateTaxRate", 0);
			cashFlow.setStateTaxRate(stateTaxRate);
			double charity = Utils.getPercentParameter(request, "charity", 0);
			cashFlow.setCharity(charity);
                        double depreciation = Utils.getDoubleParameter(request, "depreciation", 0);
                        cashFlow.setDepreciation(depreciation);
			cashFlow.update();
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("return")) {
			path = "/WEB-INF/jsp/client/setup/index.jsp";
		}

		// Make sure that we have it in the session!
		request.setAttribute("cashFlow", cashFlow);
		
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
