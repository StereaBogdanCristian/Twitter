package com.twitter.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

@Entity
@NamedQuery(name="deleteAllMessagesOfUser", query="DELETE from Message where authorSsoId = ?")
@Table(name="MESSAGES_TWITTER")
public class Message implements Comparable<Message>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotEmpty
	@Column(name="MESSAGE")
	private String message;
	
	@Column(name="DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime date;
	
	@Column(name="AUTHOR_SSOID")
	private String authorSsoId;
	
	@Column(name="AUTHOR_Name")
	private String authorName;
	
	@Column(name="MESSTYPE")
	private String messType;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name="LIST_MESSAGE_USER_LIKE_TWITTER")
	private Set<UserAuxEnt> listOfUserLike = new HashSet<UserAuxEnt>();
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name="LIST_MESSAGE_SHARE_LIKE_TWITTER")
	private Set<UserAuxEnt> listOfUserShare = new HashSet<UserAuxEnt>();
	
	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "Comment_ID")
	private Set<Message> comments = new TreeSet<Message>();

	private Long messCommentedId;
	

	@Override
	public int compareTo(Message m) {
		if (this.id.equals(m.id)) {
	        return 0;
	    }
	    return m.date.compareTo(this.date);
	}
	
	
	public Message() {
	}


	public Message(String message, DateTime date, String authorSsoId, String authorName, String messType,
			Set<UserAuxEnt> listOfUserLike, Set<UserAuxEnt> listOfUserShare, Set<Message> comments,
			Long messCommentedId) {
		super();
		this.message = message;
		this.date = date;
		this.authorSsoId = authorSsoId;
		this.authorName = authorName;
		this.messType = messType;
		this.listOfUserLike = listOfUserLike;
		this.listOfUserShare = listOfUserShare;
		this.comments = comments;
		this.messCommentedId = messCommentedId;
	}


	public Set<Message> getComments() {
		return comments;
	}


	public void setComments(Set<Message> comments) {
		this.comments = comments;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public DateTime getDate() {
		return date;
	}


	public void setDate(DateTime date) {
		this.date = date;
	}


	public String getAuthorSsoId() {
		return authorSsoId;
	}


	public void setAuthorSsoId(String authorSsoId) {
		this.authorSsoId = authorSsoId;
	}


	public String getAuthorName() {
		return authorName;
	}


	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}


	public String getMessType() {
		return messType;
	}


	public void setMessType(String messType) {
		this.messType = messType;
	}


	public Long getMessCommentedId() {
		return messCommentedId;
	}


	public void setMessCommentedId(Long messCommentedId) {
		this.messCommentedId = messCommentedId;
	}


	public Collection<UserAuxEnt> getListOfUserLike() {
		return listOfUserLike;
	}


	public void setListOfUserLike(Set<UserAuxEnt> listOfUserLike) {
		this.listOfUserLike = listOfUserLike;
	}

	

	public Set<UserAuxEnt> getListOfUserShare() {
		return listOfUserShare;
	}


	public void setListOfUserShare(Set<UserAuxEnt> listOfUserShare) {
		this.listOfUserShare = listOfUserShare;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorName == null) ? 0 : authorName.hashCode());
		result = prime * result + ((authorSsoId == null) ? 0 : authorSsoId.hashCode());
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((messCommentedId == null) ? 0 : messCommentedId.hashCode());
		result = prime * result + ((messType == null) ? 0 : messType.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (authorName == null) {
			if (other.authorName != null)
				return false;
		} else if (!authorName.equals(other.authorName))
			return false;
		if (authorSsoId == null) {
			if (other.authorSsoId != null)
				return false;
		} else if (!authorSsoId.equals(other.authorSsoId))
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (listOfUserLike == null) {
			if (other.listOfUserLike != null)
				return false;
		} else if (!listOfUserLike.equals(other.listOfUserLike))
			return false;
		if (listOfUserShare == null) {
			if (other.listOfUserShare != null)
				return false;
		} else if (!listOfUserShare.equals(other.listOfUserShare))
			return false;
		if (messCommentedId == null) {
			if (other.messCommentedId != null)
				return false;
		} else if (!messCommentedId.equals(other.messCommentedId))
			return false;
		if (messType == null) {
			if (other.messType != null)
				return false;
		} else if (!messType.equals(other.messType))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Message [id=" + id + ", message=" + message + ", date=" + date + ", authorSsoId=" + authorSsoId
				+ ", authorName=" + authorName + ", messType=" + messType + ", comments=" + comments + ", messCommentedId="
				+ messCommentedId + "]";
	}







}