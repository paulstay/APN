/*
 * Created on May 7, 2005
 *
 */
package com.teag.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.db.DBObject;
import com.estate.pdf.Locations;
import com.teag.bean.ClientBean;
import com.teag.bean.PdfBean;
import com.teag.bean.PlannerBean;
import com.teag.reports.Report;
import com.teag.webapp.EstatePlanningGlobals;

/**
 * @author Paul Stay
 * 
 */
public class PDFTest extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	boolean debug = false;

	/**
	 * Constructor of the object.
	 */
	public PDFTest() {
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

		response.setContentType("application/pdf");

		File temp_file = new File(getServletContext().getRealPath("/"));
		String app_root = temp_file.getPath();

		// Get the client id
		String toolName = request.getParameter("tool");

		// Setup access to the database
		DBObject dbObj = new DBObject();
		dbObj.start();
		dbObj.stop();

		// app_root += "\\webapp\\images\\";
		Locations.imageLocation = app_root + "\\images\\";
		Locations.fontLocation = app_root + "\\fonts\\";
		if (debug)
			System.out.println("App Root = " + app_root);

		Report rpt = new Report(response);
		HttpSession session = request.getSession();

		EstatePlanningGlobals epg = (EstatePlanningGlobals) session
				.getAttribute("epg");

		// Need to get from epg values
		String spouseFirst = "";
		
		String clientFirst = epg.getClientFirstName();
		String client = "";
		
		if( epg.isSingle()) {
			spouseFirst = epg.getSpouseFirstName();
			client = clientFirst + " " + epg.getLastName();
		} else {
			client = clientFirst + " and " + spouseFirst + " "
			+ epg.getLastName();
		}

		if (debug)
			System.out.println("Ok, here we go!");

		if (rpt.initialized == false) {
			return;
		}
		
		ClientBean curClient = epg.getClient();
		PlannerBean planner = new PlannerBean();
		planner.setDbObject();
		planner.setId(curClient.getPlannerId());
		planner.initialize();

		PdfBean userInfo = new PdfBean();
		userInfo.setClientAge(epg.getClientAge());
		userInfo.setClientFirstName(epg.getClientFirstName());
		userInfo.setClientGender(epg.getClientGender());
		userInfo.setClientLastName(epg.getLastName());
		userInfo.setClientLifeExpectancy((int)epg.getClientLifeExp());
		userInfo.setPlannerFirstName(planner.getFirstName());
		userInfo.setPlannerLastName(planner.getLastName());
		
		if(!epg.isSingle) {
			userInfo.setSpouseAge(epg.getSpouseAge());
			userInfo.setSpouseFirstName(epg.getSpouseFirstName());
			userInfo.setSpouseGender(epg.getSpouseGender());
			userInfo.setSpouseLastName(epg.getLastName());
			userInfo.setSpouseLifeExpectancy((int)epg.getSpouseLifeExp());
			userInfo.setClientHeading(userInfo.getClientFirstName() + " and " 
					+ userInfo.getSpouseFirstName() + " " + userInfo.getClientLastName());
		} else {
			userInfo.setSingle(true);
			userInfo.setClientHeading(userInfo.getClientFirstName()
					+ " " + userInfo.getClientLastName());
		}
		
		userInfo.setPlannerLastName(planner.getLastName());
		userInfo.setPlannerFirstName(planner.getFirstName());

		rpt.setUserInfo(userInfo);

		rpt.setSession(request.getSession());
		rpt.setClientID((int) epg.getClient().getPrimaryId());
		rpt.setSpouseID((int) epg.getClient().getSpouseId());
		rpt.setEpg(epg);

		if (toolName.equals("TITLE")) {
			// Need to get from database

			rpt.genTitlePage(client.toUpperCase()); 
		}
		
		if(toolName.equalsIgnoreCase("PERSONAL")){
			 rpt.genPersonalData(userInfo.getClientHeading());
		}
		if (toolName.equalsIgnoreCase("FLP")) {
			rpt.genFLP();
			rpt.newPage();
		}
		if (toolName.equalsIgnoreCase("QPRT")) {
			rpt.genQPRT(client);
			rpt.newPage();
		}
		if (toolName.equalsIgnoreCase("TEST")) {
			rpt.genEstatePlan(client);
			rpt.newPage();
		}
		
		if(toolName.equalsIgnoreCase("TCLAT2")){
			rpt.genTestCLAT2(client);
			rpt.newPage();
		}
		
		if (toolName.equalsIgnoreCase("NETW")) {
			rpt.genNetWorth(client);
			rpt.newPage();
		}
		if (toolName.equalsIgnoreCase("GRAT")) {
			rpt.setUseLLC(true);
			rpt.genGRAT(client);
			rpt.newPage();
		}
		if (toolName.equalsIgnoreCase("CRUT")) {
			rpt.genCRUT();
			rpt.newPage();
		}
		if (toolName.equalsIgnoreCase("RPM")) {
			rpt.genRPM(client);
		}
		if (toolName.equalsIgnoreCase("CLAT")) {
			rpt.genCLAT(client);
			rpt.newPage();
		}
		if (toolName.equalsIgnoreCase("IDIT")) {
			rpt.genIDIT(client);
			rpt.newPage();
		}
		if (toolName.equalsIgnoreCase("CRUMMEY")) {
			rpt.genCrummyWMulti(client);
			rpt.newPage();
		}
		if (toolName.equalsIgnoreCase("LIFE")) {
			rpt.genLife();
			rpt.newPage();
		}
		if (toolName.equalsIgnoreCase("TESTCLAT")) {
			rpt.genTestCLAT(client);
			rpt.newPage();
		}
		if (toolName.equalsIgnoreCase("MULTIGEN")) {
			rpt.genMultiGen(client);
			rpt.newPage();
		}
		if (toolName.equalsIgnoreCase("FCF")) {
			rpt.genFCF(client);
			rpt.newPage();
		}
		if (toolName.equalsIgnoreCase("BLANK")) {
			rpt.genBlank(client);
			rpt.newPage();
		}
		if (toolName.equalsIgnoreCase("LLC")) {
			rpt.genLLC();
			rpt.newPage();
		}
		if(toolName.equalsIgnoreCase("LAP")) {
			rpt.genLap();
			rpt.newPage();
		}
		if(toolName.equalsIgnoreCase("PAT")) {
			rpt.genPat();
			rpt.newPage();
		}
		
		if(toolName.equalsIgnoreCase("SCIN")) {
			rpt.genScin();
			rpt.newPage();
		}
		
		if(toolName.equalsIgnoreCase("CPLAN")) {
			rpt.genERP(client);
			rpt.newPage();
		}
		
		if(toolName.equalsIgnoreCase("SIDIT")) {
			rpt.genSIDIT(client);
			rpt.newPage();
		}

		rpt.finish();
		if (debug)
			System.out.println("Finished with " + toolName);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}

}
