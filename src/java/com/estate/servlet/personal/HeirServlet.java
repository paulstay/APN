package com.estate.servlet.personal;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.bean.ClientBean;
import com.teag.bean.HeirBean;


public class HeirServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8399853849504214633L;

	/**
	 * Constructor of the object.
	 */
	public HeirServlet() {
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
		String path = "/WEB-INF/jsp/client/personal/heirs/index.jsp";
		
		HeirBean heir = (HeirBean) request.getAttribute("heir");
		ClientBean client = (ClientBean) session.getAttribute("client");
		String action = request.getParameter("action");
		if(action == null) {
			action = "index";
		}
		
		try {
			if(client == null){
				throw new NullPointerException();
			}
			// init code
			if(heir == null){
				heir = new HeirBean();
				heir.setOwnerId(client.getPrimaryId());
				heir.initialize();
				if(heir.getId()==0)
					heir.insert();
				
				request.setAttribute("heir", heir);
			}
			
			if(action.equalsIgnoreCase("change")){
				request.setAttribute("heir",heir);
				path = "/WEB-INF/jsp/client/personal/heirs/add.jsp";
			}
			
			if(action.equalsIgnoreCase("update")){
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					int ownerId = Utils.getIntegerParameter(request, "ownerId", 0);
					heir.setOwnerId(ownerId);
					int numberOfHeirs = Utils.getIntegerParameter(request, "numberOfHeirs", 0);
					heir.setNumberOfHeirs(numberOfHeirs);
					heir.update();
				}
				request.setAttribute("heir", heir);
			}
			
		} catch (Exception e) {
			System.err.println("Can't find person in Heirs");
			path = "/admin/selectClient";
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
