package com.license.App.Controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import zlicense.create.CreateLicense;

@Controller
@RequestMapping(value = "/licenseCreate")
public class LicenseCreateControler {

	@RequestMapping(value = "/create")
	@ResponseBody
	public ResponseEntity<byte[]> create(HttpServletRequest request, HttpServletResponse response) {
		try {
			String issuedtime = request.getParameter("issuedtime");// 发布日期
			String notbefore = request.getParameter("notbefore");// 开始日期
			String notafter = request.getParameter("notafter");// 截止日期
			// String subject = request.getParameter("subject");//项目唯一标识
			String ipaddress = request.getParameter("ipaddress");
			String macaddress = request.getParameter("macaddress");
			String consumertype = request.getParameter("consumertype");// 消费类型
			// String consumeramount = request.getParameter("consumeramount");//
			// 消费数量
			CreateLicense cLicense = new CreateLicense();
			cLicense.setParam1("createparam.properties", issuedtime, notbefore, notafter, ipaddress, macaddress,
					consumertype);
			String create1 = cLicense.create1();
			if (create1 != null) {
				System.out.println("-----------------license 文件路径------------------" + create1);
				return download(create1, response);
			} else {
				// 如果下载成功，删除源文件
				JSONObject rejson = new JSONObject();
				rejson.put("success", true);
				return (ResponseEntity<byte[]>) rejson.toJSONString().chars();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/downLoads")
	@ResponseBody
	public ResponseEntity<byte[]> downLoads(HttpServletResponse response) {
		String filename = "license.lic";
		String filePath = "D:/ftptest";
		// File file = new File(filePath + "/" + filename);
		File file = new File(
				"D:/workspace/javaLicense/true-license/true-license-create/src/main/resources/license.lic");
		if (file.exists()) {
			try {
				HttpHeaders headers = new HttpHeaders();// http头信息

				String downloadFileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");// 设置编码

				headers.setContentDispositionFormData("attachment", downloadFileName);

				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

				// MediaType:互联网媒介类型 contentType：具体请求中的媒体类型信息

				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}

	public ResponseEntity<byte[]> download(String path, HttpServletResponse response) {
		String filename = "license.lic";
		// String filePath = "D:/ftptest";
		File file = new File(path);
		if (file.exists()) {
			try {
				HttpHeaders headers = new HttpHeaders();// http头信息

				String downloadFileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");// 设置编码

				headers.setContentDispositionFormData("attachment", downloadFileName);

				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

				// MediaType:互联网媒介类型 contentType：具体请求中的媒体类型信息

				ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
						headers, HttpStatus.CREATED);
				if (responseEntity != null) {
					file.delete();
					return responseEntity;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}
}
