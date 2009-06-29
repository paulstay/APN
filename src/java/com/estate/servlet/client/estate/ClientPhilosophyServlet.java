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
import com.teag.bean.PhilosophiesBean;

public class ClientPhilosophyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7847017303176534327L;

	/**
	 * Constructor of the object.
	 */
	public ClientPhilosophyServlet() {
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
		String path = "/WEB-INF/jsp/client/estate/philosophy/index.jsp";
		
		boolean processList = true;
		String action = request.getParameter("action");
		
		if(action == null){
			action = "list";
		}
		
		try {
			PersonBean person = (PersonBean) session.getAttribute("person");
		
			if(action.equalsIgnoreCase("add")){
				PhilosophiesBean pb = new PhilosophiesBean();
				pb.initialize();
				com.teag.bean.utils.HeadingText ht = new com.teag.bean.utils.HeadingText();
				request.setAttribute("phil", pb);
				request.setAttribute("ht", ht);
				path = "/WEB-INF/jsp/client/estate/philosophy/form.jsp";
			}
			
			if(action.equalsIgnoreCase("insert")){
				PhilosophiesBean pb = new PhilosophiesBean();
				processForm(pb,request);
				pb.setOwnerId(person.getId());
				pb.insert();
				processList = true;
			}
			
			if(action.equalsIgnoreCase("edit")){
				String id = Utils.getStringParameter(request, "id", null);
				if( id != null) {
					PhilosophiesBean pb = new PhilosophiesBean();
					pb.setId(Long.parseLong(id));
					pb.initialize();
					com.teag.bean.utils.HeadingText ht = new com.teag.bean.utils.HeadingText();
					ht.setText(pb.getPhilosophy());
					request.setAttribute("phil", pb);
					request.setAttribute("ht", ht);
					path = "/WEB-INF/jsp/client/estate/philosophy/form.jsp";
				} else {
					processList = true;
				}
			}
			
			if(action.equalsIgnoreCase("update")){
				String id = Utils.getStringParameter(request, "id", null);
				if( id != null) {
					PhilosophiesBean pb = new PhilosophiesBean();
					pb.setId(Long.parseLong(id));
					pb.setOwnerId(person.getId());
					processForm(pb,request);
					pb.update();
				}
				processList = true;
			}
			
			if(action.equalsIgnoreCase("delete")){
				String id = Utils.getStringParameter(request, "id", null);
				if( id != null) {
					PhilosophiesBean pb = new PhilosophiesBean();
					pb.setId(Long.parseLong(id));
					pb.delete();
				}
				processList = true;
			}
			
			if(action.equalsIgnoreCase("cancel")){
				processList = true;
			}
			
			if(action.equalsIgnoreCase("list") || processList){
				PhilosophiesBean pb = new PhilosophiesBean();
				ArrayList<PhilosophiesBean> cList = pb.getBeans(PhilosophiesBean.OWNER_ID + "='" + person.getId() + "'");
				request.setAttribute("cList", cList);
			}
		} catch(Exception e) {
			
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
	}

	
	public void processForm(PhilosophiesBean pb, HttpServletRequest request){
		com.teag.bean.utils.HeadingText ht = new com.teag.bean.utils.HeadingText();
		String header = Utils.getStringParameter(request, "header", "");
		String body = Utils.getStringParameter(request, "body", "");
		ht.setBody(body);
		ht.setHeader(header);
		pb.setPhilosophy(ht.getText());
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
