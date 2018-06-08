package com.sttri.servlet;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sttri.util.CookiesUtil;

/**
 * 程序创建时间：2010-9-9 - 上午11:13:55
 * 程序开发者：朱洪
 * 程序修改者：
 * 程序修改时间：2010-9-9 - 上午11:13:55
 * 程序作用：
 * 1、
 * 2、
 * 程序修改：
 * 1、
 * 2、
 * @version 0.01
 */
public class ValidateNumbeByCookies extends HttpServlet {
	/**
	 * 
	 * @author :朱洪
	 * @dateTime :@2010-9-9 -- 上午11:14:35
	 */
	private static final long serialVersionUID = 2073720864734330754L;
	private final String CONTENT_TYPE = "image/png";
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        
        String sign = request.getParameter("sign");
        if (sign == null) {
        	CookiesUtil.Delete("ValidateNumber",request,response);
            return;
        } else {
            if (sign.length() != 40) {
            	CookiesUtil.Delete("ValidateNumber",request,response);
                return;
            }
        }
        // 在内存中创建图象
        int width = 75, height = 20;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文
        Graphics2D g = image.createGraphics();

        //生成随机类
        Random random = new Random();

        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        //设定字体
        //g.setFont(new Font("Mistral", Font.PLAIN, 18));
        //g.setFont(new Font("Trebuchet MS", Font.PLAIN ITALIC, 18));
        g.setFont(new Font("Times New Roman",Font.PLAIN,18));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.99f));

        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // 取随机产生的认证码(4位数字)
        final String cNumber[]= {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","F","G","H","I","J","L","K","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        String sRand = "";
        for (int i = 0; i < 4; i++) {
        	String rand = String.valueOf(cNumber[random.nextInt(35)]);
        	sRand += rand;
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110),20 + random.nextInt(110),20 + random.nextInt(110)));
            //调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(rand, 18 * i + 3, 16);
        }
        CookiesUtil.setString("ValidateNumber", sRand, request, response);
        // 图象生效
        g.dispose();
        javax.imageio.ImageIO.write(image, "JPEG", response.getOutputStream());
	}

	public Color getRandColor(int fc, int bc) { //给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doGet(request, response);
	}
}
