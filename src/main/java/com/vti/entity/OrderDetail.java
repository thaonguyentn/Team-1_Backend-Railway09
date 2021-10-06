package com.vti.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "OrderDetail", catalog = "heroku_1f3b8eab4ddb340")
public class OrderDetail implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(name = "orderdetail_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderdetailId;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "quantity")
	private short quantity;
	
	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	@Cascade(value = { CascadeType.REMOVE, CascadeType.SAVE_UPDATE })
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	@Cascade(value = { CascadeType.REMOVE, CascadeType.SAVE_UPDATE })
	private Product productInOrder;
	
	public OrderDetail() {
		// TODO Auto-generated constructor stub
	}

	public OrderDetail(Double price, short quantity, Order order, Product product) {
		super();
		this.price = price;
		this.quantity = quantity;
		this.order = order;
		this.productInOrder = product;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public short getQuantity() {
		return quantity;
	}

	public void setQuantity(short quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return productInOrder;
	}

	public void setProduct(Product product) {
		this.productInOrder = product;
	}

	public int getorderdetail_id() {
		return orderdetailId;
	}
}
