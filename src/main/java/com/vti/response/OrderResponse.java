package com.vti.response;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.vti.enumerate.OrderStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

	private int orderID;
	private String description;
	private String fullname;
	private String address;
	private String phone;
	private AccountResponse accountResponse;
	private short quantity;
	private Double totalPrice;
	private Date orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatusEnum status;
}
