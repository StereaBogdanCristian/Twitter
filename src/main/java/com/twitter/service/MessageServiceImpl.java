package com.twitter.service;

import java.util.List;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twitter.dao.MessageDao;
import com.twitter.model.UserAuxEnt;
import com.twitter.model.Message;
import com.twitter.model.User;

@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	MessageDao messageDao;

	public Message findById(Long id) {
		Message message = messageDao.findById(id);
		return message;
	}

	public void save(Message message) {
		messageDao.save(message);
	}

	public void update(Message message) {
		Message messageDB = messageDao.findById(message.getId());
		if (messageDB != null) {
			messageDB.setMessage(message.getMessage());
		}
	}

	public void deleteById(Long id) {
		messageDao.deleteById(id);
	}

	
	public List<Message> findTweetsByUserPag(String authorSsoId, int currentPage, int pageSize) {
		return messageDao.findTweetsByUser(authorSsoId, currentPage, pageSize);
	}

	
	
	public TreeSet<Message> listFriendsMessagesPag(User user, int pageSize, long startAt, String way) {
		
		TreeSet<Message> listFirstMessOfAllFriends = new TreeSet<>();
		
		for (User u : user.getListOfFriends()) {
			
			for (Message message : messageDao.findFriendsMessagesByUser(u.getSsoId(), startAt, way, pageSize)) {
			
					listFirstMessOfAllFriends.add(message);
				
				if (listFirstMessOfAllFriends.size() > pageSize) {
					if (way.equals("forward")) {
						listFirstMessOfAllFriends.remove((listFirstMessOfAllFriends).last());
					} else {
						listFirstMessOfAllFriends.remove((listFirstMessOfAllFriends).first());
					}
				}
			}
		}
		return listFirstMessOfAllFriends;
	}
	
	
	
	public TreeSet<Message> listFriendsMessagesPagLike(User user, int pageSize, long startAt, String way) {
		
		TreeSet<Message> listFirstMessOfAllFriends = new TreeSet<>();
		
		for (User u : user.getListOfFriends()) {
			
			for (Message message : messageDao.findFriendsMessagesByUserLike(u.getSsoId(), startAt, way, pageSize)) {
				listFirstMessOfAllFriends.add(message);
				if (listFirstMessOfAllFriends.size() > pageSize) {
					if (way.equals("forward")) {
						listFirstMessOfAllFriends.remove((listFirstMessOfAllFriends).last());
					} else {
						listFirstMessOfAllFriends.remove((listFirstMessOfAllFriends).first());
					}
				}
			}
		}
		return listFirstMessOfAllFriends;
	}
	
	
	public int maxNumberOfFriendsMessages (User user) {
		
		int maxNumber= 0;
	
		for (User u : user.getListOfFriends()) {
			maxNumber += messageDao.userMessagesCount(u.getSsoId());
		}
		return maxNumber;
	}

	
	public Long maxIdOfMessagesInTable() {
		return messageDao.maxIdOfMessagesTable();
	}

	
	public Long userMessagesCount(String authorSsoId) {
		return messageDao.userMessagesCount(authorSsoId);
	}

	
	public void addLike (long messageId, String userAuxSsoId, String userAuxName) {
		Message messageDB = messageDao.findById(messageId);
		if (messageDB != null) {
			messageDB.getListOfUserLike().add(new UserAuxEnt(userAuxSsoId, userAuxName));
		}
	}
	
	
	public void unLike (long messageId, String userAuxSsoId, String userAuxName) {
		Message messageDB = messageDao.findById(messageId);
		if (messageDB != null) {
			messageDB.getListOfUserLike().remove(new UserAuxEnt(userAuxSsoId, userAuxName));
		}
	}
	
	
	public void addShare (long messageId, String userAuxSsoId, String userAuxName) {
		Message messageDB = messageDao.findById(messageId);
		if (messageDB != null) {
			messageDB.getListOfUserShare().add(new UserAuxEnt(userAuxSsoId, userAuxName));
		}
	}
	
	public void unShare (long messageId, String userAuxSsoId, String userAuxName) {
		Message messageDB = messageDao.findById(messageId);
		if (messageDB != null) {
			messageDB.getListOfUserShare().remove(new UserAuxEnt(userAuxSsoId, userAuxName));
		}
	}

	public void addComment(Long messageId, Message comment) {
		
		messageDao.save(comment);
		Message message = messageDao.findById(messageId);
		comment.setMessCommentedId(messageId);
		message.getComments().add(comment);
	}
	
}
