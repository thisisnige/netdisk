package com.iamnige.web.netdisk.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.iamnige.web.netdisk.model.CommonFileModel;

public class FileUploadServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String type = request.getContentType();
		if(null != type && type.contains("multipart/form-data")){
			request.setCharacterEncoding("UTF-8");
			CommonFileModel model = new CommonFileModel();
			boolean b = model.storeFileLocally(request, this.getUploadDir());
			String msg = b? "上传成功！": "上传失败";
			this.forwardToResultPage(request, response, msg);
		}else{
			String command = request.getParameter("command");
			switch(command){
			case "show":{
				
				request.getRequestDispatcher(this.getJspPath("upload-file.jsp")).forward(request,response);
				break;
			}
			default:
				break;
			}
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		this.doPost(request, response);
	}
	
	public void init() throws ServletException {
		super.init();
	}
}
