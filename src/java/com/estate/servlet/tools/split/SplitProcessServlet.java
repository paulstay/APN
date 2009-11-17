/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estate.servlet.tools.split;

import com.teag.bean.AssetUtilityBean;
import com.teag.bean.EstatePlanningToolBean;
import com.teag.client.EPTAssets;
import com.teag.estate.SplitDollarTool;
import com.teag.webapp.EstatePlanningGlobals;
import java.io.IOException;
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
public class SplitProcessServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String path = "/servlet/ToolWizardServlet";

        SplitDollarTool split = (SplitDollarTool) session.getAttribute("split");
        EstatePlanningGlobals epg = (EstatePlanningGlobals) session.getAttribute("epg");

        String pageView = request.getParameter("pageView");

        if (pageView != null && pageView.equals("save")) {
            if (split.getId() < 0) {
                split.insert();
                AssetUtilityBean aub = new AssetUtilityBean();
                aub.updateTools(split.getId(), -epg.getScenarioId());
                EstatePlanningToolBean estate = new EstatePlanningToolBean();
                estate.setScenarioId(epg.getScenarioId());
                estate.setToolId(split.getId());
                estate.setToolTableId(split.getToolTableId());
                estate.setDescription("Private Split Dollar");
                estate.insert();
            } else {
                split.update();
            }
            EPTAssets eptassets = new EPTAssets();
            eptassets.loadAssetList(epg.getScenarioId());
            epg.setEptassets(eptassets);
        }

        session.removeAttribute("split");

        RequestDispatcher dispatch = request.getRequestDispatcher(path);
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
