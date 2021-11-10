package com.vti.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.entity.Product;
import com.vti.request.ProductFilterRequest;
import com.vti.request.ProductRequest;

public interface IProductService {

	public Page<Product> getAllProducts(String category,Pageable pageable, String search, ProductFilterRequest filter);
	
	public Product getProductById(int id);
	
	public Product findByProductName(String name);
	
	public void deleteProduct(int id);
	
	public Page<Product> findAllOrderByPriceDesc(Pageable pageable);
	
	public Page<Product> findAllOrderByPriceAsc(Pageable pageable);
	
	public void createProduct(ProductRequest request);
	
	public void updateProduct(int productID, ProductRequest request);
}
