package com.coder.springjwt.models;

import com.coder.springjwt.models.customerPanelModels.CustomerOrders;
import com.coder.springjwt.models.customerPanelModels.address.CustomerAddress;
import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(	name = "users",
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User extends BaseEntity {
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

	@Size(max = 10)
	private String sellerMobile;

	@Column(name = "customerMobileVerify", columnDefinition = "varchar(255) default 'N'")
	private String customerMobileVerify;

	@Column(name = "customerEmailVerify", columnDefinition = "varchar(255) default 'N'")
	private String customerEmailVerify;

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
	private String customerMobileOtp;

	@Size(max = 20)
	@NotBlank
	private String projectRole;

	@Size(max = 20)
	//@NotBlank
	private String firstName;

	@Size(max = 20)
	//@NotBlank
	private String lastName;

	@Column(name = "customerRegisterComplete", columnDefinition = "varchar(255) default 'N'")
	private String customerRegisterComplete ;

	@Column(length = 50)
	private String customerForgotPasswordOtp ;

	@Column(length = 10)
	private String isCustomerForgotPassword;

	@Size(max = 100)
	@Email
	@Column(unique = true)
	private String customerEmail;

	@Size(max = 100)
	@Email
	@Column(unique = true)
	private String sellerEmail;

	@Column(length = 255)
	private String browserDetails;

	@Column(length = 255)
	private String userAgent;

	@Column(length = 255)
	private String userAgentVersion;

	@Column(length = 255)
	private String operatingSystem;

	@Column(length = 255)
	private String browserName;

	@Column(length = 20)
	private String customerGender;

	@Column(length = 300)
	private String emailVerificationToken;

	@Column(length = 50)
	private LocalDateTime emailTokenExpiryTime;  // Expiry timestamp

	@OneToMany(mappedBy = "user")
	private List<CustomerOrders> customerOrders;

	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<CustomerAddress> customerAddresses;

	public User() {
	}

	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}

	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}

	public LocalDateTime getEmailTokenExpiryTime() {
		return emailTokenExpiryTime;
	}

	public void setEmailTokenExpiryTime(LocalDateTime emailTokenExpiryTime) {
		this.emailTokenExpiryTime = emailTokenExpiryTime;
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

	public String getCustomerGender() {
		return customerGender;
	}

	public void setCustomerGender(String customerGender) {
		this.customerGender = customerGender;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", mobile='" + mobile + '\'' +
				", sellerMobile='" + sellerMobile + '\'' +
				", customerMobileVerify='" + customerMobileVerify + '\'' +
				", customerEmailVerify='" + customerEmailVerify + '\'' +
				", roles=" + roles +
				", passKey='" + passKey + '\'' +
				", custUsername='" + custUsername + '\'' +
				", customerMobileOtp='" + customerMobileOtp + '\'' +
				", projectRole='" + projectRole + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", customerRegisterComplete='" + customerRegisterComplete + '\'' +
				", customerForgotPasswordOtp='" + customerForgotPasswordOtp + '\'' +
				", isCustomerForgotPassword='" + isCustomerForgotPassword + '\'' +
				", customerEmail='" + customerEmail + '\'' +
				", sellerEmail='" + sellerEmail + '\'' +
				", browserDetails='" + browserDetails + '\'' +
				", userAgent='" + userAgent + '\'' +
				", userAgentVersion='" + userAgentVersion + '\'' +
				", operatingSystem='" + operatingSystem + '\'' +
				", browserName='" + browserName + '\'' +
				", customerGender='" + customerGender + '\'' +
				", emailVerificationToken='" + emailVerificationToken + '\'' +
				", emailTokenExpiryTime=" + emailTokenExpiryTime +
				", customerOrders=" + customerOrders +
				", customerAddresses=" + customerAddresses +
				'}';
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


	public String getCustomerEmailVerify() {
		return customerEmailVerify;
	}

	public void setCustomerEmailVerify(String customerEmailVerify) {
		this.customerEmailVerify = customerEmailVerify;
	}

	public String getCustomerMobileOtp() {
		return customerMobileOtp;
	}

	public void setCustomerMobileOtp(String customerMobileOtp) {
		this.customerMobileOtp = customerMobileOtp;
	}


	public String getCustomerRegisterComplete() {
		return customerRegisterComplete;
	}

	public void setCustomerRegisterComplete(String customerRegisterComplete) {
		this.customerRegisterComplete = customerRegisterComplete;
	}


	public String getProjectRole() {
		return projectRole;
	}

	public void setProjectRole(String projectRole) {
		this.projectRole = projectRole;
	}

	public String getCustomerForgotPasswordOtp() {
		return customerForgotPasswordOtp;
	}

	public void setCustomerForgotPasswordOtp(String customerForgotPasswordOtp) {
		this.customerForgotPasswordOtp = customerForgotPasswordOtp;
	}

	public String getIsCustomerForgotPassword() {
		return isCustomerForgotPassword;
	}

	public void setIsCustomerForgotPassword(String isCustomerForgotPassword) {
		this.isCustomerForgotPassword = isCustomerForgotPassword;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getBrowserDetails() {
		return browserDetails;
	}

	public void setBrowserDetails(String browserDetails) {
		this.browserDetails = browserDetails;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getUserAgentVersion() {
		return userAgentVersion;
	}

	public void setUserAgentVersion(String userAgentVersion) {
		this.userAgentVersion = userAgentVersion;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getCustomerMobileVerify() {
		return customerMobileVerify;
	}

	public void setCustomerMobileVerify(String customerMobileVerify) {
		this.customerMobileVerify = customerMobileVerify;
	}


	public String getSellerMobile() {
		return sellerMobile;
	}

	public void setSellerMobile(String sellerMobile) {
		this.sellerMobile = sellerMobile;
	}


	public List<CustomerOrders> getCustomerOrders() {
		return customerOrders;
	}

	public void setCustomerOrders(List<CustomerOrders> customerOrders) {
		this.customerOrders = customerOrders;
	}

	public List<CustomerAddress> getCustomerAddresses() {
		return customerAddresses;
	}

	public void setCustomerAddresses(List<CustomerAddress> customerAddresses) {
		this.customerAddresses = customerAddresses;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
