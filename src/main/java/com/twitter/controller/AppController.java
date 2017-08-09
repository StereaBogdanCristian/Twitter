package com.twitter.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.twitter.model.User;
import com.twitter.service.MessageService;
import com.twitter.service.UserService;

@Controller
@RequestMapping("/")
public class AppController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	MessageService messageService;
	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String addUser(ModelMap model) {
		
		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		
		return "userslist";
	}
	
	
	
	@RequestMapping(value = { "/adduser" }, method = RequestMethod.GET)
	public String newUser(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		return "registration";
	}
	
	
	
	@RequestMapping(value = { "/adduser" }, method = RequestMethod.POST)
	public String addUser(@Valid User user, BindingResult result, ModelMap model) {
		
		if (result.hasErrors()) {
			return "registration";
		}
		
		if(!userService.isUserSsoUnique(user.getId(), user.getSsoId())){
			FieldError ssoError = new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
		    result.addError(ssoError);
			return "registration";
		}
		
		user.setJoiningDate(new LocalDate(DateTimeZone.UTC));
		userService.saveUser(user);
		
		return "redirect:/list";
	}
	
	
	
	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.GET)
	public String editUser(@PathVariable String ssoId, ModelMap model) {
		User user = userService.findBySso(ssoId);
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		return "registration";
	}
	
	
	
	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.POST)
	public String uodateUser(@Valid User user, BindingResult result, ModelMap model) {
		
		if (result.hasErrors()) {
			return "registration";
		}
		
		userService.updateUser(user);
		
		return "redirect:/list";
	}
	
	
	
	@RequestMapping(value = { "/delete-user-{ssoId}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String ssoId) {
		userService.deleteUserBySsoId(ssoId);
		
		return "redirect:/list";
	}
	
	
	

}
