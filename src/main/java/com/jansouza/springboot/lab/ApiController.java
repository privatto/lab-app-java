package com.jansouza.springboot.lab;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import io.micrometer.core.annotation.Timed;

@RestController
public class ApiController {

	@Timed(value = "api")
	@GetMapping(path = "/api")
	public String version(@RequestParam(name="name", required=false, defaultValue="") String name, Model model) {
		model.addAttribute("name", name);
		return "api";
	}
}