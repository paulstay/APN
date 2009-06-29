package com.estate.servlet.tools.IditGrat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.estate.IditTool;

public class IditGratToolServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2836827962834128258L;

	/**
	 * Constructor of the object.
	 */
	public IditGratToolServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/idit2/tool.jsp";
	
		String pageView = request.getParameter("pageView");
		IditTool idit = (IditTool) session.getAttribute("idit");

		if(pageView.equalsIgnoreCase("process") || pageView.equalsIgnoreCase("life")){
			double noteInterest = Utils.getPercentParameter(request, "noteInterest", .04);
			idit.setNoteInterest(noteInterest);
			int noteLength = Utils.getIntegerParameter(request, "noteLength", 10);
			idit.setNoteLength(noteLength);
			double stateIncomeTax = Utils.getPercentParameter(request, "stateIncomeTax", 0);
			idit.setStateIncomeTax(stateIncomeTax);
			int finalDeath = Utils.getIntegerParameter(request, "finalDeath", 25);
			idit.setFinalDeath(finalDeath);
			boolean useInsurance = Utils.getBooleanParameter(request, "useInsurance", false);
			idit.setUseInsurance(useInsurance);
			double afrRate = Utils.getPercentParameter(request, "afrRate", .05);
			idit.setAfrRate(afrRate);
			Date afrDate = Utils.getDateParameter(request, "afrDate", new Date());
			SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
			idit.setAfrDate(df.format(afrDate));
			int gratTerm = Utils.getIntegerParameter(request, "gratTerm", 10);
			idit.setGratTerm(gratTerm);
			double annuityFreq = Utils.getDoubleParameter(request, "annuityFreq", 1);
			idit.setAnnuitFreq(annuityFreq);
			double noteFlpGPInterest = Utils.getPercentParameter(request, "noteFlpGPInterest", .02);
			idit.setNoteFlpGPInterest(noteFlpGPInterest);
			double noteFlpLPInterest = Utils.getPercentParameter(request, "noteFlpLPInterest", .98);
			idit.setNoteFlpLPInterest(noteFlpLPInterest);
			double noteFlpDiscount = Utils.getPercentParameter(request, "noteFlpDiscount", .25);
			idit.setNoteFlpDiscount(noteFlpDiscount);
			double lifePremium = Utils.getDoubleParameter(request, "lifePremium", 0);
			idit.setLifePremium(lifePremium);
			double lifeDeathBenefit = Utils.getDoubleParameter(request, "lifeDeathBenefit", 0);
			idit.setLifeDeathBenefit(lifeDeathBenefit);
			double lifeCashValue = Utils.getDoubleParameter(request, "lifeCashValue", 0);
			idit.setLifeCashValue(lifeCashValue);
		}
		
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
