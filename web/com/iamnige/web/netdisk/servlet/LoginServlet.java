package com.iamnige.web.netdisk.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iamnige.logger.Logger;
import com.iamnige.web.netdisk.model.CommonSessionModel;
import com.iamnige.web.netdisk.model.LoginAndAuthModel;

public class LoginServlet extends CommonServlet {

	/**
	 * Constructor of the object.
	 */
	public LoginServlet() {
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
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command");
		switch(command){
		case "login":{
			String username = request.getParameter("username");
			String passwd = request.getParameter("password");
			LoginAndAuthModel model = new LoginAndAuthModel();
			if(model.isUserExists(username, passwd)){
				HttpSession session = request.getSession(true);
				Long uid = model.getUidByUname(username);
				CommonSessionModel sm = null;
				try {
					sm = new CommonSessionModel(session);
				} catch (Exception e) {
					forwardToResultPage(request, response, "请先登录！");
				}
				sm.set(CommonSessionModel.USER, username);
				sm.set(CommonSessionModel.UID, uid);
				response.sendRedirect("fileUpload.do?command=show");
				
			}else{
				forwardToResultPage(request, response, "登录验证失败,用户不存在！");
			}
			break;
		}
		case "directToRegister":{
			request.getRequestDispatcher(this.getJspPath("register.jsp")).forward(request,response);
			break;
		}
		case "register":{
			String username = request.getParameter("username");
			String passwd = request.getParameter("password");
			LoginAndAuthModel model = new LoginAndAuthModel();
			boolean ret = model.registerUser(username, passwd);
			String message = ret ? "注册成功！" : "注册失败";
			forwardToResultPage(request, response, message);
			break;			
		}
		default:
			break;
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		super.init();
	}

}
