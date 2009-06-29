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
import com.teag.bean.BondBean;
import com.teag.bean.PersonBean;


public class BondAssetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5771121732394005077L;

	/**
	 * Constructor of the object.
	 */
	public BondAssetServlet() {
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
		String path = "/WEB-INF/jsp/client/assets/bonds/index.jsp";
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
				BondBean b = new BondBean();
				b.initialize();
				request.setAttribute("bond", b);
				request.setAttribute("btn", "Add");
				request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
				request.setAttribute("titleOptions", InputMaps.titleTypes);
				path = "/WEB-INF/jsp/client/assets/bonds/form.jsp";
			}
			
			if(action.equalsIgnoreCase("edit")){
				BondBean b = new BondBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					b.setId(id);
					b.initialize();
					request.setAttribute("bond", b);
					request.setAttribute("btn", "Edit");
					request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
					request.setAttribute("titleOptions", InputMaps.titleTypes);
					path = "/WEB-INF/jsp/client/assets/bonds/form.jsp";
				} else {
					processList = true;
				}
				
			}

			if(action.equalsIgnoreCase("insert")){
				BondBean b = new BondBean();
				processForm(b,request);
				b.insert();
				processList = true;
			}

			if(action.equalsIgnoreCase("update")){
				BondBean b = new BondBean();
				processForm(b,request);
				b.update();
				processList = true;
			}

			if(action.equalsIgnoreCase("delete")){
				BondBean b = new BondBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					b.setId(id);
					b.delete();
				}
				processList = true;
			}

			if(action.equalsIgnoreCase("view")){
				BondBean b = new BondBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id>0){
					b.setId(id);
					b.initialize();
					request.setAttribute("bond", b);
					path="/WEB-INF/jsp/client/assets/bonds/view.jsp";
				}
			}

			if(action.equalsIgnoreCase("cancel")){
				processList = true;
			}

			if(action.equalsIgnoreCase("list") || processList){
				BondBean b = new BondBean();
				ArrayList<BondBean> cList = b.getBeans(BondBean.OWNER_ID + "='" + person.getId() + "'");
				request.setAttribute("cList", cList);
				path = "/WEB-INF/jsp/client/assets/bonds/index.jsp";
			}
			
		} catch (Exception e) {
			System.err.println("Person Bean not found in Cash List");
			path="/admin/selectClient";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
	}
	
	
	public void processForm(BondBean b, HttpServletRequest request){
		 
		long ownerId = Utils.getLongParameter(request, "ownerId", -1);
		b.setOwnerId(ownerId);
		
		long id = Utils.getLongParameter(request, "id", -1);
		b.setId(id);

		String name = Utils.getStringParameter(request, "description", "");
		b.setName(name);
		
		long ownership = Utils.getLongParameter(request, "ownershipId", Ownership.CP.id());
		b.setOwnership(ownership);
		
		long title = Utils.getLongParameter(request, "titleId", Title.JT.id());
		b.setTitle(title);
		
		double basis = Utils.getDoubleParameter(request, "basis", 0);
		b.setBasis(basis);
		
		double currentValue = Utils.getDoubleParameter(request, "value", 0);
		b.setCurrentValue(currentValue);
		
		double interest = Utils.getPercentParameter(request, "interest", 0);
		b.setInterest(interest);
		
		double growth = Utils.getPercentParameter(request, "growth", 0);
		b.setGrowth(growth);
		
		String tax = Utils.getStringParameter(request, "tax", "");
		b.setTax(tax);
		
		String amt = Utils.getStringParameter(request, "amt", "N");
		b.setAmt(amt);
		
		String notes = Utils.getStringParameter(request, "notes", "");
		b.setNotes(notes);
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
