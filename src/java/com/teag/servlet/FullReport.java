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

import com.estate.constants.ToolTableTypes;
import com.estate.db.DBObject;
import com.estate.pdf.Locations;
import com.teag.EstatePlan.EstatePlanTable;
import com.teag.analysis.CashFlowTable;
import com.teag.bean.CashFlowBean;
import com.teag.bean.ClientBean;
import com.teag.bean.EstatePlanningToolBean;
import com.teag.bean.PdfBean;
import com.teag.bean.PlannerBean;
import com.teag.estate.CharliePlanTool;
import com.teag.estate.ClatTool;
import com.teag.estate.CrtTool;
import com.teag.estate.CrummeyTool;
import com.teag.estate.FCFTool;
import com.teag.estate.GratTool;
import com.teag.estate.IditTool;
import com.teag.estate.LifeTool;
import com.teag.estate.LiquidAssetProtectionTool;
import com.teag.estate.MGTrustTool;
import com.teag.estate.PrivateAnnuityTool;
import com.teag.estate.QprtTool;
import com.teag.estate.RpmTool;
import com.teag.estate.SCINTool;
import com.teag.estate.SIditTool;
import com.teag.estate.TClat2;
import com.teag.estate.TClatTool;
import com.teag.reports.Report;
import com.teag.webapp.EstatePlanningGlobals;

/**
 * @author Paul Stay
 *
 */
