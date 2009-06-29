package com.estate.servlet.std.cga;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.estate.toolbox.CGA;
import com.teag.bean.PdfBean;

public class StdCgaServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7453182919908164250L;

	/**
	 * Constructor of the object.
	 */
	public StdCgaServlet() {
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
		String path = "/WEB-INF/jsp/standalone/cga/description.jsp";
		
		String pageView = request.getParameter("pageView");
		String action = request.getParameter("action");
		
		CGA cga = (CGA) session.getAttribute("cga");
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
		
		if(cga == null) {
			cga = new CGA();
			cga.setFmv(1000000);
			cga.setBasis(250000);
			cga.setIrsRate(.05);
			cga.setIrsDate(new Date());
			cga.setClientAge(65);
			cga.setSpouseAge(0);
			cga.setGrowth(.07);
			cga.setEndBegin(1);
			cga.setFrequency(1);
			cga.setSingle(true);
			cga.setCalcType("A");
			cga.calculate();
			session.setAttribute("cga", cga);
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
			path = "/WEB-INF/jsp/standalone/cga/user.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("tool")){
			if(action != null && action.equalsIgnoreCase("update")){
				double fmv = Utils.getDoubleParameter(request, "fmv", 0);
				cga.setFmv(fmv);
				double basis = Utils.getDoubleParameter(request, "basis", 0);
				cga.setBasis(basis);
				double growth = Utils.getPercentParameter(request,"growth", .05);
				cga.setGrowth(growth);
				double income = Utils.getPercentParameter(request, "income", .02);
				cga.setIncome(income);
				double irsRate = Utils.getPercentParameter(request, "irsRate", .05);
				cga.setIrsRate(irsRate);
				Date irsDate = Utils.getDateParameter(request, "irsDate", new Date());
				cga.setIrsDate(irsDate);
				int clientAge = Utils.getIntegerParameter(request, "cAge", 65);
				cga.setClientAge(clientAge);
				int spouseAge = Utils.getIntegerParameter(request, "sAge", 0);
				cga.setSpouseAge(spouseAge);
				String calcType = Utils.getStringParameter(request, "calcType", "A");	// use standard CGA rates
				cga.setCalcType(calcType);
				if(cga.getCalcType().equals("M")){
					double annuityRate = Utils.getPercentParameter(request, "annuityRate", .05);
					cga.setAnnuityRate(annuityRate);
				}
				int freq = Utils.getIntegerParameter(request, "freq", 1);
				cga.setFrequency(freq);
				int endBegin = Utils.getIntegerParameter(request, "endBegin", 0);
				cga.setEndBegin(endBegin);
				
			}
			session.setAttribute("cga", cga);
			path = "/WEB-INF/jsp/standalone/cga/tool.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("summary")){
			cga.calculate();
			path = "/WEB-INF/jsp/standalone/cga/summary.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("cancel")) {
			session.removeAttribute("cga");
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
