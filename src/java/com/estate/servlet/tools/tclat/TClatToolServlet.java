package com.estate.servlet.tools.tclat;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.estate.TClatTool;

public class TClatToolServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6808283753459553904L;

	/**
	 * Constructor of the object.
	 */
	public TClatToolServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/tclat/tool.jsp";
		
		TClatTool tclat = (TClatTool) session.getAttribute("tclat");
		String pageView = request.getParameter("pageView");
		
		if(pageView != null && pageView.equalsIgnoreCase("process")){
			int term = Utils.getIntegerParameter(request, "term", 20);
			tclat.setTerm(term);
			double afrRate = Utils.getPercentParameter(request, "afrRate", 0.05);
			tclat.setAfrRate(afrRate);
			tclat.setAfrDate("01/01/2009");
			int annuityFreq = Utils.getIntegerParameter(request, "annuityFreq",1);
			tclat.setAnnuityFreq(annuityFreq);
			int annuityType = Utils.getIntegerParameter(request, "annuityType", 0);	// Default end of year
			tclat.setAnnuityType(annuityType);
			double incomeTaxRate = Utils.getPercentParameter(request, "incomeTaxRate", 0.35);
			tclat.setIncomeTaxRate(incomeTaxRate);
			String inclusive = Utils.getStringParameter(request, "inclusive", "Y");
			tclat.setInclusive(inclusive);
			session.setAttribute("tclat", tclat);
		}
		
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
