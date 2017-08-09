package com.twitter.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.twitter.model.Message;


@Repository("messageDao")
public class MessageDaoImpl extends AbstractDao<Long, Message>implements MessageDao {

	public Message findById(Long id) {
		Message message = getByKey(id);
		return message;
	}

	public void save(Message message) {
		persist(message);
	}
	
	public void update(Message message) {
		update(message);
	}

	public void deleteById(Long id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		Message message = (Message)crit.uniqueResult();
		delete(message);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Message> findFriendsMessagesByUser(String authorSsoId, long startAt, String way, int pageSize) {
		
		Criteria criteria;
		if (way.equals("forward")) {
			criteria = createEntityCriteria().
								add(Restrictions.eq("authorSsoId", authorSsoId)).
								add(Restrictions.lt("id", startAt)).
								add(Restrictions.eq("messType", "TWEET")).
								addOrder(Order.desc("date"));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
			criteria.setMaxResults(pageSize);
		} else {
			criteria = createEntityCriteria().
								add(Restrictions.eq("authorSsoId", authorSsoId)).
								add(Restrictions.gt("id", startAt)).
								add(Restrictions.eq("messType", "TWEET")).
								addOrder(Order.asc("date"));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
			criteria.setMaxResults(pageSize);
		}
		
		List<Message> messages = (List<Message>) criteria.list();
		
		return messages;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Message> findFriendsMessagesByUserLike(String authorSsoId, long startAt, String way, int pageSize) {
		
		Criteria criteria;
		
		if (way.equals("forward")) {
			criteria = createEntityCriteria().
								add(Restrictions.eq("authorSsoId", authorSsoId)).
								add(Restrictions.le("id", startAt)).
								add(Restrictions.eq("messType", "TWEET")).
								addOrder(Order.desc("date"));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
			criteria.setMaxResults(pageSize);
		} else {
			criteria = createEntityCriteria().
								add(Restrictions.eq("authorSsoId", authorSsoId)).
								add(Restrictions.ge("id", startAt)).
								add(Restrictions.eq("messType", "TWEET")).
								addOrder(Order.asc("date"));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
			criteria.setMaxResults(pageSize);
		}
		
		List<Message> messages = (List<Message>) criteria.list();
		
		return messages;
	}
	

	@SuppressWarnings("unchecked")
	public List<Message> findTweetsByUser(String authorSsoId, int currentPage, int pageSize) {
		
		Criteria criteria = createEntityCriteria().
									add(Restrictions.eq("authorSsoId", authorSsoId)).
									addOrder(Order.desc("date"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		criteria.setFirstResult((currentPage - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		List<Message> mesages = (List<Message>) criteria.list();
		
		return mesages;
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> findAllTweetsByUser(String authorSsoId) {
		
		Criteria criteria = createEntityCriteria().
									add(Restrictions.eq("authorSsoId", authorSsoId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<Message> mesages = (List<Message>) criteria.list();
		
		return mesages;
	}
	
	@Transactional //needed for Projections... 
	public Long userMessagesCount (String authorSsoId) {
		Criteria criteria = createEntityCriteria().
				add(Restrictions.eq("authorSsoId", authorSsoId)).
				setProjection(Projections.rowCount());
		return (Long)(criteria.uniqueResult());
	}

	
	@SuppressWarnings("unchecked")
	public void deleteAllMessagesOfUser (String authorSsoId) {
		
		Criteria criteria = createEntityCriteria().
				add(Restrictions.eq("authorSsoId", authorSsoId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		
		List<Message> messages = (List<Message>) criteria.list();
		
		for (Message message : messages) {
			message.getListOfUserLike().clear();
			message.getListOfUserShare().clear();
			for (Message mess : message.getComments()) {
				deleteById(mess.getId());
			}
			message.getComments().clear();
			if (message.getMessCommentedId() != null) {
				Message messfind = findById(message.getMessCommentedId());
				messfind.getComments().removeIf((Message mess) -> mess.getAuthorSsoId().equals(authorSsoId));
			}
		}
		Query query = getSession().getNamedQuery("deleteAllMessagesOfUser");
		query.setString(0, authorSsoId);
		query.executeUpdate();
	}
	

	@Transactional //needed for Projections... 
	public Long maxIdOfMessagesTable() {
		Criteria criteria = createEntityCriteria().
				setProjection(Projections.max("id"));
		return (Long) criteria.uniqueResult();
	}
	
	


}
