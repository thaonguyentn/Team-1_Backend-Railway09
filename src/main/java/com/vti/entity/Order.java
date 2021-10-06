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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;

import com.vti.enumerate.OrderStatusEnum;

@Entity
@Table(name = "`Order`", catalog = "heroku_1f3b8eab4ddb340")
public class Order implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(name = "order_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	
	@Column(name = "`description`", length = 1000)
	private String description;
	
	@Column(name = "fullname", length = 50, nullable = false)
	private String fullname;
	
	@Column(name = "quantity")
	private short quantity;
	
	@Column(name = "total_price")
	private Double totalPrice;
	
	@Column(name = "address", length = 500)
	private String address;
	
	@Column(name = "phone", length = 12)
	private String phone;
	
	@Column(name = "order_date")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date orderDate;
	
	@Column(name = "`status`")
	@Enumerated(EnumType.STRING)
	private OrderStatusEnum status = OrderStatusEnum.Not_Active;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@Cascade(value = { CascadeType.ALL, CascadeType.SAVE_UPDATE })
	private Account userId;
	
	@OneToMany(mappedBy = "order")
	@Cascade(value = { CascadeType.ALL, CascadeType.SAVE_UPDATE })
	private List<OrderDetail> listOrderDetail;
	
	public Order() {
		
	}

	public Order(short quantity, Double totalPrice,String fullname, String address,String phone, Account userId) {
		super();
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.fullname = fullname;
		this.address = address;
		this.phone = phone;
		this.userId = userId;
	}

	public short getQuantity() {
		return quantity;
	}

	public void setQuantity(short quantity) {
		this.quantity = quantity;
	}

	public Double getTotal_price() {
		return totalPrice;
	}

	public void setTotal_price(Double total_price) {
		this.totalPrice = total_price;
	}

	public Date getOrder_date() {
		return orderDate;
	}

	public void setOrder_date(Date order_date) {
		this.orderDate = order_date;
	}

	public OrderStatusEnum getStatus() {
		return status;
	}

	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}

	public Account getAccount() {
		return userId;
	}

	public void setAccount(Account account) {
		this.userId = account;
	}

	public List<OrderDetail> getListOrderDetail() {
		return listOrderDetail;
	}

	public void setListOrderDetail(List<OrderDetail> listOrderDetail) {
		this.listOrderDetail = listOrderDetail;
	}

	public int getOrder_id() {
		return orderId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Account getOrderAccount() {
		return userId;
	}

	public void setOrderAccount(Account userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
}
