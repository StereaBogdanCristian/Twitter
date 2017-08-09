package com.twitter.service;

import java.util.List;
import java.util.Map;

import com.twitter.model.User;

public interface UserService {
	
	User findById(Long id);
	
	User findBySso(String sso);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserBySsoId(String ssoId);

	List<User> findAllUsers(); 
	
	boolean isUserSsoUnique(Long id, String sso);

	List<User> userSearch(String userNameSearch, User user);

	void addFriend (User user, User friend);

	Map<String, Object> userSearchAndIsFollowing(String nameSearch, User user);

	void removeFriend(User user, User friend);

}
