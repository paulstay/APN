package com.estate.servlet.std.qprt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.controller.QprtController;
import com.estate.servlet.Utils;
import com.estate.toolbox.QPRT;
import com.teag.bean.PdfBean;

public class StdQprtServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7972464231437017353L;

	/**
	 * Constructor of the object.
	 */
	public StdQprtServlet() {
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
		String path = "/WEB-INF/jsp/standalone/qprt/description.jsp";
		
		String pageView = request.getParameter("pageView");
		String action = request.getParameter("action");
		
		QprtController qprt = (QprtController) session.getAttribute("qprt");
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
		
		if(qprt == null) {
			qprt = new QprtController();
			qprt.setFmv(1000000);
			qprt.setBasis(250000);
			qprt.setGrowth(.04);
			qprt.setIrsRate(.04);
			qprt.setIrsDate(new Date());
			qprt.setClientAge(65);
			qprt.setSpouseAge(0);
			qprt.setTrusts(1);
			qprt.setFractionalDiscount(.15);
			qprt.setClientTerm(10);
			qprt.setSpouseTerm(0);
			qprt.setSpousePriorGifts(0);
			qprt.setEstateTaxRate(.55);
			qprt.setReversionRetained(true);
			qprt.setFinalDeath(25);
			qprt.calculate();
			
			QPRT q1 = qprt.getQ1();
			QPRT q2 = null;
			if (qprt.getTrusts() > 1)
				q2 = qprt.getQ2();

			request.setAttribute("q1", q1);
			request.setAttribute("q2", q2);
			session.setAttribute("qprt", qprt);
			session.setAttribute("qprt", qprt);
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
			path = "/WEB-INF/jsp/standalone/qprt/user.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("tool")){
			if(action != null && action.equalsIgnoreCase("update")){
				int trusts = Utils.getIntegerParameter(request, "trusts", 1);
				qprt.setTrusts(trusts);
				double fmv = Utils.getDoubleParameter(request, "fmv", 0);
				qprt.setFmv(fmv);
				double basis = Utils.getDoubleParameter(request, "basis", 0);
				qprt.setBasis(basis);
				double growth = Utils.getPercentParameter(request, "growth", .05);
				qprt.setGrowth(growth);
				double irsRate = Utils.getPercentParameter(request, "irsRate", .05);
				qprt.setIrsRate(irsRate);
				Date irsDate = Utils.getDateParameter(request, "irsDate", new Date());
				qprt.setIrsDate(irsDate);
				double fractionalDiscount = Utils.getPercentParameter(request, "fractionalDiscount", .15);
				qprt.setFractionalDiscount(fractionalDiscount);
				int clientAge = Utils.getIntegerParameter(request, "clientAge", 65);
				qprt.setClientAge(clientAge);
				int clientTerm = Utils.getIntegerParameter(request, "clientTerm", 10);
				qprt.setClientTerm(clientTerm);
				double clientGifts = Utils.getDoubleParameter(request, "clientPriorGifts", 0);
				qprt.setClientPriorGifts(clientGifts);
				if(qprt.getTrusts()> 1) {
					int spouseAge = Utils.getIntegerParameter(request, "spouseAge", 65);
					qprt.setSpouseAge(spouseAge);
					int spouseTerm = Utils.getIntegerParameter(request, "spouseTerm", 10);
					qprt.setSpouseTerm(spouseTerm);
					double spouseGifts = Utils.getDoubleParameter(request, "spousePriorGifts", 0);
					qprt.setSpousePriorGifts(spouseGifts);
				}
			}

			qprt.calculate();
			QPRT q1 = qprt.getQ1();
			QPRT q2 = null;
			if (qprt.getTrusts() > 1)
				q2 = qprt.getQ2();

			request.setAttribute("q1", q1);
			request.setAttribute("q2", q2);
			session.setAttribute("qprt", qprt);
			path = "/WEB-INF/jsp/standalone/qprt/tool.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("summary")){
			qprt.calculate();
			path = "/WEB-INF/jsp/standalone/qprt/summary.jsp";
		}
		
		if(pageView != null && pageView.equalsIgnoreCase("cancel")) {
			session.removeAttribute("qprt");
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
