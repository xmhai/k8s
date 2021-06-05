package com.lin.hc.poc.mainService;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@PropertySource("classpath:version.properties")
public class MainServiceApplication {
	Logger logger = LoggerFactory.getLogger(MainServiceApplication.class);

	@Value( "${buildTimestamp}" )
	private String buildTimestamp;
	
	public static void main(String[] args) {
		SpringApplication.run(MainServiceApplication.class, args);
	}
	

	@RequestMapping("/")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		logger.info("REQUEST: "+request.getRequestURI());
		return "Main Service is running... Build: " + buildTimestamp;
	}

	@RequestMapping("/echo")
	public String echo(HttpServletRequest request,
			HttpServletResponse response,
			@RequestHeader Map<String, String> headers) throws IOException {
		StringBuilder headerStr = new StringBuilder();
		headers.forEach((key, value) -> {
			headerStr.append(String.format("Header '%s' = %s", key, value)).append("<br>");
	    });
		
		String body = "null";
		if ("POST".equalsIgnoreCase(request.getMethod())) {
	        Scanner s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
	        body = s.hasNext() ? s.next() : "";
	    }
		
		String queryString = request.getQueryString()==null ? "" : "?" + request.getQueryString();
		
		String s = "Request URL: " + request.getScheme() + request.getRequestURI() + queryString + "<br>" + "<br>"
				+ "Method: " + request.getMethod() + "<br>" + "<br>"
				+ headerStr.toString() + "<br>" + "<br>"
				+ "Body: " + body + "<br>";
		logger.info(s);
		return s;
	}

	@Value("${service1.url}")
	private String service1Url;

	@RequestMapping("/service1")
	public String callService1(HttpServletRequest request, HttpServletResponse response) {
		logger.info("REQUEST: "+request.getRequestURI());
		RestTemplate restTemplate = new RestTemplate();
		try {
			String name = restTemplate.getForObject(service1Url, String.class);
			return "Call service1 SUCCESS, Return: " + name;
		} catch (Exception e) {
			logger.error("ERROR: ", e);
			return "Call service1 FAILED, Error Message: " + e.getMessage();
		}
	}

	@Value("${external.api.url}")
	private String externalApiUrl;
	
	@RequestMapping("/external")
	public String callExternalApi(HttpServletRequest request, HttpServletResponse response) {
		logger.info("REQUEST: "+request.getRequestURI());
		RestTemplate restTemplate = new RestTemplate();
		try {
			String name = restTemplate.getForObject(externalApiUrl, String.class);
			return "Call external api SUCCESS, Return: " + name;
		} catch (Exception e) {
			logger.error("ERROR: ", e);
			e.printStackTrace();
			return "Call external api FAILED, Error Message: " + e.getMessage();
		}
	}
}
