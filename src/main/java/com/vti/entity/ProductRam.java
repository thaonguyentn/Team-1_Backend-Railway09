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
@Table(name = "ProductRam", catalog = "heroku_1f3b8eab4ddb340")
public class ProductRam implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "productRam_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private short productRamId;

	@Column(name = "ram_name", length = 100, nullable = false, unique = true)
	private String ramName;
	
	@OneToMany(mappedBy = "ram")
	@Cascade(value = { CascadeType.REMOVE, CascadeType.SAVE_UPDATE })
	private List<Product> listProduct;

	public ProductRam() {
		// TODO Auto-generated constructor stub
	}

	public ProductRam(short productRamId, String ramName) {
		super();
		this.productRamId = productRamId;
		this.ramName = ramName;
	}

	public int getProductRamId() {
		return productRamId;
	}

	public void setProductRamId(short productRamId) {
		this.productRamId = productRamId;
	}

	public String getRamName() {
		return ramName;
	}

	public void setRamName(String ramName) {
		this.ramName = ramName;
	}

	public List<Product> getListProduct() {
		return listProduct;
	}

	public void setListProduct(List<Product> listProduct) {
		this.listProduct = listProduct;
	}

	@Override
	public String toString() {
		return "ProductRam [productRamId=" + productRamId + ", ramName=" + ramName + "]";
	}
	
}
