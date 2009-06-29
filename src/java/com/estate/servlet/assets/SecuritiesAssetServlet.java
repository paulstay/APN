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
import com.teag.bean.SecuritiesBean;

public class SecuritiesAssetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2643477520055058016L;

	/**
	 * Constructor of the object.
	 */
	public SecuritiesAssetServlet() {
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
		String path = "/WEB-INF/jsp/client/assets/securities/index.jsp";
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
				SecuritiesBean bean = new SecuritiesBean();
				bean.initialize();
				request.setAttribute("sec", bean);
				request.setAttribute("btn", "Add");
				request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
				request.setAttribute("titleOptions", InputMaps.titleTypes);
				request.setAttribute("notesPayOptions", InputMaps.notePayTypes);
				path = "/WEB-INF/jsp/client/assets/securities/form.jsp";
			}

			if (action.equalsIgnoreCase("edit")) {
				SecuritiesBean bean = new SecuritiesBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if (id > 0) {
					bean.setId(id);
					bean.initialize();
					request.setAttribute("sec", bean);
					request.setAttribute("btn", "Edit");
					request.setAttribute("ownerOptions",
							InputMaps.ownershipTypes);
					request.setAttribute("titleOptions", InputMaps.titleTypes);
					request.setAttribute("securitiesOptions",
							InputMaps.notePayTypes);
					path = "/WEB-INF/jsp/client/assets/securities/form.jsp";
				} else {
					processList = true;
				}
			}

			if (action.equalsIgnoreCase("insert")) {
				SecuritiesBean bean = new SecuritiesBean();
				processForm(bean, request);
				bean.insert();
				processList = true;
			}

			if (action.equalsIgnoreCase("update")) {
				SecuritiesBean bean = new SecuritiesBean();
				processForm(bean, request);
				bean.update();
				processList = true;
			}

			if (action.equalsIgnoreCase("delete")) {
				SecuritiesBean bean = new SecuritiesBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if (id > 0) {
					bean.setId(id);
					bean.delete();
				}
				processList = true;
			}

			if (action.equalsIgnoreCase("view")) {
				SecuritiesBean bean = new SecuritiesBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if (id > 0) {
					bean.setId(id);
					bean.initialize();
					request.setAttribute("sec", bean);
					path = "/WEB-INF/jsp/client/assets/securities/view.jsp";
				}
			}

			if (action.equalsIgnoreCase("cancel")) {
				processList = true;
			}

			if (action.equalsIgnoreCase("list") || processList) {
				SecuritiesBean securities = new SecuritiesBean();
				ArrayList<SecuritiesBean> cList = securities
						.getBeans(SecuritiesBean.OWNER_ID + "='"
								+ person.getId() + "'");
				request.setAttribute("cList", cList);
				path = "/WEB-INF/jsp/client/assets/securities/index.jsp";
			}

		} catch (Exception e) {
			System.err.println("Person Bean not found");
			path = "/admin/selectClient";
		}

		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
	}

	public void processForm(SecuritiesBean s, HttpServletRequest request){
		long id = Utils.getLongParameter(request, "id", 0);
		s.setId(id);
		long ownerId = Utils.getLongParameter(request, "ownerId", 0);
		s.setOwnerId(ownerId);
		String description = Utils.getStringParameter(request, "description", "");
		s.setDescription(description);
		String bank = Utils.getStringParameter(request, "bank", "");
		s.setBank(bank);
		double acct = Utils.getDoubleParameter(request, "acct", 0);
		s.setAcct(acct);
		long ownershipId = Utils.getLongParameter(request, "ownershipId", Ownership.CP.id());
		s.setOwnershipId(ownershipId);
		long titleId = Utils.getLongParameter(request, "titleId", Title.JT.id());
		s.setTitleId(titleId);
		String notes = Utils.getStringParameter(request, "notes", "");
		s.setNotes(notes);
		double basis = Utils.getDoubleParameter(request, "basis", 0);
		s.setBasis(basis);
		double value = Utils.getDoubleParameter(request, "value", 0);
		s.setValue(value);
		double growthRate = Utils.getPercentParameter(request, "growthRate", 0);
		s.setGrowthRate(growthRate);
		double divInt = Utils.getPercentParameter(request, "divInt", 0);
		s.setDivInt(divInt);
		double cgTurnover = Utils.getPercentParameter(request, "cgTurnover", 1);
		s.setCgTurnover(cgTurnover);
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
