package com.estate.servlet.tools.clat;

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
import com.teag.estate.ClatTool;

public class ClatToolServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5660682157931274255L;

	/**
	 * Constructor of the object.
	 */
	public ClatToolServlet() {
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
		ClatTool clat = (ClatTool) session.getAttribute("clat");

		String pageView = request.getParameter("pageView");

		if (pageView.equals("PROCESS")) {

			String trustType = Utils.getStringParameter(request, "trustType", "T");
			clat.setClatType(trustType);
			
			String lifeType = Utils.getStringParameter(request, "lifeType", "F");
			clat.setLifeType(lifeType);
			
			double afrRate = Utils.getPercentParameter(request, "irsRate", .05);
			clat.setAfrRate(afrRate);

			Date afrDate = Utils.getDateParameter(request, "irsDate",
					new Date());
			SimpleDateFormat simpleDate = new SimpleDateFormat("M/dd/yyyy");
			clat.setAfrDate(simpleDate.format(afrDate));

			String calcType = Utils
					.getStringParameter(request, "calcType", "A");
			clat.setCalcType(calcType);

			if (calcType.equals("R")) {
				double remainderInterest = Utils.getDoubleParameter(request,
						"remainderInterest", 0);
				clat.setRemainderInterest(remainderInterest);
			}

			double annuity = Utils.getDoubleParameter(request, "annuity", 0);
			clat.setAnnuity(annuity);

			int annuityType = Utils.getIntegerParameter(request, "annuityType", 0);	// Default at end
			clat.setAnnuityType(annuityType);
			
			int freq = Utils.getIntegerParameter(request, "annuityFreq", 1); // Default
																				// annual
			clat.setAnnuityFreq(freq);

			double annuityIncrease = Utils.getPercentParameter(request,
					"annuityIncrease", 0);
			clat.setAnnuityIncrease(annuityIncrease);

			String grantor = Utils.getStringParameter(request, "grantorFlag",
					"N");
			clat.setGrantorFlag(grantor);

			int term = Utils.getIntegerParameter(request, "term", 10);
			clat.setTerm(term);

			double iTaxRate = Utils.getPercentParameter(request,
					"incomeTaxRate", .35);
			clat.setIncomeTaxRate(iTaxRate);

			double estateTax = Utils.getPercentParameter(request, "estateTaxRate",
					.55);
			clat.setEstateTaxRate(estateTax);

			session.setAttribute("clat", clat);
		}

		RequestDispatcher dispatch = request
				.getRequestDispatcher("/WEB-INF/jsp/estatetools/clat/tool.jsp");
		dispatch.forward(request, response);
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
