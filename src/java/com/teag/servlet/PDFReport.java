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
import com.teag.bean.AdminBean;
import com.teag.bean.CashFlowBean;
import com.teag.bean.ClientBean;
import com.teag.bean.PdfBean;
import com.teag.bean.PersonBean;
import com.teag.reports.Report;
import com.teag.util.Utilities;

public class PDFReport extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 7401753138952301765L;

    /**
     * Constructor of the object.
     */
    public PDFReport() {
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

        HttpSession session = request.getSession();
        response.setContentType("application/pdf");

        // Setup access to the database
        DBObject dbObj = new DBObject();
        dbObj.start();
        dbObj.stop();

        AdminBean planner = (AdminBean) session.getAttribute("validUser");
        ClientBean cb = (ClientBean) session.getAttribute("client");

        CashFlowBean cfb = new CashFlowBean();
        cfb.setOwnerId(cb.getPrimaryId());
        cfb.initialize();

        PersonBean person = new PersonBean();
        person.setId(cb.getPrimaryId());
        person.setDbObject();
        person.initialize();
        long spouseId = 0;

        PdfBean userInfo = new PdfBean();
        userInfo.setClientAge(Utilities.CalcAge(person.getBirthDate()));
        userInfo.setClientFirstName(person.getFirstName());
        userInfo.setClientGender(person.getGender());
        userInfo.setClientLastName(person.getLastName());
        userInfo.setClientLifeExpectancy((int) Utilities.getLifeExp(userInfo.getClientAge(), userInfo.getClientGender()));

        if (!cb.isSingle()) {
            PersonBean spouse = new PersonBean();
            spouse.setId(cb.getSpouseId());
            spouse.setDbObject();
            spouse.initialize();
            spouseId = spouse.getId();
            userInfo.setSpouseAge(Utilities.CalcAge(spouse.getBirthDate()));
            userInfo.setSpouseFirstName(spouse.getFirstName());
            userInfo.setSpouseGender(spouse.getGender());
            userInfo.setSpouseLastName(spouse.getLastName());
            userInfo.setSpouseLifeExpectancy((int) Utilities.getLifeExp(userInfo.getSpouseAge(), userInfo.getSpouseGender()));
            userInfo.setClientHeading(userInfo.getClientFirstName() + " and " + userInfo.getSpouseFirstName() + " " + userInfo.getClientLastName());
        } else {
            userInfo.setSingle(true);
            userInfo.setClientHeading(userInfo.getClientFirstName() + " " + userInfo.getClientLastName());
        }

        userInfo.setFinalDeath(cfb.getFinalDeath());
        File temp_file = new File(getServletContext().getRealPath("/"));

        Locations.imageLocation = temp_file.getPath() + "\\images\\";
        Locations.fontLocation = temp_file.getPath() + "\\fonts\\";


        Report rpt = new Report(response);

        if (rpt.initialized == false) {
            return;
        }

        userInfo.setPlannerLastName(planner.getLastName());
        userInfo.setPlannerFirstName(planner.getFirstName());

        rpt.setUserInfo(userInfo);

        rpt.setSession(request.getSession());
        rpt.setClientID((int) person.getId());
        rpt.setSpouseID((int) spouseId);

        String action = request.getParameter("TPAGE");

        if (action == null && action.length() > 0) {
            action = "TITLE";
        }

        if (action.equals("TITLE")) {
            rpt.genTitlePage(userInfo.getClientHeading().toUpperCase());
        }

        if (action.equals("PERSONAL")) {
            rpt.setPage(3);
            rpt.genPersonalData(userInfo.getClientHeading());
        }

        if (action.equals("NETWORTH")) {
            rpt.setPage(4);
            rpt.genNetWorth(userInfo.getClientHeading());
        }

        if (action.equals("ASSETS")) {
            rpt.setPage(5);
            rpt.genAssets(userInfo.getClientHeading());
        }

        if (action.equals("CASHFLOW")) {
            rpt.setPage(6);
            //rpt.genScenario1();
            rpt.genCashFlows(userInfo.getClientHeading());
        }

        if (action.equals("OBJECTIVES")) {
            rpt.setPage(1);
            rpt.genObjectives(userInfo.getClientHeading());
        }

        if (action.equals("OBSERVATIONS")) {
            rpt.setPage(1);
            rpt.genObservations(userInfo.getClientHeading());
        }

        if (action.equals("RECOMMENDATIONS")) {
            rpt.setPage(1);
            rpt.genRecomendations(userInfo.getClientHeading());
        }
        if (action.equals("PHILOSOPHY")) {
            rpt.setPage(1);
            rpt.genPhilosophy(userInfo.getClientHeading());
        }
        if (action.equals("SPLIT")) {
            rpt.setPage(1);
            rpt.genSplit();
        }

        rpt.finish();
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
        doGet(request, response);

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
