package com.estate.servlet.tools;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.bean.ClientBean;
import com.teag.bean.MarriageBean;
import com.teag.bean.PersonBean;
import com.teag.client.Marriages;
import com.teag.util.Utilities;
import com.teag.webapp.EstatePlanningGlobals;

public class PlanningToolsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8668422343718485164L;

	/**
	 * Constructor of the object.
	 */
	public PlanningToolsServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/index.jsp";

		session.removeAttribute("epg");

		ClientBean client = (ClientBean) session.getAttribute("client");

		EstatePlanningGlobals epg = new EstatePlanningGlobals();

		epg.setClient(client);

		PersonBean primary = new PersonBean();
		primary.setId(client.getPrimaryId());
		primary.initialize();

		Marriages m = new Marriages();
		m.setPrimaryId(primary.getId());
		m.initialize();
		MarriageBean mb = m.getCurrentMarriage();

		epg.setClientAge(Utilities.CalcAge(primary.getBirthDate()));
		epg.setClientGender(primary.getGender());
		epg.setClientFirstName(primary.getFirstName());
		epg.setLastName(primary.getLastName());

		if (mb == null) {
			epg.setSingle(true);
		} else {
			PersonBean secondary = mb.getSpouse();

			epg.setSpouseAge(Utilities.CalcAge(secondary.getBirthDate()));
			epg.setSpouseGender(secondary.getGender());
			epg.setSpouseFirstName(secondary.getFirstName());
		}
		
		// Calculate Life Expectancy
		epg.init();

		session.setAttribute("epg", epg);

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
