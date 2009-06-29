package com.estate.servlet.tools.grat;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.estate.GratTool;

public class GratToolServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9124258635830879294L;

	/**
	 * Constructor of the object.
	 */
	public GratToolServlet() {
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

		// TODO: error page if grat tool does not exist here.
		GratTool grat = (GratTool) session.getAttribute("grat");

		String pageView = request.getParameter("pageView");

		// pageView is Trusts
		if (pageView.equalsIgnoreCase("TRUST")) {
			String trusts = request.getParameter("numTrusts");
			if (trusts != null && Integer.parseInt(trusts) > 0)
				grat.setNumTrusts(Integer.parseInt(trusts));
			processForm(request,grat);
		}

		// pageView is Life
		if (pageView.equalsIgnoreCase("LIFE")) {
			String useLife = request.getParameter("useLife");
			if (useLife.equalsIgnoreCase("true") || useLife.equalsIgnoreCase("T"))
				grat.setUseLife(true);
			else
				grat.setUseLife(false);
			processForm(request,grat);
		}
		// pageView is CTYPE
		if (pageView.equalsIgnoreCase("CTYPE")) {
			String cType = request.getParameter("calcType");
			if (cType != null) {
				switch (cType.toUpperCase().charAt(0)) {
				case 'A':
					grat.setCalcType("A");
					break;
				case 'R':
					grat.setCalcType("R");
					break;
				case 'Z':
					grat.setCalcType("Z");
					break;
				default:
					grat.setCalcType("A");
				}
			} else {
				grat.setCalcType("A");
			}
			processForm(request,grat);
		}

		// pageView is PROCESS
		
		if( pageView.equalsIgnoreCase("PROCESS")) {
			processForm(request,grat);
		}

		// Forward to the discription page.
		RequestDispatcher dispatch = request
				.getRequestDispatcher("/WEB-INF/jsp/estatetools/grat/tool.jsp");

		dispatch.forward(request, response);
	}

	public void processForm(HttpServletRequest request, GratTool grat){
		// Number of trusts 
		grat.setNumTrusts(Utils.getIntegerParameter(request,"numTrusts",1));
		grat.setUseLife(Utils.getBooleanParameter(request, "useLife", false));
		grat.setAfrRate(Utils.getPercentParameter(request, "irsRate",.05));
		// Make sure we have a valid date in its proper format.
		SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
		try {
			String pDate = request.getParameter("afrDate");
			df.parse(pDate);
			grat.setAfrDate(pDate);
		} catch(ParseException e) {
			grat.setAfrDate(df.format(new java.util.Date()));
		}
		
		if(grat.getCalcType().equals("A")) {
			grat.setAnnuity(Utils.getDoubleParameter(request,"annuity",0));
		} else  if(grat.getCalcType().equals("R")){
			grat.setRemainderInterest(Utils.getDoubleParameter(request,"remainderInterest",-1));
		} else {
			grat.setRemainderInterest(0);	// Zero out grat with no remainder interest
		}
		grat.setAnnuityFreq(Utils.getIntegerParameter(request, "annuityFreq", 1));
		grat.setAnnuityIncrease(Utils.getPercentParameter(request, "annuityIncrease", 0));
		grat.setClientTermLength(Utils.getIntegerParameter(request,"clientTermLength",10));
		grat.setClientStartTerm(Utils.getDoubleParameter(request,"clientStartTerm",1));
		grat.setClientPriorGifts(Utils.getDoubleParameter(request,"clientPriorGifts",0));
		if(grat.getNumTrusts()>1){
			grat.setSpouseTermLength(Utils.getIntegerParameter(request,"spouseTermLength",10));
			grat.setSpouseStartTerm(Utils.getDoubleParameter(request,"spouseStartTerm",1));
			grat.setSpousePriorGifts(Utils.getDoubleParameter(request,"spousePriorGifts",0));
		}
		grat.setIncomeTaxRate(Utils.getPercentParameter(request,"incomeTaxRate", .35));
		grat.setEstateTaxRate(Utils.getPercentParameter(request,"estateTaxRate",.55));
		if(grat.isUseLife()) {
			grat.setLifeInsPremium(Utils.getDoubleParameter(request, "lifeInsPremium",0));
			grat.setLifeDeathBenefit(Utils.getDoubleParameter(request, "lifeDeathBenefit", 0));
			grat.setLifeCashValue(Utils.getDoubleParameter(request, "lifeCashValue", 0));
		}
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
