package com.estate.servlet.client.estate;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.bean.PersonBean;
import com.teag.bean.RecommendationBean;


public class ClientRecommendationsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3705555082843727587L;

	/**
	 * Constructor of the object.
	 */
	public ClientRecommendationsServlet() {
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
		String path = "/WEB-INF/jsp/client/estate/recommendations/index.jsp";
		
		boolean processList = true;
		String action = request.getParameter("action");
		
		if(action == null){
			action = "list";
		}
		
		try {
			PersonBean person = (PersonBean) session.getAttribute("person");
		
			if(action.equalsIgnoreCase("add")){
				RecommendationBean rec = new RecommendationBean();
				rec.initialize();
				com.teag.bean.utils.HeadingText ht = new com.teag.bean.utils.HeadingText();
				request.setAttribute("rec", rec);
				request.setAttribute("ht", ht);
				path = "/WEB-INF/jsp/client/estate/recommendations/form.jsp";
			}
			
			if(action.equalsIgnoreCase("insert")){
				RecommendationBean rec = new RecommendationBean();
				com.teag.bean.utils.HeadingText ht = new com.teag.bean.utils.HeadingText();
				String header = Utils.getStringParameter(request, "header", "");
				String body = Utils.getStringParameter(request, "body", "");
				if(header.length()>0 && body.length()>0){
					ht.setBody(body);
					ht.setHeader(header);
					rec.setRecommendation(ht.getText());
					rec.setOwnerId(person.getId());
					rec.insert();
				}
				processList = true;
			}
			
			if(action.equalsIgnoreCase("edit")){
				String id = Utils.getStringParameter(request, "id", null);
				if( id != null) {
					RecommendationBean rec = new RecommendationBean();
					rec.setId(Long.parseLong(id));
					rec.initialize();
					com.teag.bean.utils.HeadingText ht = new com.teag.bean.utils.HeadingText();
					ht.setText(rec.getRecommendation());
					request.setAttribute("rec", rec);
					request.setAttribute("ht", ht);
					path = "/WEB-INF/jsp/client/estate/recommendations/form.jsp";
				} else {
					processList = true;
				}
			}
			
			if(action.equalsIgnoreCase("update")){
				String id = Utils.getStringParameter(request, "id", null);
				if( id != null) {
					RecommendationBean rec = new RecommendationBean();
					rec.setId(Long.parseLong(id));
					rec.setOwnerId(person.getId());
					String header = Utils.getStringParameter(request, "header", "");
					String body = Utils.getStringParameter(request, "body", "");
					com.teag.bean.utils.HeadingText ht = new com.teag.bean.utils.HeadingText();
					if(header.length()>0 && body.length()> 0) {
						ht.setBody(body);
						ht.setHeader(header);
						rec.setRecommendation(ht.getText());
						rec.update();
					}
				}
				processList = true;
			}
			
			if(action.equalsIgnoreCase("delete")){
				String id = Utils.getStringParameter(request, "id", null);
				if( id != null) {
					RecommendationBean rec = new RecommendationBean();
					rec.setId(Long.parseLong(id));
					rec.delete();
				}
				processList = true;
			}
			
			if(action.equalsIgnoreCase("cancel")){
				processList = true;
			}
			
			if(action.equalsIgnoreCase("list") || processList){
				RecommendationBean rec = new RecommendationBean();
				ArrayList<RecommendationBean> cList = rec.getBeans(RecommendationBean.OWNER_ID + "='" + person.getId() + "'");
				request.setAttribute("cList", cList);
			}
			
		} catch(Exception e) {
			
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
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
