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
import com.estate.constants.NoteReceivableTypes;
import com.estate.constants.Ownership;
import com.estate.constants.Title;
import com.estate.servlet.Utils;
import com.teag.bean.NotesBean;
import com.teag.bean.PersonBean;


public class NotesRecAssetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8749411329933738262L;

	/**
	 * Constructor of the object.
	 */
	public NotesRecAssetServlet() {
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
		String path = "/WEB-INF/jsp/client/assets/notesrec/index.jsp";
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
				NotesBean bean = new NotesBean();
				bean.initialize();
				request.setAttribute("note", bean);
				request.setAttribute("btn", "Add");
				request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
				request.setAttribute("titleOptions", InputMaps.titleTypes);
				path = "/WEB-INF/jsp/client/assets/notesrec/form.jsp";
			}
			
			if(action.equalsIgnoreCase("edit")){
				NotesBean bean = new NotesBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					bean.setId(id);
					bean.initialize();
					request.setAttribute("note", bean);
					request.setAttribute("btn", "Edit");
					request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
					request.setAttribute("titleOptions", InputMaps.titleTypes);
					path = "/WEB-INF/jsp/client/assets/notesrec/form.jsp";
				} else {
					processList = true;
				}
				
			}

			if(action.equalsIgnoreCase("insert")){
				NotesBean bean = new NotesBean();
				processForm(bean,request);
				bean.insert();
				processList = true;
			}

			if(action.equalsIgnoreCase("update")){
				NotesBean bean = new NotesBean();
				processForm(bean,request);
				bean.update();
				processList = true;
			}

			if(action.equalsIgnoreCase("delete")){
				NotesBean bean = new NotesBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					bean.setId(id);
					bean.delete();
				}
				processList = true;
			}

			if(action.equalsIgnoreCase("view")){
				NotesBean bean = new NotesBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id>0){
					bean.setId(id);
					bean.initialize();
					NoteReceivableTypes np = NoteReceivableTypes.getCodeType(bean.getNoteType());
					request.setAttribute("noteType", np.description());
					request.setAttribute("note", bean);
					path="/WEB-INF/jsp/client/assets/notesrec/view.jsp";
				}
			}

			if(action.equalsIgnoreCase("cancel")){
				processList = true;
			}

			if(action.equalsIgnoreCase("list") || processList){
				NotesBean bean = new NotesBean();
				ArrayList<NotesBean> cList = bean.getBeans(NotesBean.OWNER_ID + "='" + person.getId() + "'");
				request.setAttribute("cList", cList);
				path = "/WEB-INF/jsp/client/assets/notesrec/index.jsp";
			}
			
		} catch (Exception e) {
			System.err.println("Person Bean not found in Cash List");
			path="/admin/selectClient";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);


	}

	public void processForm(NotesBean nb, HttpServletRequest request) {
		long id = Utils.getLongParameter(request, "id", 0);
		nb.setId(id);
		long ownerId = Utils.getLongParameter(request, "ownerId", 0);
		nb.setOwnerId(ownerId);
		String description = Utils.getStringParameter(request, "description", "");
		nb.setDescription(description);
		int years = Utils.getIntegerParameter(request, "years", 0);
		nb.setYears(years);
		int paymentsPerYear = Utils.getIntegerParameter(request, "paymentsPerYear", 0);
		nb.setPaymentsPerYear(paymentsPerYear);
		double interestRate = Utils.getPercentParameter(request, "interestRate", 0);
		nb.setInterestRate(interestRate);
		double loanAmount = Utils.getDoubleParameter(request, "loanAmount", 0);
		nb.setLoanAmount(loanAmount);
		String noteType = Utils.getStringParameter(request, "noteType", com.estate.constants.NoteReceivableTypes.A.code());
		nb.setNoteType(noteType);
		long ownershipId = Utils.getLongParameter(request, "ownershipId", Ownership.CP.id());
		nb.setOwnershipId(ownershipId);
		long titleId = Utils.getLongParameter(request, "titleId", Title.H.id());
		nb.setTitleId(titleId);
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
