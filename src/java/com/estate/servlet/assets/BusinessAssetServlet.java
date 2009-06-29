package com.estate.servlet.assets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.constants.BusinessTypes;
import com.estate.constants.InputMaps;
import com.estate.constants.Ownership;
import com.estate.servlet.Utils;
import com.teag.bean.BusinessBean;
import com.teag.bean.PersonBean;


public class BusinessAssetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8570142029896776115L;

	/**
	 * Constructor of the object.
	 */
	public BusinessAssetServlet() {
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
		String path = "/WEB-INF/jsp/client/assets/business/index.jsp";
		
		String action = request.getParameter("action");
		boolean processList = false;
		
		if(action == null){
			action = "list";
		}
		
		try {
			
			PersonBean person = (PersonBean) session.getAttribute("person");
			
			if(person == null){
				throw new NullPointerException();
			}
			
			if(action.equalsIgnoreCase("add")){
				BusinessBean biz = new BusinessBean();
				biz.initialize();
				request.setAttribute("biz", biz);
				request.setAttribute("btn", "Add");
				request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
				request.setAttribute("titleOptions", InputMaps.titleTypes);
				request.setAttribute("bizOptions",InputMaps.businessTypes);
				path = "/WEB-INF/jsp/client/assets/business/form.jsp";
			}
			
			if(action.equalsIgnoreCase("edit")){
				BusinessBean biz = new BusinessBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					biz.setId(id);
					biz.initialize();
					request.setAttribute("biz", biz);
					request.setAttribute("btn", "Edit");
					request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
					request.setAttribute("titleOptions", InputMaps.titleTypes);
					request.setAttribute("bizOptions",InputMaps.businessTypes);
					path = "/WEB-INF/jsp/client/assets/business/form.jsp";
				} else {
					processList = true;
				}
				
			}
			
			if(action.equalsIgnoreCase("insert")){
				BusinessBean biz = new BusinessBean();
				processForm(biz, request);
				biz.insert();
				processList = true;
			}
			
			if(action.equalsIgnoreCase("update")){
				BusinessBean biz = new BusinessBean();
				processForm(biz,request);
				biz.update();
				processList = true;
			}
			
			if(action.equalsIgnoreCase("delete")){
				BusinessBean biz = new BusinessBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					biz.setId(id);
					biz.delete();
				}
				processList = true;
			}
			
			if(action.equalsIgnoreCase("cancel")){
				processList = true;
			}
			
			if(action.equalsIgnoreCase("view")){
				BusinessBean biz = new BusinessBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					biz.setId(id);
					biz.initialize();
					request.setAttribute("biz", biz);
					path= "/WEB-INF/jsp/client/assets/business/view.jsp";
				}
				
			}
			
			
			if(action.equalsIgnoreCase("list") || processList){
				BusinessBean biz = new BusinessBean();
				ArrayList<BusinessBean> cList = biz.getBeans(BusinessBean.OWNER_ID + "='" + person.getId() + "'");
				request.setAttribute("cList", cList);
				path= "/WEB-INF/jsp/client/assets/business/index.jsp";
			}
			
		} catch (Exception e) {
			System.err.println("Error in Business Servlet 2");
			path =  "/client/Assets/index.jsp";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
	}

	public void processForm(BusinessBean biz, HttpServletRequest request){
		
		long id = Utils.getLongParameter(request, "id", -1);
		biz.setId(id);
		long ownerId = Utils.getLongParameter(request, "ownerId", -1);
		biz.setOwnerId(ownerId);
		String description  = Utils.getStringParameter(request, "description", "");
		biz.setDescription(description);
		long ownershipId = Utils.getLongParameter(request, "ownershipId", Ownership.CP.id());
		biz.setOwnershipId(ownershipId);
		long titleId = Utils.getLongParameter(request, "titleId", Ownership.CP.id());;
		biz.setTitleId(titleId);
		String notes = Utils.getStringParameter(request, "notes", "");
		biz.setNotes(notes);
		double basis = Utils.getDoubleParameter(request, "basis", 0);
		biz.setBasis(basis);
		double value = Utils.getDoubleParameter(request, "value", 0);
		biz.setValue(value);
		double growthRate = Utils.getPercentParameter(request, "growth", 0);
		biz.setGrowthRate(growthRate);
		double percentOwned = Utils.getPercentParameter(request, "percentOwned", 1);
		biz.setPercentOwned(percentOwned);
		long businessTypeId = Utils.getLongParameter(request, "businessType", BusinessTypes.O.id());
		biz.setBusinessTypeId(businessTypeId);
		String ald = Utils.getStringParameter(request, "ald", "");
		biz.setAld(ald);
		double annualIncome = Utils.getDoubleParameter(request, "annualIncome", 0);
		biz.setAnnualIncome(annualIncome);
		double incomeGrowth = Utils.getPercentParameter(request, "incomeGrowth", 0);
		biz.setIncomeGrowth(incomeGrowth);
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
