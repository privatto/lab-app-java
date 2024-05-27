package com.jansouza.springboot.lab.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.jansouza.springboot.lab.util.SimulatedApiCall;

import org.springframework.web.bind.annotation.RequestParam;
import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/api")
public class ApiController {
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@Timed(value = "api")
	@GetMapping(path = "/call")
	public String version(@RequestParam(name="name", required=false, defaultValue="") String name, Model model) {
		model.addAttribute("name", name);

		String mfServiceRerturn = callApi("mf-service");
		String legacyServiceRerturn = callApi("legacy-service");
		
		return "api return\n\n mfServiceRerturn: "+ mfServiceRerturn + "\n" + "legacyServiceRerturn: "+ legacyServiceRerturn;
	}

	public String callApi(String service){
		logger.info("Call " + service);
		StringBuilder responseData = new StringBuilder();
		try {
			long startTime = System.nanoTime();

            HttpURLConnection connection = SimulatedApiCall.simulateApiCall("http://" + service + "/api");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            
            while ((inputLine = in.readLine()) != null) {
                responseData.append(inputLine);
            }
            in.close();
			long endTime = System.nanoTime();
            
			//Log
			JsonObject logJson = new JsonObject();
			logJson.addProperty("text", "Return " + service);
            logJson.addProperty("service", service);
			logJson.addProperty("url", connection.getURL().toString());
			logJson.addProperty("response_code", connection.getResponseCode());
			if (connection.getResponseCode() != 200){
				logJson.addProperty("response_data", responseData.toString());
			}
			logJson.addProperty("duration", (endTime - startTime) / 1_000_000);
			logger.info(logJson.toString());

        } catch (Exception e) {
			logger.error(service, e);
        }

		return responseData.toString();
	}

	@GetMapping("/erro500")
    public String simularErro() {
        throw new RuntimeException("Internal Server Error");
    }
}