package com.estate.servlet.tools.mgen;

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
import com.teag.estate.MGTrustTool;
import com.teag.webapp.EstatePlanningGlobals;

public class MgenProcessServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8456026620743446709L;

	/**
	 * Constructor of the object.
	 */
	public MgenProcessServlet() {
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
		String path = "/servlet/ToolWizardServlet"; // Go back to the main menu

		MGTrustTool mgen = (MGTrustTool) session.getAttribute("mgen");
		// Either cancel the tool data, update, or insert
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session
				.getAttribute("epg");

		String pageView = request.getParameter("pageView");

		if (pageView != null && pageView.equals("save")) {
			if (mgen.getId() < 0) {
				mgen.insert();
				AssetUtilityBean aub = new AssetUtilityBean();
				aub.updateTools(mgen.getId(), -epg.getScenarioId());
				EstatePlanningToolBean estate = new EstatePlanningToolBean();
				estate.setScenarioId(epg.getScenarioId());
				estate.setToolId(mgen.getId());
				estate.setToolTableId(mgen.getToolTableId());
				estate.setDescription("Multi Generational Trust");
				estate.insert();
			} else {
				mgen.update();
			}
			EPTAssets eptassets = new EPTAssets();
			eptassets.loadAssetList(epg.getScenarioId());
			epg.setEptassets(eptassets);
		}

		// remove the tool from the session!
		session.removeAttribute("mgen");

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
