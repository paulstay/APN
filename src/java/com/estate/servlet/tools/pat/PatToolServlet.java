package com.estate.servlet.tools.pat;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.estate.PrivateAnnuityTool;

public class PatToolServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1322098217667596184L;

	/**
	 * Constructor of the object.
	 */
	public PatToolServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/pat/tool.jsp";
		
		PrivateAnnuityTool pat = (PrivateAnnuityTool) session.getAttribute("pat");

		String pageView = request.getParameter("pageView");
		if(pageView.equalsIgnoreCase("process")) {
			String description = Utils.getStringParameter(request, "description", "");
			pat.setDescription(description);
			double afrRate = Utils.getPercentParameter(request, "afrRate", .05);
			pat.setAfrRate(afrRate);
			Date afrDate = Utils.getDateParameter(request, "afrDate", new Date());
			pat.setAfrDate(afrDate);
			double incomeTaxRate = Utils.getPercentParameter(request, "incomeTaxRate", .35);
			pat.setIncomeTaxRate(incomeTaxRate);
			double estateTaxRate = Utils.getPercentParameter(request, "estateTaxRate", .55);
			pat.setEstateTaxRate(estateTaxRate);
			double capitalGainsRate = Utils.getPercentParameter(request, "capitalGainsRate", .15);
			pat.setCapitalGainsRate(capitalGainsRate);
			int annuityFreq = Utils.getIntegerParameter(request, "annuityFreq", 1);
			pat.setAnnuityFreq(annuityFreq);
			double annuityIncrease = Utils.getPercentParameter(request, "annuityIncrease", 0);
			pat.setAnnuityIncrease(annuityIncrease);
			int annuityType = Utils.getIntegerParameter(request, "annuityType", 0);	// End of year default
			pat.setAnnuityType(annuityType);
			String useBoth = Utils.getStringParameter(request, "useBoth", "C");
			pat.setUseBoth(useBoth);
		}
		
		session.setAttribute("pat", pat);
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
