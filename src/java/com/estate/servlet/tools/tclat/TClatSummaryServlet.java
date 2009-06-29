package com.estate.servlet.tools.tclat;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.estate.TClatTool;


public class TClatSummaryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3604120890969331828L;

	/**
	 * Constructor of the object.
	 */
	public TClatSummaryServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/tclat/summary.jsp";
		
		TClatTool tclat = (TClatTool) session.getAttribute("tclat");
		tclat.calculate();
		
		String frequency = "Annual";
		
		if(tclat.getAnnuityFreq()== 2)
			frequency = "SemiAnnual";
		if(tclat.getAnnuityFreq() == 4)
			frequency = "Quarterly";
		if(tclat.getAnnuityFreq() == 12)
			frequency = "Monthly";
		
		request.setAttribute("frequency", frequency);
		
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
