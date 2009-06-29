package com.estate.servlet.client.estate;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.bean.ObjectivesBean;
import com.teag.bean.PersonBean;

public class ClientObjectivesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 966520553971255818L;

	/**
	 * Constructor of the object.
	 */
	public ClientObjectivesServlet() {
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
		String path = "/WEB-INF/jsp/client/estate/objectives/index.jsp";
		
		String action = request.getParameter("action");
		
		if(action == null)
			action = "list";
		
		boolean processList = false;
		
		try{
			PersonBean person = (PersonBean) session.getAttribute("person");
			
			if(person == null) {
				throw new NullPointerException();
			}
			
			if( action.equalsIgnoreCase("add")){
				ObjectivesBean ob = new ObjectivesBean();
				request.setAttribute("ob", ob);
				path = "/WEB-INF/jsp/client/estate/objectives/form.jsp";
			}

			if( action.equalsIgnoreCase("edit")){
				String id = Utils.getStringParameter(request, "id", null);
				if( id != null) {
					ObjectivesBean ob = new ObjectivesBean();
					ob.setId(Long.parseLong(id));
					ob.initialize();
					request.setAttribute("ob", ob);
					path = "/WEB-INF/jsp/client/estate/objectives/form.jsp";
				} else {
					processList = true;
				}
			}

			if(action.equalsIgnoreCase("update")){
				String id = Utils.getStringParameter(request, "id", null);
				if( id != null) {
					ObjectivesBean ob = new ObjectivesBean();
					ob.setId(Long.parseLong(id));
					processForm(ob,request);
					ob.update();
				}				
				processList = true;
			}
			
			if(action.equalsIgnoreCase("insert")){
				ObjectivesBean ob = new ObjectivesBean();
				processForm(ob,request);
				if(ob.getObjective().length()> 0)
					ob.insert();
				processList = true;
			}
			
			if(action.equalsIgnoreCase("delete")){
				String id = Utils.getStringParameter(request, "id", null);
				if( id != null) {
					ObjectivesBean ob = new ObjectivesBean();
					ob.setId(Long.parseLong(id));
					ob.delete();
				}				
				processList = true;
			}
			
			if(action.equalsIgnoreCase("cancel")){
				processList = true;
			}
			
			if( action.equalsIgnoreCase("list") || processList){
				ObjectivesBean ob = new ObjectivesBean();
				ArrayList<ObjectivesBean> cList = ob.getBeans(ObjectivesBean.OWNER_ID + "='" + person.getId() + "'");
				request.setAttribute("cList", cList);
				path = "/WEB-INF/jsp/client/estate/objectives/index.jsp";
			}
			
		} catch (Exception e){
			System.err.println("Person Bean not found in Cash List");
			path="/admin/selectClient";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);

	}

	public void processForm(ObjectivesBean ob, HttpServletRequest request){
		long ownerId = Utils.getLongParameter(request, "ownerId", -1);
		ob.setOwnerId(ownerId);
		String objective = Utils.getStringParameter(request, "objective", "");
		ob.setObjective(objective);
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
