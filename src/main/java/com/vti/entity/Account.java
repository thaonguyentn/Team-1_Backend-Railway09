package com.vti.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;

import com.vti.enumerate.AccountGenderEnum;
import com.vti.enumerate.AccountRole;
import com.vti.enumerate.AccountStatus;

@Entity
@Table(name = "`Account`", catalog = "heroku_1f3b8eab4ddb340")
public class Account implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "account_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountId;

	@Column(name = "username", length = 50, nullable = false, unique = true)
	private String username;

	@Column(name = "fullname", length = 50, nullable = false)
	private String fullname;
	
	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	private AccountGenderEnum gender = AccountGenderEnum.Unknow;
	
	@Column(name = "email", length = 50, nullable = false, unique = true)
	private String email;
	
	@Column(name = "phone_number", length = 12, nullable = false, unique = true)
	private String phonenumber;
	
	@Column(name = "address", length = 500)
	private String address;
	
	@Column(name = "path_image", length = 500)
	private String pathImage;
	
	@Column(name = "password", length = 255)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "`status`")
	private AccountStatus status = AccountStatus.Not_Active;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "`role`")
	private AccountRole role = AccountRole.User;
	
	@Column(name = "register_date")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date registerDate;
	
	@OneToOne(mappedBy = "cartaccount")
	private Cart cart;
	
	@OneToMany(mappedBy = "userId")
	@Cascade(value = { CascadeType.ALL, CascadeType.SAVE_UPDATE })
	private List<Order> listOrder;
	
	public Account() {
		// TODO Auto-generated constructor stub
	}

	public Account(String username, String fullname, String email, String password) {
		super();
		this.username = username;
		this.fullname = fullname;
		this.email = email;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public AccountGenderEnum getGender() {
		return gender;
	}

	public void setGender(AccountGenderEnum gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPathImage() {
		return pathImage;
	}

	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public AccountRole getRole() {
		return role;
	}

	public void setRole(AccountRole role) {
		this.role = role;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<Order> getListOrder() {
		return listOrder;
	}

	public void setListOrder(List<Order> listOrder) {
		this.listOrder = listOrder;
	}

	public int getAccountId() {
		return accountId;
	}
	
}
