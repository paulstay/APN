package com.estate.servlet.personal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.db.DBObject;
import com.estate.servlet.Utils;
import com.teag.bean.ChildrenBean;
import com.teag.bean.FamilyBean;
import com.teag.bean.MarriageBean;
import com.teag.bean.PersonBean;
import com.teag.view.SpouseView;


public class ChildProcessServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2959863048171744828L;

	/**
	 * Constructor of the object.
	 */
	public ChildProcessServlet() {
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
		String path = "/WEB-INF/jsp/client/personal/children/edit.jsp";
		
		String action = request.getParameter("action");

		try {
			PersonBean person = (PersonBean) session.getAttribute("person");
			
			if(person == null){
				throw new NullPointerException();
			}
			
			if(action != null && action.equalsIgnoreCase("add")){
				PersonBean child = new PersonBean();
				ChildrenBean cb = new ChildrenBean();
				SpouseView sv = new SpouseView();
				ArrayList<SpouseView> sList = sv.getView(person.getId());
				LinkedHashMap<String,String> sMap = new LinkedHashMap<String,String>();
				for(SpouseView s : sList) {
					sMap.put(s.getFirstName(), Long.toString(s.getMarriageId()));
				}
				
				LinkedHashMap<String,String> cm = new LinkedHashMap<String,String>();
				for(int i=0; i < 13; i++) {
					cm.put(Integer.toString(i),Integer.toString(i));
				}
				
				request.setAttribute("sList", sList);	// spouse List
				request.setAttribute("child", child);
				request.setAttribute("btn", "Add");
				request.setAttribute("cm", cm);
				request.setAttribute("sp", sMap);
				request.setAttribute("cb", cb);
			}
			
			if(action != null && action.equalsIgnoreCase("insert")){
				PersonBean child = new PersonBean();
				processForm(child,request);		// We just process the personbean in the process form
				child.insert();
				long mId = Utils.getLongParameter(request, "parent", 0);
				FamilyBean family = new FamilyBean();
				family.setMId(mId);
				family.setId(child.getId());
				family.insert();
				ChildrenBean grandChildren = new ChildrenBean();
				grandChildren.setOwnerId(child.getId());
				int numChildren = Utils.getIntegerParameter(request, "numChildren", 0);
				grandChildren.setNumChildren(numChildren);
				String notes = Utils.getStringParameter(request, "family", "");
				grandChildren.setNotes(notes);
				grandChildren.insert();
				path="/servlet/ChildrenGenServlet";
			}
			
			
			if( action != null && action.equalsIgnoreCase("edit")){
				PersonBean child = new PersonBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0) {
					child.setId(id);
					child.initialize();
					ChildrenBean grandChildren = new ChildrenBean();
					ArrayList<ChildrenBean> gList = grandChildren.getBeans(ChildrenBean.OWNER_ID + "='" + child.getId() + "'");
					if(gList != null){
						ChildrenBean cb = gList.get(0);
						request.setAttribute("cb", cb);
					}
					SpouseView sv = new SpouseView();
					ArrayList<SpouseView> sList = sv.getView(person.getId());
					LinkedHashMap<String,String> sMap = new LinkedHashMap<String,String>();
					for(SpouseView s : sList) {
						sMap.put(s.getFirstName(), Long.toString(s.getMarriageId()));
					}
					
					LinkedHashMap<String,String> cm = new LinkedHashMap<String,String>();
					for(int i=0; i < 13; i++) {
						cm.put(Integer.toString(i),Integer.toString(i));
					}




					request.setAttribute("sList", sList);	// spouse List
					request.setAttribute("child", child);
					request.setAttribute("btn", "Edit");
					request.setAttribute("cm", cm);
					request.setAttribute("sp", sMap);
				}
			}
			
			if(action != null && action.equalsIgnoreCase("update")){
				PersonBean child = new PersonBean();
				processForm(child,request);		// We just process the personbean in the process form
				child.update();
				ChildrenBean grandChildren = new ChildrenBean();
				grandChildren.setOwnerId(child.getId());
				int numChildren = Utils.getIntegerParameter(request, "numChildren", 0);
				grandChildren.setNumChildren(numChildren);
				String notes = Utils.getStringParameter(request, "family", "");
				grandChildren.setNotes(notes);
				grandChildren.update();
				path="/servlet/ChildrenGenServlet";
			}
			
			if(action != null && action.equalsIgnoreCase("cancel")){
				path="/servlet/ChildrenGenServlet";
			}
			
			if(action != null && action.equalsIgnoreCase("delete")){
				DBObject db = new DBObject();
				db.start();
				db.executeStatement("SET FOREIGN_KEY_CHECKS=0");
				db.stop();
				
				MarriageBean mb = new MarriageBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					ArrayList aList = mb.getBeans(MarriageBean.PRIMARY_ID+ "='" + id + "'");
					Iterator itr = aList.iterator();
					while(itr.hasNext()) {
						MarriageBean m = (MarriageBean) itr.next();
						PersonBean spouse = new PersonBean();
						spouse.setId(m.getSpouseId());
						spouse.initialize();
						spouse.delete();
						m.delete();
					}
					// Delete the Family record
					FamilyBean family = new FamilyBean();
					family.setId(id);
					family.delete();

					// Delete any grandchildren records!
					ChildrenBean grandChildren = new ChildrenBean();
					ArrayList<ChildrenBean> gList = grandChildren.getBeans(ChildrenBean.OWNER_ID + "='" + Integer.toString(id) + "'");
					for(ChildrenBean c : gList){
						c.delete();
					}
					
					// Now we can delete the child!
					PersonBean child = new PersonBean();
					child.setId(id);
					child.delete();
					
					db.start();
					db.executeStatement("SET FOREIGN_KEY_CHECKS=1");
					db.stop();
				}
				path="/servlet/ChildrenGenServlet";
			}
			
		} catch (Exception e) {
			System.err.println("error in children edit");
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