public class FullReport extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    boolean debug = false;

    /**
     * Constructor of the object.
     */
    public FullReport() {
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

        response.setContentType("application/pdf");


        File temp_file = new File(getServletContext().getRealPath("/"));
        String app_root = temp_file.getPath();

        // Setup access to the database
        DBObject dbObj = new DBObject();
        dbObj.start();
        dbObj.stop();
        HttpSession session = request.getSession();
        EstatePlanningGlobals epg = (EstatePlanningGlobals) session.getAttribute("epg");

        //app_root += "\\webapp\\images\\";
        Locations.imageLocation = app_root + "\\images\\";
        Locations.fontLocation = app_root + "\\fonts\\";

        Report rpt = new Report(response);

        if (debug) {
            System.out.println("Ok, here we go!");
        }

        if (rpt.initialized == false) {
            return;
        }


        rpt.setSession(request.getSession());

        rpt.setClientID((int) epg.getClient().getPrimaryId());
        rpt.setSpouseID((int) epg.getClient().getSpouseId());
        rpt.setEpg(epg);
        // Need to get from database
        ClientBean curClient = epg.getClient();
        PlannerBean planner = new PlannerBean();
        planner.setDbObject();
        planner.setId(curClient.getPlannerId());
        planner.initialize();

        CashFlowBean cfb = new CashFlowBean();
        cfb.setOwnerId(curClient.getPrimaryId());
        cfb.initialize();

        PdfBean userInfo = new PdfBean();
        userInfo.setClientAge(epg.getClientAge());
        userInfo.setClientFirstName(epg.getClientFirstName());
        userInfo.setClientGender(epg.getClientGender());
        userInfo.setClientLastName(epg.getLastName());
        userInfo.setClientLifeExpectancy((int) epg.getClientLifeExp());
        userInfo.setFinalDeath(cfb.getFinalDeath());

        if (!epg.isSingle) {
            userInfo.setSpouseAge(epg.getSpouseAge());
            userInfo.setSpouseFirstName(epg.getSpouseFirstName());
            userInfo.setSpouseGender(epg.getSpouseGender());
            userInfo.setSpouseLastName(epg.getLastName());
            userInfo.setSpouseLifeExpectancy((int) epg.getSpouseLifeExp());
            userInfo.setClientHeading(userInfo.getClientFirstName() + " and " + userInfo.getSpouseFirstName() + " " + userInfo.getClientLastName());
        } else {
            userInfo.setSingle(true);
            userInfo.setClientHeading(userInfo.getClientFirstName() + " " + userInfo.getClientLastName());
        }
        userInfo.setPlannerLastName(planner.getLastName());
        userInfo.setPlannerFirstName(planner.getFirstName());

        rpt.setUserInfo(userInfo);

        // We need to calculate the Cash Flow for Scenario 1 and 2 here so we can generate the graphs
        CashFlowTable cft = new CashFlowTable();
        cft.setCb(curClient);
        cft.init();
        cft.genTable();

        EstatePlanTable ept = new EstatePlanTable();
        ept.setClient(curClient);
        ept.setScenarioId(epg.getScenarioId());
        ept.setEstate(epg);
        ept.initialize();


        rpt.genTitlePage(userInfo.getClientHeading().toUpperCase());

        rpt.genIntroduction();
        rpt.genPersonalData(userInfo.getClientHeading());
        rpt.genNetWorth(userInfo.getClientHeading());
        rpt.genAssets(userInfo.getClientHeading());
        rpt.genPhilosophy(userInfo.getClientHeading());
        rpt.genObjectives(userInfo.getClientHeading());
        rpt.genCashFlows(userInfo.getClientHeading());
        rpt.genCashFlowGraph(userInfo.getClientHeading(), cft, ept);
        rpt.genObservations(userInfo.getClientHeading());
        rpt.genRecomendations(userInfo.getClientHeading());

        // Need to figure out how to select FLP or LLC if there are some....
        EstatePlanningToolBean estateTools = new EstatePlanningToolBean();
        estateTools.setDbObject();
        Object[] toolList = estateTools.getEstateToolList(epg.getScenarioId());
        boolean doFlp = false;
        boolean doLlc = false;
        for (int i = 0; i < toolList.length; i++) {
            EstatePlanningToolBean eTool = (EstatePlanningToolBean) toolList[i];
            long toolTable = eTool.getToolTableId();
            ToolTableTypes t = ToolTableTypes.getToolTableType((int) toolTable);
            if (t == ToolTableTypes.FLP) {
                doFlp = true;
            }
            if (t == ToolTableTypes.LLC) {
                doLlc = true;
            }
        }

        if (doFlp) {
            rpt.setUseLLC(false);
            rpt.genFLP();
        }

        if (doLlc) {
            rpt.setUseLLC(true);
            rpt.genLLC();
        }

//		 Get tool list of existing tools!
        boolean fcfPresent = false;
        for (int i = 0; i < toolList.length; i++) {
            EstatePlanningToolBean eTool = (EstatePlanningToolBean) toolList[i];
            long toolId = eTool.getToolId();
            long toolTable = eTool.getToolTableId();
            ToolTableTypes t = ToolTableTypes.getToolTableType((int) toolTable);

            switch (t) {
                case CRT: {
                    CrtTool wTool = new CrtTool();
                    wTool.setId(toolId);
                    wTool.setDbObject();
                    wTool.read();
                    wTool.setClientAge(epg.getClientAge());
                    wTool.setSpouseAge(epg.getSpouseAge());
                    session.setAttribute("crt", wTool);
                    rpt.genCRUT();
                    session.removeAttribute("crt");
                    break;
                }

                case QPRT: {
                    QprtTool qTool = new QprtTool();
                    qTool.setId(toolId);
                    qTool.setDbObject();
                    qTool.read();
                    qTool.setClientAge(epg.getClientAge());
                    qTool.setSpouseAge(epg.getSpouseAge());
                    qTool.setClientFirstName(epg.getClientFirstName());
                    qTool.setSpouseFirstName(epg.getSpouseFirstName());

                    qTool.setClientLifeExp(epg.getClientLifeExp());
                    qTool.setSpouseLifeExp(epg.getSpouseLifeExp());
                    session.setAttribute("qprt", qTool);
                    rpt.genQPRT(userInfo.getClientHeading());
                    session.removeAttribute("qprt");
                    break;
                }

                case GRAT: {
                    GratTool gTool = new GratTool();
                    gTool.setId(toolId);
                    gTool.setDbObject();
                    gTool.read();
                    session.setAttribute("grat", gTool);
                    rpt.genGRAT(userInfo.getClientHeading());
                    session.removeAttribute("grat");
                    break;
                }

                case CLAT: {
                    ClatTool clatTool = new ClatTool();
                    clatTool.setId(toolId);
                    clatTool.setDbObject();
                    clatTool.read();
                    session.setAttribute("clat", clatTool);
                    rpt.genCLAT(userInfo.getClientHeading());
                    session.removeAttribute("clat");
                    break;
                }

                case CRUM: {
                    CrummeyTool cTool = new CrummeyTool();
                    cTool.setId(toolId);
                    cTool.setDbObject();
                    cTool.read();
                    session.setAttribute("crum", cTool);
                    rpt.genCrummyWMulti(userInfo.getClientHeading());
                    session.removeAttribute("crum");
                    break;
                }

                case LIFE: {
                    LifeTool lTool = new LifeTool();
                    lTool.setId(toolId);
                    lTool.setDbObject();
                    lTool.read();
                    session.setAttribute("life", lTool);
                    rpt.genLife();
                    session.removeAttribute("life");
                    break;


                }
                case MGEN: {
                    MGTrustTool mTool = new MGTrustTool();
                    mTool.setId(toolId);
                    mTool.setDbObject();
                    mTool.read();
                    mTool.setLifeExpectancy((int) Math.max(epg.getClientLifeExp(), epg.getSpouseLifeExp()));
                    session.setAttribute("mgen", mTool);
                    rpt.genMultiGen(userInfo.getClientHeading());
                    session.getAttribute("mgen");
                    break;
                }

                case RPM: {
                    RpmTool rTool = new RpmTool();
                    rTool.setId(toolId);
                    rTool.setDbObject();
                    rTool.read();
                    rTool.setClientAge(epg.getClientAge());
                    rTool.setSpouseAge(epg.getSpouseAge());
                    session.setAttribute("rpm", rTool);
                    rpt.genRPM(userInfo.getClientHeading());
                    session.removeAttribute("rpm");
                    break;
                }

                case TCLAT: {
                    TClatTool tcTool = new TClatTool();
                    tcTool.setId(toolId);
                    tcTool.setDbObject();
                    tcTool.read();
                    tcTool.setScenarioId(epg.getScenarioId());
                    session.setAttribute("tclat", tcTool);
                    rpt.genTestCLAT(userInfo.getClientHeading());
                    session.removeAttribute("tclat");
                    break;
                }

                case TCLAT2: {
                    TClat2 tcTool = new TClat2();
                    tcTool.setId(toolId);
                    tcTool.setDbObject();
                    tcTool.read();
                    tcTool.calculate();
                    session.setAttribute("tclat2", tcTool);
                    rpt.genTestCLAT2(userInfo.getClientHeading());
                    session.removeAttribute("tclat2");
                    break;
                }

                case IDIT: {
                    IditTool idit = new IditTool();
                    idit.setId(toolId);
                    idit.setDbObject();
                    idit.setEstate(epg);
                    idit.read();
                    session.setAttribute("idit", idit);
                    rpt.genIDIT(userInfo.getClientHeading());
                    session.removeAttribute("idit");
                    break;
                }

                case FCF: {
                    FCFTool fcf = new FCFTool();
                    fcf.setId(toolId);
                    fcf.setDbObject();
                    fcf.read();
                    session.setAttribute("fcf", fcf);
                    fcfPresent = true;
                    break;
                }

                case LAP: {
                    LiquidAssetProtectionTool lap = new LiquidAssetProtectionTool();
                    lap.setId(toolId);
                    lap.read();
                    session.setAttribute("lap", lap);
                    rpt.genLap();
                    session.removeAttribute("lap");
                    break;
                }

                case PANNUITY: {
                    PrivateAnnuityTool pat = new PrivateAnnuityTool();
                    pat.setId(toolId);
                    pat.read();
                    session.setAttribute("pat", pat);
                    rpt.genPat();
                    session.removeAttribute("pat");
                    break;
                }

                case CPLAN: {
                    CharliePlanTool cplan = new CharliePlanTool();
                    cplan.setId(toolId);
                    cplan.read();
                    session.setAttribute("cplan", cplan);
                    rpt.genERP(userInfo.getClientHeading());
                    break;
                }

                case SIDIT: {
                    SIditTool sidit = new SIditTool();
                    sidit.setId(toolId);
                    sidit.read();
                    session.setAttribute("sidit", sidit);
                    rpt.genSIDIT(userInfo.getClientHeading());
                    break;
                }

                case SCIN: {
                    SCINTool scin = new SCINTool();
                    scin.setId(toolId);
                    scin.read();
                    session.setAttribute("scin", scin);
                    rpt.genScin();
                    break;
                }

                case SPLIT: {
                    rpt.genSplit();
                    break;
                }
            }
        }

        //rpt.newPage();

        if (fcfPresent) {
            rpt.genFCF(userInfo.getClientHeading());
            session.removeAttribute("fcf");
        }
        rpt.genEstatePlan(userInfo.getClientHeading());
        rpt.genFinalGraph(userInfo.getClientHeading(), cft, ept);

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
