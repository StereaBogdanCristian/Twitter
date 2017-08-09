package com.twitter.dao;

import java.util.List;

import com.twitter.model.Message;

public interface MessageDao {

	Message findById (Long id);
	
	void save (Message message);
	
	void deleteById (Long id);
	
	List<Message> findTweetsByUser(String authorSsoId, int currentPage, int pageSize);
	
	List<Message> findAllTweetsByUser(String authorSsoId);
	
	List<Message> findFriendsMessagesByUser(String authorSsoId, long startAt, String way, int pageSize);
	
	List<Message> findFriendsMessagesByUserLike(String authorSsoId, long startAt, String way, int pageSize);
	
	void deleteAllMessagesOfUser (String authorSsoId);
	
	Long userMessagesCount (String authorSsoId);

	Long maxIdOfMessagesTable();
}
