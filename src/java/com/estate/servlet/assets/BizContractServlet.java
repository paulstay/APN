/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estate.servlet.assets;

import com.estate.servlet.Utils;
import com.teag.bean.BizContractBean;
import com.teag.bean.PersonBean;
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
 * @author Paul Stay
 * Date October 2009
 *
 */
public class BizContractServlet extends HttpServlet {

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
        String path = "/WEB-INF/jsp/client/assets/contracts";

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        boolean processList = false;

        try {
            PersonBean person = (PersonBean) session.getAttribute("person");
            if (person == null) {
                throw new NullPointerException();
            }

            if (action.equalsIgnoreCase("add")) {
                BizContractBean cb = new BizContractBean();
                cb.initialize();
                request.setAttribute("biz", cb);
                request.setAttribute("btn", "Add");
                path = "/WEB-INF/jsp/client/assets/contracts/form.jsp";
            }

            if (action.equalsIgnoreCase("edit")) {
                String id = request.getParameter("id");
                if (id != null) {
                    BizContractBean bb = new BizContractBean();
                    bb.setId(Long.parseLong(id));
                    bb.initialize();
                    request.setAttribute("biz", bb);
                    request.setAttribute("btn", "Edit");
                    path = "/WEB-INF/jsp/client/assets/contracts/form.jsp";
                } else {
                    processList = true;
                }
            }

            if (action.equalsIgnoreCase("insert")) {
                BizContractBean bb = new BizContractBean();
                processForm(bb, request);
                bb.insert();
                processList = true;
            }

            if (action.equalsIgnoreCase("update")) {
                BizContractBean bb = new BizContractBean();
                processForm(bb, request);
                bb.update();
                processList = true;
            }

            if (action.equalsIgnoreCase("delete")) {
                BizContractBean bb = new BizContractBean();
                int id = Utils.getIntegerParameter(request, "id", -1);
                if (id > 0) {
                    bb.setId(id);
                    bb.delete();
                }
                processList = true;
            }

            if (action.equalsIgnoreCase("view")) {
                BizContractBean bb = new BizContractBean();
                int id = Utils.getIntegerParameter(request, "id", -1);
                if (id > 0) {
                    bb.setId(id);
                    bb.initialize();
                    request.setAttribute("biz", bb);
                    path = "/WEB-INF/jsp/client/assets/contracts/view.jsp";
                } else {
                    processList = true;
                }
            }

            if (action.equalsIgnoreCase("cancel")) {
                processList = true;
            }

            if (action.equalsIgnoreCase("list") || processList) {
                BizContractBean bb = new BizContractBean();
                // add list here
                ArrayList<BizContractBean> cList = bb.getBeans(BizContractBean.OWNER_ID + "='" + person.getId() + "'");
                request.setAttribute("cList", cList);
                path = "/WEB-INF/jsp/client/assets/contracts/index.jsp";
            }
        } catch (Exception e) {
            System.err.println("Person Bean is not found in cash list");
            path = "/admin/selectCLient";
        }

        RequestDispatcher dispatch = request.getRequestDispatcher(path);
        dispatch.forward(request, response);
    }

    public void processForm(BizContractBean bb, HttpServletRequest request) {
        long id = Utils.getLongParameter(request, "id", -1);
        bb.setId(id);

        long ownerId = Utils.getLongParameter(request, "ownerId", 0);
        bb.setOwnerId(ownerId);

        String description = Utils.getStringParameter(request, "description", "");
        bb.setDescription(description);

        double value = Utils.getDoubleParameter(request, "value", 0);
        bb.setValue(value);

        double salary = Utils.getDoubleParameter(request, "salary", 0);
        bb.setSalary(salary);

        int startYear = Utils.getIntegerParameter(request, "start_year", 2010);
        int endYear = Utils.getIntegerParameter(request, "end_year", 2010);

        bb.setStartYear(startYear);
        bb.setEndYear(endYear);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        return "Businsess Contracts";
    }// </editor-fold>
}
