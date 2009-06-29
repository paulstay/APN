package com.estate.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.servlet.Utils;
import com.teag.bean.PlannerBean;

public class PlannerForm extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6418353462966059665L;

	/**
	 * Constructor of the object.
	 */
	public PlannerForm() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String pageView = request.getParameter("pageView");
		String forward = "/WEB-INF/jsp/admin/planner/planner.jsp";
		Boolean errorFlag = false;
		PlannerBean pb = new PlannerBean();

		request.setAttribute("view", "add");

		if (pageView == null || pageView.equals("quit")) {
			session.removeAttribute("planner");
			forward = "/WEB-INF/jsp/admin/planner/planner.jsp";
		} else {
			// Process the form elements generating errors if needed
			if (pageView.equals("update")) {
				request.setAttribute("view", "edit");
				pb = (PlannerBean) session.getAttribute("planner");
			} else {
				pb = new PlannerBean();
			}

			String firstName = Utils.getStringParameter(request, "firstName",
					null);
			if (firstName == null || firstName.length() == 0) {
				request.setAttribute("errorFirstName", " * required");
				errorFlag = true;
			}
			if (firstName != null && firstName.length() > 0) {
				pb.setFirstName(firstName);
			}

			String middleName = Utils.getStringParameter(request, "middleName",
					"");
			if (middleName != null) {
				pb.setMiddleName(middleName);
			}

			String lastName = Utils.getStringParameter(request, "lastName",
					null);

			if (lastName == null || lastName.length() == 0) {
				request.setAttribute("errorLastName", "* required");
				errorFlag = true;
			}
			if (lastName != null && lastName.length() > 0) {
				pb.setLastName(lastName);
			}

			String address1 = Utils.getStringParameter(request, "address1", "");
			pb.setAddress1(address1);

			String address2 = Utils.getStringParameter(request, "address1", "");
			pb.setAddress1(address2);

			String city = Utils.getStringParameter(request, "city", "");
			pb.setCity(city);

			String state = Utils.getStringParameter(request, "state", "AL");
			pb.setState(state);

			String zip = Utils.getStringParameter(request, "zipcode", "");
			pb.setZipcode(zip);

			String phone = Utils.getStringParameter(request, "phone", "");
			pb.setPhone(phone);

			String status = Utils.getStringParameter(request, "status", "A");
			pb.setStatus(status);

			String email = Utils.getStringParameter(request, "email", null);

			if (email == null || email.length() == 0) {
				request.setAttribute("errorEmail", " * required");
				errorFlag = true;
			}
			pb.setEmail(email);

			// test username to make sure it is unique and not already taken
			String userName = Utils.getStringParameter(request, "pusername",
					null);

			String pass = Utils.getStringParameter(request, "upassword", "");
			if (pass != null && pass.length() > 0) {
				pb.setPassword(pass);
			} else {
				request.setAttribute("errorPass", "* required");
				errorFlag = true;
			}

			if (userName != null && userName.length() > 0) {
				PlannerBean sb = new PlannerBean();
				if (pageView.equals("add")) {
					java.util.ArrayList<PlannerBean> aList = sb
							.getBeans(PlannerBean.USER_NAME + "='" + userName
									+ "'");
					if (aList.isEmpty()) {
						pb.setUserName(userName);
					} else {
						request.setAttribute("errorUserName",
								"Username already taken (" + userName + ")");
						errorFlag = true;
					}
				}
				if (pageView.equals("update")) {
					// If we are chaging the username, we need to first check it
					// for duplicates
					if (!userName.equals(pb.getUserName())) {
						java.util.ArrayList<PlannerBean> aList = sb
								.getBeans(PlannerBean.USER_NAME + "='"
										+ userName + "'");
						if (aList.isEmpty()) {
							pb.setUserName(userName);
						} else {
							request
									.setAttribute("errorUserName",
											"Username already taken ("
													+ userName + ")");
							errorFlag = true;
						}
					}
				}
			} else {
				request.setAttribute("errorUserName", "Username is required!");
				errorFlag = true;
			}
			if (errorFlag) {
				forward = "/WEB-INF/jsp/admin/planner/ePlanner.jsp";
				session.setAttribute("planner", pb);
			} else {
				if (pageView.equals("update")) {
					pb.update();
				} else {
					pb.setOrgId(15); // Advance Estate Strategies
					pb.insert();
				}
			}
		}
		RequestDispatcher dispatch = request.getRequestDispatcher(forward);
		dispatch.forward(request, response);
	}

	/**
	 * Returns information about the servlet, such as author, version, and
	 * copyright.
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
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}

}
