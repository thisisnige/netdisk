package com.iamnige.logger;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class Logger {
	private static int FINE = 1;
	private static int INFO = 2;
	private static int WARNING = 3;
	private static int SEVERE = 4;
	private static java.util.logging.Logger logger;
	private static String logPath = "logs/log.txt";
	public static void logTrace(String msg){
		log(FINE, msg);
	}
	
	public static void logInfo(String msg){
		log(INFO, msg);
	}
	
	public static void logWarning(String msg){
		log(SEVERE, msg);
	}
	
	public static void logError(String msg){
		System.out.println(msg);
		log(WARNING, msg);
	}
	
	public static void setContextLogPath(String s){
		if(s.equals("logs/log.txt"))
			logPath = s + "/" + logPath;
	}
	private static java.util.logging.Logger getLogger(){
		if(null == logger){
			logger =  java.util.logging.Logger.getLogger(Logger.class.getName());
			createLogFile();
			try {
				File f = new File(logPath);
				FileHandler fh = new FileHandler(f.toString(), true);
				fh.setFormatter(new SimpleFormatter());
				logger.addHandler(fh);
				ConsoleHandler ch = new ConsoleHandler();
				ch.setLevel(Level.FINEST);
				logger.addHandler(ch);
				logger.setLevel(Level.FINEST);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return logger;
		}else{
			return logger;
		}
	}
	
	private static void createLogFile(){
		String[] pathArr = logPath.split("/");
		String path = "";
		File f = new File(logPath);
		if(!f.exists()){
			for(int i = 0; i < pathArr.length - 1; i++){
				path = path + pathArr[i] + "/";
				f = new File(path);
				if(!f.exists()){
					f.mkdir();
				}
			}
			path = path + File.separator + pathArr[pathArr.length - 1];
			f = new File(path);
			try {
				f.createNewFile();
			} catch (IOException e) {
				System.out.println("Can't Create Log File : " + logPath);
				e.printStackTrace();
			}
		}
	}
	
	private static void log(int level, String msg){
		java.util.logging.Logger logger = getLogger();
		switch(level){
		case 1:
			logger.fine(msg);
			break;
		case 2:
			logger.info(msg);
			break;
		case 3:
			logger.warning(msg);
			break;
		case 4:
			logger.severe(msg);
			break;
		default:
			break;
		}
	}
}
