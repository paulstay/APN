package com.estate.servlet.std.idit;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.estate.toolbox.Idit;
import com.teag.bean.PdfBean;

public class StdIditServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8392521237741652884L;

	/**
	 * Constructor of the object.
	 */
	public StdIditServlet() {
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
		String path = "/WEB-INF/jsp/standalone/idit/description.jsp";
		
		String pageView = request.getParameter("pageView");
		String action = request.getParameter("action");
		
		Idit idit = (Idit) session.getAttribute("idit");
		PdfBean userInfo = (PdfBean) session.getAttribute("userInfo");
		
		if(userInfo == null) {
			userInfo = new PdfBean();
			userInfo.setFirstName("Clark");
			userInfo.setMiddleName("");
			userInfo.setLastName("Jones");
			userInfo.setAge(65);
			userInfo.setGender("M");
			userInfo.setFinalDeath(25);
			session.setAttribute("userInfo", userInfo);
		}
		
		if(idit == null) { // Create it if this is the first time through
			idit = new Idit();
			idit.setFmv(1000000);
			idit.setDiscount(.35);
			idit.setNoteType(2);
			idit.setAssetGrowth(.05);
			idit.setAssetIncome(.04);
			idit.setGift(10000);
			idit.setLifeDeathBenefit(1000000);
			idit.setLifePremium(36000);
			idit.setLifePremiumYears(10);
			idit.setNoteRate(.0478);
			idit.setNoteTerm(10);
			idit.setNoteType(2);
			idit.setTaxRate(.35);
			idit.setEstateTaxRate(.55);
			idit.setFinalDeath(25);
			idit.calculate();
			
			session.setAttribute("idit",idit);
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("user")) {
			if(action != null && action.equalsIgnoreCase("update")){
				String firstName = Utils.getStringParameter(request, "firstName", "");
				userInfo.setFirstName(firstName);
				String lastName = Utils.getStringParameter(request, "lastName", "");
				userInfo.setLastName(lastName);
				String middleName = Utils.getStringParameter(request, "middleName", "");
				userInfo.setMiddleName(middleName);
				session.setAttribute("userInfo", userInfo);
			}
			path = "/WEB-INF/jsp/standalone/idit/user.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("tool")){
			if(action != null && action.equalsIgnoreCase("update")){
				double fmv = Utils.getDoubleParameter(request, "fmv", 0);
				idit.setFmv(fmv);
				String llc = Utils.getStringParameter(request, "llc", "L");
				if(llc.equals("L")){
					idit.setUseLLC(true);
				} else {
					idit.setUseLLC(false);
				}
				double discount = Utils.getPercentParameter(request, "discount", .25);
				idit.setDiscount(discount);
				double assetGrowth = Utils.getPercentParameter(request, "assetGrowth", .05);
				idit.setAssetGrowth(assetGrowth);
				double assetIncome = Utils.getPercentParameter(request, "assetIncome", .02);
				idit.setAssetIncome(assetIncome);
				double taxRate = Utils.getPercentParameter(request, "taxRate", .35);
				idit.setTaxRate(taxRate);
				double estateTaxRate = Utils.getPercentParameter(request, "estateTaxRate", .45);
				idit.setEstateTaxRate(estateTaxRate);
				int noteTerm = Utils.getIntegerParameter(request, "noteTerm", 10);
				idit.setNoteTerm(noteTerm);
				double noteRate = Utils.getPercentParameter(request, "noteRate", .06);
				idit.setNoteRate(noteRate);
				int noteType = Utils.getIntegerParameter(request, "noteType", 3);
				idit.setNoteType(noteType);
				int finalDeath = Utils.getIntegerParameter(request, "finalDeath", 25);
				idit.setFinalDeath(finalDeath);
				double lifeDeathBenefit = Utils.getDoubleParameter(request, "lifeDeathBenefit", 0);
				idit.setLifeDeathBenefit(lifeDeathBenefit);
				double lifePremium = Utils.getDoubleParameter(request, "lifePremium", 0);
				idit.setLifePremium(lifePremium);
				int lifePremiumYears = Utils.getIntegerParameter(request, "lifePremiumYears", 0);
				idit.setLifePremiumYears(lifePremiumYears);
				double giftAmount = Utils.getDoubleParameter(request, "giftAmount", 0);
				idit.setGiftAmount(giftAmount);
				String giftType = Utils.getStringParameter(request, "giftType", "C");
				idit.setGiftType(giftType);
				idit.calculate();
			}
			session.setAttribute("idit", idit);
			path = "/WEB-INF/jsp/standalone/idit/tool.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("summary")){
			idit.calculate();
			path = "/WEB-INF/jsp/standalone/idit/summary.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("cancel")) {
			session.removeAttribute("idit");
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
