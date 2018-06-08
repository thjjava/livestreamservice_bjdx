package com.sttri.util;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.File;
import java.io.IOException;

public class FtpUtil {
	private static FTPClient client = new FTPClient();

	public static boolean connectToServer() throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException {
		try {
			String server = "202.109.75.157";
			String user = "view";
			String password = "123456";
//			String path = "/home/view/";
			client.connect(server);
			client.login(user, password);
//			client.changeDirectory(path);
//			if (client.isCompressionSupported()) {
//			 	client.setCompressionEnabled(true);
//			}
			System.out.println("connect...success!");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void closeConnect() {
		try {
			client.disconnect(true);
			System.out.println("shut...down!");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			e.printStackTrace();
		} catch (FTPException e) {
			e.printStackTrace();
		}
	}

	public static void upload(String path) throws IllegalStateException, FTPIllegalReplyException, FTPException {
		try {
			boolean con=connectToServer();
			if(con){
				File srcFile = new File(path); 
				 try {
		            client.upload(srcFile);
		            System.out.println("upload...success!");
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					closeConnect();
				}
			}
		} catch (IOException ex) {
			System.out.println("upload...fail!");
			System.out.println(ex);
		}
	}

	public static void main(String[] args) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException {
		upload("C:/Users/Administrator/Desktop/miag_dev_company.sql");
	}
}
