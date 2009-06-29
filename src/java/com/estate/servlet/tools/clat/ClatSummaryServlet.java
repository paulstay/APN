package com.estate.servlet.tools.clat;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.estate.ClatTool;
import com.teag.webapp.EstatePlanningGlobals;

public class ClatSummaryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3533426293192695961L;

	/**
	 * Constructor of the object.
	 */
	public ClatSummaryServlet() {
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
		
		ClatTool clat = (ClatTool)session.getAttribute("clat");
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session
		.getAttribute("epg");
		
		if(!clat.getClatType().equalsIgnoreCase("T")){
			clat.setAge1(epg.getClientAge());
			clat.setAge2(epg.getSpouseAge());
		} else {
			clat.setAge1(0);
			clat.setAge2(0);
		}
		
		if(clat.getClatType().equalsIgnoreCase("L")){
			clat.setTerm(0);
		}

		clat.calculate();
		clat.buildStdClat();
		double[][] schedule = clat.getSchedule();
		
		request.setAttribute("schedule", schedule);
		session.setAttribute("clat", clat);
		
		RequestDispatcher dispatch  = request.getRequestDispatcher("/WEB-INF/jsp/estatetools/clat/summary.jsp");
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
