package com.twitter.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.twitter.model.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Long, User> implements UserDao {

	public User findById(long id) {
		User user = getByKey(id);
		return user;
	}

	public User findBySso(String sso) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", sso));
		User user = (User)crit.uniqueResult();
		return user;
	}

	public void save(User user) {
		persist(user);
	}

	public void deleteBySsoId(String ssoId) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", ssoId));
		User user = (User)crit.uniqueResult();
		for (User u : user.getWhosFriend()) {
			u.getListOfFriends().remove(user);
		}
		delete(user);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("name"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		
		List<User> users = (List<User>) criteria.list();
		return users;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findSearchUsers(String name, User user) {
		Criteria criteria = createEntityCriteria().
									add(Restrictions.ilike("name", "%"+name+"%")).
									add(Restrictions.ne("ssoId", user.getSsoId())).
									addOrder(Order.asc("name"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		
		List<User> users = (List<User>) criteria.list();
		return users;
	}

}
