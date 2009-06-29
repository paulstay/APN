package com.estate.servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.jdom.*;
import com.estate.utils.AfrRates;
import java.util.Date;
import java.util.Calendar;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class IrsRatesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 810968994717383528L;

	/**
	 * Constructor of the object.
	 */
	public IrsRatesServlet() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String path = "/WEB-INF/jsp/admin/irs7520.jsp";
		String rates[][] = new String[10][12];

		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);

		int year = 2002;
		int currentYear = cal.get(Calendar.YEAR);
		String yearStr = "YEAR_";
		Document doc;

		ServletContext context = getServletContext();
		InputStream is = context.getResourceAsStream("/textFiles/afr.xml");
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(isr);
		
		try {
			doc = AfrRates.loadDoc(reader);
			
			if(doc == null){
				throw new NullPointerException();
			}
			Element root = doc.getRootElement();

			for (int i = 0; i < currentYear-year+1; i++) {
				for (int m = 0; m < 12; m++) {
					Element child = root.getChild("ANNUAL_AFR").getChild(
							yearStr + Integer.toString(i+year)).getChild(
							AfrRates.monthArray[m]);
					if (child != null) {
						double irsRate = AfrRates.calcRate(Integer
								.parseInt(child.getText()));
						String str = Double.toString(irsRate) + "%";
						rates[i][m] = str;
					} else {
						rates[i][m] = "0";
					}
				}
			}

			session.setAttribute("rates", rates);
			session.setAttribute("maxYear", currentYear);
			session.setAttribute("startYear", 2002);
		} catch (Exception e) {
			session.setAttribute("errMsg","Error in 7520 rates");
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	/**
	 * Returns information about the servlet, such as author, version, and
	 * copyright.
	 * 
	 * @return String information about this servlet
	 */
	public String getServletInfo() {
		return "This is my default servlet created by Eclipse";
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
