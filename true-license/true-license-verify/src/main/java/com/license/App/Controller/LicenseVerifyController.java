package com.license.App.Controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import zlicense.verify.VerifyLicense;

@Controller
@RequestMapping(value = "/licenseVerify")
public class LicenseVerifyController {

	@RequestMapping(value = "/verify")
	@ResponseBody
	public String verify(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("fileName") MultipartFile file,
			@RequestParam("localIp") String localIp,@RequestParam("localMac") String localMac) {
		
		JSONObject resjon = new JSONObject();
		if (file.isEmpty()) {
			resjon.put("success", "false");
			resjon.put("msg", "上传文件失败");
			return resjon.toJSONString();
		}
//		String path = "D:/workspace/javaLicense/true-license/true-license-verify/src/main/resources/license/";
		String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date())+".lic";
		
		//上传文件路径
		String property = System.getProperty("user.dir");//项目路径
		property=property.replaceAll("\"", "/");
		String path=property+"/src/main/webapp/WEB-INF/license/";
//		String path=property+"/src/main/resources/license/";
		System.out.println("------------------------上传路径-------------------"+path);
		File dest = new File(path + fileName);
		if (!dest.getParentFile().exists()) { // 判断文件父目录是否存在
			dest.getParentFile().mkdir();
		}
		try {
			file.transferTo(dest); // 保存文件
			// 安装校验
			Boolean licenvi = licenvi("verifyparam.properties", fileName,localIp,localMac);
			resjon.put("success", "true");
			if(true){
				resjon.put("msg", "证书校验成功");
			}else{
				resjon.put("msg", "证书校验失败");
			}
			return resjon.toJSONString();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resjon.put("success", "false");
			resjon.put("msg", "上传文件失败");
			return resjon.toJSONString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resjon.put("success", "false");
			resjon.put("msg", "上传文件失败");
			return resjon.toJSONString();
		}
	}

	public Boolean licenvi(String propertiesPath, String fileName,String localIp,String localMac) {
		VerifyLicense vLicense = new VerifyLicense();
		try {
			vLicense.setParam1(propertiesPath, fileName);
			boolean verify = vLicense.verify(localIp,localMac);
			return verify;
		} catch (Exception er) {
			er.printStackTrace();
		}
		return false;
	}
	
	private static HashSet<Object> hashSet = new HashSet<>();
	public static void main(String[] args) {
//		System.out.println(System.getProperty("user.dir"));
		for(int i=0;i<20;i++){
			if(!hashSet.contains(i)){
				hashSet.add(i);
			}
			System.out.println("==================hashSet  size============"+hashSet.size());
		}
	}

}
