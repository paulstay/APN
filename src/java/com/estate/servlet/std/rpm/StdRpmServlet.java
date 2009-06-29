package com.estate.servlet.std.rpm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.estate.toolbox.Rpm;
import com.teag.bean.PdfBean;

public class StdRpmServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4970381514604740200L;

	/**
	 * Constructor of the object.
	 */
	public StdRpmServlet() {
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
		String path = "/WEB-INF/jsp/standalone/rpm/description.jsp";
		
		String pageView = request.getParameter("pageView");
		String action = request.getParameter("action");
		
		Rpm rpm = (Rpm) session.getAttribute("rpm");
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
		
		if(rpm == null) {
			rpm = new Rpm();
			rpm.setTerm(15);
			rpm.setPlanValue(10000000);
			rpm.setPlanGrowth(.06);
			rpm.setSecDivRate(.055);
			rpm.setSecGrowthRate(.04);
			rpm.setSecTurnoverRate(.75);
			rpm.setStateIncomeTaxRate(.095);
			session.setAttribute("rpm", rpm);
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
			path = "/WEB-INF/jsp/standalone/rpm/user.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("tool")){
			if(action != null && action.equalsIgnoreCase("update")){
				double planValue = Utils.getDoubleParameter(request, "planValue", 0);
				rpm.setPlanValue(planValue);
				double planGrowth = Utils.getPercentParameter(request, "planGrowth", 0);
				rpm.setPlanGrowth(planGrowth);
				int clientAge = Utils.getIntegerParameter(request, "clientAge", 65);
				rpm.setClientAge(clientAge);
				int spouseAge = Utils.getIntegerParameter(request, "spouseAge", 65);
				rpm.setSpouseAge(spouseAge);
				int term = Utils.getIntegerParameter(request, "term", 15);
				rpm.setTerm(term);
				double stateIncomeTaxRate = Utils.getPercentParameter(request, "stateIncomeTaxRate", .15);
				rpm.setStateIncomeTaxRate(stateIncomeTaxRate);
				double secGrowthRate = Utils.getPercentParameter(request, "secAGrowthRate", .05);
				rpm.setSecGrowthRate(secGrowthRate);
				double secDivRate = Utils.getPercentParameter(request, "secDivRate", .02);
				rpm.setSecDivRate(secDivRate);
				double secTurnoverRate = Utils.getPercentParameter(request, "secTurnoverRate", .75);
				rpm.setSecTurnoverRate(secTurnoverRate);
				double lifeInsPremium = Utils.getDoubleParameter(request, "lifeInsPremium", 0);
				rpm.setLifeInsPremium(lifeInsPremium);
				double lifeInsDeathBenefit = Utils.getDoubleParameter(request, "lifeInsDeathBenefit", 0);
				rpm.setLifeInsDeathBenefit(lifeInsDeathBenefit);
				rpm.calculate();
				rpm.calculateRpm();
			}
			rpm.calculate();
			session.setAttribute("rpm", rpm);
			path = "/WEB-INF/jsp/standalone/rpm/tool.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("summary")){
			String rpmView = request.getParameter("rpmView");
			String showPage = "comparison";
			if(rpmView != null){
				showPage = rpmView;
			}
			request.setAttribute("showPage", showPage);
			rpm.calculate();
			rpm.calculateRpm();
			path = "/WEB-INF/jsp/standalone/rpm/summary.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("cancel")) {
			session.removeAttribute("rpm");
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
