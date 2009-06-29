package com.estate.servlet.tools.clat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.estate.ClatTool;
import com.teag.webapp.EstatePlanningGlobals;

public class ClatServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6304062736889367209L;

	/**
	 * Constructor of the object.
	 */
	public ClatServlet() {
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

		EstatePlanningGlobals epg = (EstatePlanningGlobals) session
				.getAttribute("epg");

		ClatTool tool = new ClatTool();
		String toolView = request.getParameter("action");

		if (toolView != null && toolView.equalsIgnoreCase("edit")) {
			String toolId = request.getParameter("id");
			if(toolId != null && Integer.parseInt(toolId)> 0){
				tool.setId(Integer.parseInt(toolId));
				tool.read();
			} else {
				toolView = "add";	// Just in case!
			}
		}

		if(toolView != null && toolView.equalsIgnoreCase("add")){
			Date now = new Date();
			SimpleDateFormat currentDate = new SimpleDateFormat("M/dd/yyyy");
			tool = new ClatTool();
			tool.setEstateTaxRate(.55);
			tool.setIncomeTaxRate(.464);
			tool.setAfrDate(currentDate.format(now));
			tool.setAfrRate(.05);
			tool.setAnnuity(0);
			tool.setAnnuityFreq(4);
			tool.setAnnuityType(0);		// At the End of the term End = 0, Begin = 1
			tool.setCalcType("A");
			tool.setAnnuity(0);
			tool.setFinalDeath(25);
			tool.setTerm(10);
			tool.setGrantorFlag("N");
			tool.setAge1(0);
			tool.setAge2(0);
			tool.setAnnuityIncrease(0);
			tool.setLifeType("S");
			tool.setClatType("T");
			tool.setId(-epg.getScenarioId());
		}

		session.setAttribute("clat", tool);

		// Forward to the discription page.
		RequestDispatcher dispatch = request
				.getRequestDispatcher("/WEB-INF/jsp/estatetools/clat/description.jsp");

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
