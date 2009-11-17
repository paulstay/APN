/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.estate.servlet.tools.split;

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
import com.teag.estate.SplitDollarTool;
import com.teag.webapp.EstatePlanningGlobals;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Paul
 */
public class SplitAssetServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       String path = "/WEB-INF/jsp/estatetools/split/assets.jsp";
		HttpSession session = request.getSession();

		SplitDollarTool split = (SplitDollarTool) session.getAttribute("split");
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
					asset.distribute(asset.getRemainingValue()* percentDist, split.getId(), ToolTableTypes.SPLIT.id());
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

		EPTAssetDistBean[] distributions = eptAssets.getToolDistributions(split.getId(),
				ToolTableTypes.SPLIT.id());

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
		RequestDispatcher dispatch  = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
    } 
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
