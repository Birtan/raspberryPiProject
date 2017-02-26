package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.LedImpl;

@RestController
public class LedController {
	
	@Autowired
	LedImpl ledService;
	
	@RequestMapping("/")
	public String greeting() {
		return "Hello World";
	}
	
	@RequestMapping("/light")
	public String light() {
		return ledService.light();
	}
	
	@RequestMapping("/on")
	public String on() {
		return ledService.on();
	}
	
	@RequestMapping("/off")
	public String off() {
		return ledService.off();
	}
	
	@RequestMapping("/shutdown")
	public String shutdown() {
		return ledService.shutdown();
	}

}
