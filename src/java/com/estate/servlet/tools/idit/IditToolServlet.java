package com.estate.servlet.tools.idit;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.estate.SIditTool;

public class IditToolServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6789937141939218961L;

	/**
	 * Constructor of the object.
	 */
	public IditToolServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/idit1/tool.jsp";

		String pageView = request.getParameter("pageView");
		SIditTool sidit = (SIditTool) session.getAttribute("sidit");

		if(pageView.equalsIgnoreCase("process")){
			
			double noteRate = Utils.getPercentParameter(request, "noteRate", .05);
			sidit.setNoteRate(noteRate);
			int noteTerm = Utils.getIntegerParameter(request, "noteTerm", 10);
			sidit.setNoteTerm(noteTerm);
			double taxRate = Utils.getPercentParameter(request, "taxRate", .35);
			sidit.setTaxRate(taxRate);
			int finalDeath = Utils.getIntegerParameter(request, "finalDeath", 20);
			sidit.setFinalDeath(finalDeath);
			double lifePremium = Utils.getDoubleParameter(request, "lifePremium", 0);
			sidit.setLifePremium(lifePremium);
			double lifeDeathBenefit = Utils.getDoubleParameter(request, "lifeDeathBenefit", 0);
			sidit.setLifeDeathBenefit(lifeDeathBenefit);
			int years = Utils.getIntegerParameter(request, "lifePremiumYears", 0);
			sidit.setLifePremiumYears(years);
			int noteType = Utils.getIntegerParameter(request, "noteType", 2);	// Default is Interest + balloon payment
			sidit.setNoteType(noteType);
		}
		
		session.setAttribute("sidit", sidit);
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
