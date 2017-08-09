package com.twitter.service;

import java.util.List;
import java.util.TreeSet;

import com.twitter.model.Message;
import com.twitter.model.User;

public interface MessageService {
	
	Message findById(Long id);
	
	void save (Message message);
	
	void addComment (Long messageId, Message comment);
	
	void update (Message message);
	
	void deleteById (Long id);
	
	List<Message> findTweetsByUserPag(String authorSsoId, int pageNumber, int pageSize);
	
	TreeSet<Message> listFriendsMessagesPag(User user, int pageSize, long startAt, String way);
	
	TreeSet<Message> listFriendsMessagesPagLike(User user, int pageSize, long startAt, String way);
	
	int maxNumberOfFriendsMessages (User user);

	Long maxIdOfMessagesInTable();
	
	Long userMessagesCount (String authorSsoId);
	
	void addLike (long messageId, String userAuxSsoId, String userAuxName);
	
	void unLike (long messageId, String userAuxSsoId, String userAuxName);
	
	void addShare (long messageId, String userAuxSsoId, String userAuxName);
	
	void unShare (long messageId, String userAuxSsoId, String userAuxName);

}
