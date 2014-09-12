package com.iamnige.hadoop.hdfs;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import com.iamnige.logger.Logger;

public class DfsFileDealer {
	private static DfsFileDealer dealer;
	DfsConf dc;
	private DfsFileDealer(DfsConf dcs){
		dc = dcs;
		
	}
	
	public static DfsFileDealer getDealer(DfsConf conf){
		if(null == dealer){
			dealer = new DfsFileDealer(conf);
			return dealer;
		}
		return dealer;
	}
	
	
    public boolean createFile(String dst , byte[] contents){
        Configuration conf = new Configuration();
        FileSystem fs;
		try {
			fs = FileSystem.get(conf);
			Path dstPath = new Path(dc.getLocation() + dst);
	        FSDataOutputStream outputStream = fs.create(dstPath);
	        outputStream.write(contents);
	        outputStream.close();
	        fs.close();
	        Logger.logTrace("Create File successful. Path : " + dc.getLocation() + dst);
		} catch (IOException e) {
			Logger.logError("Create File Failed. Path : " + dc.getLocation() + dst);
			e.printStackTrace();
		} 
        return true;
    }
    
    @SuppressWarnings("deprecation")
	public void uploadFile(String src,String dst){
        Configuration conf = new Configuration();
        FileSystem fs;
		try {
			fs = FileSystem.get(conf);
			Path srcPath = new Path(src);
	        Path dstPath = new Path(dc.getLocation() + dst);
	        if(fs.isDirectory(dstPath)){
	        	String s [] = src.split("\\\\|/");
	        	String fileName  = s[s.length - 1];
	        	dstPath = new Path(dc.getLocation() + dst + "/" + fileName);
	        }
	        fs.copyFromLocalFile(false,srcPath, dstPath);
	        Logger.logTrace("Upload File successful. Source Path : " + src + ". Destination Path : " + dc.getLocation() + dst);
	        fs.close();
		} catch (IOException e) {
			Logger.logError("Upload File Failed. Source Path : " + src + ". Destination Path : " + dc.getLocation()  + dst);
			e.printStackTrace();
		} 
    }
    
    //文件重命名
    public void rename(String oldName,String newName){
        Configuration conf = new Configuration();
        FileSystem fs;
		try {
			fs = FileSystem.get(conf);
			Path oldPath = new Path(dc.getLocation() + oldName);
	        Path newPath = new Path(dc.getLocation() + newName);
	        boolean isok = fs.rename(oldPath, newPath);
	        if(isok){
	        	Logger.logTrace("Rename File successful. Old Path : " + dc.getLocation() +  oldName + " New path : " + dc.getLocation() + newName);
	        }else{
	            Logger.logError("Rename File Failed. Old Path : " + dc.getLocation() + oldName + " New path : " + dc.getLocation() + newName);
	        }
	        fs.close();
		} catch (IOException e) {
			Logger.logError("Rename File Failed. Old Path : " + dc.getLocation() + oldName + " New path : " + dc.getLocation() + newName);
			e.printStackTrace();
		}
    }
    
    //删除文件
    public void delete(String filePath){
        Configuration conf = new Configuration();
        FileSystem fs;
		try {
			fs = FileSystem.get(conf);
			Path path = new Path(dc.getLocation() + filePath);
	        boolean isok = fs.deleteOnExit(path);
	        if(isok){
	        	Logger.logTrace("Delete File successful. Path : " + dc.getLocation() + filePath);
	        }else{
	        	Logger.logError("Delete File Failed. Path : " + dc.getLocation() + filePath);
	        }
	        fs.close();
		} catch (IOException e) {
			Logger.logError("Delete File Failed. Path : " + dc.getLocation() + filePath);
			e.printStackTrace();
		} 
    }
    
    //创建目录
    public void mkdir(String path){
        Configuration conf = new Configuration();
        FileSystem fs;
        Path srcPath = null;
		try {
			fs = FileSystem.get(conf);
			srcPath = new Path(dc.getLocation() + path);
			if(fs.isFile(srcPath)){
				Logger.logError("Mkdir Failed. Path is a file, already exists: " + srcPath);
				return;
			}
			if(fs.exists(srcPath)){
				Logger.logTrace("Mkdir successful. Path is a directory, already exists: " + srcPath);
				return;
			}
	        boolean isok = fs.mkdirs(srcPath);
	        if(isok){
	        	Logger.logTrace("Mkdir successful. Path : " + srcPath);
	        }else{
	        	Logger.logError("Mkdir Failed. Path : " + srcPath);
	        }
	        fs.close();
		} catch (IOException e) {
			Logger.logError("Mkdir File Failed. Path : " + srcPath);
			e.printStackTrace();
		} 
    }
    
    //读取文件的内容
    public InputStream readFile(String filePath){
        Configuration conf = new Configuration();
        InputStream in = null;
        Path srcPath = null;
        try {
        	FileSystem fs = FileSystem.get(conf);
            srcPath = new Path(dc.getLocation() + filePath);
            in = fs.open(srcPath);
            return in;
        } catch (IOException e) {
        	Logger.logError("Read File Failed. Path : " + srcPath);
			e.printStackTrace();
		} 
        return null;
    }
     
    /*public static void main(String[] args) throws IOException {
    	DfsConstant dfs = new DfsConstant();
    	dfs.setLocation("hdfs://192.168.1.11:9000");
    	DfsFileDealer dealer = new DfsFileDealer(dfs);
        //测试上传文件
        //测试创建文件
        //byte[] contents =  "hello world 世界你好\n".getBytes();
        //dealer.createFile("/user/hadoop/test1/d.txt",contents);
        
        //测试删除文件
        //dealer.delete("/user/hadoop/test1/dd.txt"); //使用相对路径
        //dealer.delete("/user/hadoop/test1/");    //删除目录
        //测试新建目录
    	//System.out.println(p.getParent());
        dealer.mkdir("/user/hadoop/test1");
        //测试读取文件
        dealer.uploadFile("D:\\picture\\壁纸\\51d24328c2634.jpg", "/user/hadoop/test1");
        InputStream stream = dealer.readFile("/user/hadoop/test1/51d24328c2634.jpg");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line = "";
        while((line = reader.readLine()) != null){
        	System.out.println(line);
        }
        stream.close();
    }*/
    
}
