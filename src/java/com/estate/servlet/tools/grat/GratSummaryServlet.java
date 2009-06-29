package com.estate.servlet.tools.grat;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.view.GratSummaryView;
import com.teag.estate.GratTool;
import com.teag.estate.GratTrust;
import com.teag.webapp.EstatePlanningGlobals;

public class GratSummaryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5789324347341583015L;

	/**
	 * Constructor of the object.
	 */
	public GratSummaryServlet() {
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
		GratTool grat = (GratTool) session.getAttribute("grat");
		
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session
		.getAttribute("epg");
		
		grat.setClientAge(epg.getClientAge());
		grat.setSpouseAge(epg.getSpouseAge());
		grat.calculate();

		GratTrust cGrat = grat.getClientTrust();
		GratTrust sGrat = null;

		if (grat.getNumTrusts() > 1)
			sGrat = grat.getSpouseTrust();

		grat.buildSCTable();
		grat.buildCFTable();

		GratSummaryView gsv = new GratSummaryView();
		
		gsv.setEstateTotalValue(grat.getSCTable(grat.getFinalDeath()));
		gsv.setEstateFet(gsv.getEstateTotalValue()* grat.getEstateTaxRate());
		gsv.setEstateToFamily(gsv.getEstateToFamily() - gsv.getEstateFet());
		gsv.setGratToFamily(grat.getCFTotalValue(grat.getFinalDeath()));
		gsv.setGratTaxableGift(cGrat.getRemainderInterest());
		if(grat.getNumTrusts()>1 && sGrat != null){
			gsv.setGratTaxableGift(gsv.getGratTaxableGift()+ sGrat.getRemainderInterest());
		}
		gsv.setGratEstateTax(gsv.getGratTaxableGift()* grat.getEstateTaxRate());
		gsv.setGratTotalValue(gsv.getGratToFamily() - gsv.getGratEstateTax());

		gsv.setCGrat(cGrat);
		
		// Put the grat calculations in the request so we can access them	
		if (sGrat != null)
			gsv.setSGrat(sGrat);

		session.setAttribute("gsv", gsv);
		session.setAttribute("cGrat",cGrat);
		session.setAttribute("sGrat",sGrat);
		
		// Forward to the discription page.
		RequestDispatcher dispatch = request
				.getRequestDispatcher("/WEB-INF/jsp/estatetools/grat/summary.jsp");
		
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
