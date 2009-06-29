package com.estate.admin;

import com.sun.appserv.security.ProgrammaticLogin;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teag.bean.AdminBean;
import com.teag.bean.LogonBean;


public class PlannerLogin extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8912982138892696835L;

	/**
	 * Constructor of the object.
	 */
	public PlannerLogin() {
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
		String path = "/index.jsp";	// Default to is to go back to the main index page..
		
		session.removeAttribute("validUser");
		
		String userName = request.getParameter("j_username");
		String password = request.getParameter("j_password");
        boolean hash = false;

        // If the hash parameter is set, and not null, we already have the hash code.
        if(request.getParameter("hash")!= null){
            hash = true;
        }

        if(userName == null || password == null){
			request.setAttribute("errorMsg", "User name and or Password cannot be blank");
			path = "/login.jsp";
		}
	
		if(userName.length()> 0 && password.length() > 0){
			AdminBean validUser = new AdminBean();
			validUser.setPassword(password);
			validUser.setUserName(userName);
            if(hash) {
                validUser.setHash(true);
            }
			validUser.initialize();
			
			if(validUser.isAuthorized()&& validUser.getStatus().equalsIgnoreCase("A")){
				session.setAttribute("validUser", validUser);
				path = "/admin/index.jsp";
				Date now = new Date();
				SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy hh:mm");
				System.out.println("User Login: " + validUser.getUserName() + " @ "+ df.format(now));
				LogonBean lb = new LogonBean();
				lb.setPlannerId(validUser.getId());
				lb.setIpAddress(request.getRemoteAddr());
				lb.insert();

                // We need to add the group table, and the test for the password here
                // If we get a true value we can access all of the data.
                // Instead of adding the JDBC connection and we only need
                // Two roles for all users, we will just pick
                // Fish refers to glassfish server. You will need to add the security
                // role in the software for this to work
                String fishUser = "user";
                String fishPass = "user";

                if (userName.equalsIgnoreCase("admin")) {
                    fishUser = "admin";
                }
                ProgrammaticLogin pl = new ProgrammaticLogin();
                boolean validLogin = pl.login(fishUser, fishPass, request, response);

            } else {
				// Need to redirect to an login error page.
				path = "/login.jsp";
			}
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
