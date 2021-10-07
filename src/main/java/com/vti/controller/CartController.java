package com.vti.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.entity.Cart;
import com.vti.entity.CartDetail;
import com.vti.entity.Product;
import com.vti.exception.NotFoundException;
import com.vti.response.CartDetailResponse;
import com.vti.response.CartResponse;
import com.vti.response.ProductResponse;
import com.vti.service.ICartService;

@RestController
@RequestMapping(value = "api/v5/cart")
@CrossOrigin("*")
public class CartController {

	@Autowired
	private ICartService cartService;
	
	
	/**
	 * API lấy cart = accountID
	 */
	@PreAuthorize("hasAnyRole('User','Admin')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getCartById(@PathVariable(name = "id") int id){
		Cart cart = cartService.getCartbyId(id);
		if (cart == null) {
			throw new NotFoundException(String.format("Cart (ID = %s) is not found", id));
		}
		CartResponse cartResponse = new CartResponse(cart.getQuantity(), cart.getTotal_price());
		return new ResponseEntity<CartResponse>(cartResponse, HttpStatus.OK);		
	}
	
	
	/**
	 * API lấy ListCartDetail = CartID
	 */
	@PreAuthorize("hasAnyRole('User','Admin')")
	@GetMapping(value = "/{id}/cartDetails")
	public ResponseEntity<?> getListCartDetail(@PathVariable(name = "id") int id){
		Cart cart = cartService.getCartbyId(id);
		if (cart == null) {
			throw new NotFoundException(String.format("Cart (ID = %s) is not found", id));
		}
		List<CartDetail> listcartdetail = cart.getListCartDetail();
		List<CartDetailResponse> listRespone = new ArrayList<>();
		
		for (CartDetail cartDetail : listcartdetail) {
			CartDetailResponse cartDetailResponse = new CartDetailResponse();
			cartDetailResponse.setId(cartDetail.getCartdetail_id());
			cartDetailResponse.setPrice(cartDetail.getPrice());
			cartDetailResponse.setQuantity(cartDetail.getQuantity());
			Product product = cartDetail.getProduct();
			ProductResponse productResponse = new ProductResponse(product.getProductId(), product.getProductName(),
					product.getDescription(), product.getPrice(), product.getRam().getRamName(), product.getMemory().getMemoryName(),
					product.getBrand().getBrandName(), product.getCategory(), product.getQuantity(),product.getCamera(),product.getColor(),
					product.getScreenSize(),product.getOperatingSystem(), product.getChip(),product.getBattery(),
					product.getSim() ,product.getPathImage(),product.getDiscount() ,product.getEnterDate());
			cartDetailResponse.setProduct(productResponse);
			cartDetailResponse.setStatus(cartDetail.getStatus());
			listRespone.add(cartDetailResponse);
		}
		return new ResponseEntity<>(listRespone, HttpStatus.OK);
		
	}
}
