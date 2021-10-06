package com.vti.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "ProductBrand", catalog = "heroku_1f3b8eab4ddb340")
public class ProductBrand implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "productBrand_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private short productBrandId;

	@Column(name = "brand_name", length = 100, nullable = false, unique = true)
	private String brandName;
	
	@OneToMany(mappedBy = "brand")
	@Cascade(value = { CascadeType.REMOVE, CascadeType.SAVE_UPDATE })
	private List<Product> listProduct;
	
	public ProductBrand() {
		// TODO Auto-generated constructor stub
	}

	public ProductBrand(short productBrandId, String brandName) {
		super();
		this.productBrandId = productBrandId;
		this.brandName = brandName;
	}

	public int getProductBrandId() {
		return productBrandId;
	}

	public void setProductBrandId(short productBrandId) {
		this.productBrandId = productBrandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public List<Product> getListProduct() {
		return listProduct;
	}

	public void setListProduct(List<Product> listProduct) {
		this.listProduct = listProduct;
	}

	@Override
	public String toString() {
		return "ProductBrand [productBrandId=" + productBrandId + ", brandName=" + brandName + "]";
	}
	
}
