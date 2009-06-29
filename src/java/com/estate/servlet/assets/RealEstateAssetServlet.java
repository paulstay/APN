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
import com.teag.bean.RealEstateBean;

public class RealEstateAssetServlet extends HttpServlet {

	private static final long serialVersionUID = -8287658142993768888L;

	/**
	 * Constructor of the object.
	 */
	public RealEstateAssetServlet() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String path = "/WEB-INF/jsp/client/assets/RealEstate/index.jsp";

		String action = request.getParameter("action");

		if (action == null)
			action = "list";
		
		boolean processList = false;

		try {
			PersonBean person = (PersonBean) session.getAttribute("person");
			
			// If we don't have the client information, return to the client selection menu.
			if(person == null){
				throw new NullPointerException();
			}

			// Add a new Real Estate Bean to the list
			if (action.equalsIgnoreCase("add")) {
				RealEstateBean realEstate = new RealEstateBean();
				realEstate.initialize();
				request.setAttribute("realEstate", realEstate);
				request.setAttribute("btn", "Add");
				request.setAttribute("stateOptions", InputMaps.states);
				request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
				request.setAttribute("titleOptions", InputMaps.titleTypes);
				path = "/WEB-INF/jsp/client/assets/RealEstate/form.jsp";
			}

			// Edit an existing Real Estate Asset
			if (action.equalsIgnoreCase("edit")) {
				String id = request.getParameter("id");
				if(id != null) {
					RealEstateBean realEstate = new RealEstateBean();
					realEstate.setId(Long.parseLong(id));
					realEstate.initialize();
					request.setAttribute("realEstate", realEstate);
					request.setAttribute("btn", "Edit");
					request.setAttribute("stateOptions", InputMaps.states);
					request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
					request.setAttribute("titleOptions", InputMaps.titleTypes);
					path = "/WEB-INF/jsp/client/assets/RealEstate/form.jsp";
				} else {
					// Since we don't have a valid id, we just need to go back to the list.
					processList = true;
				}
			}

			// We have the data, now process it and add it to the list
			if (action.equalsIgnoreCase("insert")) {
				RealEstateBean rb = new RealEstateBean();
				processForm(rb,request);
				rb.insert();
				processList = true;
			}

			// Change the data to reflect the new asset.
			if (action.equalsIgnoreCase("update")) {
				RealEstateBean rb = new RealEstateBean();
				processForm(rb,request);
				rb.update();
				processList = true;
			}

			// We want to delete this asset.
			if (action.equalsIgnoreCase("delete")) {
				RealEstateBean rb = new RealEstateBean();
				int id = Utils.getIntegerParameter(request, "id", 0);
				
				if(id > 0){
					rb.setId(id);
					rb.delete();
				}
				processList = true;
			}

			// View the data for the asset. Need to update to include a cash flow analysis for 10 years.
			if (action.equalsIgnoreCase("view")) {
				RealEstateBean realEstate = new RealEstateBean();
				int id = Utils.getIntegerParameter(request, "id", 0);
				if( id > 0) {
					realEstate.setId(id);
					realEstate.initialize();
					session.removeAttribute("realEstate");		// remove the old one!
					request.setAttribute("realEstate", realEstate);
					path = "/WEB-INF/jsp/client/assets/RealEstate/view.jsp";
				} else {
					processList = true;	// no valid real estate, so go back to list
				}
			}
			
			// Either in a view, add, or an edit so we just want to go back to the index.
			if(action.equalsIgnoreCase("cancel")) {
				processList = true;
			}

			// We may need to process the list, even though the action is different, 
			// For instance, after processing, we want to go back to the index.
			if (action.equalsIgnoreCase("list") || processList) {
				RealEstateBean rb = new RealEstateBean();
				ArrayList<RealEstateBean> cList = rb.getBeans(RealEstateBean.OWNER_ID + "='" +
						person.getId() + "'");
				request.setAttribute("cList", cList);
				
				path = "/WEB-INF/jsp/client/assets/RealEstate/index.jsp";
			}
		} catch (NullPointerException e) {
			/*
			 * The person bean is the client, if this is null, then something is
			 * wrong and we can't proceed. Throw an null pointer exception, and
			 * go back to the select client page.
			 */
			System.err.println("Person Bean not found in Real Estate List");
			path = "/admin/selectClients.jsp";

		}

		// Go to the appropriate page.
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
	}

	public void processForm(RealEstateBean rb, HttpServletRequest request) {
		int id = Utils.getIntegerParameter(request, "id", 0);
		rb.setId(id);

		String description = Utils.getStringParameter(request, "description", "");
		rb.setDescription(description);
		
		int ownerId = Utils.getIntegerParameter(request, "ownerId", 0);
		rb.setOwnerId(ownerId);
		rb.setRealestateOwner(ownerId);
		
		int ownershipId = Utils.getIntegerParameter(request, "ownershipId", Ownership.CP.id());
		rb.setOwnershipId(ownershipId);
		
		int titleId = Utils.getIntegerParameter(request, "titleId", Title.JT.id());
		rb.setTitleId(titleId);
		
		String locationCity = Utils.getStringParameter(request, "city", "");
		rb.setLocationCity(locationCity);
		
		String locationState = Utils.getStringParameter(request, "state", "CA");
		rb.setLocationState(locationState);
		
		double value = Utils.getDoubleParameter(request, "value", 0);
		rb.setValue(value);
		
		double basis = Utils.getDoubleParameter(request, "basis", 0);
		rb.setBasis(basis);
		
		double growthRate = Utils.getPercentParameter(request, "growth", .05);
		rb.setGrowthRate(growthRate);
		
		double grossRents = Utils.getDoubleParameter(request, "grossRents", 0);
		rb.setGrossRents(grossRents);
		
		double grossRentsGrowth = Utils.getPercentParameter(request, "grossRentsGrowth", .02);
		rb.setGrossRentsGrowth(grossRentsGrowth);
		
		double operatingExpenses = Utils.getDoubleParameter(request,"operatingExpenses",0);
		rb.setOperatingExpenses(operatingExpenses);
		
		double growthExpenses = Utils.getPercentParameter(request, "growthExpenses", 0);
		rb.setGrowthExpenses(growthExpenses);
		
		double loanBalance = Utils.getDoubleParameter(request, "loanBalance", 0);
		rb.setLoanBalance(loanBalance);
		
		double loanInterest = Utils.getPercentParameter(request, "loanInterest", 0);
		rb.setLoanInterest(loanInterest);
		
		double loanPayment = Utils.getDoubleParameter(request, "loanPayment", 0);
		rb.setLoanPayment(loanPayment);
		
		int loanFreq = Utils.getIntegerParameter(request, "loanFreq", 1);
		rb.setLoanFreq(loanFreq);
		
		int loanTerm = Utils.getIntegerParameter(request, "loanTerm", 0);
		rb.setLoanTerm(loanTerm);
		
		rb.setDepreciationMethod("S");		// We default to straight line only for now
		
		double depreciationValue = Utils.getDoubleParameter(request, "depreciationValue", 0);
		rb.setDepreciationValue(depreciationValue);
		
		int depreciationYears = Utils.getIntegerParameter(request, "depreciationYears", 0);
		rb.setDepreciationYears(depreciationYears);
		
		double salvageValue = Utils.getDoubleParameter(request, "salvageValue", 0);
		rb.setSalvageValue(salvageValue);
		
		String notes = Utils.getStringParameter(request, "notes", "");
		rb.setNotes(notes);
		// Add depreciation here when we need to!
	}
	
	/**
	 * Returns information about the servlet, such as author, version, and
	 * copyright.
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
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}
}
