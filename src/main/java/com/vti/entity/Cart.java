package com.vti.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "Cart", catalog = "heroku_1f3b8eab4ddb340")
public class Cart implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(name = "cart_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartId;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "total_price")
	private Double totalPrice;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "account_id")
	@Cascade(value = { CascadeType.ALL, CascadeType.SAVE_UPDATE })
	private Account cartaccount;
	
	@OneToMany(mappedBy = "cart")
	@Cascade(value = { CascadeType.ALL, CascadeType.SAVE_UPDATE })
	private List<CartDetail> listCartDetail;
	
	public Cart() {
		// TODO Auto-generated constructor stub
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getTotal_price() {
		return totalPrice;
	}

	public void setTotal_price(Double total_price) {
		this.totalPrice = total_price;
	}

	public Account getAccount() {
		return cartaccount;
	}

	public void setAccount(Account account) {
		this.cartaccount = account;
	}

	public List<CartDetail> getListCartDetail() {
		return listCartDetail;
	}

	public void setListCartDetail(List<CartDetail> listCartDetail) {
		this.listCartDetail = listCartDetail;
	}

	public int getCart_id() {
		return cartId;
	}
}
