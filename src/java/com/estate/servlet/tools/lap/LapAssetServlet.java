package com.estate.servlet.tools.lap;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.constants.ToolTableTypes;
import com.estate.validate.ValidatePercent;
import com.estate.view.AssetViewBean;
import com.estate.view.AssetsAvailableBean;
import com.teag.bean.AssetSqlBean;
import com.teag.bean.AssetUtilityBean;
import com.teag.bean.EPTAssetBean;
import com.teag.bean.EPTAssetDistBean;
import com.teag.bean.LoadAssetBean;
import com.teag.client.EPTAssets;
import com.teag.estate.LiquidAssetProtectionTool;
import com.teag.webapp.EstatePlanningGlobals;


public class LapAssetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4588978881641581115L;

	/**
	 * Constructor of the object.
	 */
	public LapAssetServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/lap/assets.jsp";
		
		LiquidAssetProtectionTool lap = (LiquidAssetProtectionTool) session.getAttribute("lap");
		EstatePlanningGlobals epg = (EstatePlanningGlobals) session
				.getAttribute("epg");

		String action = request.getParameter("action");
		
		EPTAssets eptAssets = epg.getEptassets();
		EPTAssetBean[] assetList = eptAssets.getEPTAssets();
		
		
		if(action != null && action.equalsIgnoreCase("addAsset")){
			String pStr = request.getParameter("assetId");
			String[] result = pStr.split(":");
			int assetId = Integer.parseInt(result[0]);
			int assetType = Integer.parseInt(result[1]);
			
			double percentDist = ValidatePercent.checkPercent(request.getParameter("v_dist"), 1.0);
			for(int i=0; i < assetList.length; i++){
				EPTAssetBean asset = assetList[i];
				if(assetId == asset.getAssetId() && assetType == asset.getAssetType()) {
					asset.distribute(asset.getRemainingValue()* percentDist, lap.getId(), ToolTableTypes.LAP.id());
				}
			}
		}
		
		if(action!= null && action.equalsIgnoreCase("delAsset")) {
			int assetId = Integer.parseInt(request.getParameter("delId"));
			if( assetId > 0) {
				AssetUtilityBean aub = new AssetUtilityBean();
				aub.deleteDistribution(assetId);
			}
		}

		EPTAssetDistBean[] distributions = eptAssets.getToolDistributions(lap.getId(),
				ToolTableTypes.LAP.id());
		
		ArrayList<AssetViewBean> aList = new ArrayList<AssetViewBean>();
		for (int i = 0; i < distributions.length; i++) {
			long distId = distributions[i].getId();
			EPTAssetBean toolAsset = new EPTAssetBean();
			toolAsset.setId(distributions[i].getEptassetId());
			toolAsset.initialize();
			LoadAssetBean lab = new LoadAssetBean();
			AssetSqlBean sb = lab.loadAssetBean(toolAsset);
			AssetViewBean avb = new AssetViewBean();
			avb.setId((int)distId);
			avb.setName(sb.getAssetName());
			avb.setValue(distributions[i].getValue());
			double basis = sb.getAssetBasis() * sb.getAssetValue()/distributions[i].getValue();
			avb.setBasis(basis);
			avb.setGrowth(sb.getAssetGrowth());
			avb.setIncome(sb.getAssetIncome());
			avb.setAssetType(sb.getBeanType());
			avb.setAssetId((int)toolAsset.getAssetId());
			aList.add(avb);
		}
		
		ArrayList<AssetsAvailableBean> bList = new ArrayList<AssetsAvailableBean>();
		for(EPTAssetBean e : assetList){
			if(e.getRemainingValue()>1) {
				AssetsAvailableBean aab = new AssetsAvailableBean();
				aab.setDescription(e.getDescription());
				aab.setId((int)e.getAssetId());
				aab.setRemainingValue(e.getRemainingValue());
				aab.setAssetType((int)e.getAssetType());
				bList.add(aab);
				
			}
		}
		
		request.setAttribute("aList",aList);
		request.setAttribute("eptAssets", eptAssets);
		request.setAttribute("bList", bList);
		
		// Forward to the discription page.
		RequestDispatcher dispatch = request
				.getRequestDispatcher(path);
		
		dispatch.forward(request, response);
		
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
