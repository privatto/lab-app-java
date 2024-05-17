package com.jansouza.springboot.lab;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

	private static final Logger logger = LoggerFactory.getLogger(GreetingController.class.getName());

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="greeting") String name, Model model) {
		model.addAttribute("name", name);

		logger.debug("GreetingController");
		logger.trace("{}",new JSONObject().put("action", "greeting").put("response", 200));
		
		return "greeting";
	}

}