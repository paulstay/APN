package com.estate.servlet.tools.IditGrat;

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
import com.teag.bean.FlpLPBean;
import com.teag.bean.LlcLPBean;
import com.teag.bean.LoadAssetBean;
import com.teag.client.EPTAssets;
import com.teag.estate.IditGratPay;
import com.teag.estate.IditTool;
import com.teag.webapp.EstatePlanningGlobals;


public class IditGratAssetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4912308585826175038L;

	/**
	 * Constructor of the object.
	 */
	public IditGratAssetServlet() {
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
		String path = "/WEB-INF/jsp/estatetools/idit2/assets.jsp";
		
		double totalValue = 0;

		IditTool idit = (IditTool) session.getAttribute("idit");
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
					asset.distribute(asset.getRemainingValue()* percentDist, idit.getId(), ToolTableTypes.IDIT.id());
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

		EPTAssetDistBean[] distributions = eptAssets.getToolDistributions(idit.getId(),
				ToolTableTypes.IDIT.id());
		
		/*
		 * This is where we setup the current view, we need to get all of the assets to list.
		 */
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
			totalValue += distributions[i].getValue();
			double basis = sb.getAssetBasis() * sb.getAssetValue()/distributions[i].getValue();
			avb.setBasis(basis);
			avb.setGrowth(sb.getAssetGrowth());
			avb.setIncome(sb.getAssetIncome());
			avb.setAssetType(sb.getBeanType());
			avb.setAssetId((int)toolAsset.getAssetId());
			aList.add(avb);
		}
		
		/*
		 * I know this might be redundant, from the above, but We need to get the values for just
		 * the FLP or LLC values here. Then we can determine the amount of liquid securities needed
		 * to handle the note!
		 */
		
		double dValue = 0;
		double cValue = 0;
		double secGrowth = .05;
		for(int i=0; i < distributions.length; i++){
			EPTAssetBean toolAsset = new EPTAssetBean();
			toolAsset.setId(distributions[i].getEptassetId());
			toolAsset.initialize();
			LoadAssetBean lab = new LoadAssetBean();
			AssetSqlBean asb = lab.loadAssetBean(toolAsset);
			double discount = .25;	// Default discount for LLC or FLP
			if( asb instanceof FlpLPBean){
				FlpLPBean flp = (FlpLPBean) asb;
				discount = flp.getDiscount();
				dValue += distributions[i].getValue() * (1.0 * discount);
				cValue += distributions[i].getValue();
			} else if( asb instanceof LlcLPBean){
					LlcLPBean llc = (LlcLPBean) asb;
				discount = llc.getDiscount();
				dValue += distributions[i].getValue() * (1.0 * discount);
				cValue += distributions[i].getValue();
			} else {
				double tGrowth = asb.getAssetGrowth();
				if( tGrowth != 0 && tGrowth < secGrowth)
					secGrowth = tGrowth;
			}
		}

		double sv = 0;
		
		if(dValue > 0) {
			IditGratPay igc = new IditGratPay();
			igc.setNote(dValue);
			igc.setNoteRate(idit.getNoteInterest());
			igc.setFlpDiscount(idit.getNoteFlpDiscount());
			igc.setFlpLpInterests(idit.getNoteFlpLPInterest());
			igc.setFreq((int) idit.getAnnuityFreq());
			igc.setTerm(idit.getGratTrusts());
			igc.setSecuritiesGrowth(secGrowth);
			igc.calculate();
			sv = igc.getSecurityValue();
			idit.setOptimalPaymentRate(igc.getOptimalRate());
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

		request.setAttribute("totalValue", totalValue);
		request.setAttribute("liquidValue", sv);
		request.setAttribute("liqHave", totalValue - cValue);
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
