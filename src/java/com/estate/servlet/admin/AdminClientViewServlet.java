package com.estate.servlet.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estate.constants.HealthTypes;
import com.estate.servlet.Utils;
import com.teag.bean.PersonBean;

public class AdminClientViewServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1592867242743659852L;

	/**
	 * Constructor of the object.
	 */
	public AdminClientViewServlet() {
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
		String path = "/WEB-INF/jsp/admin/client/edit.jsp";
		
		String action = request.getParameter("action");
		
		if(action == null) {
			action = "cancel";
		}
		
		try {
			PersonBean person = new PersonBean();
			int id = Utils.getIntegerParameter(request, "id", 0);
			if(id>0){
				person.setId(id);
				person.initialize();
				request.setAttribute("person", person);
			} else {
				throw new Exception();
			}

			long hId = person.getHealthId();
			
			HealthTypes ht = HealthTypes.getType((int)hId);
			
			if(ht==null){
				ht = HealthTypes.NI;
			}

			request.setAttribute("hDesc", ht.description());
			
			if(action.equalsIgnoreCase("view")){
				path = "/WEB-INF/jsp/admin/client/view.jsp";
			}
			
			if(action.equalsIgnoreCase("edit")){
				path = "/WEB-INF/jsp/admin/client/edit.jsp";
			}
		} catch(Exception e) {
			System.err.println("Error in view or edit client");
			path = "/servlet/AdminSelectClientServlet";
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
