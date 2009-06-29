package com.aes.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.pdf.Locations;
import com.estate.pdf.Report;
import com.teag.bean.PdfBean;

public class AesReportServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2847707200582514332L;

	/**
	 * Constructor of the object.
	 */
	public AesReportServlet() {
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

		// app_root += "\\webapp\\images\\";
		Locations.imageLocation = app_root + "\\webapp\\images\\";
		Locations.fontLocation = app_root + "\\webapp\\fonts\\";

		String pageView = request.getParameter("pageView");
		
		Report rpt = new Report(response);
		
		HttpSession session = request.getSession();
		
		PdfBean userInfo = (PdfBean) session.getAttribute("userInfo");
		rpt.setSession(session);
		rpt.setUserInfo(userInfo);
		
		if(pageView.equalsIgnoreCase("ClatLife"))
			rpt.genClatLife();
		

		rpt.finish();
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
