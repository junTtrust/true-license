package com.license.App.util;

import java.net.InetAddress;
import java.net.NetworkInterface;

import org.springframework.stereotype.Service;

@Service
public class Licensemac {
	public  String  getLocalMac(InetAddress ia){
		try {
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
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
