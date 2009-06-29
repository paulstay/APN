package com.estate.servlet.std.clat;

import java.io.IOException;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;
import com.estate.toolbox.Clat;
import com.teag.bean.PdfBean;

public class StdClatInitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4125724322372395691L;

	/**
	 * Constructor of the object.
	 */
	public StdClatInitServlet() {
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
		HttpSession session = request.getSession();
		String path = "/WEB-INF/jsp/standalone/clat/description.jsp";

		Clat clat = (Clat) session.getAttribute("clat");
		PdfBean userInfo = (PdfBean) session.getAttribute("userInfo");

		// Initialize the User Information for the Report
		userInfo = new PdfBean();
		userInfo.setFirstName("Clark");
		userInfo.setMiddleName("");
		userInfo.setLastName("Jones");
		userInfo.setGender("M");
		userInfo.setFinalDeath(25);
		userInfo.setAge(0);
		session.setAttribute("userInfo", userInfo);
		
		// Initialize the CLAT tools
		clat = new Clat();
		clat.setTrustType("T");
		clat.setLifeType(0);	// Last to Die
		clat.setAge1(0);		
		clat.setAge2(0);
		clat.setFmv(1000000);
		clat.setDiscount(.35);
		clat.setDiscountAssumptionRate(.072);
		clat.setIrsDate(new Date());
		clat.setIrsRate(.05);
		clat.setEstateTaxRate(.55);
		clat.setTerm(10);
		clat.setCalculationType("R");
		clat.setFinalDeath(userInfo.getFinalDeath());
		clat.setAssetGrowth(.05);
		clat.setAssetIncome(.02);
		clat.setRemainderInterest(50000);
		clat.setTaxRate(.35);
		clat.setGrantor(false);
		clat.calculate();
		session.setAttribute("clat", clat);

		// We should always start out at the description page!
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
		
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
