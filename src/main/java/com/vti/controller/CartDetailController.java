package com.vti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.entity.CartDetail;
import com.vti.entity.Product;
import com.vti.exception.CustomerException;
import com.vti.response.CartDetailResponse;
import com.vti.response.ProductResponse;
import com.vti.service.ICartDetailService;

@RestController
@RequestMapping(value = "api/v4/cartdetail")
@CrossOrigin("*")
public class CartDetailController {

	@Autowired
	private ICartDetailService cartdetailService;
	
	/**
	 * API create CartDetail by productId and accountId
	 * Thông số sẽ đồng bộ với cart có cartID tương ứng accountID
	 * @throws CustomerException 
	 */
	@PreAuthorize("hasAnyRole('User','Admin')")
	@PostMapping
	public ResponseEntity<?> createCartDetail(@RequestParam(name = "productId") int productId,@RequestParam(name = "accountId") int accountId) throws CustomerException{
		cartdetailService.createCartDetail(productId, accountId);
		return new ResponseEntity<String>("Create successfully!!",
				HttpStatus.CREATED);	
	}
	
	/**
	 * API get CartDetail by ID
	 */
	@PreAuthorize("hasAnyRole('User','Admin')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getCartDetailById(@PathVariable(name = "id") int id){
		CartDetail cartDetail = cartdetailService.getCartDetailById(id);
		if (cartDetail == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
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
		
		return new ResponseEntity<>(cartDetailResponse, HttpStatus.OK);
	}
	
	/**
	 * API delete CartDetail by CartdetailID
	 * Thông số sẽ đồng bộ với cart có cartID tương ứng accountID
	 */
	@PreAuthorize("hasAnyRole('User','Admin')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteCartDetail(@PathVariable(name = "id") int id) {
		CartDetail cartDetail = cartdetailService.getCartDetailById(id);
		if (cartDetail == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		cartdetailService.deleteCartDetail(id);
		return new ResponseEntity<String>("Delete successfully!", HttpStatus.OK);
	}
	
	/**
	 * API update CartDetail by CartdetailID ( tăng quantity )
	 * Thông số sẽ đồng bộ với cart có cartID tương ứng accountID
	 */
	@PreAuthorize("hasAnyRole('User','Admin')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateCartDetailUp(@PathVariable(name = "id") int id) {
		CartDetail cartDetail = cartdetailService.getCartDetailById(id);
		if (cartDetail == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		cartdetailService.updateCartDetailUp(id);
		return new ResponseEntity<String>("Update successfully!", HttpStatus.OK);		
	}
	
	/**
	 * API update CartDetail by CartdetailID ( giảm quantity )
	 * Thông số sẽ đồng bộ với cart có cartID tương ứng accountID
	 */
	@PreAuthorize("hasAnyRole('User','Admin')")
	@PutMapping()
	public ResponseEntity<?> updateCartDetailDown(@RequestParam(name = "id") int id) {
		CartDetail cartDetail = cartdetailService.getCartDetailById(id);
		if (cartDetail == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		cartdetailService.updateCartDetailDown(id);
		return new ResponseEntity<String>("Update successfully!", HttpStatus.OK);	
	}
	
	/**
	 * API update CartDetailStatus -> order
	 */
	@PreAuthorize("hasAnyRole('User','Admin')")
	@PostMapping(value = "/{id}")
	public ResponseEntity<?> updateStatusCartDetail(@PathVariable(name = "id") int id){
		CartDetail cartDetail = cartdetailService.getCartDetailById(id);
		if (cartDetail == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		cartdetailService.updateStatusCartDetail(id);
		return new ResponseEntity<String>("Update successfully!", HttpStatus.OK);	
	}
}
