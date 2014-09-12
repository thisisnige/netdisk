package com.iamnige.web.netdisk.model;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.iamnige.database.CommonDBModel;
import com.iamnige.database.DBField;
import com.iamnige.database.DBFieldType;
import com.iamnige.database.Operator;
import com.iamnige.lib.Md5Digest;
import com.iamnige.logger.Logger;

public class LoginAndAuthModel {
	CommonDBModel model;
	private final String tbName = "user";
	public LoginAndAuthModel(){
		model = new CommonDBModel();
		model.setTbName(this.tbName);
	}
	public boolean isLogin(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session.getAttribute(CommonSessionModel.USER) == null){
			return false;
		}
		return true;
	}
	
	public boolean registerUser(String username, String passwd){
		ArrayList<DBField> select = new ArrayList<DBField>();
		select.add(new DBField("uid", DBFieldType.LONG));
		ArrayList<DBField> condition = new ArrayList<DBField>();
		condition.add(new DBField("uname", (Object)username, DBFieldType.STRING, Operator.EQUAL));
		try {
			condition.add(new DBField("passwd", (Object)Md5Digest.md5(passwd), DBFieldType.STRING, Operator.EQUAL));
			ArrayList<ArrayList<DBField>> ret = model.query(select, condition, "");
			if(ret.size() > 0){
				String errMsg = "User already exists! Username : " + username;
				Logger.logError(errMsg);
				throw new Exception(errMsg);
			}
			model.insert(condition);			
		} catch (NoSuchAlgorithmException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean isUserExists(String username, String passwd){
		ArrayList<DBField> select = new ArrayList<DBField>();
		select.add(new DBField("uid", DBFieldType.LONG));
		ArrayList<DBField> condition = new ArrayList<DBField>();
		condition.add(new DBField("uname", (Object)username, DBFieldType.STRING, Operator.EQUAL));
		try {
			condition.add(new DBField("passwd", (Object)Md5Digest.md5(passwd), DBFieldType.STRING, Operator.EQUAL));
			ArrayList<ArrayList<DBField>> ret;
			try {
				ret = model.query(select, condition, "");
			} catch (Exception e) {
				return false;
			}
			return ret.size() > 0;
		} catch (NoSuchAlgorithmException e) {
			return false;
		}
	}
	
	public Long getUidByUname(String uname){
		ArrayList<DBField> select = new ArrayList<DBField>();
		select.add(new DBField("uid", DBFieldType.LONG));
		ArrayList<DBField> condition = new ArrayList<DBField>();
		condition.add(new DBField("uname", (Object)uname, DBFieldType.STRING, Operator.EQUAL));
		ArrayList<ArrayList<DBField>> ret = null;
		try {
			ret = model.query(select, condition, "");
		} catch (Exception e) {
			return (long) -1;
		}
		if(ret.size() > 0){
			return (Long)ret.get(0).get(0).value;
		}else{
			return (long) 0;
		}
	}
}
