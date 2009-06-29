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
import com.teag.bean.ObservationBean;
import com.teag.bean.PersonBean;


public class ClientObservationsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -528319287107811884L;

	/**
	 * Constructor of the object.
	 */
	public ClientObservationsServlet() {
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
		String path = "/WEB-INF/jsp/client/estate/observations/index.jsp";
		
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
				ObservationBean ob = new ObservationBean();
				request.setAttribute("ob", ob);
				path = "/WEB-INF/jsp/client/estate/observations/form.jsp";
			}

			if( action.equalsIgnoreCase("edit")){
				String id = Utils.getStringParameter(request, "id", null);
				if( id != null) {
					ObservationBean ob = new ObservationBean();
					ob.setId(Long.parseLong(id));
					ob.initialize();
					request.setAttribute("ob", ob);
					path = "/WEB-INF/jsp/client/estate/observations/form.jsp";
				} else {
					processList = true;
				}
			}

			if(action.equalsIgnoreCase("update")){
				String id = Utils.getStringParameter(request, "id", null);
				if( id != null) {
					ObservationBean ob = new ObservationBean();
					ob.setId(Long.parseLong(id));
					processForm(ob,request);
					ob.update();
				}				
				processList = true;
			}
			
			if(action.equalsIgnoreCase("insert")){
				ObservationBean ob = new ObservationBean();
				processForm(ob,request);
				if(ob.getObservation().length()> 0)
					ob.insert();
				processList = true;
			}
			
			if(action.equalsIgnoreCase("delete")){
				String id = Utils.getStringParameter(request, "id", null);
				if( id != null) {
					ObservationBean ob = new ObservationBean();
					ob.setId(Long.parseLong(id));
					ob.delete();
				}				
				processList = true;
			}
			
			if(action.equalsIgnoreCase("cancel")){
				processList = true;
			}
			
			if( action.equalsIgnoreCase("list") || processList){
				ObservationBean ob = new ObservationBean();
				ArrayList<ObservationBean> cList = ob.getBeans(ObservationBean.OWNER_ID + "='" + person.getId() + "'");
				request.setAttribute("cList", cList);
				path = "/WEB-INF/jsp/client/estate/observations/index.jsp";
			}
			
		} catch (Exception e){
			System.err.println("Person Bean not found in Cash List");
			path="/admin/selectClient";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
	}
	
	public void processForm(ObservationBean ob, HttpServletRequest request){
		long ownerId = Utils.getLongParameter(request, "ownerId", 0);
		ob.setOwnerId(ownerId);
		String observation = Utils.getStringParameter(request,"observation", "");
		ob.setObservation(observation);
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
