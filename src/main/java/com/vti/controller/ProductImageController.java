package com.vti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.request.ProductImageRequest;
import com.vti.service.IProductImageService;

@RestController
@RequestMapping(value = "api/v5/productimages")
@CrossOrigin("*")
public class ProductImageController {

	@Autowired
	private IProductImageService imgService;
	
	/**
	 * API create ProductIMG 
	 */
	
	@PostMapping(value = "/{productID}")
	public ResponseEntity<?> createOrder(@PathVariable(name = "productID") int productID,@RequestBody List<ProductImageRequest>  request){
		imgService.createIMGforProduct(productID, request);
		return new ResponseEntity<String>("Thêm ảnh thành công!!",  HttpStatus.CREATED);
	}
}
