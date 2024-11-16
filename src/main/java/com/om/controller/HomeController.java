package com.om.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.om.entity.User;
import com.om.repository.UserRepository;
import com.om.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;
	
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	
	@ModelAttribute
	public void getUser(Principal p, Model m) {
		if (p!=null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}
	}

	
	@GetMapping("/signin")
	public String signin() {
		return "login";
	}
	
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, HttpSession session, HttpServletRequest request) {
		System.out.println("==========SAVE USER======");
		
		boolean f = userService.existEmailCheck(user.getEmail());
		
		String url = request.getRequestURL().toString();
		url = url.replace(request.getServletPath(), "");
		
		if (f) {
			session.setAttribute("msg", "Email Already Exists");
		}else {
			User newUser = userService.saveUser(user, url);
			
			if (newUser != null) {
				session.setAttribute("msg", "Register Succesfully");
			}else {
				session.setAttribute("msg", "Something Went Wrong");
			}
			
		}
		
		System.out.println(user);
		return "redirect:/register";
	}
	
	
	@GetMapping("/verify")
	public String verifyAccount(@Param("code") String code, Model m) {
		
		boolean f = userService.verifyAccount(code);
		
		if (f) {
			m.addAttribute("msg", "Your account is verified succesfully");
		}else {
			m.addAttribute("msg", "Verification code is incorrect or already verified");
		}
		
		return "message";
	}
	
	
}
