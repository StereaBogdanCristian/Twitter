package com.twitter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table( name = "SHARE_TWITTER" )
@NamedQuery(name="deleteAllSharesOfUser", query="DELETE from Share where messAuthorSsoId = ? or userSsoId = ?")
public class Share {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private long messId;
	
	private String messAuthorSsoId;
	
	private String userSsoId;
	
	private String userName;
	
	@Column(name="DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime date;

	
	public Share(){
	}
	

	public Share(long messId, String messAuthorSsoId, String userSsoId, String userName, DateTime date) {
		super();
		this.messId = messId;
		this.messAuthorSsoId = messAuthorSsoId;
		this.userSsoId = userSsoId;
		this.userName = userName;
		this.date = date;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getMessId() {
		return messId;
	}

	public void setMessId(long messId) {
		this.messId = messId;
	}

	public String getUserSsoId() {
		return userSsoId;
	}

	public void setUserSsoId(String userSsoId) {
		this.userSsoId = userSsoId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	

	public String getMessAuthorSsoId() {
		return messAuthorSsoId;
	}


	public void setMessAuthorSsoId(String messAuthorSsoId) {
		this.messAuthorSsoId = messAuthorSsoId;
	}


	public DateTime getDate() {
		return date;
	}


	public void setDate(DateTime date) {
		this.date = date;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + id;
		result = prime * result + ((messAuthorSsoId == null) ? 0 : messAuthorSsoId.hashCode());
		result = prime * result + (int) (messId ^ (messId >>> 32));
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((userSsoId == null) ? 0 : userSsoId.hashCode());
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
		Share other = (Share) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		if (messAuthorSsoId == null) {
			if (other.messAuthorSsoId != null)
				return false;
		} else if (!messAuthorSsoId.equals(other.messAuthorSsoId))
			return false;
		if (messId != other.messId)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userSsoId == null) {
			if (other.userSsoId != null)
				return false;
		} else if (!userSsoId.equals(other.userSsoId))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Share [id=" + id + ", messId=" + messId + ", messAuthorSsoId=" + messAuthorSsoId + ", userSsoId="
				+ userSsoId + ", userName=" + userName + ", date=" + date + "]";
	}



}
