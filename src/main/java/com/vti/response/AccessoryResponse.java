package com.vti.response;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class AccessoryResponse {
	private int id;
	private String name;
	private String description;
	private Double price;	
	private String category;
	private short quantity;
	private String color;
	private String image;
	private short discount;
	private List<ProductImagesRespone> listResponse;
	private Date enter_date;
}
