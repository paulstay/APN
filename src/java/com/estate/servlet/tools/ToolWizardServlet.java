package com.estate.servlet.tools;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.bean.AssetUtilityBean;
import com.teag.bean.EstatePlanningToolBean;
import com.teag.webapp.EstatePlanningGlobals;

public class ToolWizardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6376762649487929708L;

	/**
	 * Constructor of the object.
	 */
	public ToolWizardServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/toolwizard.jsp";
		
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session.getAttribute("epg");
		// Get tool list of existing tools!
		EstatePlanningToolBean estateTools = new EstatePlanningToolBean();
		Object[] toolList = estateTools.getEstateToolList(epg.getScenarioId());
		
		ArrayList<EstatePlanningToolBean> toolArray = new ArrayList<EstatePlanningToolBean>();
		for(int i=0; i < toolList.length; i++) {
			toolArray.add((EstatePlanningToolBean)toolList[i]);
		}
		
		request.setAttribute("toolArray", toolArray);

		AssetUtilityBean aub = new AssetUtilityBean();
		aub.cleanDB(-epg.getScenarioId());
		
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
