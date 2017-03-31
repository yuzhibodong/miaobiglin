package com.weixin.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WeiXinChecker {

	private static HttpServletRequest request;
	private static HttpServletResponse response;
	
	
	public static void Check(HttpServletRequest request,HttpServletResponse response,String token)
	{
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String signature = request.getParameter("signature");
		String echostr = request.getParameter("echostr");
		
		String[] arr = new String[]{token,timestamp,nonce};
		Arrays.sort(arr);
		StringBuffer content = new StringBuffer();
		for (String string : arr) {
			content.append(string);
		}
		String temp = GetSha1(content.toString());
		
		if (temp.equalsIgnoreCase(signature)) {
			PrintWriter printWriter=null;
			try {
				printWriter = response.getWriter();
				printWriter.print(echostr);
				System.out.println("checked......");
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally
			{
				if (printWriter!=null) {
					printWriter.close();
				}
			}
		}
		
	}
	
	
	
	private static String GetSha1(String str){
		   if (null == str || 0 == str.length()){
		       return null;
		   }
		   char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
		           'a', 'b', 'c', 'd', 'e', 'f'};
		   try {
		       MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
		       mdTemp.update(str.getBytes("UTF-8"));
		        
		       byte[] md = mdTemp.digest();
		       int j = md.length;
		       char[] buf = new char[j * 2];
		       int k = 0;
		       for (int i = 0; i < j; i++) {
		           byte byte0 = md[i];
		           buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
		           buf[k++] = hexDigits[byte0 & 0xf];
		       }
		       return new String(buf);
		   } catch (Exception e) {
		       return null;
		   }
		}
	
	
}
