package com.vti.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "`Reset_Password_Token`", catalog = "heroku_1f3b8eab4ddb340")
public class ResetPasswordToken implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "token", nullable = false, length = 36, unique = true)
	private String token;

	@OneToOne(targetEntity = Account.class)
	@JoinColumn(nullable = false, name = "user_id")
	private Account account;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiryDate", nullable = false)
	private Date expiryDate;
	
	public ResetPasswordToken(String token, Account account) {
		this.token = token;
		this.account = account;

		// 1h
		expiryDate = new Date(System.currentTimeMillis() + 360000);
	}
}
