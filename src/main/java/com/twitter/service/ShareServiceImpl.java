package com.twitter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twitter.dao.ShareDao;
import com.twitter.model.Share;

@Service("shareService")
@Transactional
public class ShareServiceImpl implements ShareService {

	@Autowired
	ShareDao shareDao;
	
	public void addShare(Share share) {
		shareDao.save(share);
	}

	public void deleteShareByMessIdUserSsoId(long messId, String userSsoId) {
		shareDao.deleteByMessIdUserSsoId(messId, userSsoId);
	}

}
