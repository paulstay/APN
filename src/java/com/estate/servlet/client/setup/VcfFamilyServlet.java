package com.estate.servlet.client.setup;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.bean.PersonBean;
import com.teag.bean.VCFBean;


public class VcfFamilyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3252860128672480044L;

	/**
	 * Constructor of the object.
	 */
	public VcfFamilyServlet() {
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
		String path = "/WEB-INF/jsp/client/setup/vcf/toFamily/index.jsp";
		
		String action = request.getParameter("action");
		
		boolean processList = false;
		
		if(action==null){
			action = "list";
		}
		
		try {
			PersonBean person = (PersonBean) session.getAttribute("person");
			if( action.equalsIgnoreCase("add")){
				VCFBean vcf = new VCFBean();
				vcf.initialize();
				request.setAttribute("vcf",vcf);
				request.setAttribute("btn", "Add");
				path = "/WEB-INF/jsp/client/setup/vcf/toFamily/form.jsp";
			}
			
			if(action.equalsIgnoreCase("edit")){
				VCFBean vcf = new VCFBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if(id > 0){
					vcf.setId(id);
					vcf.initialize();
					request.setAttribute("vcf",vcf);
					request.setAttribute("btn", "Edit");
					path = "/WEB-INF/jsp/client/setup/vcf/toFamily/form.jsp";
				} else {
					processList = true;
				}
			}
			
			if(action.equalsIgnoreCase("insert")){
				VCFBean vcf = new VCFBean();
				processForm(vcf,request);
				vcf.setOwner_id(person.getId());
				vcf.insert();
				processList = true;
			}
			
			if(action.equalsIgnoreCase("update")){
				VCFBean vcf = new VCFBean();
				processForm(vcf,request);
				vcf.update();
				processList = true;
			}
			
			if(action.equalsIgnoreCase("delete")) {
				VCFBean vcf = new VCFBean();
				int id = Utils.getIntegerParameter(request, "id", -1);
				if( id>0){
					vcf.setId(id);
					vcf.delete();
				}
				processList = true;
			}
			
			if(action.equalsIgnoreCase("cancel")){
				processList = true;
			}
			
			if(action.equalsIgnoreCase("list") || processList){
				VCFBean vcf = new VCFBean();
				ArrayList<VCFBean> cList = vcf.getBeans(VCFBean.OWNER_ID + "='" + person.getId() + "'");
				request.setAttribute("cList", cList);
			}
		} catch (Exception e){
			System.out.println("error in vcf cash receipts servlet");
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
		
	}
	public void processForm(VCFBean vcf, HttpServletRequest request){
		int id = Utils.getIntegerParameter(request, "id", -1);
		vcf.setId(id);
		String description = Utils.getStringParameter(request, "description", "Cash Receipt Item");
		vcf.setDescription(description);
		double value = Utils.getDoubleParameter(request, "value", 0);
		vcf.setValue(value);
		double prctTax = Utils.getPercentParameter(request, "prctTax", 1);
		vcf.setPrctTax(prctTax);
		double growth = Utils.getPercentParameter(request, "growth", 0);
		vcf.setGrowth(growth);
		int startYear = Utils.getIntegerParameter(request, "startYear", 2008);
		vcf.setStartYear(startYear);
		int endYear = Utils.getIntegerParameter(request, "endYear", 0);
		vcf.setEndYear(endYear);
		String cfFlag = Utils.getStringParameter(request, "cfFlag", "C");
		vcf.setCfFlag(cfFlag);
		String taxDeduction = Utils.getStringParameter(request, "taxDeduction", "N");
		vcf.setTaxDeduction(taxDeduction);
		String charitableDed = Utils.getStringParameter(request, "charitableDed", "N");
		vcf.setCharitableDed(charitableDed);
		String useFlag = Utils.getStringParameter(request, "useFlag", "B");
		vcf.setUseFlag(useFlag);
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
