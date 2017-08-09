package com.twitter.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;

@SuppressWarnings("serial")
@Entity
@Table(name = "USERS_TWITTER")
public class User implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Size(min=3, max=30)
	@Column(name="SSO_ID", nullable=false)
	private String ssoId;
	
	@Size(min=3, max=30)
	@Column(name="NAME", nullable=false)
	private String name;
	
	@Email
	@Column(name="EMAIL", nullable=false)
	private String email;
	
	@Column(name="JOINING_DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate joiningDate;
	
	@NotEmpty
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="STATUS")
	private String status;
	
	@NotEmpty
	@Column(name="PASSWORD", nullable=false)
	private String password;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="FOLLOWING_FOLLOWER_TWITTER", joinColumns={@JoinColumn(name="UserId")}, 
							   inverseJoinColumns={@JoinColumn(name="FollowingId")})
	private Collection<User> listOfFriends = new HashSet<User>();

	@ManyToMany(mappedBy = "listOfFriends", fetch=FetchType.EAGER)
	private Collection<User> whosFriend = new HashSet<User>();
	
	
	
	public User() {
	}



	public User(String ssoId, String name, String email, LocalDate joiningDate, String gender, String status,
			String password, Collection<User> listOfFriends, Collection<User> whosFriend) {
		this.ssoId = ssoId;
		this.name = name;
		this.email = email;
		this.joiningDate = joiningDate;
		this.gender = gender;
		this.status = status;
		this.password = password;
		this.listOfFriends = listOfFriends;
		this.whosFriend = whosFriend;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getSsoId() {
		return ssoId;
	}



	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public LocalDate getJoiningDate() {
		return joiningDate;
	}



	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public Collection<User> getListOfFriends() {
		return listOfFriends;
	}



	public void setListOfFriends(Collection<User> listOfFriends) {
		this.listOfFriends = listOfFriends;
	}



	public Collection<User> getWhosFriend() {
		return whosFriend;
	}



	public void setWhosFriend(Collection<User> whosFriend) {
		this.whosFriend = whosFriend;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((joiningDate == null) ? 0 : joiningDate.hashCode());
		result = prime * result + ((listOfFriends == null) ? 0 : listOfFriends.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((ssoId == null) ? 0 : ssoId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((whosFriend == null) ? 0 : whosFriend.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (joiningDate == null) {
			if (other.joiningDate != null)
				return false;
		} else if (!joiningDate.equals(other.joiningDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ssoId == null) {
			if (other.ssoId != null)
				return false;
		} else if (!ssoId.equals(other.ssoId))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", ssoId=" + ssoId + ", name=" + name + "]";
	}

	

}
