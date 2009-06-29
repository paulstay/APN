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

import com.estate.servlet.Utils;
import com.teag.estate.QprtTool;

public class QprtToolServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7200898463310558263L;

	/**
	 * Constructor of the object.
	 */
	public QprtToolServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/qprt/tool.jsp";
		SimpleDateFormat df = new SimpleDateFormat("M/dd/yyyy");
		Date now = new Date();
		String pageView = request.getParameter("pageView");
		
		// Update the tool form here!
		if(pageView.equalsIgnoreCase("PROCESS")) {
			int numTrusts = Utils.getIntegerParameter(request, "numberOfTrusts", 1);
			qprt.setNumberOfTrusts(numTrusts);
			
			double irsRate = Utils.getPercentParameter(request, "irsRate", .05);
			qprt.setAfrRate(irsRate);

			Date afrDate = Utils.getDateParameter(request, "afrDate", now);
			qprt.setAfrDate(df.format(afrDate));
			
			int clientTerm = Utils.getIntegerParameter(request, "clientTerm", 10);
			qprt.setClientTerm(clientTerm);
			
			int clientStartTerm = Utils.getIntegerParameter(request, "clientStartTerm", 1);
			qprt.setClientStartTerm(clientStartTerm);
			
			double clientPriorGifts = Utils.getDoubleParameter(request, "clientPriorGifts", 0);
			qprt.setClientPriorGifts(clientPriorGifts);
			
			if(qprt.getNumberOfTrusts()>1) {

				int spouseTerm = Utils.getIntegerParameter(request, "spouseTerm", 10);
				qprt.setSpouseTerm(spouseTerm);
				
				int spouseStartTerm = Utils.getIntegerParameter(request, "spouseStartTerm", 1);
				qprt.setSpouseStartTerm(spouseStartTerm);
				
				double spousePriorGifts = Utils.getDoubleParameter(request, "spousePriorGifts", 0);
				qprt.setSpousePriorGifts(spousePriorGifts);
			}
			
			int homeType = Utils.getIntegerParameter(request, "typeOfHome", 1);
			qprt.setTypeOfHome(homeType);
			
			double frac = Utils.getPercentParameter(request, "fractionalDiscountRate", .15);
			qprt.setFractionalInterestDiscount(frac);
			
			double estateTaxRate = Utils.getPercentParameter(request, "estateTaxRate", .55);
			qprt.setEstateTaxRate(estateTaxRate);
			
			double rentAfterTerm = Utils.getPercentParameter(request, "rentAfterTerm", 0);
			qprt.setRentAfterTerm(rentAfterTerm);
			
			int finalDeath = Utils.getIntegerParameter(request, "finalDeath", 25);
			qprt.setFinalDeath(finalDeath);
			
			boolean reversion = Utils.getBooleanParameter(request, "reversion", true);
			qprt.setRevisionRetained(reversion);
			
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
