package com.estate.servlet.std.clat;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import com.teag.bean.PdfBean;
import com.estate.servlet.*;

public class StdClatUserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1261703802821914705L;

	/**
	 * Constructor of the object.
	 */
	public StdClatUserServlet() {
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
		String path = "/WEB-INF/jsp/standalone/clat/user.jsp";
		String action = request.getParameter("action");
		PdfBean userInfo = (PdfBean) session.getAttribute("userInfo");
		
		if(action != null && action.equals("update")){
			String firstName = Utils.getStringParameter(request, "firstName", "");
			userInfo.setFirstName(firstName);
			String lastName = Utils.getStringParameter(request, "lastName", "");
			userInfo.setLastName(lastName);
			String middleName = Utils.getStringParameter(request, "middleName", "");
			userInfo.setMiddleName(middleName);
			String gender = Utils.getStringParameter(request, "gender", "B");
			userInfo.setGender(gender);
			session.setAttribute("userInfo", userInfo);
		}
		
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
