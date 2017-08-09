package com.twitter.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.twitter.model.Share;

@Repository("shareDao")
public class ShareDaoImpl extends AbstractDao<Integer, Share> implements ShareDao {

	@Override
	public Share findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(Share share) {
		persist(share);
	}
	
	public void deleteByMessIdUserSsoId (long messId, String userSsoId) {
		Criteria criteria = createEntityCriteria().
								add(Restrictions.eq("messId", messId)).
								add(Restrictions.eq("userSsoId", userSsoId));
		Share share = (Share) criteria.uniqueResult();
		delete(share);
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Share> findShareByUser(String authorSsoId, int startAt, String way, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteAllSharesToUser(String authorMessSsoId) {
		Query query = getSession().getNamedQuery("deleteAllSharesOfUser");
		query.setString(0, authorMessSsoId);
		query.setString(1, authorMessSsoId);
		query.executeUpdate();		
	}

	@Override
	public Long userSharesCount(String authorSsoId) {
		// TODO Auto-generated method stub
		return null;
	}

}
