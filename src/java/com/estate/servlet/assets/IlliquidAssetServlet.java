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
import com.teag.bean.IlliquidBean;
import com.teag.bean.PersonBean;


public class IlliquidAssetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 236942859987258684L;

	/**
	 * Constructor of the object.
	 */
	public IlliquidAssetServlet() {
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
		String path = "/WEB-INF/jsp/client/assets/illiquid/index.jsp";
		
		boolean processList = false;
		
		String action = request.getParameter("action");
		if(action == null)
			action = "list";
		
		try {
			PersonBean person = (PersonBean) session.getAttribute("person");
			
			if(person == null){
				throw new NullPointerException();
			}
			
			if(action.equalsIgnoreCase("add")){
				IlliquidBean ib = new IlliquidBean();
				request.setAttribute("illiquid", ib);
				request.setAttribute("btn", "Add");
				request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
				request.setAttribute("titleOptions", InputMaps.titleTypes);
				path = "/WEB-INF/jsp/client/assets/illiquid/form.jsp";
			}
			
			if(action.equalsIgnoreCase("edit")){
				IlliquidBean ib = new IlliquidBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					ib.setId(id);
					ib.initialize();
					request.setAttribute("illiquid", ib);
					request.setAttribute("btn", "Edit");
					request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
					request.setAttribute("titleOptions", InputMaps.titleTypes);
					path = "/WEB-INF/jsp/client/assets/illiquid/form.jsp";
				} else {
					processList = true;
				}
			}
			
			if(action.equalsIgnoreCase("insert")){
				IlliquidBean ib = new IlliquidBean();
				processForm(ib,request);
				ib.insert();
				processList = true;
			}
			
			if(action.equalsIgnoreCase("update")){
				IlliquidBean ib = new IlliquidBean();
				processForm(ib,request);
				ib.update();
				processList = true;
			}
			
			if(action.equalsIgnoreCase("delete")){
				IlliquidBean ib = new IlliquidBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id>0){
					ib.setId(id);
					ib.delete();
					processList = true;
				}
			}
			
			if(action.equalsIgnoreCase("view")){
				IlliquidBean ib = new IlliquidBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					ib.setId(id);
					ib.initialize();
					request.setAttribute("illiquid", ib);
					path = "/WEB-INF/jsp/client/assets/illiquid/view.jsp";
				}
			}
			
			if(action.equalsIgnoreCase("cancel")){
				processList = true;
			}

			if(action.equalsIgnoreCase("list") || processList){
				IlliquidBean ib = new IlliquidBean();
				ArrayList<IlliquidBean> cList = ib.getBeans(IlliquidBean.OWNER_ID + "='" + person.getId() + "'");
				request.setAttribute("cList", cList);
				path = "/WEB-INF/jsp/client/assets/illiquid/index.jsp";
			}
			
		} catch(Exception e){
			System.err.println("Error in Illiquid Asset");
			path = "/client/Assets/index.jsp";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
	}

	public void processForm(IlliquidBean ib, HttpServletRequest request){

		long id = Utils.getLongParameter(request, "id",-1);
		ib.setId(id);
				
		long ownerId = Utils.getLongParameter(request, "ownerId", -1);
		ib.setOwnerId(ownerId);
		
		String description = Utils.getStringParameter(request, "description", "Dummy");
		ib.setDescription(description);
		
		long ownershipId = Utils.getLongParameter(request, "ownershipId", Ownership.CP.id());
		ib.setOwnershipId(ownershipId);
		
		long titleId = Utils.getLongParameter(request, "titleId", Title.JT.id());
		ib.setTitleId(titleId);
		
		String notes = Utils.getStringParameter(request, "notes", "");
		ib.setNotes(notes);
		
		double basis = Utils.getDoubleParameter(request, "basis", 0);
		ib.setBasis(basis);
		
		double value = Utils.getDoubleParameter(request, "value", 0);
		ib.setValue(value);
		
		double growthRate = Utils.getPercentParameter(request, "growth", 0);
		ib.setGrowthRate(growthRate);
		
		double divInt = Utils.getPercentParameter(request, "interest", 0);
		ib.setDivInt(divInt);
		
		String ald = Utils.getStringParameter(request, "ald", "");
		ib.setAld(ald);
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
