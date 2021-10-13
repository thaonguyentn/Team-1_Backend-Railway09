package com.vti.service;

import java.util.List;

import com.vti.request.ProductImageRequest;

public interface IProductImageService {	
	
	public void createIMGforProduct(int productID,List<ProductImageRequest>  request);
	
}
