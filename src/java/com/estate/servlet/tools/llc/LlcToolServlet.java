package com.estate.servlet.tools.llc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.validate.ValidatePercent;
import com.teag.estate.LlcTool;

public class LlcToolServlet extends HttpServlet {

	/**
	 * 
	 */
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5193388851394066300L;

	/**
	 * Constructor of the object.
	 */
	public LlcToolServlet() {
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
		LlcTool tool = (LlcTool) session.getAttribute("llc");

		String pageView = request.getParameter("pageView");

		if (pageView != null && pageView.equalsIgnoreCase("process")) {
			// Get each of the parameters for the tool
			String desc = request.getParameter("description");
			if (desc != null)
				tool.setName(desc);
			else {
				tool.setName("");
			}

			String gpShares = request.getParameter("managerShares");
			double gpValue = ValidatePercent.checkPercent(gpShares);
			tool.setManagerShares(gpValue);

			String lpShares = request.getParameter("memberShares");
			double lpValue = ValidatePercent.checkPercent(lpShares);
			tool.setMemberShares(lpValue);

			String discountRate = request.getParameter("discountRate");
			double discountValue = ValidatePercent.checkPercent(discountRate);
			tool.setDiscountRate(discountValue);

			String premiumGp = request.getParameter("premiumGPValue");
			double premiumValue = ValidatePercent.checkPercent(premiumGp);
			tool.setPremiumGpValue(premiumValue);
		}

		// Send us back to the form!
		request.setAttribute("URI", request.getRequestURI());
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/WEB-INF/jsp/estatetools/llc/tool.jsp");

		dispatcher.forward(request, response);

	}

	/**
	 * The doPut method of the servlet. <br>
	 * 
	 * This method is called when a HTTP put request is received.
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
	public void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Put your code here
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
