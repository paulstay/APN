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
import com.estate.constants.NotePayableTypes;
import com.estate.constants.Ownership;
import com.estate.constants.Title;
import com.estate.servlet.Utils;
import com.teag.bean.NotePayableBean;
import com.teag.bean.PersonBean;

public class NotesPayAssetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1111638667685898579L;

	/**
	 * Constructor of the object.
	 */
	public NotesPayAssetServlet() {
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
		String path = "/WEB-INF/jsp/client/assets/notespay/index.jsp";
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
				NotePayableBean bean = new NotePayableBean();
				bean.initialize();
				request.setAttribute("notePay", bean);
				request.setAttribute("btn", "Add");
				request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
				request.setAttribute("titleOptions", InputMaps.titleTypes);
				request.setAttribute("notesPayOptions", InputMaps.notePayTypes);
				path = "/WEB-INF/jsp/client/assets/notespay/form.jsp";
			}
			
			if(action.equalsIgnoreCase("edit")){
				NotePayableBean bean = new NotePayableBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					bean.setId(id);
					bean.initialize();
					request.setAttribute("notePay", bean);
					request.setAttribute("btn", "Edit");
					request.setAttribute("ownerOptions", InputMaps.ownershipTypes);
					request.setAttribute("titleOptions", InputMaps.titleTypes);
					request.setAttribute("notespayOptions", InputMaps.notePayTypes);
					path = "/WEB-INF/jsp/client/assets/notespay/form.jsp";
				} else {
					processList = true;
				}
			}

			if(action.equalsIgnoreCase("insert")){
				NotePayableBean bean = new NotePayableBean();
				processForm(bean,request);
				bean.insert();
				processList = true;
			}

			if(action.equalsIgnoreCase("update")){
				NotePayableBean bean = new NotePayableBean();
				processForm(bean,request);
				bean.update();
				processList = true;
			}

			if(action.equalsIgnoreCase("delete")){
				NotePayableBean bean = new NotePayableBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					bean.setId(id);
					bean.delete();
				}
				processList = true;
			}

			if(action.equalsIgnoreCase("view")){
				NotePayableBean bean = new NotePayableBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id>0){
					bean.setId(id);
					bean.initialize();
					NotePayableTypes np = NotePayableTypes.getCodeType(bean.getNoteType());
					request.setAttribute("notePay", bean);
					request.setAttribute("noteType", np.description());
					path="/WEB-INF/jsp/client/assets/notespay/view.jsp";
				}
			}

			if(action.equalsIgnoreCase("cancel")){
				processList = true;
			}

			if(action.equalsIgnoreCase("list") || processList){
				NotePayableBean notespay = new NotePayableBean();
				ArrayList<NotePayableBean> cList = notespay.getBeans(NotePayableBean.OWNER_ID + "='" + person.getId() + "'");
				request.setAttribute("cList", cList);
				path = "/WEB-INF/jsp/client/assets/notespay/index.jsp";
			}
			
		} catch (Exception e) {
			System.err.println("Person Bean not found in Cash List");
			path="/admin/selectClient";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
		

	}

	public void processForm(NotePayableBean pb, HttpServletRequest request){
		long id = Utils.getLongParameter(request, "id", 0);
		pb.setId(id);
		long ownerId = Utils.getLongParameter(request, "ownerId", 0);
		pb.setOwnerId(ownerId);
		String description = Utils.getStringParameter(request, "description", "");
		pb.setDescription(description);
		int years = Utils.getIntegerParameter(request, "years", 0);
		pb.setYears(years);
		int paymentsPerYear = Utils.getIntegerParameter(request, "paymentsPerYear", 0);
		pb.setPaymentsPerYear(paymentsPerYear);
		double interestRate = Utils.getPercentParameter(request, "interestRate", 0);
		pb.setInterestRate(interestRate);
		double loanAmount = Utils.getDoubleParameter(request, "loanAmount", 0);
		pb.setLoanAmount(loanAmount);
		String noteType = Utils.getStringParameter(request, "noteType", NotePayableTypes.I.code());
		pb.setNoteType(noteType);
		long ownershipId = Utils.getLongParameter(request, "ownershipId", Ownership.CP.id());
		pb.setOwnershipId(ownershipId);
		long titleId = Utils.getLongParameter(request, "titleId", Title.JT.id());
		pb.setTitleId(titleId);
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
