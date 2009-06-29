package com.estate.servlet.tools.llc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.estate.LlcTool;
import com.teag.webapp.EstatePlanningGlobals;

public class LlcServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6630999881820286680L;

	/**
	 * Constructor of the object.
	 */
	public LlcServlet() {
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
		// Create a FlpTool, or get the existing tool.
		HttpSession session = request.getSession();

		LlcTool tool = (LlcTool) session.getAttribute("llc");
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session
				.getAttribute("epg");

		if (tool == null) {
			tool = new LlcTool();
			// This needs to be the scenario ID and negative for setup
			tool.setId(-epg.getScenarioId());
			tool.setDiscountRate(.35);
			tool.setManagerShares(.02);
			tool.setMemberShares(.98);
			tool.setPremiumGpValue(1.0);
			tool.setOwnerId(epg.getClient().getPrimaryId());
			tool.setId(-epg.getScenarioId());
			tool.setName("LLC");
			session.setAttribute("llc", tool);
		}

		// Forward to the discription page.
		RequestDispatcher dispatch = request
				.getRequestDispatcher("/WEB-INF/jsp/estatetools/llc/description.jsp");
		
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
