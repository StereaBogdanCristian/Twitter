package com.twitter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "USERAUXENT_TWITTER")
public class UserAuxEnt {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String userAuxEntName;
	
	private String userAuxEntSsoId;

	public UserAuxEnt() {
	}

	public UserAuxEnt(String userAuxEntSsoId, String userAuxEntName) {
		this.userAuxEntName = userAuxEntName;
		this.userAuxEntSsoId = userAuxEntSsoId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserAuxEntName() {
		return userAuxEntName;
	}

	public void setUserAuxEntName(String userAuxEntName) {
		this.userAuxEntName = userAuxEntName;
	}

	public String getUserAuxEntSsoId() {
		return userAuxEntSsoId;
	}

	public void setUserAuxEntSsoId(String userAuxEntSsoId) {
		this.userAuxEntSsoId = userAuxEntSsoId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userAuxEntName == null) ? 0 : userAuxEntName.hashCode());
		result = prime * result + ((userAuxEntSsoId == null) ? 0 : userAuxEntSsoId.hashCode());
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
		UserAuxEnt other = (UserAuxEnt) obj;
		if (userAuxEntName == null) {
			if (other.userAuxEntName != null)
				return false;
		} else if (!userAuxEntName.equals(other.userAuxEntName))
			return false;
		if (userAuxEntSsoId == null) {
			if (other.userAuxEntSsoId != null)
				return false;
		} else if (!userAuxEntSsoId.equals(other.userAuxEntSsoId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserAuxEnt [id=" + id + ", userAuxEntName=" + userAuxEntName + ", userAuxEntSsoId=" + userAuxEntSsoId
				+ "]";
	}
	
	
	
}
