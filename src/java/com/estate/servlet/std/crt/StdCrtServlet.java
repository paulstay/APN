package com.estate.servlet.std.crt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.estate.toolbox.CRT;
import com.teag.bean.PdfBean;

public class StdCrtServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2513101266423589444L;

	/**
	 * Constructor of the object.
	 */
	public StdCrtServlet() {
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
		String path = "/WEB-INF/jsp/standalone/crt/description.jsp";
		
		CRT sCrt = (CRT) session.getAttribute("sCrt");
		PdfBean userInfo = (PdfBean) session.getAttribute("userInfo");
		
		// If null, we are probably here for the first time
		if(userInfo == null) {
			userInfo = new PdfBean();
			userInfo.setFirstName("Clark");
			userInfo.setMiddleName("");
			userInfo.setLastName("Jones");
			userInfo.setGender("M");
			userInfo.setFinalDeath(25);
			userInfo.setAge(68);
			session.setAttribute("userInfo", userInfo);
		}

		if(sCrt == null) {
			sCrt = new CRT();
			sCrt.setIrsRate(.044);
			sCrt.setIrsDate(new Date());
			sCrt.setPayoutRate(.075);
			sCrt.setSpouseAge(0);
			sCrt.setInvestmentReturn(.08);
			sCrt.setFmv(9500000);
			sCrt.setLiability(0);
			sCrt.setBasis(120000);
			sCrt.setMarginalTaxRate(.4313);
			sCrt.setEstateTaxRate(.55);
			sCrt.setCapitalGainsTax(.2);
			sCrt.setTaxGrowth(.04);
			sCrt.setTaxIncome(.035);
			sCrt.setAgi(2000000);
			sCrt.setInsuranceBenefit(9350000);
			sCrt.setInsurancePremium(115200);
			sCrt.setFinalDeath(25);
			sCrt.setCrtType("CRUT");
			sCrt.setPayoutOption("P");
			sCrt.setSpouseAge(64);
			sCrt.setUniLag(3);
			sCrt.setClientAge(65);
			sCrt.calculate();
			sCrt.calcMax();
			session.setAttribute("sCrt", sCrt);
		}
		
		String pageView = request.getParameter("pageView");
		
		if(pageView != null && pageView.equalsIgnoreCase("description")){
			path = "/WEB-INF/jsp/standalone/crt/description.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("user")){
			String action = request.getParameter("action");
			if(action != null && action.equalsIgnoreCase("update")){
				String firstName = Utils.getStringParameter(request, "firstName", "");
				userInfo.setFirstName(firstName);
				String lastName = Utils.getStringParameter(request, "lastName", "");
				userInfo.setLastName(lastName);
				String middleName = Utils.getStringParameter(request, "middleName", "");
				userInfo.setMiddleName(middleName);
				session.setAttribute("userInfo", userInfo);
			}
			path = "/WEB-INF/jsp/standalone/crt/user.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("tool")){
			String action = request.getParameter("action");
			if(action != null && action.equalsIgnoreCase("update")){
				double fmv = Utils.getDoubleParameter(request, "fmv", 0);
				sCrt.setFmv(fmv);
				double liability = Utils.getDoubleParameter(request, "liability", 0);
				sCrt.setLiability(liability);
				double basis = Utils.getDoubleParameter(request, "basis", 0);
				sCrt.setBasis(basis);
				String payoutOption = Utils.getStringParameter(request, "payoutOption", "P");
				sCrt.setPayoutOption(payoutOption);
				double investmentReturn = Utils.getPercentParameter(request, "investmentReturn", 0);
				sCrt.setInvestmentReturn(investmentReturn);
				double marginalTaxRate = Utils.getPercentParameter(request, "marginalTaxRate", .35);
				sCrt.setMarginalTaxRate(marginalTaxRate);
				double estateTaxRate = Utils.getPercentParameter(request, "estateTaxRate", .45);
				sCrt.setEstateTaxRate(estateTaxRate);
				double capitalGainsTax = Utils.getPercentParameter(request, "capitalGainsTax", .15);
				sCrt.setCapitalGainsTax(capitalGainsTax);
				double agi = Utils.getDoubleParameter(request, "agi", 0);
				sCrt.setAgi(agi);
				int clientAge = Utils.getIntegerParameter(request, "clientAge", 65);
				sCrt.setClientAge(clientAge);
				int spouseAge = Utils.getIntegerParameter(request, "spouseAge", 0);
				sCrt.setSpouseAge(spouseAge);
				int pmtPeriod = Utils.getIntegerParameter(request, "pmtPeriod", 1);
				sCrt.setPmtPeriod(pmtPeriod);
				int uniLag = Utils.getIntegerParameter(request, "uniLag", 0);
				sCrt.setUniLag(uniLag);
				double irsRate = Utils.getPercentParameter(request, "irsRate", .5);
				sCrt.setIrsRate(irsRate);
				Date irsDate = Utils.getDateParameter(request, "irsDate", new Date());
				sCrt.setIrsDate(irsDate);
				double insurancePremium = Utils.getDoubleParameter(request, "insurancePremium", 0);
				sCrt.setInsurancePremium(insurancePremium);
				double insuranceBenefit = Utils.getDoubleParameter(request, "insuranceBenefit", 0);
				sCrt.setInsuranceBenefit(insuranceBenefit);
				
				sCrt.calcMax();

				// We don't want to exceed the maximum, nor do we want to go under the minimum
				double payoutRate = Utils.getPercentParameter(request, "payoutRate", .5);

				if(payoutRate < .05) {
					payoutRate = .05;
					request.setAttribute("errorPayout", "Reset - Payout Rate must be equal to or greater than 5%");
				}
				
				if(payoutRate > sCrt.getMaxPayout()) {
					payoutRate = sCrt.getMaxPayout();
					request.setAttribute("errorPayout", "Reset - Payout Rate cannont exceed Max Payout");
				}
				
				sCrt.setPayoutRate(payoutRate);
				double taxGrowth = Utils.getPercentParameter(request, "taxGrowth", payoutRate/2);
				sCrt.setTaxGrowth(taxGrowth);
				double taxIncome = Utils.getPercentParameter(request, "taxIncome", payoutRate/2);
				sCrt.setTaxIncome(taxIncome);
				
				int finalDeath = Utils.getIntegerParameter(request, "finalDeath", 25);
				sCrt.setFinalDeath(finalDeath);
				sCrt.calculate();
				session.setAttribute("sCrt", sCrt);
			}
			path = "/WEB-INF/jsp/standalone/crt/tool.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("summary")){
			sCrt.calculate();
			path = "/WEB-INF/jsp/standalone/crt/summary.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("cancel")){
			session.removeAttribute("sCrt");
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
