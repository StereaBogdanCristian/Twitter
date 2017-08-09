package com.twitter.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twitter.dao.MessageDao;
import com.twitter.dao.ShareDao;
import com.twitter.dao.UserDao;
import com.twitter.model.Message;
import com.twitter.model.User;
import com.twitter.model.UserAuxEnt;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	MessageDao messageDao;
	
	@Autowired
	ShareDao shareDao;

	public User findById(Long id) {
		return userDao.findById(id);
	}

	public User findBySso(String sso) {
		return userDao.findBySso(sso);
	}

	public void saveUser(User user) {
		userDao.save(user);
	}

	public void updateUser(User user) {
		User userDB = userDao.findById(user.getId());
		if(userDB!=null){
			userDB.setSsoId(user.getSsoId());
			userDB.setName(user.getName());
			userDB.setEmail(user.getEmail());
			userDB.setJoiningDate(user.getJoiningDate());
			userDB.setGender(user.getGender());
			userDB.setStatus(user.getStatus());
			userDB.setPassword(user.getPassword());
		}
	}
	

	public void deleteUserBySsoId(String userSsoId) {
		
		User user = userDao.findBySso(userSsoId);
	
		for (User u : user.getWhosFriend()) {
			User u1 = userDao.findBySso(u.getSsoId());
			u1.getListOfFriends().remove(user);
		}
		
		for (User u : user.getListOfFriends()) {
			for (Message m : messageDao.findAllTweetsByUser(u.getSsoId())) {
				m.getListOfUserLike().removeIf((UserAuxEnt uae) -> uae.getUserAuxEntName().equals(userSsoId));
				m.getListOfUserShare().removeIf((UserAuxEnt uae) -> uae.getUserAuxEntSsoId().equals(userSsoId));
			} 
		}
		
		userDao.deleteBySsoId(userSsoId);
		messageDao.deleteAllMessagesOfUser(userSsoId);
		shareDao.deleteAllSharesToUser(userSsoId);
	
	}

	public List<User> findAllUsers() {
		return userDao.findAllUsers();
	}

	
	public boolean isUserSsoUnique(Long id, String sso) {
		User user = findBySso(sso);
		return ( user == null || ((id != null) && (user.getId() == id)));
	}

	
	public List<User> userSearch(String userNameSearch, User user) {
		return userDao.findSearchUsers(userNameSearch, user);
	}
	
	
	public void addFriend (User user, User friend) {
		
		User userDB = userDao.findById(user.getId());
		User friendDBwhosFriend = userDao.findBySso(friend.getSsoId());
		
		userDB.getListOfFriends().add(friendDBwhosFriend);
		friendDBwhosFriend.getWhosFriend().add(userDB);
		
	}

	public Map<String, Object> userSearchAndIsFollowing(String nameSearch, User user) {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		List<User> listUserFind = userDao.findSearchUsers(nameSearch, user);
		Map<String, Boolean> isFollowing = new HashMap<>();
		
		returnMap.put("userSearch", listUserFind);
		
		for (User u : listUserFind) {
			
			User friend = userDao.findBySso(u.getSsoId());
			if (user.getListOfFriends().contains(friend)) {
				isFollowing.put(u.getSsoId(), true);
			} else {
				isFollowing.put(u.getSsoId(), false);
			}
		}
		
		returnMap.put("isFollowing", isFollowing);
		
		return returnMap;
	}
	
	
	public void removeFriend(User user, User friend) {
		
		User userDB = userDao.findBySso(user.getSsoId());
		User userDBwhosFriend = userDao.findBySso(friend.getSsoId());
		if (userDB != null) {
			userDB.getListOfFriends().remove(userDBwhosFriend);
		}
		
		if(userDBwhosFriend != null){
			userDBwhosFriend.getWhosFriend().remove(userDB);
		}
	}

}
