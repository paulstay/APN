package com.estate.servlet.tools.scin;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.estate.SCINTool;

public class ScinToolServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2373887352679954476L;

	/**
	 * Constructor of the object.
	 */
	public ScinToolServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/scin/tool.jsp";
		String pageView = request.getParameter("pageView");

		SCINTool scin = (SCINTool) session.getAttribute("scin");

		
		if(pageView.equalsIgnoreCase("process")) {
			double irsRate = Utils.getPercentParameter(request, "irsRate", .042);
			scin.setIrsRate(irsRate);
			Date irsDate = Utils.getDateParameter(request, "irsDate", new Date());
			scin.setIrsDate(irsDate);
			int noteType = Utils.getIntegerParameter(request, "noteType", 0);
			scin.setNoteType(noteType);
			double noteRate = Utils.getPercentParameter(request, "noteRate", 0.042);
			scin.setNoteRate(noteRate);
			int term = Utils.getIntegerParameter(request, "term", 10);
			scin.setTerm(term);
			int yrsDeferred = Utils.getIntegerParameter(request, "yrsDeferred", 0);
			scin.setYrsDeferred(yrsDeferred);
			int endBegin = Utils.getIntegerParameter(request, "endBegin", 0);
			scin.setEndBegin(endBegin);
			int paymentType = Utils.getIntegerParameter(request, "paymentType", 0);
			scin.setPaymentType(paymentType);
		}

		session.setAttribute("scin", scin);
		
		
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
