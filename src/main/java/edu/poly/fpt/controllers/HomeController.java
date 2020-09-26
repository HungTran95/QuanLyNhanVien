package edu.poly.fpt.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/views")
public class HomeController {
	@RequestMapping("/index")
	public String index() {
		return "views/index";
	}
}
