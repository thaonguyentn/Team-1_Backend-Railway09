package com.vti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.entity.Product;
import com.vti.entity.ProductImage;
import com.vti.exception.NotFoundException;
import com.vti.repository.IProductImageRepository;
import com.vti.repository.IProductRepository;
import com.vti.request.ProductImageRequest;

@Service
public class ProductImageService implements IProductImageService{

	@Autowired
	private IProductImageRepository imageRepo;
	
	@Autowired
	private IProductRepository productRepo;

	@Override
	public void createIMGforProduct(int productID,List<ProductImageRequest>  request) {
		Product product = productRepo.getById(productID);
		if (product == null) {
			throw new NotFoundException("Sản phẩm không tồn tại");	
		}
		for (ProductImageRequest productImageRequest : request) {
		ProductImage img = new ProductImage(productImageRequest.getPathIMG(), product);
		imageRepo.save(img);
		}
		
	}

}
