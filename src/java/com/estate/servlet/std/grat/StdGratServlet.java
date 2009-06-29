package com.estate.servlet.std.grat;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.controller.GratController;
import com.estate.servlet.Utils;
import com.teag.bean.PdfBean;

public class StdGratServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7412682364765926958L;

	/**
	 * Constructor of the object.
	 */
	public StdGratServlet() {
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
		String path = "/WEB-INF/jsp/standalone/grat/description.jsp";

		GratController grat = (GratController) session.getAttribute("grat");
		PdfBean userInfo = (PdfBean) session.getAttribute("userInfo");
		
		// Test to see if we are here for the first time.....
		if(userInfo == null){
			userInfo = new PdfBean();
			userInfo.setFirstName("Clark");
			userInfo.setLastName("Jones");
			userInfo.setMiddleName("");
			userInfo.setGender("M");
			userInfo.setFinalDeath(25);
			userInfo.setAge(68);
			session.setAttribute("userInfo", userInfo);
		}
		
		if( grat == null){
			grat = new GratController();
			grat.setUseInsurance(false);
			grat.setDiscount(.35);
			grat.setIrsRate(.052);
			grat.setIrsDate(new Date());
			grat.setFmv(1000000);
			grat.setEndBegin(0);
			grat.setFreq(1);
			grat.setAssetGrowth(.05);
			grat.setAssetIncome(.02);
			grat.setTypeId(1);
			grat.setEstateTaxRate(.55);
			grat.setTerm(10);
			grat.setRemainderInterest(50000);
			grat.setUseLLC(false);
			grat.calculate();
			session.setAttribute("grat", grat);
		}
		
		String pageView = request.getParameter("pageView");
		
		if(pageView != null && pageView.equalsIgnoreCase("user")){
			String action = request.getParameter("action");
			if(action != null && action.equalsIgnoreCase("update")){
				String firstName = Utils.getStringParameter(request, "firstName", "");
				userInfo.setFirstName(firstName);
				String lastName = Utils.getStringParameter(request, "lastName", "");
				userInfo.setLastName(lastName);
				String middleName = Utils.getStringParameter(request, "middleName", "");
				userInfo.setMiddleName(middleName);
				int age = Utils.getIntegerParameter(request, "age", 65);
				userInfo.setAge(age);
				String gender = Utils.getStringParameter(request, "gender", "M");
				userInfo.setGender(gender);
				int finalDeath = Utils.getIntegerParameter(request, "finalDeath", 25);
				userInfo.setFinalDeath(finalDeath);
				session.setAttribute("userInfo", userInfo);
			}
			path = "/WEB-INF/jsp/standalone/grat/user.jsp";
			session.setAttribute("userInfo", userInfo);
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("tool")){
			String action = request.getParameter("action");
			if(action != null && action.equalsIgnoreCase("ins")){
				boolean insFlag = Utils.getBooleanParameter(request, "useIns", false);
				grat.setUseInsurance(insFlag);
			}
			if(action != null && action.equalsIgnoreCase("calc")){
				int calcType = Utils.getIntegerParameter(request, "calculationMethod", 1);	// Default is remainder interest
				grat.setTypeId(calcType);
			}
			if( action != null && action.equalsIgnoreCase("update")){
				boolean insFlag = Utils.getBooleanParameter(request, "useIns", false);
				grat.setUseInsurance(insFlag);
				double fmv = Utils.getDoubleParameter(request, "fmv", 0);
				grat.setFmv(fmv);
				String llcType = Utils.getStringParameter(request, "llc", "L");
				if( llcType.equals("L"))
					grat.setUseLLC(true);
				else
					grat.setUseLLC(false);
				double discount = Utils.getPercentParameter(request, "discount", .25);
				grat.setDiscount(discount);
				double assetGrowth = Utils.getPercentParameter(request, "assetGrowth", .05);
				grat.setAssetGrowth(assetGrowth);
				double assetIncome = Utils.getPercentParameter(request, "assetIncome", .02);
				grat.setAssetIncome(assetIncome);
				double irsRate = Utils.getPercentParameter(request, "irsRate", .05);
				grat.setIrsRate(irsRate);
				Date irsDate = Utils.getDateParameter(request, "irsDate", new Date());
				grat.setIrsDate(irsDate);
				double estateTaxRate = Utils.getPercentParameter(request, "estateTaxRate", .45);
				grat.setEstateTaxRate(estateTaxRate);
				int term = Utils.getIntegerParameter(request, "term", 10);
				grat.setTerm(term);
				int endBegin = Utils.getIntegerParameter(request, "endBegin", 0);
				grat.setEndBegin(endBegin);
				int freq = Utils.getIntegerParameter(request, "freq", 1);
				grat.setFreq(freq);
				if(grat.getTypeId()== 0) { // Annuity Payment
					double annuityPayment = Utils.getDoubleParameter(request, "annuityPayment", 0);
					grat.setAnnuityPayment(annuityPayment);
				} if(grat.getTypeId() == 1) {	// Remainder Interest
					double remainderInterest = Utils.getDoubleParameter(request, "remainderInterest", 0);
					grat.setRemainderInterest(remainderInterest);
				} else {
					grat.setRemainderInterest(0);
				}
				if(grat.isUseInsurance()) {
					double lifePremium = Utils.getDoubleParameter(request, "lifePremium", 0);
					grat.setLifePremium(lifePremium);
					double lifeDeathBenefit = Utils.getDoubleParameter(request, "lifeDeathBenefit", 0);
					grat.setLifeDeathBenefit(lifeDeathBenefit);
					double lifeCashValue = Utils.getDoubleParameter(request, "lifeCashValue", 0);
					grat.setLifeCashValue(lifeCashValue);
				}
				
			}
			path = "/WEB-INF/jsp/standalone/grat/tool.jsp";
			grat.calculate();
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("summary")){
			path = "/WEB-INF/jsp/standalone/grat/summary.jsp";
			grat.calculate();
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("cancel")){
			
			session.removeAttribute("grat");
			session.removeAttribute("userInfo");
			path = "/toolbox/index.jsp";
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
