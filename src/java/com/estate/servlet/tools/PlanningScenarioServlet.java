package com.estate.servlet.tools;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.bean.ClientBean;
import com.teag.bean.EstatePlanningToolBean;
import com.teag.bean.ScenarioBean;
import com.teag.client.EPTAssets;
import com.teag.webapp.EstatePlanningGlobals;

public class PlanningScenarioServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 151509302197144873L;

	/**
	 * Constructor of the object.
	 */
	public PlanningScenarioServlet() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String path = "/client/index.jsp"; // Default return

		String action = request.getParameter("action");

		if (action != null && !action.equalsIgnoreCase("return")) {
			ClientBean client = (ClientBean) session.getAttribute("client");
			EPTAssets eptAssets = new EPTAssets();
			EstatePlanningGlobals epg = (EstatePlanningGlobals) session
					.getAttribute("epg");

			// We first check to see if there is a existing Scenario
			// Later we can add the selection of additional Scenario's
			ScenarioBean scenario = new ScenarioBean();

			ArrayList<ScenarioBean> sList = scenario
					.getBeans(ScenarioBean.OWNER_ID + "='"
							+ client.getPrimaryId() + "'");

			if (!sList.isEmpty()) {
				scenario = sList.get(0);
			} else {
				scenario.setOwnerId(client.getPrimaryId());
				scenario.insert();
			}

			if (action.equalsIgnoreCase("new")) {
				EstatePlanningToolBean etools = new EstatePlanningToolBean();
				etools.deleteTools(scenario.getId());
				eptAssets.newAssetList(scenario.getId());
				epg.setScenarioId(scenario.getId());
				epg.setEptassets(eptAssets);
				path = "/servlet/ToolWizardServlet";
			} else if (action.equalsIgnoreCase("update")) {
				eptAssets.loadAssetList(scenario.getId());
				epg.setEptassets(eptAssets);
				epg.setScenarioId(scenario.getId());
				path = "/servlet/ToolWizardServlet";
			}
		}
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);

	}

	/**
	 * Returns information about the servlet, such as author, version, and
	 * copyright.
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
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}
}
