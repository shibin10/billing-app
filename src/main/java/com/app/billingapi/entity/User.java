
package com.app.billingapi.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.billingapi.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Table(name = "user")
@Entity
public class User implements UserDetails {
	private static final long serialVersionUID = 8591589972911374544L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid", nullable = false)
	private Long userId;

	@Column(name = "fullname", nullable = false)
	private String fullName;

	@Column(unique = true, length = 100, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String place;

	@Column(nullable = false)
	private String phone;

	@Column(name = "status", nullable = true)
	private UserStatus status;

	@CreationTimestamp
	@Column(updatable = false, name = "createdat")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updatedat")
	private Date updatedAt;

	@Column
	private LocalDateTime deletedAt;


	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_shop", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "shop_id"))
	private List<Shop> shops;


	@ManyToOne
	@JsonIgnore
    @JoinColumn(name = "referredby", referencedColumnName = "userid", nullable = true)
    private User referredBy;

    @OneToMany(mappedBy = "referredBy", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> referredUsers;
    
    @OneToMany(mappedBy = "owner")
    private List<Shop> ownedShops = new ArrayList<>();

	public User() {
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public String getFullName() {
		return fullName;
	}

	public User setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public User setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public User setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public User setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
		return this;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPlace() {
		return place;
	}

	public User setPlace(String place) {
		this.place = place;
		return this;
	}

	public String getPhone() {
		return phone;
	}



	public User setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public User getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(User referredBy) {
		this.referredBy = referredBy;
	}

	public List<User> getReferredUsers() {
		return referredUsers;
	}

	public void setReferredUsers(List<User> referredUsers) {
		this.referredUsers = referredUsers;
	}


	public List<Shop> getOwnedShops() {
		return ownedShops;
	}


	public void setOwnedShops(List<Shop> ownedShops) {
		this.ownedShops = ownedShops;
	}


	public UserStatus getStatus() {
		return status;
	}

	public User setStatus(UserStatus status) {
		this.status = status;
		return this;

	}
	

	public List<Shop> getShops() {
		return shops;
	}


	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}


	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", fullName=" + fullName + ", email=" + email + ", password=" + password
				+ ", place=" + place + ",  phone=" + phone + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", deltedAt=" + deletedAt + "roles=" + roles + ",status=" + status
				+ "]";
	}

	

}