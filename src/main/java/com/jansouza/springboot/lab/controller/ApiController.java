package com.jansouza.springboot.lab.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jansouza.springboot.lab.util.SimulatedApiCall;

import org.springframework.web.bind.annotation.RequestParam;
import io.micrometer.core.annotation.Timed;

@RestController
public class ApiController {
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@Timed(value = "api")
	@GetMapping(path = "/api")
	public String version(@RequestParam(name="name", required=false, defaultValue="") String name, Model model) {
		model.addAttribute("name", name);

		callApi("mf-service");
		callApi("legacy-service");

		return "api";
	}

	public void callApi(String service){
		logger.info("Call " + service);
		try {
			long startTime = System.nanoTime();

            HttpURLConnection connection = SimulatedApiCall.simulateApiCall("http://" + service + "/api");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
			long endTime = System.nanoTime();
            
			//Log
			MDC.put("service", service);
			MDC.put("url", connection.getURL().toString());
			MDC.put("response", response.toString());
			MDC.put("duration", Long.toString((endTime - startTime) / 1_000_000));
    	    logger.info("Return " + service);
			MDC.remove("service");
			MDC.remove("url");
			MDC.remove("response");
			MDC.remove("duration");
				
        } catch (Exception e) {
			logger.error(service, e);
        }
	}
}