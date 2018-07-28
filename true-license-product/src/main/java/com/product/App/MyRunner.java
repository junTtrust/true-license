package com.product.App;

import java.io.File;
import java.net.InetAddress;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.product.util.HttpClientUtils;
import com.product.util.IpMacUtils;

@Component
public class MyRunner implements CommandLineRunner {
    @Override
    public void run(String... strings) throws Exception {
    	
        System.out.println("----------------项目启动了1111-----------------");
        //获取项目启动服务器ip/mac
        String localIp = IpMacUtils.getLocalIp();//ip
        InetAddress ia = InetAddress.getLocalHost();
        String localMac = IpMacUtils.getLocalMac(ia);//mac
        
        String property = System.getProperty("user.dir");//项目路径
		property=property.replaceAll("\"", "/");
		String path=property+"/src/main/resources/license.lic";
		System.out.println("--------------------文件路径---------------------"+path);
		File file = new File(path);
		
		if(file.exists()){
			String name = file.getName();
			System.out.println("---------------------文件名-------------"+name);
			
			Map<String,String> uploadParams = new LinkedHashMap<String, String>();
			uploadParams.put("ContentType", "file");
			uploadParams.put("FileName", "license.lic");
			uploadParams.put("localIp", localIp);
			uploadParams.put("localMac", localMac);
			String uploadFileImpl = HttpClientUtils.getInstance().uploadFileImpl(
					"http://192.168.3.55:8098/licenseVerify/verify", path,
					"fileName", uploadParams);
			System.out.println("------------返回结构----------------"+uploadFileImpl);
			
			JSONObject vJSONObject = HttpClientUtils.vJSONObject(uploadFileImpl);
			if(vJSONObject!=null){
				String msg = vJSONObject.getString("msg");
				String status = vJSONObject.getString("status");
				if("证书校验失败".equals(msg) || "500".equals(status)){
					throw new Exception();
				}
			}
		}
    }
}
