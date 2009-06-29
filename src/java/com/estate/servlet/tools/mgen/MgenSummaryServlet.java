package com.estate.servlet.tools.mgen;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.estate.MGTrustTool;

public class MgenSummaryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -218300040058710601L;

	/**
	 * Constructor of the object.
	 */
	public MgenSummaryServlet() {
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
		String path = "/WEB-INF//jsp/estatetools/mgen/summary.jsp";
		MGTrustTool mgen = (MGTrustTool) session.getAttribute("mgen");
		
		mgen.calculate();
		
		String header1 = "First Geneartion (" + Integer.toString(mgen.getLifeExpectancy()) + " years)";
		String header2 = "Second Geneartion (" + Integer.toString(mgen.getYearsPerGeneration()) + " years)";
		String header3 = "Third Geneartion (" + Integer.toString(mgen.getYearsPerGeneration()) + " years)";
		String header4 = "Fourth Geneartion (" + Integer.toString(mgen.getYearsPerGeneration()) + " years)";

		String hdrs[] = {header1, header2, header3, header4};
		request.setAttribute("hdrs",hdrs);
		
		double s1 = mgen.getKeepInEstate()[3][1] - mgen.getKeepInEstate()[3][2];
		double s2 = (mgen.getCurrentStateTrust()[3][1] - mgen.getCurrentStateTrust()[3][2])/s1;
		double s3 = (mgen.getDelawareTrust()[3][1] - mgen.getDelawareTrust()[3][2])/s1;

		request.setAttribute("lev1", 1);
		request.setAttribute("lev2", s2);
		request.setAttribute("lev3", s3);
		
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
