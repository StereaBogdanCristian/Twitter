package com.twitter.dao;

import java.util.List;

import com.twitter.model.Share;

public interface ShareDao {

	Share findById (int id);
	
	void save (Share share);
	
	void deleteByMessIdUserSsoId (long messId, String userSsoId);
	
	void deleteById (int id);
	
	List<Share> findShareByUser(String authorSsoId, int startAt, String way, int pageSize);
	
	void deleteAllSharesToUser (String authorSsoId);
	
	Long userSharesCount (String authorSsoId);

}
