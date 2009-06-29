package com.estate.servlet.personal;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.bean.LocationBean;
import com.teag.bean.PersonBean;

public class ResidenceServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4025343054549092888L;

	/**
	 * Constructor of the object.
	 */
	public ResidenceServlet() {
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
		String path = "/WEB-INF/jsp/client/personal/residence/index.jsp";
		
		boolean processList = false;
		String action = request.getParameter("action");
		if(action == null){
			action = "list";
		}
		
		LocationBean res = new LocationBean();
		
		try{
			PersonBean person = (PersonBean) session.getAttribute("person");
			
			if(person == null){
				throw new NullPointerException();
			}
			
			if(action.equalsIgnoreCase("add")){
				res.initialize();
				request.setAttribute("res", res);
				request.setAttribute("btn", "add");
				path = "/WEB-INF/jsp/client/personal/residence/edit.jsp";
			}
			
			if(action.equalsIgnoreCase("edit")){
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					res.setId(id);
					res.initialize();
					request.setAttribute("res", res);
					request.setAttribute("btn", "edit");
					path = "/WEB-INF/jsp/client/personal/residence/edit.jsp";
				} else {
					processList = true;
				}
			}
			
			if(action.equalsIgnoreCase("update")){
				processForm(res, request);
				if(res.getId() > 0){
					res.update();
					request.setAttribute("res",res);
					request.setAttribute("btn", "edit");
					path = "/WEB-INF/jsp/client/personal/residence/edit.jsp";
				}
				processList = true;
			}
			
			if(action.equalsIgnoreCase("insert")){
				processForm(res, request);
				res.insert();
				processList = true;
			}
			
			if(action.equalsIgnoreCase("delete")){
				long id = Utils.getLongParameter(request, "id", -1);
				if(id > 0){
					res.setId(id);
					res.delete();
				}
				processList = true;
			}
			
			if(action.equalsIgnoreCase("cancel")){
				processList = true;
			}
			
			if(action.equalsIgnoreCase("list") || processList){
				ArrayList<LocationBean> cList = res.getBeans(LocationBean.OWNER_ID +"='" + Long.toString(person.getId()) + "'");
				request.setAttribute("cList", cList);
				path = "/WEB-INF/jsp/client/personal/residence/index.jsp";
			}

		} catch(Exception e) {
			System.err.println("error in Location List Person is null");
			path = "/WEB-INF/jsp/client/personal/index.jsp";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
	}

	
	public void processForm(LocationBean res, HttpServletRequest request){
		long id = Utils.getLongParameter(request, "id", -1);
		res.setId(id);
		String type = Utils.getStringParameter(request, "rType", "P");
		res.setType(type);
		String name = Utils.getStringParameter(request, "name", "");
		res.setName(name);
		String address1 = Utils.getStringParameter(request, "address1", "");
		res.setAddress1(address1);
		String address2 = Utils.getStringParameter(request, "address2", "");
		res.setAddress2(address2);
		String city = Utils.getStringParameter(request, "city", "");
		res.setCity(city);
		String state = Utils.getStringParameter(request, "state", "CA");
		res.setState(state);
		String zip = Utils.getStringParameter(request, "zip", "");
		res.setZip(zip);
		String phone = Utils.getStringParameter(request, "phone", "");
		res.setPhone(phone);
		String fax = Utils.getStringParameter(request, "fax", "");
		res.setFax(fax);
		long ownerId = Utils.getLongParameter(request, "ownerId", -1);
		res.setOwnerId(ownerId);
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
