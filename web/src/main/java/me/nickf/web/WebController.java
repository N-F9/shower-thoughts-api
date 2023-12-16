package me.nickf.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class WebController {
	// @GetMapping("/")
	// public String index() {
	// 	return "index";
	// }

	@GetMapping
	public ModelAndView getTestData() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");

		return mv;
	}
}
