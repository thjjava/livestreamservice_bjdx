package com.sttri.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

@SuppressWarnings("all")
public class HttpUtil {	
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 建立实际的连接
            connection.connect();
            /*
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            */
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 获取图片
     * @param adds	图片地址
     * @param param 参数name1=s&name2=s
     * @param purl	图片保存地址
     */
    public static void getImage(String adds, String param, String purl) {
    	try {
    		//new一个URL对象
    		URL url = new URL(adds+"?"+param);
    		//打开链接
    		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    		//设置请求方式为"GET"
    		conn.setRequestMethod("GET");
    		//超时响应时间为5秒
    		conn.setConnectTimeout(5 * 1000);
    		//通过输入流获取图片数据
    		InputStream inStream = conn.getInputStream();
    		//得到图片的二进制数据，以二进制封装得到数据，具有通用性
    		byte[] data = ImageUtil.readInputStream(inStream);
    		//new一个文件对象用来保存图片，默认保存当前工程根目录
    		File imageFile = new File(purl);
    		//创建输出流
    		FileOutputStream outStream = new FileOutputStream(imageFile);
    		//写入数据
    		outStream.write(data);
    		//关闭输出流
    		outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**  
     * 发送xml数据请求到server端  
     * @param url xml请求数据地址  
     * @param xmlString 发送的xml数据流  
     * @return null发送失败，否则返回响应内容  
     */    
    public String post(String url,String xmlFileName){    
        //关闭   
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");     
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");     
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");    
          
        //创建httpclient工具对象   
        HttpClient client = new HttpClient();    
        //创建post请求方法   
        PostMethod myPost = new PostMethod(url);    
        //设置请求超时时间   
        client.setConnectionTimeout(300*1000);  
        String responseString = null;    
        try{    
            //设置请求头部类型   
            myPost.setRequestHeader("Content-Type","text/xml");  
            myPost.setRequestHeader("charset","utf-8");  
              
            //设置请求体，即xml文本内容，注：这里写了两种方式，一种是直接获取xml内容字符串，一种是读取xml文件以流的形式   
//          myPost.setRequestBody(xmlString);   
              
            InputStream body=this.getClass().getResourceAsStream("/"+xmlFileName);  
            myPost.setRequestBody(body);  
//            myPost.setRequestEntity(new StringRequestEntity(xmlString,"text/xml","utf-8"));     
            int statusCode = client.executeMethod(myPost);    
            if(statusCode == HttpStatus.SC_OK){    
                BufferedInputStream bis = new BufferedInputStream(myPost.getResponseBodyAsStream());    
                byte[] bytes = new byte[1024];    
                ByteArrayOutputStream bos = new ByteArrayOutputStream();    
                int count = 0;    
                while((count = bis.read(bytes))!= -1){    
                    bos.write(bytes, 0, count);    
                }    
                byte[] strByte = bos.toByteArray();    
                responseString = new String(strByte,0,strByte.length,"utf-8");    
                bos.close();    
                bis.close();    
            }    
        }catch (Exception e) {    
            e.printStackTrace();    
        }    
        myPost.releaseConnection();    
        return responseString;    
    }
    
    /**
     * xml文本信息发送请求
     * @param urlStr	访问地址
     * @param xmlInfo	xml格式文本
     */
    public static String xmlPost(String urlStr, String xmlInfo) {  
        String line = "";  
        try {  
            URL url = new URL(urlStr);  
            URLConnection con = url.openConnection();  
            con.setDoOutput(true);  
            con.setRequestProperty("Pragma:", "no-cache");  
            con.setRequestProperty("Cache-Control", "no-cache");  
            con.setRequestProperty("Content-Type", "text/xml");  
  
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(new String(xmlInfo.getBytes("UTF-8")));  
            out.flush();  
            out.close();  
            BufferedReader br = new BufferedReader(new InputStreamReader(con  
                    .getInputStream()));  
            for (line = br.readLine(); line != null; line = br.readLine()) {  
                line += line;
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return line;
    }  
  
    private static String getXmlInfo() {  
        StringBuilder sb = new StringBuilder();  
        sb.append("<?xml version='1.0' encoding='UTF-8'?>");  
        sb.append("<Message>");  
        sb.append(" <header>");  
        sb.append("     <action>readMeetingStatus</action>");  
        sb.append("     <service>meeting</service>");  
        sb.append("     <type>xml</type>");  
        sb.append("     <userName>admin</userName>");  
        sb.append("     <password>admin</password>");  
        sb.append("     <siteName>box</siteName>");  
        sb.append(" </header>");  
        sb.append(" <body>");  
        sb.append("     <confKey>43283344</confKey>");  
        sb.append("     <confKey1></confKey1>");  
        sb.append(" </body>");  
        sb.append("</Message>");  
        
        return sb.toString();  
    }
    
    /** 
     * 上传图片 
     *  
     * @param urlStr 
     * @param textMap 
     * @param fileMap 
     * @return 
     */ 
    public static String formUpload(String urlStr, Map<String, String> textMap,  
            Map<String, String> fileMap) {  
        String res = "";  
        HttpURLConnection conn = null;  
        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符  
        try {  
            URL url = new URL(urlStr);  
            conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setReadTimeout(30000);  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setUseCaches(false);  
            conn.setRequestMethod("POST");  
            conn.setRequestProperty("Connection", "Keep-Alive");  
            //conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);  
   
            OutputStream out = new DataOutputStream(conn.getOutputStream());  
            // text  
            if (textMap != null) {  
                StringBuffer strBuf = new StringBuffer();  
                Iterator iter = textMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry entry = (Map.Entry) iter.next();  
                    String inputName = (String) entry.getKey();  
                    String inputValue = (String) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append(  
                            "\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\"" 
                            + inputName + "\"\r\n\r\n");  
                    strBuf.append(inputValue);  
                }  
                out.write(strBuf.toString().getBytes());  
            }  
   
            // file  
            if (fileMap != null) {  
                Iterator iter = fileMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry entry = (Map.Entry) iter.next();  
                    String inputName = (String) entry.getKey();  
                    String inputValue = (String) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    File file = new File(inputValue);  
                    String filename = file.getName();  
                    String contentType = new MimetypesFileTypeMap()  
                            .getContentType(file);  
                    String fname = filename.toLowerCase();
                    if (fname.endsWith(".png")) {  
                        contentType = "image/png";  
                    }
                    if (fname.endsWith(".jpeg")||fname.endsWith(".jpg")||fname.endsWith(".jpe")) {  
                        contentType = "image/jpeg";  
                    }
                    if (contentType == null || contentType.equals("")) {  
                        contentType = "application/octet-stream";  
                    }
   
                    StringBuffer strBuf = new StringBuffer();  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append(  
                            "\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\"" 
                            + inputName + "\"; filename=\"" + filename  
                            + "\"\r\n");  
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");  
   
                    out.write(strBuf.toString().getBytes());  
   
                    DataInputStream in = new DataInputStream(  
                            new FileInputStream(file));  
                    int bytes = 0;  
                    byte[] bufferOut = new byte[1024];  
                    while ((bytes = in.read(bufferOut)) != -1) {  
                        out.write(bufferOut, 0, bytes);  
                    }  
                    in.close();  
                }  
            }  
   
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();  
            out.write(endData);  
            out.flush();  
            out.close();  
   
            // 读取返回数据  
            StringBuffer strBuf = new StringBuffer();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;  
            while ((line = reader.readLine()) != null) {  
                strBuf.append(line).append("\n");  
            }  
            res = strBuf.toString();  
            reader.close();  
            reader = null;  
        } catch (Exception e) {  
            System.out.println("发送POST请求出错。" + urlStr);  
            e.printStackTrace();  
        } finally {  
            if (conn != null) {  
                conn.disconnect();  
                conn = null;  
            }  
        }  
        return res;  
    }
    
    public static void main(String[] args) {
    	//System.out.println(xmlPost("http://localhost:8888/livestreamservice/server_recordStart.do", getXmlInfo()));
    	String url = "https://oauth.api.189.cn/emp/oauth2/authorize";
    	String param = "app_id=305116030000243936&app_secret=b79af044e10b2811aef8eb94f1c742c6&response_type=token&redirect_uri=http://www.baidu.com&display=mobile";
    	url = "http://api.189.cn/upc/real/age_and_sex";
    	param = "app_id=305116030000243936&access_token=ee5cfd295f0933a446fcd72f040176c81435818728726&type=json";
    	//System.out.println(sendPost(url, param));
    	//url = "http://api.189.cn/MegviiInc./detection/detect";
    	//param = "app_id=305116030000243936&access_token=ee5cfd295f0933a446fcd72f040176c81435818728726";
    	url = "http://api.189.cn/upc/real/cellphone_and_province";
    	param = "app_id=305116030000243936&access_token=ee5cfd295f0933a446fcd72f040176c81435818728726&type=json";
    	url = "http://api.189.cn/v2/besttone/getAddrPositionInfo";
    	param = "app_id=305116030000243936&access_token=ee5cfd295f0933a446fcd72f040176c81435818728726&address=上海市浦东新区杨高南路5678号&rid=123&encode=UTF-8&timestamp="+Util.dateToStr(new Date());
    	url = "http://api.189.cn/MegviiInc./detection/detect";
    	//param = "app_id=305116030000243936&access_token=ee5cfd295f0933a446fcd72f040176c81435818728726&url=http://116.228.171.45:8083/syymanager/uploadFile/advert/20140430104248123456/20140430104248123456.jpg";
    	param = "app_id=305116030000243936&access_token=ee5cfd295f0933a446fcd72f040176c81435818728726&url=http://e.hiphotos.baidu.com/album/w%3D230/sign=b7edb539cefc1e17fdbf8b327a90f67c/a8ec8a13632762d00347a690a1ec08fa513dc6a0.jpg";
    	Map<String, String> paramMap = new HashMap<String, String>();
    	paramMap.put("app_id", "305116030000243936");
    	paramMap.put("access_token", "ee5cfd295f0933a446fcd72f040176c81435818728726");
    	
    	Map<String, String> fileMap = new HashMap<String, String>();
    	fileMap.put("img", "G:\\other\\qq\\32d9f488cddceea08f4d2be72c3714b7.jpg");
    	//System.out.println(sendGet(url, param));
    	//System.out.println(formUpload(url, paramMap, fileMap));
    	
    	url = "http://121.40.226.24:8080/eyahserver/dev/devUserModify";
    	paramMap = new HashMap<String, String>();
    	paramMap.put("mobile", "18900000001");
    	paramMap.put("devId", "1");
    	paramMap.put("devUser", "{\"age\":10,\"birthday\":\"2014-7-31\",\"icon\":\"uploadFile\\\\devuser\\\\icon\\\\2015073110431218025541.jpg\",\"id\":2,\"name\":\"eehdjjdjdjdjjssshh\",\"sex\":1}");
    	fileMap = new HashMap<String, String>();
    	//fileMap.put("upload", "G:\\other\\qq\\32d9f488cddceea08f4d2be72c3714b7.jpg");
    	
    	System.out.println(formUpload(url, paramMap, fileMap));
    	url = "http://api.189.cn/UploadLogo";
    	param = "app_id=305116030000243936&access_token=ee5cfd295f0933a446fcd72f040176c81435818728726&format=json&file_extension=.jpg";
    	url = "http://api.189.cn/openapi/services/share";
    	param = "app_id=305116030000243936&access_token=ee5cfd295f0933a446fcd72f040176c81435818728726&mdn=18901800027&url=http://www.baidu.com&content=aaaaaaaaaa&friends=18918834011";

    	url = "http://api.189.cn/huafeng/api/getforecast24";
    	param = "app_id=305116030000243936&access_token=ee5cfd295f0933a446fcd72f040176c81435818728726&city_id=0101260102";
    	url = "http://api.189.cn/v2/besttone/getRoundInfo";
    	param = "app_id=305116030000243936&access_token=ee5cfd295f0933a446fcd72f040176c81435818728726&city=0101260102&cenx=31.135395&ceny=121.542715&timestamp="+Util.dateToStr(new Date());
    	//param = "page=1&rows=10&geotype=1&cenx=121.23&ceny=31.23&range=3000&sort=0&app_id=305116030000243936&access_token=ee5cfd295f0933a446fcd72f040176c81435818728726&timestamp=2013-06-19+14%3A30%3A27&city=%25E4%25B8%258A%25E6%25B5%25B7%25E5%25B8%2582&featcode=%25E5%259C%25B0%25E6%25A0%2587&keyword=%25E5%25A4%25A7%25E5%258E%25A6";
    	//System.out.println(sendPost(url, param));
    	/*
    	//System.out.println(Xml2JsonUtil.xml2JSON(sendPost(url, param)));
    	System.out.println();
    	String ss = XmlConverUtil.xmltoJson(sendPost(url, param)).replace("@", "");
    	System.out.println(ss);
    	JSONObject obj = JSONObject.fromObject(XmlConverUtil.xmltoJson(sendPost(url, param)));
    	System.out.println(obj.optString("@name",""));
    	*/
	}
    
}