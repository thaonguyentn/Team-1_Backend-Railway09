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
@Table(name = "ProductMemory", catalog = "heroku_1f3b8eab4ddb340")
public class ProductMemory implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "productMemory_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private short productMemoryId;

	@Column(name = "memory_name", length = 100, nullable = false, unique = true)
	private String memoryName;
	
	@OneToMany(mappedBy = "memory")
	@Cascade(value = { CascadeType.REMOVE, CascadeType.SAVE_UPDATE })
	private List<Product> listProduct;
	
	public ProductMemory() {
		// TODO Auto-generated constructor stub
	}

	public ProductMemory(short productMemoryId, String memoryName) {
		super();
		this.productMemoryId = productMemoryId;
		this.memoryName = memoryName;
	}

	public int getProductMemoryId() {
		return productMemoryId;
	}

	public void setProductMemoryId(short productMemoryId) {
		this.productMemoryId = productMemoryId;
	}

	public String getMemoryName() {
		return memoryName;
	}

	public void setMemoryName(String memoryName) {
		this.memoryName = memoryName;
	}

	public List<Product> getListProduct() {
		return listProduct;
	}

	public void setListProduct(List<Product> listProduct) {
		this.listProduct = listProduct;
	}

	@Override
	public String toString() {
		return "ProductMemory [productMemoryId=" + productMemoryId + ", memoryName=" + memoryName + "]";
	}
}
