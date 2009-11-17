/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.estate.servlet.tools.split;

import com.teag.estate.SplitDollarTool;
import com.teag.webapp.EstatePlanningGlobals;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class SplitServlet extends HttpServlet {
   
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

		EstatePlanningGlobals epg = (EstatePlanningGlobals) session
				.getAttribute("epg");

		SplitDollarTool tool = new SplitDollarTool();
		String toolView = request.getParameter("action");

		if (toolView != null && toolView.equalsIgnoreCase("edit")) {
			String toolId = request.getParameter("id");
			if(toolId != null && Integer.parseInt(toolId)> 0){
				tool.setId(Integer.parseInt(toolId));
				tool.read();
			} else {
				toolView = "add";	// Just in case!
			}
		}

		if(toolView != null && toolView.equalsIgnoreCase("add")){
			Date now = new Date();
			SimpleDateFormat currentDate = new SimpleDateFormat("M/dd/yyyy");
                        tool = new SplitDollarTool();
                        tool.setId((-epg.getScenarioId()));
		}

		session.setAttribute("split", tool);

		// Forward to the discription page.
		RequestDispatcher dispatch = request
				.getRequestDispatcher("/WEB-INF/jsp/estatetools/split/description.jsp");

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
