package com.estate.servlet.tools.IditGrat;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.bean.AssetUtilityBean;
import com.teag.bean.EstatePlanningToolBean;
import com.teag.client.EPTAssets;
import com.teag.estate.IditTool;
import com.teag.webapp.EstatePlanningGlobals;


public class IditGratProcessServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7954359751249447494L;

	/**
	 * Constructor of the object.
	 */
	public IditGratProcessServlet() {
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
		IditTool idit = (IditTool) session.getAttribute("idit");
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session
				.getAttribute("epg");

		String pageView = request.getParameter("pageView");

		if (pageView != null && pageView.equals("save")) {
			if (idit.getId() < 0) {
				idit.insert();
				AssetUtilityBean aub = new AssetUtilityBean();
				aub.updateTools(idit.getId(), -epg.getScenarioId());
				EstatePlanningToolBean estate = new EstatePlanningToolBean();
				estate.setScenarioId(epg.getScenarioId());
				estate.setToolId(idit.getId());
				estate.setToolTableId(idit.getToolTableId());
				estate.setDescription("Intentionally Deffective Irrevocable Trust");
				estate.insert();
			} else {
				idit.update();
			}
			EPTAssets eptassets = new EPTAssets();
			eptassets.loadAssetList(epg.getScenarioId());
			epg.setEptassets(eptassets);
		}
		
		session.removeAttribute("sidit");

		// Send us back to the tool selection
		request.setAttribute("URI", request.getRequestURI());
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/servlet/ToolWizardServlet");
		dispatcher.forward(request, response);

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
