package com.twitter.dao;

import java.util.List;

import com.twitter.model.User;


public interface UserDao {

	User findById(long id);
	
	User findBySso(String sso);
	
	void save(User user);
	
	void deleteBySsoId(String ssoId);
	
	List<User> findAllUsers();
	
	public List<User> findSearchUsers(String name, User user);
}
