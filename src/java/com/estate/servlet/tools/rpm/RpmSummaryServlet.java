package com.estate.servlet.tools.rpm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.estate.RpmTool;

public class RpmSummaryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6496993592505405853L;

	/**
	 * Constructor of the object.
	 */
	public RpmSummaryServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/rpm/summaryComparison.jsp";
		
		RpmTool rpm = (RpmTool) session.getAttribute("rpm");
		String pageView = request.getParameter("pageView");
		
		rpm.calculate();
		double cTable[][];
		double rTable[][];

		if(pageView.equals("rpm")) {
			rTable = rpm.getRpTable().getRpmTable();
			path = "/WEB-INF/jsp/estatetools/rpm/summaryRpm.jsp";
			request.setAttribute("rTable", rTable);
			request.setAttribute("tableLen", rTable.length);
		} else if(pageView.equals("current")){
			cTable = rpm.getRpTable().getRpTable();
			path = "/WEB-INF/jsp/estatetools/rpm/summaryCurrent.jsp";
			request.setAttribute("cTable", cTable);
			request.setAttribute("tableLen", cTable.length);
		} else {
			rTable = rpm.getRpTable().getRpmTable();
			cTable = rpm.getRpTable().getRpTable();
			path = "/WEB-INF/jsp/estatetools/rpm/summaryComparison.jsp";
			request.setAttribute("rTable", rTable);
			request.setAttribute("cTable", cTable);
			request.setAttribute("tableLen", rTable.length);
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
