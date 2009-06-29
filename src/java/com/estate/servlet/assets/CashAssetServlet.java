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
import com.teag.bean.CashBean;
import com.teag.bean.PersonBean;

public class CashAssetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3360962432127194163L;

	/**
	 * Constructor of the object.
	 */
	public CashAssetServlet() {
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
		String path = "/WEB-INF/jsp/client/assets/cash";
		
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
				CashBean cb = new CashBean();
				cb.initialize();
				request.setAttribute("cash", cb);
				request.setAttribute("btn", "Add");
				request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
				request.setAttribute("titleOptions", InputMaps.titleTypes);
				path = "/WEB-INF/jsp/client/assets/cash/form.jsp";
			}

			if( action.equalsIgnoreCase("edit")){
				String id = request.getParameter("id");
				if(id != null){
					CashBean cb = new CashBean();
					cb.setId(Long.parseLong(id));
					cb.initialize();
					request.setAttribute("cash", cb);
					request.setAttribute("btn", "Edit");
					request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
					request.setAttribute("titleOptions", InputMaps.titleTypes);
					path = "/WEB-INF/jsp/client/assets/cash/form.jsp";
				} else {
					processList = true;
				}
			}

			if( action.equalsIgnoreCase("insert")){
				CashBean cb = new CashBean();
				processForm(cb,request);
				cb.insert();
				processList = true;
			}

			if( action.equalsIgnoreCase("update")){
				CashBean cb = new CashBean();
				processForm(cb,request);
				cb.update();
				processList = true;
			}
			
			if( action.equalsIgnoreCase("delete")){
				CashBean cb = new CashBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if( id > 0){
					cb.setId(id);
					cb.delete();
				}
				processList = true;
			}

			if( action.equalsIgnoreCase("view")){
				CashBean cb = new CashBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					cb.setId(id);
					cb.initialize();
					request.setAttribute("cash", cb);
					path = "/WEB-INF/jsp/client/assets/cash/view.jsp";
				} else {
					processList = true;
				}
			}

			if( action.equalsIgnoreCase("cancel")){
				processList = true;
			}

			if( action.equalsIgnoreCase("list") || processList){
				CashBean cb = new CashBean();
				ArrayList<CashBean> cList = cb.getBeans(CashBean.OWNER_ID + "='" + person.getId() + "'");
				request.setAttribute("cList", cList);
				path = "/WEB-INF/jsp/client/assets/cash/index.jsp";
			}
			
		} catch (Exception e){
			System.err.println("Person Bean not found in Cash List");
			path="/admin/selectClient";
		}
		
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
		
	}

	public void processForm(CashBean cb, HttpServletRequest request){

		long id = Utils.getLongParameter(request, "id", -1);
		cb.setId(id);
		
		long ownerId = Utils.getLongParameter(request, "ownerId", 0);
		cb.setOwnerId(ownerId);
		
		String name = Utils.getStringParameter(request, "description", "");
		cb.setName(name);
		
		long ownership = Utils.getLongParameter(request, "ownershipId", Ownership.CP.id());
		cb.setOwnership(ownership);
		
		long title = Utils.getLongParameter(request, "titleId", Title.JT.id());
		cb.setTitle(title);
		
		double basis = Utils.getDoubleParameter(request, "basis", 0);
		cb.setBasis(basis);
		
		double currentValue = Utils.getDoubleParameter(request, "value", 0);
		cb.setCurrentValue(currentValue);
		
		double interest = Utils.getPercentParameter(request, "interest", .02);
		cb.setInterest(interest);
		
		double growth = Utils.getPercentParameter(request, "growth", 0);
		cb.setGrowth(growth);
		
		String bank = Utils.getStringParameter(request, "bank", "");
		cb.setBank(bank);
		
		long acct = Utils.getLongParameter(request, "acct", 0);
		cb.setAcct(acct);
		
		String notes = Utils.getStringParameter(request, "notes", "");
		cb.setNotes(notes);
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
