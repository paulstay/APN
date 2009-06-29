package com.estate.servlet.tools.crum;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.estate.CrummeyTool;

public class CrumToolServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -88725104485739352L;

	/**
	 * Constructor of the object.
	 */
	public CrumToolServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/crum/tool.jsp";
		
		CrummeyTool crum = (CrummeyTool) session.getAttribute("crum");
		
		String pageView = request.getParameter("pageView");
		
		if(pageView != null && pageView.equalsIgnoreCase("process")){
			double annualGift = Utils.getDoubleParameter(request, "annualGift", 0);
			crum.setAnnualGift(annualGift);
			int term = Utils.getIntegerParameter(request, "term", 0);
			crum.setTerm(term);
			double lifePremium = Utils.getDoubleParameter(request, "lifePremium", 0);
			crum.setLifePremium(lifePremium);
			double lifeDeathBenefit = Utils.getDoubleParameter(request, "lifeDeathBenefit", 0);
			crum.setLifeDeathBenefit(lifeDeathBenefit);
			boolean withMgt = Utils.getBooleanParameter(request, "withMgt", false);
			crum.setWithMgt(withMgt);
		}
		
		session.setAttribute("crum", crum);
		
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
