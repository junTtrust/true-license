package com.product.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

public class IpMacUtils {
	public static void main(String[] args) {
		try {

			InetAddress addr = InetAddress.getLocalHost();  
	         String ip=addr.getHostAddress().toString(); //获取本机ip  
	         String hostName=addr.getHostName().toString(); //获取本机计算机名称  
	         System.out.println(ip);
	         System.out.println(hostName);
		
	         
	         InetAddress ia = InetAddress.getLocalHost();
	 		System.out.println(ia);
	 		getLocalMac(ia);
	 		
	 		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取ip
	 * @return
	 * @throws SocketException
	 */
	public static String getLocalIp() throws SocketException {
		try {
			InetAddress addr = InetAddress.getLocalHost();  
	        String ip=addr.getHostAddress().toString(); //获取本机ip  
	        System.out.println(ip);
	        return ip;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取mac
	 * @param ia
	 * @throws SocketException
	 */
	public static String getLocalMac(InetAddress ia) throws SocketException {
		// TODO Auto-generated method stub
		//获取网卡，获取地址
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		System.out.println("mac数组长度："+mac.length);
		StringBuffer sb = new StringBuffer("");
		for(int i=0; i<mac.length; i++) {
			if(i!=0) {
				sb.append("-");
			}
			//字节转换为整数
			int temp = mac[i]&0xff;
			String str = Integer.toHexString(temp);
			System.out.println("每8位:"+str);
			if(str.length()==1) {
				sb.append("0"+str);
			}else {
				sb.append(str);
			}
		}
		System.out.println("本机MAC地址:"+sb.toString().toUpperCase());
		return sb.toString().toUpperCase();
	}


}
