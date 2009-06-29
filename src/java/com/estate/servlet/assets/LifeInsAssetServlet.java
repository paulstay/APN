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
import com.estate.constants.LifeInsuranceTypes;
import com.estate.servlet.Utils;
import com.teag.bean.InsuranceBean;
import com.teag.bean.PersonBean;

public class LifeInsAssetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6961132358329384180L;

	/**
	 * Constructor of the object.
	 */
	public LifeInsAssetServlet() {
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
		String path = "/WEB-INF/jsp/client/assets/life/index.jsp";
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
				InsuranceBean bean = new InsuranceBean();
				bean.initialize();
				request.setAttribute("life", bean);
				request.setAttribute("btn", "Add");
				request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
				request.setAttribute("titleOptions", InputMaps.titleTypes);
				path = "/WEB-INF/jsp/client/assets/life/form.jsp";
			}
			
			if(action.equalsIgnoreCase("edit")){
				InsuranceBean bean = new InsuranceBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					bean.setId(id);
					bean.initialize();
					request.setAttribute("life", bean);
					request.setAttribute("btn", "Edit");
					request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
					request.setAttribute("titleOptions", InputMaps.titleTypes);
					path = "/WEB-INF/jsp/client/assets/life/form.jsp";
				} else {
					processList = true;
				}
				
			}

			if(action.equalsIgnoreCase("insert")){
				InsuranceBean bean = new InsuranceBean();
				processForm(bean,request);
				bean.insert();
				processList = true;
			}

			if(action.equalsIgnoreCase("update")){
				InsuranceBean bean = new InsuranceBean();
				processForm(bean,request);
				bean.update();
				processList = true;
			}

			if(action.equalsIgnoreCase("delete")){
				InsuranceBean bean = new InsuranceBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					bean.setId(id);
					bean.delete();
				}
				processList = true;
			}

			if(action.equalsIgnoreCase("view")){
				InsuranceBean bean = new InsuranceBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id>0){
					bean.setId(id);
					bean.initialize();
					request.setAttribute("life", bean);
					path="/WEB-INF/jsp/client/assets/life/view.jsp";
				}
			}

			if(action.equalsIgnoreCase("cancel")){
				processList = true;
			}

			if(action.equalsIgnoreCase("list") || processList){
				InsuranceBean bean = new InsuranceBean();
				ArrayList<InsuranceBean> cList = bean.getBeans(InsuranceBean.OWNER_ID + "='" + person.getId() + "'");
				request.setAttribute("cList", cList);
				path = "/WEB-INF/jsp/client/assets/life/index.jsp";
			}
			
		} catch (Exception e) {
			System.err.println("Person Bean not found in Cash List");
			path="/admin/selectClient";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);

	}

	public void processForm(InsuranceBean ib, HttpServletRequest request){
		long id = Utils.getLongParameter(request, "id", 0);
		ib.setId(id);
		long ownerId = Utils.getLongParameter(request, "ownerId", 0);
		ib.setOwnerId(ownerId);
		String description = Utils.getStringParameter(request, "description", "");
		ib.setDescription(description);
		double value = Utils.getDoubleParameter(request, "value", 0);
		ib.setValue(value);
		String insured = Utils.getStringParameter(request, "insured", "");
		ib.setInsured(insured);
		String owner = Utils.getStringParameter(request, "owner", "");
		ib.setOwner(owner);
		String beneficiary = Utils.getStringParameter(request, "beneficiary", "");
		ib.setBeneficiary(beneficiary);
		String dateAcquired = Utils.getStringParameter(request, "dateAcquired", "");
		ib.setDateAcquired(dateAcquired);
		double faceValue = Utils.getDoubleParameter(request, "faceValue", 0);
		ib.setFaceValue(faceValue);
		double futureCashValue = Utils.getDoubleParameter(request, "futureCashValue", 0);
		ib.setFutureCashValue(futureCashValue);
		long policyTypeId = Utils.getLongParameter(request, "policyTypeId", LifeInsuranceTypes.T.id());
		ib.setPolicyTypeId(policyTypeId);
		double premiums = Utils.getDoubleParameter(request, "premiums", 0);
		ib.setPremiums(premiums);
		long premiumFreqId = Utils.getLongParameter(request, "premiumFreq", 1);
		ib.setPremiumFreqId(premiumFreqId);
		String insuranceCompany = Utils.getStringParameter(request, "insuranceCompany", "");
		ib.setInsuranceCompany(insuranceCompany);
		String policyNumber = Utils.getStringParameter(request, "policyNumber", "");
		ib.setPolicyNumber(policyNumber);
		double loans = Utils.getDoubleParameter(request, "loans", 0);
		ib.setLoans(loans);
		String taxableLifeInc = Utils.getStringParameter(request, "taxableLifeInc", "Y");
		ib.setTaxableLifeInc(taxableLifeInc);
		String notes = Utils.getStringParameter(request, "notes", "");
		ib.setNotes(notes);
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
