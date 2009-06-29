package com.estate.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teag.bean.PlannerBean;


public class PlannerList extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3962273641849711778L;

	/**
	 * Constructor of the object.
	 */
	public PlannerList() {
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
		int first = 0;
		int count = 10;		// list 20 per page
		
		RequestDispatcher dispatch;
        String path = "/WEB-INF/jsp/admin/planner/listPlanners.jsp";
		
		ArrayList<PlannerBean> planners = new ArrayList<PlannerBean>();
        
        int pageNum = 0;

        if(request.isUserInRole("admin")){

		    PlannerBean pb = new PlannerBean();

            String page = request.getParameter("pageNum");

            if (page != null) {
                pageNum = Integer.parseInt(page);
            }

            if (pageNum >= 0) {
                first = pageNum * 10;
            }

            planners = pb.getBeans(PlannerBean.ID + ">0 limit " + first + "," + count);
        } else {
            path = "/index/admin.jsp";
        }
		
		dispatch = request.getRequestDispatcher(path);
		
		request.setAttribute("planners", planners);
		request.setAttribute("page",Integer.toString(pageNum++));
		request.setAttribute("prev",Integer.toString(pageNum-1));

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
