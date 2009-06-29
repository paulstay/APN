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
import com.teag.bean.AdvisorBean;
import com.teag.bean.PersonBean;


public class AdvisorServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7361162277768230629L;

	/**
	 * Constructor of the object.
	 */
	public AdvisorServlet() {
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
		String path = "/WEB-INF/jsp/client/personal/advisors/index.jsp";
		
		boolean processList = false;
		String action = request.getParameter("action");
		if(action == null){
			action = "list";
		}
		
		AdvisorBean advisor = new AdvisorBean();
		
		try{
			PersonBean person = (PersonBean) session.getAttribute("person");
			
			if(person == null){
				throw new NullPointerException();
			}
			
			if(action.equalsIgnoreCase("add")){
				advisor.initialize();
				request.setAttribute("advisor", advisor);
				request.setAttribute("btn", "add");
				path = "/WEB-INF/jsp/client/personal/advisors/edit.jsp";
			}
			
			if(action.equalsIgnoreCase("edit")){
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					advisor.setId(id);
					advisor.initialize();
					request.setAttribute("advisor", advisor);
					request.setAttribute("btn", "edit");
					path = "/WEB-INF/jsp/client/personal/advisors/edit.jsp";
				} else {
					processList = true;
				}
			}
			
			if(action.equalsIgnoreCase("update")){
				processForm(advisor, request);
				if(advisor.getId() > 0){
					advisor.update();
					request.setAttribute("advisor", advisor);
					request.setAttribute("btn", "edit");
					path = "/WEB-INF/jsp/client/personal/advisors/edit.jsp";
				}
				processList = true;
			}
			
			if(action.equalsIgnoreCase("insert")){
				processForm(advisor, request);
				advisor.insert();
				processList = true;
			}
			
			if(action.equalsIgnoreCase("delete")){
				long id = Utils.getLongParameter(request, "id", -1);
				if(id > 0){
					advisor.setId(id);
					advisor.delete();
				}
				processList = true;
			}
			
			if(action.equalsIgnoreCase("cancel")){
				processList = true;
			}
			
			if(action.equalsIgnoreCase("list") || processList){
				ArrayList<AdvisorBean> cList = advisor.getBeans(AdvisorBean.OWNER_ID +"='" + Long.toString(person.getId()) + "'");
				request.setAttribute("cList", cList);
				path = "/WEB-INF/jsp/client/personal/advisors/index.jsp";
			}

		} catch(Exception e) {
			System.err.println("error in Advisor List Person is null");
			path = "/WEB-INF/jsp/client/personal/index.jsp";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);

	}
	
	public void processForm(AdvisorBean advisor, HttpServletRequest request){
		long id = Utils.getLongParameter(request, "id", -1);
		advisor.setId(id);
		long ownerID = Utils.getLongParameter(request, "ownerId", -1);
		advisor.setOwnerID(ownerID);
		long typeId = Utils.getLongParameter(request, "advisorType", 1);
		advisor.setTypeId(typeId);
		String firstName = Utils.getStringParameter(request, "firstName", "");
		advisor.setFirstName(firstName);
		String middleName = Utils.getStringParameter(request, "middleName", "");
		advisor.setMiddleName(middleName);
		String lastName = Utils.getStringParameter(request, "lastName", "");
		advisor.setLastName(lastName);
		String suffix = Utils.getStringParameter(request, "suffix", "");
		advisor.setSuffix(suffix);
		String title = Utils.getStringParameter(request, "title", "");
		advisor.setTitle(title);
		String firmName = Utils.getStringParameter(request, "firmName", "");
		advisor.setFirmName(firmName);
		String address1 = Utils.getStringParameter(request, "address1", "");
		advisor.setAddress1(address1);
		String address2 = Utils.getStringParameter(request, "address2", "");
		advisor.setAddress2(address2);
		String city = Utils.getStringParameter(request, "city", "");
		advisor.setCity(city);
		String state = Utils.getStringParameter(request, "state", "");
		advisor.setState(state);
		String zip = Utils.getStringParameter(request, "zip", "");
		advisor.setZip(zip);
		String phone =  Utils.getStringParameter(request, "phone", "");
		advisor.setPhone(phone);
		String fax = Utils.getStringParameter(request, "fax", "");
		advisor.setFax(fax);
		String email = Utils.getStringParameter(request, "email", "");
		advisor.setEmail(email);
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
