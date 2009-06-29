package com.teag.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.pdf.Locations;
import com.teag.analysis.CashFlowTable;
import com.teag.bean.AdminBean;
import com.teag.bean.CashFlowBean;
import com.teag.bean.ClientBean;
import com.teag.bean.PdfBean;
import com.teag.bean.PersonBean;
import com.teag.reports.Report;
import com.teag.util.Utilities;

/** 
 * @author Paul Stay
 * BaselineReport.java
 * Created on Jun 22, 2005
 *
 */

public class BaselineReport extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean debug = false;
    /**
     * Constructor of the object.
     */
    public BaselineReport() {
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
        doPost(request, response);
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
        response.setContentType("application/pdf");

        File temp_file = new File(getServletContext().getRealPath("/"));
        String app_root = temp_file.getPath();
        
        AdminBean planner = (AdminBean) session.getAttribute("validUser");
        ClientBean cb = (ClientBean) session.getAttribute("client");

        CashFlowBean cfb = new CashFlowBean();
        cfb.setOwnerId(cb.getPrimaryId());
        cfb.initialize();

        PersonBean  person = new PersonBean();
        person.setId(cb.getPrimaryId());
        person.initialize();
        long spouseId = 0;
        
        PdfBean userInfo = new PdfBean();
		userInfo.setClientAge(Utilities.CalcAge(person.getBirthDate()));
		userInfo.setClientFirstName(person.getFirstName());
		userInfo.setClientGender(person.getGender());
		userInfo.setClientLastName(person.getLastName());
		userInfo.setClientLifeExpectancy((int)Utilities.getLifeExp(userInfo.getClientAge(),userInfo.getClientGender()));

        if( !cb.isSingle()) {
	        PersonBean spouse = new PersonBean();
	        spouse.setId(cb.getSpouseId());
	        spouse.initialize();
	        spouseId = spouse.getId();
			userInfo.setSpouseAge(Utilities.CalcAge(spouse.getBirthDate()));
			userInfo.setSpouseFirstName(spouse.getFirstName());
			userInfo.setSpouseGender(spouse.getGender());
			userInfo.setSpouseLastName(spouse.getLastName());
			userInfo.setSpouseLifeExpectancy((int)
					Utilities.getLifeExp(userInfo.getSpouseAge(),userInfo.getSpouseGender()));
			userInfo.setClientHeading(userInfo.getClientFirstName() + " and " 
					+ userInfo.getSpouseFirstName() + " " + userInfo.getClientLastName());        
        } else {
			userInfo.setSingle(true);
			userInfo.setClientHeading(userInfo.getClientFirstName()
					+ " " + userInfo.getClientLastName());
        }
        
        userInfo.setFinalDeath(cfb.getFinalDeath());
        
        //app_root += "\\webapp\\images\\";
        Locations.imageLocation = app_root + "\\images\\";
        Locations.fontLocation = app_root + "\\fonts\\";
        if( debug)
            System.out.println("App Root = " + app_root);

        Report rpt = new Report(response);

        if(rpt.initialized == false)
        {
            return;
        }
        
		userInfo.setPlannerLastName(planner.getLastName());
		userInfo.setPlannerFirstName(planner.getFirstName());
		
		rpt.setUserInfo(userInfo);
		
        rpt.setSession(request.getSession());
        rpt.setClientID((int)person.getId());
       	rpt.setSpouseID((int)spouseId);
        
        // We need to calculate the Cash Flow for Scenario 1 and 2 here so we can generate the graphs
        CashFlowTable cft = new CashFlowTable();
        cft.setCb(cb);
        cft.init();
        cft.genTable();
        
        rpt.genTitlePage(userInfo.getClientHeading());
        rpt.genIntroduction();
        rpt.genPersonalData(userInfo.getClientHeading());
        rpt.genNetWorth(userInfo.getClientHeading());
        rpt.genAssets(userInfo.getClientHeading());
        rpt.genObjectives(userInfo.getClientHeading());
		rpt.genPhilosophy(userInfo.getClientHeading());
        rpt.genCashFlows(userInfo.getClientHeading());
        rpt.genSc1Graphs(userInfo.getClientHeading(), cft);
        rpt.genObservations(userInfo.getClientHeading());
        rpt.genRecomendations(userInfo.getClientHeading());
        rpt.finish();
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occure
     */
    @Override
	public void init() throws ServletException {
        // Put your code here
    }
}

