package com.estate.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.security.PasswordGenerator;
import com.teag.bean.PlannerBean;

public class PlannerDispatch extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 350336621264943684L;

	/**
	 * Constructor of the object.
	 */
	public PlannerDispatch() {
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
		String pageView = request.getParameter("pageView");
		
		if(pageView == null) {
			pageView = "menu";
		}
		RequestDispatcher dispatch;
		
		// Default to menu
		String forward = "/WEB-INF/jsp/admin/planner/planner.jsp"; 

        // Check to see if we are in the administrative role, if not than
        // we need to go back to the menu.
        if(!request.isUserInRole("admin")){
            forward = "/admin/index.jsp";
        } else if(pageView.equals("add")){
			PasswordGenerator pg = new PasswordGenerator();
			
			PlannerBean pb = new PlannerBean();
			pb.setPassword(pg.getPass());
			pb.setPrivilages("VE");
			request.setAttribute("view", "add");
			session.setAttribute("planner",pb);
			forward = "/WEB-INF/jsp/admin/planner/ePlanner.jsp";
		} else if(pageView.equals("edit")){
			PlannerBean pb = new PlannerBean();
			String id = request.getParameter("id");
			if(id != null) {
				pb.setId(Long.parseLong(id));
				pb.initialize();
				request.setAttribute("view", "edit");
				session.setAttribute("planner", pb);
				forward = "/WEB-INF/jsp/admin/planner/ePlanner.jsp";
			} else {
				forward = "/WEB-INF/jsp/admin/planner/planner.jsp";
			}
		} else if(pageView.equals("list")){
			forward = "/servlet/PlannerList";
		} else if(pageView.equals("menu")){
			forward = "/WEB-INF/jsp/admin/planner/planner.jsp";
		} else {
			forward = "/admin/index.jsp";
		}
		
		dispatch = request.getRequestDispatcher(forward);
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
