package com.estate.servlet.personal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.db.DBObject;
import com.estate.servlet.Utils;
import com.teag.bean.MarriageBean;
import com.teag.bean.PersonBean;


public class ChildSpouseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8184867956743134325L;

	/**
	 * Constructor of the object.
	 */
	public ChildSpouseServlet() {
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
		String path = "/WEB-INF/jsp/client/personal/children/spouseForm.jsp";
		
		String action = request.getParameter("action");
		if(action == null)
			action = "cancel";
		
		try {
			PersonBean person = (PersonBean) session.getAttribute("person");
			
			if(person == null){
				throw new NullPointerException();
			}
			
			if(action.equalsIgnoreCase("add")){
				PersonBean spouse = new PersonBean();
				spouse.initialize();
				
				// Get the child id
				int cid = Utils.getIntegerParameter(request, "cid", 0);
				request.setAttribute("cid", cid);
				request.setAttribute("sp", spouse);
				request.setAttribute("btn", "Add");
				
			}
			
			if(action.equalsIgnoreCase("edit")){
				PersonBean spouse = new PersonBean();
				int id = Utils.getIntegerParameter(request, "id", 0);
				// Get the child id
				int cid = Utils.getIntegerParameter(request, "cid", 0);
				if(id > 0){
					spouse.setId(id);
					spouse.initialize();
					request.setAttribute("sp", spouse);
					request.setAttribute("btn", "Edit");
					request.setAttribute("cid",cid);
				}
			}
			
			if(action.equalsIgnoreCase("insert")){
				int cid = Utils.getIntegerParameter(request, "cid", 0);
				PersonBean spouse = new PersonBean();
				processForm(spouse,request);
				spouse.insert();
				MarriageBean marriage = new MarriageBean();
				marriage.setPersonId(cid);
				marriage.setSpouseId(spouse.getId());
				marriage.setState("MD");
				marriage.setCity("NOT REQUIRED");
				marriage.setStatus("C");
				marriage.setDate("9/21/1984");
				marriage.insert();
				path="/servlet/ChildrenGenServlet";
			}
			
			if(action.equalsIgnoreCase("update")){
				PersonBean spouse = new PersonBean();
				processForm(spouse,request);
				spouse.update();
				
			}
			
			if(action.equalsIgnoreCase("delete")){
				PersonBean spouse = new PersonBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					spouse.setId(id);
					DBObject db = new DBObject();
					db.start();
					db.executeStatement("SET FOREIGN_KEY_CHECKS=0");
					// Get marriage bean and then delete it.
					MarriageBean marriage = new MarriageBean();
					ArrayList mList = marriage.getBeans(MarriageBean.SPOUSE_ID + "='" + spouse.getId() + "'");
					Iterator itr = mList.iterator();
					while(itr.hasNext()) {
						MarriageBean m = (MarriageBean) itr.next();
						m.delete();
					}
					// Delete the spouse record.
					spouse.delete();
					db.executeStatement("SET FOREIGN_KEY_CHECKS=1");
					db.stop();
					path = "/servlet/ChildrenGenServlet";
				}
			}
			if(action.equalsIgnoreCase("cancel")){
				path = "/servlet/ChildrenGenServlet";
			}
			
		} catch (Exception e) {
			System.err.println("error in children spouse edit");
			path = "/admin/selectClients.jsp";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
	}

	public void processForm(PersonBean child, HttpServletRequest request){

		long id = Utils.getLongParameter(request, "id", 0L);
		child.setId(id);

		String firstName = Utils.getStringParameter(request, "firstName", "");
		child.setFirstName(firstName);

		String lastName = Utils.getStringParameter(request, "lastName", "");
		child.setLastName(lastName);
		
		String middleName = Utils.getStringParameter(request, "middleName", "");
		child.setMiddleName(middleName);
		
		Date birthDate = Utils.getDateParameter(request, "birthDate", new Date());
		SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
		child.setBirthDate(df.format(birthDate));
		
		String city = Utils.getStringParameter(request, "city", "Belcamp");
		child.setCity(city);
		
		String state = Utils.getStringParameter(request, "state", "MD");
		child.setState(state);
		
		String gender = Utils.getStringParameter(request, "gender", "F");
		child.setGender(gender);

		// We don't use the SSN anymore!
		String ssn = "000-00-0000";
		child.setSsn(ssn);
		
		String occupation = Utils.getStringParameter(request, "occupation", "");
		child.setOccupation(occupation);
		
		// We assume that citizenship is always US
		String citizenship = "US";
		child.setCitizenship(citizenship);
		
		long healthId = Utils.getLongParameter(request, "healthId", 1);
		child.setHealthId(healthId);

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
