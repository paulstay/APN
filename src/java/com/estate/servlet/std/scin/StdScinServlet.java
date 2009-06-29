package com.estate.servlet.std.scin;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.estate.toolbox.SCIN;
import com.teag.bean.PdfBean;

public class StdScinServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6348569520706911629L;

	/**
	 * Constructor of the object.
	 */
	public StdScinServlet() {
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
		String path = "/WEB-INF/jsp/standalone/scin/description.jsp";
		
		String pageView = request.getParameter("pageView");
		String action = request.getParameter("action");
		
		SCIN scin = (SCIN) session.getAttribute("scin");
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
		
		if(scin == null) { // Create it if this is the first time through
			scin = new SCIN();
			scin.setFinalDeath(25);
			scin.setFmv(1000000);
			scin.setIrsRate(.042);
			scin.setIrsDate(new Date());
			scin.setBasis(250000);
			scin.setClientAge(68);
			scin.setSpouseAge(68);
			scin.setTerm(15);
			scin.setEndBegin(0);
			scin.setGrowth(.05);
			session.setAttribute("scin",scin);
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
			path = "/WEB-INF/jsp/standalone/scin/user.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("tool")){
			if(action != null && action.equalsIgnoreCase("update")){
				double fmv = Utils.getDoubleParameter(request, "fmv", 0);
				scin.setFmv(fmv);
				double basis = Utils.getDoubleParameter(request, "basis", 0);
				scin.setBasis(basis);
				double growth = Utils.getPercentParameter(request, "growth", .05);
				scin.setGrowth(growth);
				double irsRate = Utils.getPercentParameter(request, "irsRate", .05);
				scin.setIrsRate(irsRate);
				Date irsDate = Utils.getDateParameter(request, "irsDate", new Date());
				scin.setIrsDate(irsDate);
				int clientAge = Utils.getIntegerParameter(request, "clientAge", 65);
				scin.setClientAge(clientAge);
				int spouseAge = Utils.getIntegerParameter(request, "spouseAge", 0);
				scin.setSpouseAge(spouseAge);
				int noteType = Utils.getIntegerParameter(request, "noteType", 2);
				scin.setNoteType(noteType);
				int paymentType = Utils.getIntegerParameter(request, "paymentType", 0);
				scin.setPaymentType(paymentType);
				double noteRate = Utils.getPercentParameter(request, "noteRate", .046);
				scin.setNoteRate(noteRate);
				int term = Utils.getIntegerParameter(request, "term", 10);
				scin.setTerm(term);
				int yrsDeffered = Utils.getIntegerParameter(request, "yrsDeffered", 0);
				scin.setYrsDeffered(yrsDeffered);
				scin.calculate();
			}
			session.setAttribute("scin", scin);
			path = "/WEB-INF/jsp/standalone/scin/tool.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("summary")){
			scin.calculate();
			path = "/WEB-INF/jsp/standalone/scin/summary.jsp";
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
