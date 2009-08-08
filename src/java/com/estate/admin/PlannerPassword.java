/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estate.admin;

import com.teag.bean.AdminBean;
import com.teag.util.StringUtil;
import java.io.IOException;
import java.io.PrintWriter;
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
public class PlannerPassword extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        String path = "/WEB-INF/jsp/admin/profile/changePass.jsp";
        String errMsg = "";
        AdminBean validUser = (AdminBean) session.getAttribute("validUser");

        String action = request.getParameter("action");

        if (action == null) {
            action = "cancel";
        }

        try {

            if (action != null && action.equalsIgnoreCase("modify")) {
                String oldPass = request.getParameter("oldPass");
                String newPass1 = request.getParameter("newPass1");
                String newPass2 = request.getParameter("newPass2");
                newPass1 = StringUtil.removeChar(newPass1, "'".charAt(0));
                newPass2 = StringUtil.removeChar(newPass2, "'".charAt(0));
                boolean fail = false;

                // First check to see if the old password is correct.
                String passCheck = null;
                passCheck = com.estate.security.PasswordService.getInstance().encrypt(oldPass);

                if (validUser.getEPass().equals(passCheck)) {
                    // OK we have a valid password
                    System.out.println("Valid password check!");
                } else {
                    errMsg = "Old password does not match, Please try again.";
                    request.setAttribute("errMsg", errMsg);
                    path = "/WEB-INF/jsp/admin/profile/changePass.jsp";
                    fail = true;
                }

                if (!fail) {
                    if (newPass1.equals(newPass2)) {
                        validUser.setPassword(newPass1);
                        validUser.update();
                        errMsg = "User Password has been Changed";
                        request.setAttribute("errMsg", errMsg);
                        path = "/WEB-INF/jsp/admin/profile/changePass.jsp";
                    } else {
                        errMsg = "Passwords do not match, Please try again, or cancel.";
                        request.setAttribute("errMsg", errMsg);
                        path = "/WEB-INF/jsp/admin/profile/changePass.jsp";
                    }
                }
            }
            if (action.equalsIgnoreCase("cancel")) {
                path = "/admin/index.jsp";
            }

        } catch (Exception e) {
        } finally {
        }

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
