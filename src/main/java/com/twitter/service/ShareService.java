package com.twitter.service;

import com.twitter.model.Share;

public interface ShareService {
	
	void addShare(Share share);
	
	void deleteShareByMessIdUserSsoId (long messId, String userSsoId);

}
