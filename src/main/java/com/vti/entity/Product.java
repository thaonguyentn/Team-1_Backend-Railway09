package com.vti.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product", catalog = "heroku_1f3b8eab4ddb340")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "product_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;

	@Column(name = "product_name", length = 100, nullable = false)
	private String productName;

	@Column(name = "`description`", length = 1000)
	private String description;

	@Column(name = "discount", nullable = true)
	private short discount = 0;

	@Column(name = "price")
	private Double price;

	@Column(name = "path_image", length = 500)
	private String pathImage;

	@ManyToOne
	@JoinColumn(name = "productRam_id")
	private ProductRam ram;

	@ManyToOne
	@JoinColumn(name = "productMemory_id")
	private ProductMemory memory;

	@ManyToOne
	@JoinColumn(name = "productBrand_id")
	private ProductBrand brand;

	@Column(name = "category")
	private String category;

	@Column(name = "quantity")
	private short quantity;
	
	@Column(name = "camera", length = 50)
	private String camera;
	
	@Column(name = "color", length = 50)
	private String color;
	
	@Column(name = "screenSize", length = 50)
	private String screenSize;
	
	@Column(name = "operatingSystem", length = 50)
	private String operatingSystem;
	
	@Column(name = "chip", length = 50)
	private String chip;
	
	@Column(name = "battery", length = 50)
	private String battery;
	
	@Column(name = "sim", length = 50)
	private String sim;

	@Column(name = "enter_date")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date enterDate;

	@OneToMany(mappedBy = "productInImage")
	@Cascade(value = { CascadeType.ALL, CascadeType.SAVE_UPDATE })
	@Fetch(value = FetchMode.SUBSELECT)
	private List<ProductImage> listProductImage;

	@OneToMany(mappedBy = "productInCartdetail")
	@Cascade(value = { CascadeType.ALL, CascadeType.SAVE_UPDATE })
	@Fetch(value = FetchMode.SUBSELECT)
	private List<CartDetail> listCartDetail;

	@OneToMany(mappedBy = "productInOrder")
	@Cascade(value = { CascadeType.ALL, CascadeType.SAVE_UPDATE })
	@Fetch(value = FetchMode.SUBSELECT)
	private List<OrderDetail> listOrderDetail;

}
