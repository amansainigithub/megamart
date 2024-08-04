package com.coder.springjwt.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(	name = "users",
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String username;



	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@Size(max = 10)
	private String mobile;

	@Column(name = "isMobileVerify", columnDefinition = "varchar(255) default 'N'")
	private String isMobileVerify;

	@Column(name = "isEmailVerify", columnDefinition = "varchar(255) default 'N'")
	private String isEmailVerify;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@Size(max = 200)
	private String passKey;

	@Size(max = 200)
	private String custUsername;

	@Size(max = 20)
	private String mobileOtp;

	@Size(max = 20)
	@NotBlank
	private String projectRole;

	@Column(name = "registrationCompleted", columnDefinition = "varchar(255) default 'N'")
	private String registrationCompleted ;

	@Column(length = 50)
	private String forgotPasswordOtp ;

	@Column(length = 10)
	private String isForgotPassword;

	@Column(length = 100)
	private String forgotPasswordDate;

	@Column(length = 100)
	private String forgotPasswordTime;

	@Column(length = 100)
	private String forgotPasswordDateTime;


	public User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(String username, String email, String password,String custUsername) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.custUsername = custUsername;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getPassKey() {
		return passKey;
	}

	public void setPassKey(String passKey) {
		this.passKey = passKey;
	}

	public String getCustUsername() {
		return custUsername;
	}

	public void setCustUsername(String custUsername) {
		this.custUsername = custUsername;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIsMobileVerify() {
		return isMobileVerify;
	}

	public void setIsMobileVerify(String isMobileVerify) {
		this.isMobileVerify = isMobileVerify;
	}

	public String getIsEmailVerify() {
		return isEmailVerify;
	}

	public void setIsEmailVerify(String isEmailVerify) {
		this.isEmailVerify = isEmailVerify;
	}

	public String getMobileOtp() {
		return mobileOtp;
	}

	public void setMobileOtp(String mobileOtp) {
		this.mobileOtp = mobileOtp;
	}


	public String getRegistrationCompleted() {
		return registrationCompleted;
	}

	public void setRegistrationCompleted(String registrationCompleted) {
		this.registrationCompleted = registrationCompleted;
	}


	public User(Long id, String username, String email, String password, String mobile, String isMobileVerify, String isEmailVerify, Set<Role> roles, String passKey, String custUsername, String mobileOtp, String projectRole, String registrationCompleted, String forgotPasswordOtp) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
		this.isMobileVerify = isMobileVerify;
		this.isEmailVerify = isEmailVerify;
		this.roles = roles;
		this.passKey = passKey;
		this.custUsername = custUsername;
		this.mobileOtp = mobileOtp;
		this.projectRole = projectRole;
		this.registrationCompleted = registrationCompleted;
		this.forgotPasswordOtp = forgotPasswordOtp;
	}

	public String getForgotPasswordOtp() {
		return forgotPasswordOtp;
	}

	public void setForgotPasswordOtp(String forgotPasswordOtp) {
		this.forgotPasswordOtp = forgotPasswordOtp;
	}

	public String getProjectRole() {
		return projectRole;
	}

	public void setProjectRole(String projectRole) {
		this.projectRole = projectRole;
	}

	public String getIsForgotPassword() {
		return isForgotPassword;
	}

	public void setIsForgotPassword(String isForgotPassword) {
		this.isForgotPassword = isForgotPassword;
	}


	public String getForgotPasswordDate() {
		return forgotPasswordDate;
	}

	public void setForgotPasswordDate(String forgotPasswordDate) {
		this.forgotPasswordDate = forgotPasswordDate;
	}

	public String getForgotPasswordTime() {
		return forgotPasswordTime;
	}

	public void setForgotPasswordTime(String forgotPasswordTime) {
		this.forgotPasswordTime = forgotPasswordTime;
	}

	public String getForgotPasswordDateTime() {
		return forgotPasswordDateTime;
	}

	public void setForgotPasswordDateTime(String forgotPasswordDateTime) {
		this.forgotPasswordDateTime = forgotPasswordDateTime;
	}
}
