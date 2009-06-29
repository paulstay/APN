package com.estate.servlet.tools.pat;

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
import com.teag.estate.PrivateAnnuityTool;
import com.teag.webapp.EstatePlanningGlobals;

public class PatProcessServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -697098970153736277L;

	/**
	 * Constructor of the object.
	 */
	public PatProcessServlet() {
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
		String path = "/servlet/ToolWizardServlet";

		PrivateAnnuityTool pat = (PrivateAnnuityTool) session.getAttribute("pat");
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session
				.getAttribute("epg");

		String pageView = request.getParameter("pageView");

		if (pageView != null && pageView.equals("save")) {
			if (pat.getId() < 0) {
				pat.insert();
				AssetUtilityBean aub = new AssetUtilityBean();
				aub.updateTools(pat.getId(), -epg.getScenarioId());
				EstatePlanningToolBean estate = new EstatePlanningToolBean();
				estate.setScenarioId(epg.getScenarioId());
				estate.setToolId(pat.getId());
				estate.setToolTableId(pat.getToolTableId());
				estate.setDescription("Private Annuity");
				estate.insert();
			} else {
				pat.update();
			}
			EPTAssets eptassets = new EPTAssets();
			eptassets.loadAssetList(epg.getScenarioId());
			epg.setEptassets(eptassets);
		}

		session.removeAttribute("pat");

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
