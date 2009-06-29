package com.estate.servlet.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.bean.AdminBean;
import com.teag.bean.PersonBean;

public class AdminClientFormServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1903279131001910228L;

	/**
	 * Constructor of the object.
	 */
	public AdminClientFormServlet() {
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
		String path = "/servlet/AdminSelectClientServlet";
		
		String action = request.getParameter("action");
		
		if(action == null) {
			action = "cancel";
		}
		
		try {
			AdminBean planner = (AdminBean) session.getAttribute("validUser");
			
			if(planner == null) {
				throw new NullPointerException();
			}
			
			if(action.equalsIgnoreCase("update")){
				PersonBean person = new PersonBean();
				processForm(person,request);
				person.update();
			}
			
			if(action.equalsIgnoreCase("cancel")){
				path = "/servlet/AdminSelectClientServlet";
			}
			
		} catch(Exception e) {
			System.err.println("Error in creating new client!");
			path = "/admin/index.jsp";
		}
		
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request,response);

	}

	public void processForm(PersonBean person, HttpServletRequest request){
		int id = Utils.getIntegerParameter(request, "id", 0);
		person.setId(id);
		String firstName = Utils.getStringParameter(request, "firstName", "Clark");
		person.setFirstName(firstName);
		String middleName = Utils.getStringParameter(request, "middleName", "");
		person.setMiddleName(middleName);
		String lastName = Utils.getStringParameter(request, "lastName", "Jones");
		person.setLastName(lastName);
		String occupation = Utils.getStringParameter(request, "occupation", "");
		person.setOccupation(occupation);
		String gender = Utils.getStringParameter(request, "gender", "M");
		person.setGender(gender);
		int healthId = Utils.getIntegerParameter(request, "healthId", 1);
		person.setHealthId(healthId);
		Date bDate =Utils.getDateParameter(request, "birthDate", new Date());
		SimpleDateFormat df  = new SimpleDateFormat("M/d/yyyy");
		person.setBirthDate(df.format(bDate));
		String city = Utils.getStringParameter(request, "city", "Belcamp");
		person.setCity(city);
		String state = Utils.getStringParameter(request, "state", "MD");
		person.setState(state);
		person.setCitizenship("US");
		person.setSsn("not used");
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
