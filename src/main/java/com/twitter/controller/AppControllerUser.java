package com.twitter.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.twitter.model.Message;
import com.twitter.model.Share;
import com.twitter.model.User;
import com.twitter.service.MessageService;
import com.twitter.service.ShareService;
import com.twitter.service.UserService;

@Controller
public class AppControllerUser {
	
	@Autowired
	UserService userService;

	@Autowired
	MessageService messageService;
	
	@Autowired
	ShareService shareService;
	
	final int pageSize = 4;
	
	@RequestMapping(value = { "/user-{ssoId}" }, method = RequestMethod.GET)
	public String showUser (@PathVariable String ssoId, @RequestParam(required = false) Long maxNumberOfPages,
			@RequestParam(required = false) Integer currentPage, ModelMap model, HttpSession session) {
		
		User user = userService.findBySso(ssoId);
		
		if(currentPage == null) {
			currentPage = 1;
		 }
		
		if(maxNumberOfPages == null) {
			Long maxMess = messageService.userMessagesCount(user.getSsoId());
			if ((messageService.userMessagesCount(user.getSsoId()) % pageSize) > 0) {
				maxNumberOfPages = (Long)(maxMess / pageSize) + 1;
			} else {
				maxNumberOfPages = (Long)(maxMess / pageSize);
			}
				
		 }
		
		model.addAttribute("user", user);
		model.addAttribute("messages", messageService.findTweetsByUserPag(ssoId, currentPage, pageSize));
		model.put("maxNumberOfPages", maxNumberOfPages);
		model.put("currentPage", currentPage);
		
		session.setAttribute("user", user);
		
		return "user";
	}
	
	
	@RequestMapping(value = { "/addmessage" }, method = RequestMethod.GET)
	public String message (ModelMap model) {
		
		Message message = new Message();
		
		model.addAttribute("message", message);
		model.addAttribute("update", false);
		
		return "message";
	}
	
	
	@RequestMapping(value = { "/addmessage" }, method = RequestMethod.POST)
	public String saveMessage (@Valid Message message, BindingResult result, ModelMap model, HttpSession session) {
		
		if (result.hasErrors()) {
			return "message";
		}
		
		User user = (User)session.getAttribute("user");
		
		message.setAuthorName(user.getName());
		message.setAuthorSsoId(user.getSsoId());
		message.setDate(new DateTime());
		message.setMessType("TWEET");
		
			messageService.save(message);
		
		return "redirect:/user-" + ((User)session.getAttribute("user")).getSsoId();
	}
	
	
	@RequestMapping(value = {"/message-edit-{id}"}, method = RequestMethod.GET)
	public String editMessage (@PathVariable Long id, ModelMap model){
		
		Message message = messageService.findById(id);
		
		model.put("message", message);
		model.put("update", true);
		
		return "message";
	}
	
	
	@RequestMapping(value = {"/message-edit-{id}"}, method = RequestMethod.POST)
	public String updateMessage (@Valid Message message, BindingResult result, ModelMap model, HttpSession session){
		
		if (result.hasErrors()) {
			return "message";
		}
		
		messageService.update(message);
		
		return "redirect:/user-" + ((User)session.getAttribute("user")).getSsoId();
	}
	
	
	@RequestMapping(value = {"/message-delete-{id}"}, method = RequestMethod.GET)
	public String deleteMessage (@PathVariable Long id, @RequestParam String userssoId){
		
		messageService.deleteById(id);
		
		return "redirect:/user-" + userssoId;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/search"}, method = RequestMethod.GET)
	public String searchUser (@RequestParam String nameSearch, HttpSession session, ModelMap model){
		
		User user = (User)session.getAttribute("user");
		Map<String, Object> userSearchAndIsFollowing = userService.userSearchAndIsFollowing(nameSearch, user);
		
		model.put("nameSearch", nameSearch);
		model.put("users", (List<User>) userSearchAndIsFollowing.get("userSearch"));
		model.put("isFollowing", (Map<String, Boolean>) userSearchAndIsFollowing.get("isFollowing"));
		
		return "listusersearch";
		}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/fallow-{ssoId}"}, method = RequestMethod.GET)
	public String addFriend (@PathVariable String ssoId, HttpSession session, 
			@RequestParam String nameSearch, ModelMap model){
		
		User user = (User) session.getAttribute("user");
		userService.addFriend(user, userService.findBySso(ssoId));
		user = userService.findBySso(user.getSsoId());
		
		Map<String, Object> userSearchAndIsFollowing = userService.userSearchAndIsFollowing(nameSearch, user);
		
		model.put("nameSearch", nameSearch);
		model.put("users", (List<User>) userSearchAndIsFollowing.get("userSearch"));
		model.put("isFollowing", (Map<String, Boolean>) userSearchAndIsFollowing.get("isFollowing"));
		return "listusersearch";
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/unfallow-{ssoId}"}, method = RequestMethod.GET)
	public String removeFriend (@PathVariable String ssoId, HttpSession session, 
			@RequestParam String nameSearch, ModelMap model){
		
		User user = (User) session.getAttribute("user");
		userService.removeFriend(user, userService.findBySso(ssoId));
		user = userService.findBySso(user.getSsoId());
		
		Map<String, Object> userSearchAndIsFollowing = userService.userSearchAndIsFollowing(nameSearch, user);
		
		model.put("nameSearch", nameSearch);
		model.put("users", (List<User>) userSearchAndIsFollowing.get("userSearch"));
		model.put("isFollowing", (Map<String, Boolean>) userSearchAndIsFollowing.get("isFollowing"));
		
		return "listusersearch";
	}
	
	
	@RequestMapping(value={ "/friendsmessages"}, method = RequestMethod.GET)
	public String friendsMessages (@RequestParam(required = false) Integer maxNumberOfPages,
			@RequestParam(required = false) Long messageid, @RequestParam(required = false) String way,
			@RequestParam(required = false) Integer currentPage, ModelMap model, HttpSession session) {
		
		User user = (User)session.getAttribute("user");
		
		if (currentPage == null) {
			currentPage = 1;
		 }
		
		if (way == null) {
			way = "forward";
		}
		
		if (messageid == null) {
			messageid = messageService.maxIdOfMessagesInTable() + 1;
		 }
		
		if (maxNumberOfPages == null) {
			Long maxMess = (long) messageService.maxNumberOfFriendsMessages(user);
			if ((maxMess % pageSize) > 0) {
				maxNumberOfPages = (int) ((maxMess / pageSize) + 1);
			} else {
				maxNumberOfPages = (int)(maxMess / pageSize);
			}
		 }
		
		model.put("user", user);
		model.put("messages", messageService.listFriendsMessagesPag(user, pageSize, messageid, way));
		model.put("currentPage", currentPage);
		model.put("maxNumberOfPages", maxNumberOfPages);
		
		return "/friendsmessages";
	}
	
	
	
	@RequestMapping(value={ "/message-like-{id}"}, method = RequestMethod.GET)
	public String messagesAddLike (@PathVariable Long id, @RequestParam String userssoid, 
			@RequestParam String username, @RequestParam Integer maxNumberOfPages, @RequestParam Long messageid,
			@RequestParam Integer currentPage, ModelMap model, HttpSession session) {
				
		messageService.addLike(id, userssoid, username);
		
		User user = (User)session.getAttribute("user");
		String way = "forward";
		
		model.put("user", user);
		model.put("messages", messageService.listFriendsMessagesPagLike(user, pageSize, messageid, way));
		model.put("currentPage", currentPage);
		model.put("maxNumberOfPages", maxNumberOfPages);
		
		return "/friendsmessages";
	}
	
	
	
	@RequestMapping(value={ "/message-unlike-{id}"}, method = RequestMethod.GET)
	public String messagesUnlike (@PathVariable Long id, @RequestParam String userssoid, 
			@RequestParam String username, @RequestParam Integer maxNumberOfPages, @RequestParam Long messageid,
			@RequestParam Integer currentPage, ModelMap model, HttpSession session) {
				
		messageService.unLike(id, userssoid, username);
		
		User user = (User)session.getAttribute("user");
		String way = "forward";
		
		model.put("user", user);
		model.put("messages", messageService.listFriendsMessagesPagLike(user, pageSize, messageid, way));
		model.put("currentPage", currentPage);
		model.put("maxNumberOfPages", maxNumberOfPages);
		
		return "/friendsmessages";
	}
	
	
	@RequestMapping(value={ "/message-share-{id}"}, method = RequestMethod.GET)
	public String messagesAddShare (@PathVariable Long id, @RequestParam String userssoid, 
			@RequestParam String username, @RequestParam Integer maxNumberOfPages, @RequestParam Long messageid,
			@RequestParam Integer currentPage, ModelMap model, HttpSession session) {
				
		User user = (User)session.getAttribute("user");
		String way = "forward";
		
		messageService.addShare(id, userssoid, username);
		shareService.addShare(new Share(id, messageService.findById(id).getAuthorSsoId(), userssoid, username, new DateTime()));
		
		model.put("user", user);
		model.put("messages", messageService.listFriendsMessagesPagLike(user, pageSize, messageid, way));
		model.put("currentPage", currentPage);
		model.put("maxNumberOfPages", maxNumberOfPages);
		
		return "/friendsmessages";
	}
	
	
	@RequestMapping(value={ "/message-unshare-{id}"}, method = RequestMethod.GET)
	public String messagesUnShare (@PathVariable Long id, @RequestParam String userssoid, 
			@RequestParam String username, @RequestParam Integer maxNumberOfPages, @RequestParam Long messageid,
			@RequestParam Integer currentPage, ModelMap model, HttpSession session) {
				
		messageService.unShare(id, userssoid, username);
		shareService.deleteShareByMessIdUserSsoId(id, userssoid);
		
		User user = (User)session.getAttribute("user");
		String way = "forward";
		
		model.put("user", user);
		model.put("messages", messageService.listFriendsMessagesPagLike(user, pageSize, messageid, way));
		model.put("currentPage", currentPage);
		model.put("maxNumberOfPages", maxNumberOfPages);
		
		return "/friendsmessages";
	}
	
	
	@RequestMapping(value={"/message-comments"}, method = RequestMethod.GET)
	public String messagesComments (@RequestParam Long id, @RequestParam String userssoid, 
			@RequestParam String username, @RequestParam Integer maxNumberOfPages, @RequestParam Long messageid,
			@RequestParam Integer currentPage, ModelMap model, HttpSession session) {
				
		
		User user = (User)session.getAttribute("user");
		String way = "forward";
		
		Message comment = new Message();
		model.addAttribute("comment", comment);
		model.addAttribute("update", false);
		
		model.put("messageCommentId", id);
		model.put("user", user);
		model.put("messages", messageService.listFriendsMessagesPagLike(user, pageSize, messageid, way));
		model.put("currentPage", currentPage);
		model.put("maxNumberOfPages", maxNumberOfPages);
		
		return "/friendsmessages";
	}
	
	
	@RequestMapping(value={"/message-comments"}, method = RequestMethod.POST, headers = "Content-Type=application/x-www-form-urlencoded")
	public String messagesAddComment (@ModelAttribute("comment") @Valid Message comment, BindingResult result, @RequestParam Long idmess,  
			@RequestParam String username, @RequestParam Integer maxNumberOfPages, @RequestParam Long messageid,
			@RequestParam Integer currentPage, ModelMap model, HttpSession session) {
		
		User user = (User)session.getAttribute("user");
		
		comment.setAuthorName(user.getName());
		comment.setAuthorSsoId(user.getSsoId());
		comment.setDate(new DateTime());
		comment.setMessType("COMMENT");
		
		if ((comment.getMessage() == null) || (comment.getMessage().equals(""))) {
		if (result.hasErrors()) {
			
			String way = "forward";
			
			model.addAttribute("update", false);
			
			model.put("messageCommentId", idmess);
			model.put("user", user);
			model.put("messages", messageService.listFriendsMessagesPagLike(user, pageSize, messageid, way));
			model.put("currentPage", currentPage);
			model.put("maxNumberOfPages", maxNumberOfPages);
			
			return "/friendsmessages";
		}
		}
		
		messageService.addComment(idmess, comment);
		
		String way = "forward";
		
		model.addAttribute("update", false);
		
		model.put("messageCommentId", idmess);
		model.put("user", user);
		model.put("messages", messageService.listFriendsMessagesPagLike(user, pageSize, messageid, way));
		model.put("currentPage", currentPage);
		model.put("maxNumberOfPages", maxNumberOfPages);
		
		return "redirect:/friendsmessages";
	}
	
}
	

