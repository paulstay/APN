package com.estate.servlet.tools.qprt;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.estate.QprtTool;
import com.teag.estate.QprtTrustTool;
import com.teag.webapp.EstatePlanningGlobals;

public class QprtSummaryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 588004076959565405L;

	/**
	 * Constructor of the object.
	 */
	public QprtSummaryServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/qprt/summary.jsp";
		
		// remove the old versions first!
		request.removeAttribute("cQprt");
		request.removeAttribute("sQprt");
		
		QprtTool qprt = (QprtTool) session.getAttribute("qprt");
		EstatePlanningGlobals epg = (EstatePlanningGlobals)session.getAttribute("epg");
		
		qprt.setClientAge(epg.getClientAge());
		qprt.setSpouseAge(epg.getSpouseAge());
		qprt.calculate();
		
		QprtTrustTool cQprt = qprt.getClientQprt();
		request.setAttribute("cQprt", cQprt);

		double qprtTaxEstate = cQprt.getTaxableGiftValue();
		double qprtGiftTax = qprtTaxEstate * qprt.getEstateTaxRate();
		double valueOfEstate = 0;

		// If the final death has not been set, than just calcualte the growth throught the first term,or the client term.
		int eYears = qprt.getFinalDeath();
		if(eYears <=0)
			eYears = qprt.getClientTerm();
		
		valueOfEstate = qprt.getValue() * Math.pow((1+qprt.getGrowth()),eYears);
		
		QprtTrustTool sQprt;
		
		if(qprt.getNumberOfTrusts()> 1) {
			sQprt = qprt.getSpouseQprt();
			qprtTaxEstate += sQprt.getTaxableGiftValue();
			qprtGiftTax = qprtTaxEstate * qprt.getEstateTaxRate();
			request.setAttribute("sQprt", sQprt);
		}
		
		request.setAttribute("qprtTaxEstate", qprtTaxEstate);
		request.setAttribute("qprtGiftTax", qprtGiftTax);
		request.setAttribute("valueOfEstate", valueOfEstate);
		request.setAttribute("eYears", eYears);
		
		
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
