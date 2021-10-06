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
@Table(name = "ProductImage", catalog = "heroku_1f3b8eab4ddb340")
public class ProductImage implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(name = "image_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int image_id;
	
	@Column(name = "path_image", length = 100)
	private String path_image;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	@Cascade(value = { CascadeType.REMOVE, CascadeType.SAVE_UPDATE })
	private Product productInImage;
	
	public ProductImage() {
		// TODO Auto-generated constructor stub
	}

	public ProductImage(String path_image, Product product) {
		super();
		this.path_image = path_image;
		this.productInImage = product;
	}

	public String getPath_image() {
		return path_image;
	}

	public void setPath_image(String path_image) {
		this.path_image = path_image;
	}

	public Product getProduct() {
		return productInImage;
	}

	public void setProduct(Product product) {
		this.productInImage = product;
	}

	public int getImage_id() {
		return image_id;
	}
}
