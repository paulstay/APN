package com.estate.servlet.tools;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.constants.ToolTableTypes;
import com.teag.bean.AssetUtilityBean;
import com.teag.bean.ToolUtilityBean;
import com.teag.estate.CrtTool;
import com.teag.estate.CrummeyTool;
import com.teag.estate.FCFTool;
import com.teag.estate.FlpTool;
import com.teag.estate.GratTool;
import com.teag.estate.IditTool;
import com.teag.estate.LifeTool;
import com.teag.estate.LiquidAssetProtectionTool;
import com.teag.estate.LlcTool;
import com.teag.estate.MGTrustTool;
import com.teag.estate.PrivateAnnuityTool;
import com.teag.estate.QprtTool;
import com.teag.estate.RpmTool;
import com.teag.estate.SCINTool;
import com.teag.estate.SIditTool;
import com.teag.estate.TClat2;
import com.teag.estate.TClatTool;
import com.teag.webapp.EstatePlanningGlobals;

public class ToolSelectServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5310404889626191292L;

	/**
	 * Constructor of the object.
	 */
	public ToolSelectServlet() {
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
		// Shift control to either the add, or edit tool code!
		String action = request.getParameter("action");
		String path = "/servlet/ToolWizardServlet";
		String tool = request.getParameter("toolName");

		if(action != null && action.equalsIgnoreCase("edit")){
			path = editTool(request);
		} 
		
		if(action != null && action.equalsIgnoreCase("add")) {
			path = addTool(tool);
		}
		
		if(action != null && action.equalsIgnoreCase("delete")){
			deleteTool(request);
		}

		if(path.length()== 0){
			path = "/servlet/ToolWizardServlet";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request,response);

	}
	
	public String addTool(String tool){
		String path = "";
		
		
		if(tool.equals("FLP")) {
			path = "/servlet/FlpServlet";
		} else if(tool.equalsIgnoreCase("LLC")){
			path = "/servlet/LlcServlet";
		} else if(tool.equalsIgnoreCase("GRAT")){
			path = "/servlet/GratServlet";
		} else if(tool.equalsIgnoreCase("CRT")){
			path = "/servlet/CrtServlet";
		} else if(tool.equalsIgnoreCase("QPRT")){
			path = "/servlet/QprtServlet";
		} else if(tool.equalsIgnoreCase("CLAT")){
			path = "/servlet/ClatServlet";
		} else if(tool.equalsIgnoreCase("RETI")){
			path = "/servlet/RpmServlet";
		} else if(tool.equalsIgnoreCase("SIDIT")){
			path = "/servlet/IditServlet";
		} else if(tool.equalsIgnoreCase("SCIN")){
			path = "/servlet/ScinServlet";
		} else if(tool.equalsIgnoreCase("PAT")) {
			path = "/servlet/PatServlet";
		} else if(tool.equalsIgnoreCase("LAP")){
			path = "/servlet/LapServlet";
		} else if(tool.equalsIgnoreCase("FCF")){
			path = "/servlet/FcfServlet";
		} else if(tool.equalsIgnoreCase("CRUM")){
			path = "/servlet/CrumServlet";
		} else if(tool.equalsIgnoreCase("TCLAT")){
			path = "/servlet/TClatServlet";
		} else if(tool.equalsIgnoreCase("IDIT")){
			path = "/servlet/IditGratServlet";
		} else if(tool.equalsIgnoreCase("LIFE")){
			path = "/servlet/LifeServlet";
		} else if(tool.equalsIgnoreCase("MULTI")){
			path = "/servlet/MgenServlet";
		} else {
			path = "/servlet/ToolWizardServlet";
		}
		
		return path;
	}
	
	
	public String editTool(HttpServletRequest request) {
		String path = "";
		HttpSession session = request.getSession();
		
		long toolId = Long.parseLong(request.getParameter("id"));
		long tableId = Long.parseLong(request.getParameter("toolType"));
		
		ToolTableTypes tType = ToolTableTypes.getToolTableType((int)tableId);

		if( tType == ToolTableTypes.FLP) {
			FlpTool flpTool = new FlpTool();
			flpTool.setId(toolId);
			flpTool.read();
			session.setAttribute("flp", flpTool);
			return  "/servlet/FlpServlet";
		}
		if( tType == ToolTableTypes.LLC) {
			LlcTool llcTool = new LlcTool();
			llcTool.setId(toolId);
			llcTool.read();
			session.setAttribute("llc", llcTool);
			return "/servlet/LlcServlet";
		}
		
		if( tType == ToolTableTypes.CRT ) {
			CrtTool tool = new CrtTool();
			tool.setId(toolId);
			tool.read();
			session.setAttribute("crt", tool);
			return "/servlet/CrtServlet";
		} 

		if( tType == ToolTableTypes.GRAT) {
			GratTool gTool = new GratTool();
			gTool.setId(toolId);
			gTool.read();
			session.setAttribute("grat", gTool);
			return "/servlet/GratServlet";
		}
	
		if( tType == ToolTableTypes.QPRT) {
			QprtTool qTool = new QprtTool();
			qTool.setId(toolId);
			qTool.read();
			session.setAttribute("qprt", qTool);
			return "/servlet/QprtServlet";
		}

		if( tType == ToolTableTypes.CLAT) {
			return "/servlet/ClatServlet";
		}

		if( tType == ToolTableTypes.TCLAT) {
			TClatTool tcTool = new TClatTool();
			tcTool.setId(toolId);
			tcTool.read();
			session.setAttribute("tclat", tcTool);
			return "/servlet/TClatServlet";
		}

		if( tType == ToolTableTypes.CRUM) {
			CrummeyTool cTool = new CrummeyTool();
			cTool.setId(toolId);
			cTool.read();
			session.setAttribute("crum",cTool);
			return "/servlet/CrumServlet";
		}
		
		if( tType == ToolTableTypes.MGEN) {
			MGTrustTool mTool = new MGTrustTool();
			mTool.setId(toolId);
			mTool.read();
			session.setAttribute("mgen", mTool);
			return "/servlet/MgenServlet";
		}

		if (tType == ToolTableTypes.RPM) {
			RpmTool rTool = new RpmTool();
			rTool.setId(toolId);
			rTool.read();
			session.setAttribute("rpm", rTool);
			return "/servlet/RpmServlet";
		}
		
		if( tType == ToolTableTypes.FCF) {
			FCFTool fcf = new FCFTool();
			fcf.setId(toolId);
			fcf.read();
			session.setAttribute("fcf", fcf);
			return "/servlet/FcfServlet";
		}

		if( tType == ToolTableTypes.TCLAT2) {
			TClat2 tclat = new TClat2();
			tclat.setId(toolId);
			tclat.read();
			session.setAttribute("tclat2", tclat);
			return "tclat2/view.jsp";
		}

		if( tType == ToolTableTypes.LAP) {
			LiquidAssetProtectionTool lap = new LiquidAssetProtectionTool();
			lap.setId(toolId);
			lap.read();
			session.setAttribute("lap", lap);
			return "/servlet/LapServlet";
		}
	
		if( tType == ToolTableTypes.PANNUITY) {
			PrivateAnnuityTool pat = new PrivateAnnuityTool();
			pat.setId(toolId);
			pat.read();
			session.setAttribute("pat", pat);
			return "/servlet/PatServlet";
		}
		
		if( tType == ToolTableTypes.SCIN) {
			SCINTool scin = new SCINTool();
			scin.setId(toolId);
			scin.read();
			session.setAttribute("scin", scin);
			return "/servlet/ScinServlet";
		}

		if( tType == ToolTableTypes.SIDIT) {
			SIditTool sidit = new SIditTool();
			sidit.setId(toolId);
			sidit.read();
			session.setAttribute("sidit", sidit);
			return "/servlet/IditServlet";
		}
		
		if( tType == ToolTableTypes.IDIT) {
			IditTool idit = new IditTool();
			idit.setId(toolId);
			idit.read();
			session.setAttribute("idit", idit);
			return "/servlet/IditGratServlet";
		}
		
		if(tType == ToolTableTypes.LIFE){
			LifeTool life = new LifeTool();
			life.setId(toolId);
			life.read();
			session.setAttribute("life", life);
			return "/servlet/LifeServlet";
		}
		
		return path;
	}
	
	public void deleteTool(HttpServletRequest request) {

		EstatePlanningGlobals epg = (EstatePlanningGlobals) request.getSession().getAttribute("epg");
		
		long toolId = Long.parseLong(request.getParameter("id"));
		long tableId = Long.parseLong(request.getParameter("toolType"));
		long planToolId = Long.parseLong(request.getParameter("planTableId"));
		
		ToolTableTypes tType = ToolTableTypes.getToolTableType((int)tableId);
		// First check if its a TCLAT and if so, update the assets
		if( tType == ToolTableTypes.TCLAT ) {
			AssetUtilityBean aub = new AssetUtilityBean();
			aub.deleteTClatAssts(epg.getScenarioId());
		}
		
		ToolUtilityBean tub = new ToolUtilityBean();
		tub.deleteTool(toolId, tableId, planToolId);
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
