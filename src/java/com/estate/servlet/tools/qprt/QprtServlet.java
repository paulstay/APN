package com.estate.servlet.tools.qprt;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.estate.QprtTool;
import com.teag.webapp.EstatePlanningGlobals;

public class QprtServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4877368397397629716L;

	/**
	 * Constructor of the object.
	 */
	public QprtServlet() {
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
		QprtTool qprt = (QprtTool) session.getAttribute("qprt");
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session.getAttribute("epg");
		String path = "/WEB-INF/jsp/estatetools/qprt/description.jsp";
		SimpleDateFormat currentDate = new SimpleDateFormat("MM/dd/yyyy");
		Date now = new Date();
		
		if(qprt == null) {
			qprt = new QprtTool();
			qprt.setNumberOfTrusts(2);
			qprt.setBasis(0.0);
			qprt.setValue(0.0);
			qprt.setGrowth(0.0);
			qprt.setClientPriorGifts(0.0);
			qprt.setClientStartTerm(1);
			qprt.setClientTerm((int) epg.getClientLifeExp()-5);
			qprt.setSpousePriorGifts(0.0);
			qprt.setSpouseStartTerm(1);
			qprt.setSpouseTerm((int) epg.getSpouseLifeExp()-5);
			qprt.setFractionalInterestDiscount(.15);
			qprt.setEstateTaxRate(.55);
			qprt.setRentAfterTerm(.04);
			qprt.setRentGrowthRateForHeirs(.035);
			qprt.setAfrDate(currentDate.format(now));
			qprt.setAfrRate(.05);
			qprt.setTypeOfHome(1);
			qprt.setFinalDeath(25);
			qprt.setId(-epg.getScenarioId());
		}
		
		session.setAttribute("qprt", qprt);
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
