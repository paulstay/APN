package com.estate.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.pdf.Locations;
import com.estate.pdf.Report;
import com.teag.bean.AdminBean;
import com.teag.bean.PdfBean;

public class ToolboxServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5456992395280763308L;

	/**
	 * Constructor of the object.
	 */
	public ToolboxServlet() {
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

		response.setContentType("application/pdf");

		File temp_file = new File(getServletContext().getRealPath("/"));
		String app_root = temp_file.getPath();

		Locations.imageLocation = app_root + "\\images\\";
		Locations.fontLocation = app_root + "\\fonts\\";

		Report rpt = new Report(response);
		HttpSession session = request.getSession();
		
		AdminBean admin = (AdminBean) session.getAttribute("validUser");
		PdfBean userInfo = (PdfBean) session.getAttribute("userInfo");
		
		String pageView = request.getParameter("pageView");
		
		if(admin != null){
			userInfo.setPlannerFirstName(admin.getFirstName());
			userInfo.setPlannerLastName(admin.getLastName());
		} else {
			userInfo.setPlannerFirstName("Teag");
			userInfo.setPlannerLastName("Admin");
		}

		// report options passed
		rpt.setSession(session);
		rpt.setUserInfo(userInfo);
		if(pageView.equalsIgnoreCase("GRAT"))
			rpt.genGrat();
		if(pageView.equalsIgnoreCase("CLAT"))
			rpt.genClat();
		if(pageView.equalsIgnoreCase("IDIT"))
			rpt.genIdit();
		if(pageView.equals("CRT"))
			rpt.genCrt();
		if(pageView.equalsIgnoreCase("SCIN")){
			rpt.genScin();
		}
		if(pageView.equalsIgnoreCase("RPM")) {
			rpt.genRpm();
		}
		if(pageView.equalsIgnoreCase("CLATLIFE")) {
			rpt.genClatLife();
		}
		if(pageView.equalsIgnoreCase("QPRT")){
			rpt.genQprt();
		}
		
		rpt.finish();
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * The doPut method of the servlet. <br>
	 *
	 * This method is called when a HTTP put request is received.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

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
