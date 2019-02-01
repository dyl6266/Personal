package com.dy.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
	
	@RequestMapping(value = "/test.do")
	public ModelAndView test() {
		ModelAndView mav = new ModelAndView("jsonView");
		
		HashMap<String, Object> hashMap = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			hashMap.put("key" + i, "value" + i);
		}

		mav.addObject(hashMap);
		return mav;
	}

}
