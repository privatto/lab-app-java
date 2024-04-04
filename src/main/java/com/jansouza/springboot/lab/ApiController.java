package com.jansouza.springboot.lab;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import io.micrometer.core.annotation.Timed;

@RestController
public class ApiController {

	@GetMapping(path = "/version")
	public String version(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "api";
	}

	@Timed(value = "info")
	@GetMapping(path = "/info")
	public String info(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "info";
	}

}