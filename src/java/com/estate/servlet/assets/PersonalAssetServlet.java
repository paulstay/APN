package com.estate.servlet.assets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.constants.InputMaps;
import com.estate.constants.Ownership;
import com.estate.constants.Title;
import com.estate.servlet.Utils;
import com.teag.bean.PersonBean;
import com.teag.bean.PropertyBean;


public class PersonalAssetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3234171522652467855L;

	/**
	 * Constructor of the object.
	 */
	public PersonalAssetServlet() {
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
		String path = "/WEB-INF/jsp/client/assets/personal/index.jsp";
		String action = request.getParameter("action");
		
		boolean processList = false;
		
		if(action == null) {
			action = "list";
		}
		
		try {
			PersonBean person = (PersonBean) session.getAttribute("person");
			
			if(person == null) {
				throw new NullPointerException();
			}
			
			if(action.equalsIgnoreCase("add")){
				PropertyBean bean = new PropertyBean();
				bean.initialize();
				request.setAttribute("prop", bean);
				request.setAttribute("btn", "Add");
				request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
				request.setAttribute("titleOptions", InputMaps.titleTypes);
				request.setAttribute("notesPayOptions", InputMaps.notePayTypes);
				path = "/WEB-INF/jsp/client/assets/personal/form.jsp";
			}
			
			if(action.equalsIgnoreCase("edit")){
				PropertyBean bean = new PropertyBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					bean.setId(id);
					bean.initialize();
					request.setAttribute("prop", bean);
					request.setAttribute("btn", "Edit");
					request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
					request.setAttribute("titleOptions", InputMaps.titleTypes);
					request.setAttribute("personalOptions", InputMaps.notePayTypes);
					path = "/WEB-INF/jsp/client/assets/personal/form.jsp";
				} else {
					processList = true;
				}
			}

			if(action.equalsIgnoreCase("insert")){
				PropertyBean bean = new PropertyBean();
				processForm(bean,request);
				bean.setOwnerId(person.getId());
				bean.insert();
				processList = true;
			}

			if(action.equalsIgnoreCase("update")){
				PropertyBean bean = new PropertyBean();
				processForm(bean,request);
				bean.update();
				processList = true;
			}

			if(action.equalsIgnoreCase("delete")){
				PropertyBean bean = new PropertyBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					bean.setId(id);
					bean.delete();
				}
				processList = true;
			}

			if(action.equalsIgnoreCase("view")){
				PropertyBean bean = new PropertyBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id>0){
					bean.setId(id);
					bean.initialize();
					request.setAttribute("prop", bean);
					path="/WEB-INF/jsp/client/assets/personal/view.jsp";
				}
			}

			if(action.equalsIgnoreCase("cancel")){
				processList = true;
			}

			if(action.equalsIgnoreCase("list") || processList){
				PropertyBean personal = new PropertyBean();
				ArrayList<PropertyBean> cList = personal.getBeans(PropertyBean.OWNER_ID + "='" + person.getId() + "'");
				request.setAttribute("cList", cList);
				path = "/WEB-INF/jsp/client/assets/personal/index.jsp";
			}
			
		} catch (Exception e) {
			System.err.println("Person Bean not found");
			path="/admin/selectClient";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
		
	}

	public void processForm(PropertyBean pb, HttpServletRequest request){
		long id = Utils.getLongParameter(request, "id", 0);
		pb.setId(id);
		long ownerId = Utils.getLongParameter(request, "ownerid", 0);
		pb.setOwnerId(ownerId);
		String description = Utils.getStringParameter(request, "description", "");
		pb.setDescription(description);
		long ownershipId = Utils.getLongParameter(request, "ownershipId", Ownership.CP.id());
		pb.setOwnershipId(ownershipId);
		long titleId = Utils.getLongParameter(request, "titleId", Title.JT.id());
		pb.setTitleId(titleId);
		String locationCity = Utils.getStringParameter(request, "locationCity", "");
		pb.setLocationCity(locationCity);
		String locationState = Utils.getStringParameter(request, "locationState", "");
		pb.setLocationState(locationState);
		String notes = Utils.getStringParameter(request, "notes", "");
		pb.setNotes(notes);
		double basis = Utils.getDoubleParameter(request, "basis", 0);
		pb.setBasis(basis);
		double value = Utils.getDoubleParameter(request, "value", 0);
		pb.setValue(value);
		double growthRate = Utils.getPercentParameter(request, "growthRate", 0);
		pb.setGrowthRate(growthRate);
		double loanBalance = Utils.getDoubleParameter(request, "loanBalance", 0);
		pb.setLoanBalance(loanBalance);
		double loanInterest = Utils.getPercentParameter(request, "loanInterest", 0);
		pb.setLoanInterest(loanInterest);
		long loanFreq = Utils.getIntegerParameter(request, "loanFreq", 1);
		pb.setLoanFreq(loanFreq);
		double loanPayment = Utils.getDoubleParameter(request, "loanPayment", 0);
		pb.setLoanPayment(loanPayment);
		long loanTerm = Utils.getIntegerParameter(request, "loanTerm", 0);
		pb.setLoanTerm(loanTerm);
		String intDed = Utils.getStringParameter(request, "intDed", "N");
		pb.setIntDed(intDed);
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
