package com.sttri.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

public class SocketUtils {
	static Socket socket = null;
	
	public static boolean getSocket(String ip,int port){
		boolean flag = false;
		//1.建立连接
		try {
			socket = new Socket(ip, port);
			 // 设置读取超时时间  
            socket.setSoTimeout(5 * 1000);
            //2、获取输出流，向服务器端发送信息
			OutputStream os = socket.getOutputStream();//字节输出流
			PrintWriter pw =new PrintWriter(os);//将输出流包装成打印流
			pw.write("connection\r\n");
			pw.flush();
			
			//3、获取输入流，并读取服务器端的响应信息
			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String result = br.readLine();
			if ("RTSP/1.0 400 Bad Request".equals(result)) {
				flag = true;
			}
			//4、关闭资源
			br.close();
			is.close();
			pw.close();
			os.close();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			 System.out.println(ip+"== socket is UnknownHostException");
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			 System.out.println(ip+"== socket is error!");
			e1.printStackTrace();
		}finally{
			if (socket != null) {
                try {
                    socket.close();
                    System.out.println(ip+"==socket is closed");
                } catch (IOException e) {
                    socket = null;
                    System.out.println("客户端 finally 异常:" + e.getMessage());
                }
            }
		}
		return flag;
	}
	
	public static void main(String[] args) {
		String url = "http://livevideo.189.cn:8082";
		try {
			URL url1 = new URL(url.replace("rtmp", "http").replace("rtsp", "http"));
			System.out.println(url1.getHost());
			System.out.println(url1.getPort());
			System.out.println(getSocket(url1.getHost(), url1.getPort()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
