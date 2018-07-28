package com.license.App;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@SpringBootApplication
public class App {
	
	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}
	
	@RequestMapping("/home1")
	String home1(HttpServletRequest request,HttpServletResponse response) {
		String name = request.getParameter("name");
		JSONObject rejson = new JSONObject();
		rejson.put("name", name);
		return rejson.toJSONString();
	}
	
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}

}
