package com.iamnige.web.netdisk.model;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.iamnige.database.CommonDBModel;
import com.iamnige.database.DBField;
import com.iamnige.database.DBFieldType;
import com.iamnige.database.Operator;
import com.iamnige.lib.Md5Digest;
import com.iamnige.logger.Logger;
import com.iamnige.web.netdisk.servlet.CommonServlet;

public class CommonFileModel {
	CommonDBModel model;
	private final String tbName = "user_directory";
	public CommonFileModel(){
		model = new CommonDBModel();
		model.setTbName(tbName);
	}
	public boolean storeFileLocally(HttpServletRequest request, String dirPath){
		DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            @SuppressWarnings("rawtypes")
			List items = upload.parseRequest(request);
            @SuppressWarnings("rawtypes")
			Iterator itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                if (item.isFormField()) {
                	
                } else {
                    if (item.getName() != null && !item.getName().equals("")) {
                        System.out.println("上传文件大小:" + item.getSize());
                        System.out.println("上传文件类型:" + item.getContentType());
                        System.out.println("上传文件名称:" + item.getName());                   
                        File tempFile = new File(item.getName());   
                        File file = new File(dirPath, tempFile.getName());
                        item.write(file);
                        if(!storeFileInfo(request, item, file)){
                        	return false;
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
        	Logger.logError(e.getMessage());
            return false;
        }
        return true;
	}
	
	public boolean storeFileInfo(HttpServletRequest request, FileItem item, File f){
		String fileName = item.getName();
		CommonSessionModel sm;
		try {
			sm = new CommonSessionModel(request.getSession());
		} catch (Exception e) {
			return false;
		}
		long uid = (Long)sm.get(CommonSessionModel.UID);
		long parentID = getPid(request);
		if(isFileRepeated(parentID, uid, fileName)){
			return false;
		}
		Timestamp ts = getCurrentTS();
		Timestamp updateTS = ts;
		int isDir = isDir(request) ? 1 : 0;
		long fileSize = item.getSize();
		String dig = "";
		if(!isDir(request)){
			try {
				 dig = Md5Digest.fileMD5(f);
			} catch (NoSuchAlgorithmException | IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		ArrayList<DBField> condition = new ArrayList<DBField>();
		condition.add(new DBField("fname", fileName, DBFieldType.STRING));
		condition.add(new DBField("uid", uid, DBFieldType.LONG));
		condition.add(new DBField("parent_id", parentID, DBFieldType.LONG));
		condition.add(new DBField("update_time", updateTS, DBFieldType.TIMESTAMP));
		condition.add(new DBField("create_time", ts, DBFieldType.TIMESTAMP));
		condition.add(new DBField("is_dir", isDir, DBFieldType.INT));
		condition.add(new DBField("file_size", fileSize, DBFieldType.LONG));
		condition.add(new DBField("digest", dig, DBFieldType.STRING));
		try{
			return model.insert(condition);
		}catch(Exception e){
			return false;
		}
	}
	
	private boolean isFileRepeated(long pid, long uid, String fname){
		ArrayList<DBField> condition = new ArrayList<DBField>();
		condition.add(new DBField("parent_id", pid, DBFieldType.LONG));
		condition.add(new DBField("uid", uid,  DBFieldType.LONG));
		condition.add(new DBField("fname", fname, DBFieldType.STRING));
		ArrayList<DBField> select = new ArrayList<DBField>();
		select.add(new DBField("fid", DBFieldType.LONG));
		ArrayList<ArrayList<DBField>> ret = null;
		try {
			ret = model.query(select, condition, "");
		} catch (Exception e) {
			return true;
		}
		if(ret.size() > 0){
			Logger.logError("File repeated!" + uid + " " + pid + " " + fname);
			return true;
		}
		return false;
	}
	
	private boolean isDir(HttpServletRequest request){
		String isDir = request.getParameter("is_dir");
		if(isDir == null){
			return false;
		}
		int isD = Integer.parseInt(request.getParameter("is_dir"));
		return 1 == isD ? true : false;
	}
	
	private Long getPid(HttpServletRequest request){
		String parentID = request.getParameter("pid");
		if(null == parentID){
			return (long) -1;
		}
		return Long.parseLong(parentID);
	}
	
	public static Timestamp getCurrentTS(){
		return new Timestamp(System.currentTimeMillis());
	}
	
	public ArrayList<ArrayList<DBField>> getUploaderFile(String uname){
		LoginAndAuthModel authModel = new LoginAndAuthModel();
		Long uid = authModel.getUidByUname(uname);
		ArrayList<DBField> select = new ArrayList<DBField>();
		select.add(new DBField("fid", DBFieldType.LONG));
		select.add(new DBField("fname", DBFieldType.STRING));
		select.add(new DBField("parent_id", DBFieldType.LONG));
		select.add(new DBField("create_time", DBFieldType.LONG));
		select.add(new DBField("is_dir", DBFieldType.INT));
		select.add(new DBField("file_size", DBFieldType.LONG));
		select.add(new DBField("hdfs_id", DBFieldType.LONG));
		ArrayList<DBField> condition = new ArrayList<DBField>();
		condition.add(new DBField("uid", (Object)uid, DBFieldType.LONG));
		ArrayList<ArrayList<DBField>> ret;
		try {
			ret = model.query(select, condition, "");
		} catch (Exception e) {
			return null;
		}
		return ret;
	}
}
