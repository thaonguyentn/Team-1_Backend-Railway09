package com.vti.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "Rate", catalog = "heroku_1f3b8eab4ddb340")
public class Rate implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(name = "rate_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rateId;
	
	@Column(name = "star")
	private Double star;
	
	@Column(name = "`comment`", length = 255)
	private String comment;
	
	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	@Cascade(value = { CascadeType.REMOVE, CascadeType.SAVE_UPDATE })
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	@Cascade(value = { CascadeType.REMOVE, CascadeType.SAVE_UPDATE })
	private Product product;
	
	@Column(name = "rate_date")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date rateDate;
	
	public Rate() {
		// TODO Auto-generated constructor stub
	}

	public Rate(Double star, String comment, Account account, Product product, Date rate_date) {
		super();
		this.star = star;
		this.comment = comment;
		this.account = account;
		this.product = product;
		this.rateDate = rate_date;
	}

	public Double getStar() {
		return star;
	}

	public void setStar(Double star) {
		this.star = star;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getRate_date() {
		return rateDate;
	}

	public void setRate_date(Date rate_date) {
		this.rateDate = rate_date;
	}

	public int getRate_id() {
		return rateId;
	}
}
