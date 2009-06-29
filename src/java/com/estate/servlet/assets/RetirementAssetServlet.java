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
import com.teag.bean.RetirementBean;

public class RetirementAssetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6885031499608242081L;

	/**
	 * Constructor of the object.
	 */
	public RetirementAssetServlet() {
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
		String path = "/WEB-INF/jsp/client/assets/retirement/index.jsp";
		String action = request.getParameter("action");

		boolean processList = false;

		if (action == null) {
			action = "list";
		}

		try {
			PersonBean person = (PersonBean) session.getAttribute("person");

			if (person == null) {
				throw new NullPointerException();
			}

			if (action.equalsIgnoreCase("add")) {
				RetirementBean bean = new RetirementBean();
				bean.initialize();
				request.setAttribute("retire", bean);
				request.setAttribute("btn", "Add");
				request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
				request.setAttribute("titleOptions", InputMaps.titleTypes);
				request.setAttribute("notesPayOptions", InputMaps.notePayTypes);
				path = "/WEB-INF/jsp/client/assets/retirement/form.jsp";
			}

			if (action.equalsIgnoreCase("edit")) {
				RetirementBean bean = new RetirementBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if (id > 0) {
					bean.setId(id);
					bean.initialize();
					request.setAttribute("retire", bean);
					request.setAttribute("btn", "Edit");
					request.setAttribute("ownerOptions",
							InputMaps.ownershipTypes);
					request.setAttribute("titleOptions", InputMaps.titleTypes);
					request.setAttribute("retirementOptions",
							InputMaps.notePayTypes);
					path = "/WEB-INF/jsp/client/assets/retirement/form.jsp";
				} else {
					processList = true;
				}
			}

			if (action.equalsIgnoreCase("insert")) {
				RetirementBean bean = new RetirementBean();
				processForm(bean, request);
				bean.insert();
				processList = true;
			}

			if (action.equalsIgnoreCase("update")) {
				RetirementBean bean = new RetirementBean();
				processForm(bean, request);
				bean.update();
				processList = true;
			}

			if (action.equalsIgnoreCase("delete")) {
				RetirementBean bean = new RetirementBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if (id > 0) {
					bean.setId(id);
					bean.delete();
				}
				processList = true;
			}

			if (action.equalsIgnoreCase("view")) {
				RetirementBean bean = new RetirementBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if (id > 0) {
					bean.setId(id);
					bean.initialize();
					request.setAttribute("retire", bean);
					path = "/WEB-INF/jsp/client/assets/retirement/view.jsp";
				}
			}

			if (action.equalsIgnoreCase("cancel")) {
				processList = true;
			}

			if (action.equalsIgnoreCase("list") || processList) {
				RetirementBean retirement = new RetirementBean();
				ArrayList<RetirementBean> cList = retirement
						.getBeans(RetirementBean.OWNER_ID + "='"
								+ person.getId() + "'");
				request.setAttribute("cList", cList);
				path = "/WEB-INF/jsp/client/assets/retirement/index.jsp";
			}

		} catch (Exception e) {
			System.err.println("Person Bean not found");
			path = "/admin/selectClient";
		}

		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);

	}

	public void processForm(RetirementBean rb, HttpServletRequest request) {
		long id= Utils.getLongParameter(request, "id", 0);
		rb.setId(id);
		long ownerId = Utils.getLongParameter(request, "ownerId", 0);
		rb.setOwnerId(ownerId);
		String description = Utils.getStringParameter(request, "description", "");
		rb.setDescription(description);
		long ownershipId = Utils.getLongParameter(request, "ownershipId", Ownership.CP.id());
		rb.setOwnershipId(ownershipId);
		long titleId = Utils.getLongParameter(request, "titleId", Title.JT.id());
		rb.setTitleId(titleId);
		String notes = Utils.getStringParameter(request, "notes", "");
		rb.setNotes(notes);
		double annualContrib = Utils.getDoubleParameter(request, "annualContrib", 0);
		rb.setAnnualContrib(annualContrib);
		double value = Utils.getDoubleParameter(request, "value", 0);
		rb.setValue(value);
		double employeeContrib = Utils.getDoubleParameter(request, "employeeContrib", 0);
		rb.setEmployeeContrib(employeeContrib);
		double employerContrib = Utils.getDoubleParameter(request, "employerContrib", 0);
		rb.setEmployerContrib(employerContrib);
		String namedBeneficiary = Utils.getStringParameter(request, "namedBeneficiary", "");
		rb.setNamedBeneficiary(namedBeneficiary);
		String beneficiaryType = Utils.getStringParameter(request, "beneficiaryType", "B");
		rb.setBeneficiaryType(beneficiaryType);
		String birthDate = Utils.getStringParameter(request, "birthDate", "");
		rb.setBirthDate(birthDate);
		String lifeExpCalc = Utils.getStringParameter(request, "lifeExpCalc", "B");
		rb.setLifeExpCalc(lifeExpCalc.toUpperCase());
		double growthRate = Utils.getPercentParameter(request, "growthRate", 0);
		rb.setGrowthRate(growthRate);
		String toCharity = "N";
		rb.setToCharity(toCharity);
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
