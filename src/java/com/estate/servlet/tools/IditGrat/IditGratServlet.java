package com.estate.servlet.tools.IditGrat;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.estate.IditTool;
import com.teag.estate.IditTrustTool;
import com.teag.webapp.EstatePlanningGlobals;

public class IditGratServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5194724555434320623L;

	/**
	 * Constructor of the object.
	 */
	public IditGratServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/idit2/description.jsp";
		
		IditTool tool = (IditTool) session.getAttribute("idit");
		IditTrustTool trustTool = (IditTrustTool) session.getAttribute("iditTrust");
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session.getAttribute("epg");
		
		if(tool == null) {
			tool = new IditTool();
			tool.setId(-epg.getScenarioId());
			tool.setNoteInterest(.06);
			tool.setNoteLength(15);
			tool.setStateIncomeTax(.093);
			tool.setGratTerm(10);
			tool.setGratTrusts(2);
			tool.setAfrDate("4/15/2005");
			tool.setAfrRate(.05);
			tool.setAnnuity(0);
			tool.setAnnuitFreq(4);
			tool.setAnnuityIncrease(0);
			tool.setFinalDeath(25);
			tool.setUseInsurance(false);
			tool.setLifePremium(0);
			tool.setLifeCashValue(0);
			tool.setSecuritiesValue(0);
			tool.setOptimalPaymentRate(0);
			tool.setNoteFlpDiscount(.25);
			tool.setNoteFlpGPInterest(.02);
			tool.setNoteFlpLPInterest(.98);
			tool.setNoteFlpToolId(0);
			tool.setEstate(epg);
			
			session.setAttribute("idit", tool);
		}

		if( trustTool == null ) {
			trustTool = new IditTrustTool();
			trustTool.setId(-epg.getScenarioId());
			trustTool.setAfrDate("4/15/2005");
			trustTool.setAfrRate(.05);
			trustTool.setAnnuity(0);
			trustTool.setAnnuityFreq(4);
			trustTool.setStateIncomeTax(.093);
			trustTool.setLifeDeathBenefit(0);
			trustTool.setLifePremium(0);
			trustTool.setNoteFlpDiscount(.25);
			trustTool.setNoteFlpLPInterest(.98);
			trustTool.setNoteFlpGPInterest(.02);
			trustTool.setTerm(10);
			trustTool.setOptimalGratRate(0);
			session.setAttribute("iditTrust", trustTool);
		}		

		tool.setEstate(epg);
		
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
