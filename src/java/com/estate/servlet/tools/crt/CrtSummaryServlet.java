package com.estate.servlet.tools.crt;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.estate.CrtTool;
import com.teag.webapp.EstatePlanningGlobals;


public class CrtSummaryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6215615204077602178L;

	/**
	 * Constructor of the object.
	 */
	public CrtSummaryServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/crt/summary.jsp";
		
		CrtTool crt = (CrtTool) session.getAttribute("crt");
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session.getAttribute("epg");

		crt.calculate();
		
		// Get the table lengths so we can printout the whole table.
		int tableLength = crt.getOutRightSale().length -1;
		request.setAttribute("tableLength", tableLength);
		
		int cLe = (int) Math.round(epg.getClientLifeExp());
		int sLe = (int) Math.round(epg.getSpouseLifeExp());

		request.setAttribute("cle", cLe);
		request.setAttribute("sle", sLe);
		
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