package com.estate.servlet.assets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.constants.DebtTypes;
import com.estate.constants.InputMaps;
import com.estate.constants.Ownership;
import com.estate.constants.Title;
import com.estate.servlet.Utils;
import com.teag.bean.DebtBean;
import com.teag.bean.PersonBean;

public class DebtAssetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8352668229855912187L;

	/**
	 * Constructor of the object.
	 */
	public DebtAssetServlet() {
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
		String path = "/WEB-INF/jsp/client/assets/debt/index.jsp";
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
				DebtBean d = new DebtBean();
				d.initialize();
				request.setAttribute("debt", d);
				request.setAttribute("btn", "Add");
				request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
				request.setAttribute("titleOptions", InputMaps.titleTypes);
				request.setAttribute("debtOptions", InputMaps.debtTypes);
				path = "/WEB-INF/jsp/client/assets/debt/form.jsp";
			}
			
			if(action.equalsIgnoreCase("edit")){
				DebtBean d = new DebtBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					d.setId(id);
					d.initialize();
					request.setAttribute("debt", d);
					request.setAttribute("btn", "Edit");
					request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
					request.setAttribute("titleOptions", InputMaps.titleTypes);
					request.setAttribute("debtOptions", InputMaps.debtTypes);
					path = "/WEB-INF/jsp/client/assets/debt/form.jsp";
				} else {
					processList = true;
				}
				
			}

			if(action.equalsIgnoreCase("insert")){
				DebtBean d = new DebtBean();
				processForm(d,request);
				d.insert();
				processList = true;
			}

			if(action.equalsIgnoreCase("update")){
				DebtBean d = new DebtBean();
				processForm(d,request);
				d.update();
				processList = true;
			}

			if(action.equalsIgnoreCase("delete")){
				DebtBean d = new DebtBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					d.setId(id);
					d.delete();
				}
				processList = true;
			}

			if(action.equalsIgnoreCase("view")){
				DebtBean d = new DebtBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id>0){
					d.setId(id);
					d.initialize();
					request.setAttribute("bond", d);
					path="/WEB-INF/jsp/client/assets/debt/view.jsp";
				}
			}

			if(action.equalsIgnoreCase("cancel")){
				processList = true;
			}

			if(action.equalsIgnoreCase("list") || processList){
				DebtBean debt = new DebtBean();
				ArrayList<DebtBean> cList = debt.getBeans(DebtBean.OWNER_ID + "='" + person.getId() + "'");
				request.setAttribute("cList", cList);
				path = "/WEB-INF/jsp/client/assets/debt/index.jsp";
			}
			
		} catch (Exception e) {
			System.err.println("Person Bean not found in Cash List");
			path="/admin/selectClient";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
		
	}

	
	public void processForm(DebtBean d, HttpServletRequest request){
		long id = Utils.getLongParameter(request, "id", -1);		
		d.setId(id);
		long ownerId = Utils.getLongParameter(request, "ownerId", 0);
		d.setOwnerId(ownerId);
		String description = Utils.getStringParameter(request, "description", "Dummy record");
		d.setDescription(description);
		long debtTypeId = Utils.getLongParameter(request, "debtType", DebtTypes.C.id());
		d.setDebtTypeId(debtTypeId);
		String bank = Utils.getStringParameter(request, "bank", "");
		d.setBank(bank);
		long acct = Utils.getLongParameter(request, "acct", 0l);
		d.setAcct(acct);
		long ownershipId = Utils.getLongParameter(request, "ownershipId", Ownership.CP.id());
		d.setOwnershipId(ownershipId);
		long titleId = Utils.getLongParameter(request, "titleId", Title.B.id());
		d.setTitleId(titleId);
		String notes = Utils.getStringParameter(request, "notes", "");
		d.setNotes(notes);
		double value = Utils.getDoubleParameter(request, "value", 0);
		d.setValue(value);
		long loanTerm = Utils.getLongParameter(request, "term", 0);
		d.setLoanTerm(loanTerm);
		double interestRate = Utils.getPercentParameter(request, "interestRate", 0);
		d.setInterestRate(interestRate);
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
