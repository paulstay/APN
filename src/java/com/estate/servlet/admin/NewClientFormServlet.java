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
import com.teag.bean.ABTrust;
import com.teag.bean.AdminBean;
import com.teag.bean.CashFlowBean;
import com.teag.bean.ClientBean;
import com.teag.bean.HeirBean;
import com.teag.bean.PersonBean;
import com.teag.client.AddDummySpouse;

public class NewClientFormServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5822862046779729707L;

	/**
	 * Constructor of the object.
	 */
	public NewClientFormServlet() {
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
		String path = "/client/index.jsp";
		
		String action = request.getParameter("action");
		
		if(action == null) {
			action = "cancel";
		}
		
		
		try {
			AdminBean planner = (AdminBean) session.getAttribute("validUser");
			
			if(planner == null) {
				throw new NullPointerException();
			}
			
			if(action.equalsIgnoreCase("add")){
				PersonBean person = new PersonBean();
				processForm(person,request);
				person.insert();

				// Add a dummy spouse for single clients
				AddDummySpouse ads = new AddDummySpouse();
				ads.setPersonId(person.getId());
				ads.addSpouse();
				
				// Add heirs
				HeirBean heir = new HeirBean();
				heir.setOwnerId(person.getId());
				heir.setNumberOfHeirs(0);
				heir.insert();
				
				// AB Trust - Default
				ABTrust ab = new ABTrust();
				ab.setOwnerId(person.getId());
				ab.setTrusts(1);
				ab.setUsed("O");
				ab.insert();
				
				// Create a new Cash Flow Variables
				CashFlowBean cashFlow = new CashFlowBean();
				cashFlow.setOwnerId(person.getId());
				// Fisrt we get the age of the person, and then add five to get the final death
				// The user can then change this later.
				int age = com.teag.util.Utilities.CalcAge(person.getBirthDate());
				int le = (int) com.teag.util.Utilities.getLifeExp(age, "M");
				
				if(age > 0) {
					cashFlow.setFinalDeath(le+5);
				} else {
					cashFlow.setFinalDeath(25);	// Since we could not figure out the age, we just use 25.
				}
				
				cashFlow.insert();
				
				// Client
				ClientBean client = new ClientBean();
				client.setPrimaryId(person.getId());
				client.setPlannerId(planner.getId());
				client.insert();
				
				session.setAttribute("person", person);
				session.setAttribute("client", client);
				
			}
			
			if(action.equalsIgnoreCase("cancel")){
				path = "/admin/index.jsp";
			}
			
		} catch(Exception e) {
			System.err.println("Error in creating new client!");
			path = "/admin/index.jsp";
		}
		
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request,response);
	}

	public void processForm(PersonBean person, HttpServletRequest request){
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
