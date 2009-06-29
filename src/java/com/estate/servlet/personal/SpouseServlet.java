package com.estate.servlet.personal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.db.DBObject;
import com.estate.servlet.Utils;
import com.teag.bean.ClientBean;
import com.teag.bean.MarriageBean;
import com.teag.bean.PersonBean;
import com.teag.view.SpouseView;

public class SpouseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4591306843703009615L;

	/**
	 * Constructor of the object.
	 */
	public SpouseServlet() {
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
		String path = "/WEB-INF/jsp/client/personal/spouse/index.jsp";
		
		String action = request.getParameter("action");
		
		boolean processList = false;
		
		if(action == null) {
			action = "list";
		}
		
		try {
			PersonBean person = (PersonBean) session.getAttribute("person");
			
			if(person == null){
				ClientBean client = (ClientBean) session.getAttribute("client");
				if(client != null) {
					person = new PersonBean();
					person.setId(client.getPrimaryId());
					person.initialize();
				} else {
					throw new NullPointerException();
				}
			}
			
			if(action.equalsIgnoreCase("add")){
				PersonBean spouse = new PersonBean();
				MarriageBean marriage = new MarriageBean();
				spouse.initialize();
				marriage.initialize();
				// We need to update the client information since we now have a current spouse
				
				request.setAttribute("btn", "add");
				request.setAttribute("spouse", spouse);
				request.setAttribute("marriage", marriage);
				path = "/WEB-INF/jsp/client/personal/spouse/edit.jsp";
			}
			
			if(action.equalsIgnoreCase("edit")){
				PersonBean spouse = new PersonBean();
				MarriageBean marriage = new MarriageBean();
				
				long id = Utils.getLongParameter(request, "id", -1);
				long mid = Utils.getLongParameter(request, "mid", -1);
				
				if( id > 0 && mid > 0){
					spouse.setId(id);
					marriage.setId(mid);
					spouse.initialize();
					marriage.initialize();
					request.setAttribute("btn", "edit");
					request.setAttribute("spouse", spouse);
					request.setAttribute("marriage", marriage);
					path = "/WEB-INF/jsp/client/personal/spouse/edit.jsp";
				}
			}
			
			if(action.equalsIgnoreCase("update")){
				PersonBean spouse = new PersonBean();
				MarriageBean marriage = new MarriageBean();
				processForm(spouse,marriage,request);
				spouse.update();
				marriage.update();
				processList = true;
			}
			
			if(action.equalsIgnoreCase("insert")){
				PersonBean spouse = new PersonBean();
				MarriageBean marriage = new MarriageBean();
				processForm(spouse,marriage,request);
				spouse.insert();
				marriage.setPersonId(person.getId());
				marriage.setSpouseId(spouse.getId());
				marriage.insert();
				if(marriage.getStatus().equals("C")){
					ClientBean client = (ClientBean) session.getAttribute("client");
					if(client != null){
						client.setSpouseId(spouse.getId());
						client.setSingle(false);
						session.setAttribute("client", client);
					}
				}				
				processList = true;
			}
			
			if(action.equalsIgnoreCase("cancel")){
				processList = true;
			}
			
			if(action.equalsIgnoreCase("delete")){
				/*
				 * We need to delete both the marriage record, and the spouse.
				 * We also need to turn off Foreign Key Checks and then turn them on
				 */
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id >0){
					DBObject dbobj = new DBObject();
					dbobj.start();
					dbobj.executeStatement("SET FOREIGN_KEY_CHECKS=0");
					dbobj.delete("FAMILY", "PERSON_ID='" + person.getId() + "'");			// Family Record
					dbobj.delete("MARRIAGE", "SPOUSE_ID='" + Integer.toString(id) + "'");	// Marriage Record
					dbobj.delete("PERSON", "PERSON_ID='" + Integer.toString(id) + "'");		// Spouse
					dbobj.executeStatement("SET FOREIGN_KEY_CHECKS=1");
					dbobj.stop();
				}
				processList = true;
				
			}
			
			if(action.equalsIgnoreCase("list") || processList){
				SpouseView sp = new SpouseView();
				ArrayList<SpouseView> sList = sp.getView(person.getId());
				request.setAttribute("sList", sList);
			}

		} catch (Exception e){
			System.err.println("Person is null in Spouse");
			path = "/servlet/AdminSelectClientServlet";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
	}

	// Process the the form data here
	public void processForm(PersonBean spouse, MarriageBean marriage, HttpServletRequest request){
		/*
		 * Process the fields for the Spouse which is a PersonBean
		 */
		long id = Utils.getLongParameter(request, "id", 0L);
		spouse.setId(id);
		
		
		String firstName = Utils.getStringParameter(request, "firstName", "");
		spouse.setFirstName(firstName);

		String lastName = Utils.getStringParameter(request, "lastName", "");
		spouse.setLastName(lastName);
		
		String middleName = Utils.getStringParameter(request, "middleName", "");
		spouse.setMiddleName(middleName);
		
		Date birthDate = Utils.getDateParameter(request, "birthDate", new Date());
		SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
		spouse.setBirthDate(df.format(birthDate));
		
		String city = Utils.getStringParameter(request, "city", "Belcamp");
		spouse.setCity(city);
		
		String state = Utils.getStringParameter(request, "state", "MD");
		spouse.setState(state);
		
		String gender = Utils.getStringParameter(request, "gender", "F");
		spouse.setGender(gender);

		// We don't use the SSN anymore!
		String ssn = "000-00-0000";
		spouse.setSsn(ssn);
		
		String occupation = Utils.getStringParameter(request, "occupation", "");
		spouse.setOccupation(occupation);
		
		// We assume that citizenship is always US
		String citizenship = "US";
		spouse.setCitizenship(citizenship);
		
		long healthId = Utils.getLongParameter(request, "healthId", 1);
		spouse.setHealthId(healthId);

		/*
		 *  Process the fields for the marriage bean
		 */
		long mid = Utils.getLongParameter(request, "mid", 0L);
		marriage.setId(mid);

		Date marriageDate = Utils.getDateParameter(request, "mDate", new Date());
		marriage.setDate(df.format(marriageDate));
		
		String mCity = Utils.getStringParameter(request, "mCity", "Belcamp");
		String mState = Utils.getStringParameter(request, "mState", "MD");
		String mStatus = Utils.getStringParameter(request, "mStatus", "C");
		
		marriage.setCity(mCity);
		marriage.setState(mState);
		marriage.setStatus(mStatus);
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
