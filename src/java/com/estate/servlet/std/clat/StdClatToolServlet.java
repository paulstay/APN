package com.estate.servlet.std.clat;

import java.io.IOException;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;

import com.estate.servlet.Utils;
import com.estate.toolbox.Clat;

public class StdClatToolServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -8971598526557581572L;

    /**
     * Constructor of the object.
     */
    public StdClatToolServlet() {
        super();
    }

    /**
     * Destruction of the servlet. <br>
     */
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
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String path = "/WEB-INF/jsp/standalone/clat/tool.jsp";
        String action = request.getParameter("action");

        java.util.Enumeration e = request.getParameterNames();
        while (e.hasMoreElements()) {
            String name = (String) e.nextElement();
            String value = request.getParameter(name);
            System.out.println("parameter : " + name + " : " + value);
        }
        
        Clat clat = (Clat) session.getAttribute("clat");

        if (action != null && action.equals("update")) {
            String trustType = Utils.getStringParameter(request, "trustType", "T");
            clat.setTrustType(trustType);

            if (clat.getTrustType().equals("T")) {
                clat.setAge1(0);
                clat.setAge2(0);
            } else {
                int age1 = Utils.getIntegerParameter(request, "age1", 0);
                clat.setAge1(age1);
                int age2 = Utils.getIntegerParameter(request, "age2", 0);
                clat.setAge2(age2);
            }

            boolean grantor = Utils.getBooleanParameter(request, "grantor", false);
            clat.setGrantor(grantor);
            double fmv = Utils.getDoubleParameter(request, "fmv", 0);
            clat.setFmv(fmv);
            boolean llc = Utils.getBooleanParameter(request, "llc", true);
            clat.setUseLLC(llc);
            double discount = Utils.getPercentParameter(request, "discount", .25);
            clat.setDiscount(discount);
            double assetGrowth = Utils.getPercentParameter(request, "assetGrowth", 0);
            clat.setAssetGrowth(assetGrowth);
            double assetIncome = Utils.getPercentParameter(request, "assetIncome", 0);
            clat.setAssetIncome(assetIncome);
            double irsRate = Utils.getPercentParameter(request, "irsRate", .05);
            clat.setIrsRate(irsRate);
            Date irsDate = Utils.getDateParameter(request, "irsDate", new Date());
            clat.setIrsDate(irsDate);
            double taxRate = Utils.getPercentParameter(request, "taxRate", .035);
            clat.setTaxRate(taxRate);
            double estateTaxRate = Utils.getPercentParameter(request, "estateTaxRate", .45);
            clat.setEstateTaxRate(estateTaxRate);
            int term = Utils.getIntegerParameter(request, "term", 10);
            clat.setTerm(term);
            int endBegin = Utils.getIntegerParameter(request, "endBegin", 0);	// Default end of term
            clat.setEndBegin(endBegin);
            int freq = Utils.getIntegerParameter(request, "freq", 1);	// Annual
            clat.setFreq(freq);
            String calculationMethod = Utils.getStringParameter(request, "calculationMethod", "R"); // Remainder Interest
            if (calculationMethod.equalsIgnoreCase("A")) {
                double annuityPayment = Utils.getDoubleParameter(request, "annuityPayment", 0);
                clat.setAnnuityPayment(annuityPayment);
            } else if (calculationMethod.equalsIgnoreCase("R")) {
                double remainderInterest = Utils.getDoubleParameter(request, "remainderInterest", 0);
                clat.setRemainderInterest(remainderInterest);
            } else {
                clat.setRemainderInterest(0);		// Zero out CLAT
            }

            double annuityIncrease = Utils.getPercentParameter(request, "annuityIncrease", 0);
            clat.setAnnuityIncrease(annuityIncrease);

            // 0 = last to die, 1 = first to die
            int lifeType = Utils.getIntegerParameter(request, "lifeType", 0);
            clat.setLifeType(lifeType);

            session.setAttribute("clat", clat);
        }

        boolean dispTerm = true;
        boolean dispLife = true;

        if (clat.getTrustType().equals("T")) {
            clat.setAge1(0);
            clat.setAge2(0);
            dispTerm = true;
            dispLife = false;
        }

        if (clat.getTrustType().equals("L")) {
            clat.setTerm(0);
            dispTerm = false;
            dispLife = true;
        }

        request.setAttribute("dispTerm", dispTerm);
        request.setAttribute("dispLife", dispLife);

        RequestDispatcher dispatch = request.getRequestDispatcher(path);
        dispatch.forward(request, response);

    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException {
        // Put your code here
    }
}
