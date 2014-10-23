package com.iamnige.web.netdisk.model;

import javax.servlet.http.HttpSession;

import com.iamnige.logger.Logger;

public class CommonSessionModel {
	private int interval = 3600;
	private HttpSession session;
	
	public static String USER = "username";
	public static String UID = "uid";
	public CommonSessionModel(HttpSession session) throws Exception{
		if(session == null){
			Logger.logError("Session don't exists!");
			throw new Exception("Session don't exists!");
		}
		this.session = session;
	}
	
	public void set(String key, Object value){
		session.setMaxInactiveInterval(this.interval);
		session.setAttribute(key, value);
	}
	
	public Object get(String key){
		return session.getAttribute(key);
	}
}
