package com.estate.servlet.client.setup;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.bean.ABTrust;
import com.teag.bean.ClientBean;

public class ABTrustServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3273764333385539584L;

	/**
	 * Constructor of the object.
	 */
	public ABTrustServlet() {
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
		String path = "/WEB-INF/jsp/client/setup/abtrust/index.jsp";
		
		ClientBean client = (ClientBean) session.getAttribute("client");

		// We want to get the most current each time we visit!
		ABTrust ab = new ABTrust();
		ab.query(client.getPrimaryId());

		// Process and form events
		String pageView = request.getParameter("pageView");
		
		if(pageView!= null && pageView.equalsIgnoreCase("update")){
			String useFlag = Utils.getStringParameter(request, "useBoth", "0");
			ab.setUsed(useFlag);
			int beforeTrusts = Utils.getIntegerParameter(request, "beforeTrusts", 1);
			ab.setBeforeTrusts(beforeTrusts);
			int afterTrusts =  Utils.getIntegerParameter(request, "afterTrusts", 1);
			ab.setAfterTrusts(afterTrusts);
			ab.setOwnerId(client.getPrimaryId());
			ab.update();
		}
		
		if(pageView!= null && pageView.equalsIgnoreCase("return")){
			path = "/WEB-INF/jsp/client/setup/index.jsp";
		}
		
		request.setAttribute("ab", ab);
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
