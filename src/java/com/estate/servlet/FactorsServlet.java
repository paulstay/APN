package com.estate.servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import com.estate.toolbox.*;

public class FactorsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6091059002283352237L;

	/**
	 * Constructor of the object.
	 */
	public FactorsServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = "/WEB-INF/jsp/standalone/factors/tool.jsp";
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		
		TermLifeFactors factors = (TermLifeFactors) session.getAttribute("factors");
		
		if(factors == null) {
			factors = new TermLifeFactors();
			factors.setIrsRate(.05);
			factors.setTerm(10);
			factors.setFreq(1);
			factors.setAnnuityType(0);
			factors.setAnnuityIncrease(0);
			factors.setAge1(65);
			factors.setAge2(64);
			factors.setYrsDef(0);
			factors.calculate();
		}
		if(action != null && action.equals("update")){
			double irsRate = Utils.getPercentParameter(request, "irsRate", .05);
			factors.setIrsRate(irsRate);
			int term = Utils.getIntegerParameter(request, "term", 10);
			factors.setTerm(term);
			double annuityIncrease = Utils.getPercentParameter(request, "annuityIncrease", 0);
			factors.setAnnuityIncrease(annuityIncrease);
			int freq = Utils.getIntegerParameter(request, "freq", 1);	// Default is annual
			factors.setFreq(freq);
			int annuityType = Utils.getIntegerParameter(request, "aType", 0);	// End of TERM Payment
			factors.setAnnuityType(annuityType);
			int yrsDeferred = Utils.getIntegerParameter(request, "yrsDeferred", 0);
			factors.setYrsDef(yrsDeferred);
			int age1 = Utils.getIntegerParameter(request, "age1", 0);
			factors.setAge1(age1);
			int age2 = Utils.getIntegerParameter(request, "age2", 0);
			factors.setAge2(age2);
			factors.calculate();
		}
		
		TermLifeFactors termCertain = new TermLifeFactors();
		TermLifeFactors lifeStatus1 = new TermLifeFactors();
		TermLifeFactors lifeStatus2 = new TermLifeFactors();
		TermLifeFactors termLifeStatus1 = new TermLifeFactors();
		TermLifeFactors termLifeStatus2 = new TermLifeFactors();
		TermLifeFactors termLifeStatus3 = new TermLifeFactors();
		TermLifeFactors termLifeStatus4 = new TermLifeFactors();
		
		// Calculate the Term Certain factors:
		termCertain.setAge1(0);
		termCertain.setAge2(0);
		termCertain.setStatus(0);
		termCertain.setIrsRate(factors.getIrsRate());
		termCertain.setAnnuityIncrease(factors.getAnnuityIncrease());
		termCertain.setAnnuityType(factors.getAnnuityType());
		termCertain.setFreq(factors.getFreq());
		termCertain.setYrsDef(factors.getYrsDef());
		termCertain.setTerm(factors.getTerm());
		termCertain.calculate();
		request.setAttribute("termCertain", termCertain);
		
		// Caluclate the Life Statuses
		// Last To Die
		lifeStatus1.setAge1(factors.getAge1());
		lifeStatus1.setAge2(factors.getAge2());
		lifeStatus1.setTerm(0);
		lifeStatus1.setStatus(0);
		lifeStatus1.setIrsRate(factors.getIrsRate());
		lifeStatus1.setAnnuityIncrease(factors.getAnnuityIncrease());
		lifeStatus1.setAnnuityType(factors.getAnnuityType());
		lifeStatus1.setFreq(factors.getFreq());
		lifeStatus1.setYrsDef(factors.getYrsDef());
		lifeStatus1.calculate();
		request.setAttribute("lifeStatus1", lifeStatus1);

		// First To Die
		lifeStatus2.setAge1(factors.getAge1());
		lifeStatus2.setAge2(factors.getAge2());
		lifeStatus2.setTerm(0);
		lifeStatus2.setStatus(1);
		lifeStatus2.setIrsRate(factors.getIrsRate());
		lifeStatus2.setAnnuityIncrease(factors.getAnnuityIncrease());
		lifeStatus2.setAnnuityType(factors.getAnnuityType());
		lifeStatus2.setFreq(factors.getFreq());
		lifeStatus2.setYrsDef(factors.getYrsDef());
		lifeStatus2.calculate();
		request.setAttribute("lifeStatus2", lifeStatus2);

		// Shorter of Term and Last to Die
		termLifeStatus1.setAge1(factors.getAge1());
		termLifeStatus1.setAge2(factors.getAge2());
		termLifeStatus1.setTerm(factors.getTerm());
		termLifeStatus1.setStatus(0);
		termLifeStatus1.setIrsRate(factors.getIrsRate());
		termLifeStatus1.setAnnuityIncrease(factors.getAnnuityIncrease());
		termLifeStatus1.setAnnuityType(factors.getAnnuityType());
		termLifeStatus1.setFreq(factors.getFreq());
		termLifeStatus1.setYrsDef(factors.getYrsDef());
		termLifeStatus1.calculate();
		request.setAttribute("termLifeStatus1", termLifeStatus1);

		// Shorter of Term and Last to Die
		termLifeStatus2.setAge1(factors.getAge1());
		termLifeStatus2.setAge2(factors.getAge2());
		termLifeStatus2.setTerm(factors.getTerm());
		termLifeStatus2.setStatus(1);
		termLifeStatus2.setIrsRate(factors.getIrsRate());
		termLifeStatus2.setAnnuityIncrease(factors.getAnnuityIncrease());
		termLifeStatus2.setAnnuityType(factors.getAnnuityType());
		termLifeStatus2.setFreq(factors.getFreq());
		termLifeStatus2.setYrsDef(factors.getYrsDef());
		termLifeStatus2.calculate();
		request.setAttribute("termLifeStatus2", termLifeStatus2);

		// Shorter of Term and Last to Die
		termLifeStatus3.setAge1(factors.getAge1());
		termLifeStatus3.setAge2(factors.getAge2());
		termLifeStatus3.setTerm(factors.getTerm());
		termLifeStatus3.setStatus(2);
		termLifeStatus3.setIrsRate(factors.getIrsRate());
		termLifeStatus3.setAnnuityIncrease(factors.getAnnuityIncrease());
		termLifeStatus3.setAnnuityType(factors.getAnnuityType());
		termLifeStatus3.setFreq(factors.getFreq());
		termLifeStatus3.setYrsDef(factors.getYrsDef());
		termLifeStatus3.calculate();
		request.setAttribute("termLifeStatus3", termLifeStatus3);

		// Shorter of Term and Last to Die
		termLifeStatus4.setAge1(factors.getAge1());
		termLifeStatus4.setAge2(factors.getAge2());
		termLifeStatus4.setTerm(factors.getTerm());
		termLifeStatus4.setStatus(3);
		termLifeStatus4.setIrsRate(factors.getIrsRate());
		termLifeStatus4.setAnnuityIncrease(factors.getAnnuityIncrease());
		termLifeStatus4.setAnnuityType(factors.getAnnuityType());
		termLifeStatus4.setFreq(factors.getFreq());
		termLifeStatus4.setYrsDef(factors.getYrsDef());
		termLifeStatus4.calculate();
		request.setAttribute("termLifeStatus4", termLifeStatus4);
		
		session.setAttribute("factors", factors);

		if(action != null && action.equalsIgnoreCase("cancel")){
			session.removeAttribute("factors");
			session.removeAttribute("termCertain");
			session.removeAttribute("TermLifeStatus1");
			session.removeAttribute("TermLifeStatus2");
			session.removeAttribute("TermLifeStatus3");
			session.removeAttribute("TermLifeStatus4");
			session.removeAttribute("lifeStatus1");
			session.removeAttribute("lifeStatus2");
			path="/toolbox/index.jsp";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
