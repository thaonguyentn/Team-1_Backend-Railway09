package com.vti.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.entity.Product;
import com.vti.entity.ProductImage;
import com.vti.exception.NotFoundException;
import com.vti.repository.IProductRepository;
import com.vti.request.ProductFilterRequest;
import com.vti.request.ProductRequest;
import com.vti.response.ProductImagesRespone;
import com.vti.response.ProductResponse;
import com.vti.service.IProductService;

@RestController
@RequestMapping(value = "api/v2/products")
@CrossOrigin("*")
public class ProductController {

	@Autowired
	private IProductRepository productRepository;

	@Autowired
	private IProductService productService;

	/**
	 * API getAll Product Search theo ProductName Filter theo brand - memory - ram
	 * fix trả ra thêm list ProductImgResponse
	 */

	@GetMapping()
	public ResponseEntity<?> getAllProducts(Pageable pageable, @RequestParam(required = false) String search,
			ProductFilterRequest filter) {
		Page<Product> entities = productService.getAllProducts(pageable, search, filter);

		Page<ProductResponse> pageResponse = entities.map(new Function<Product, ProductResponse>() {

			@Override
			public ProductResponse apply(Product product) {
				List<ProductImage> listIMG = product.getListProductImage();
				List<ProductImagesRespone> listResponse = new ArrayList<>();
				for (ProductImage productImage : listIMG) {
					ProductImagesRespone imgresponse = new ProductImagesRespone(productImage.getImage_id(), 
							productImage.getProduct().getProductId(), productImage.getPath_image());
					listResponse.add(imgresponse);
				}
				
				ProductResponse response = new ProductResponse(product.getProductId(), product.getProductName(),
						product.getDescription(), product.getPrice(), product.getRam().getRamName(),
						product.getMemory().getMemoryName(), product.getBrand().getBrandName(), product.getCategory(),
						product.getQuantity(), product.getCamera(), product.getColor(), product.getScreenSize(),
						product.getOperatingSystem(), product.getChip(), product.getBattery(), product.getSim(),
						product.getPathImage(), product.getDiscount(),listResponse, product.getEnterDate());
				return response;
			}
		});
		return new ResponseEntity<>(pageResponse, HttpStatus.OK);
	}

	/**
	 * API getAll Product Filter theo price down
	 * fix trả ra thêm list ProductImgResponse
	 */

	@RequestMapping(value = "/desc", method = RequestMethod.GET)
	public ResponseEntity<?> findAllOrderByPriceDesc(Pageable pageable) {
		Page<Product> entities = productService.findAllOrderByPriceDesc(pageable);

		Page<ProductResponse> pageResponse = entities.map(new Function<Product, ProductResponse>() {

			@Override
			public ProductResponse apply(Product product) {
				List<ProductImage> listIMG = product.getListProductImage();
				List<ProductImagesRespone> listResponse = new ArrayList<>();
				for (ProductImage productImage : listIMG) {
					ProductImagesRespone imgresponse = new ProductImagesRespone(productImage.getImage_id(), 
							productImage.getProduct().getProductId(), productImage.getPath_image());
					listResponse.add(imgresponse);
				}
				ProductResponse response = new ProductResponse(product.getProductId(), product.getProductName(),
						product.getDescription(), product.getPrice(), product.getRam().getRamName(),
						product.getMemory().getMemoryName(), product.getBrand().getBrandName(), product.getCategory(),
						product.getQuantity(), product.getCamera(), product.getColor(), product.getScreenSize(),
						product.getOperatingSystem(), product.getChip(), product.getBattery(), product.getSim(),
						product.getPathImage(), product.getDiscount(),listResponse, product.getEnterDate());
				return response;
			}
		});
		return new ResponseEntity<>(pageResponse, HttpStatus.OK);
	}

	/**
	 * API getAll Product Filter theo price up
	 * fix trả ra thêm list ProductImgResponse
	 */

	@RequestMapping(value = "/asc", method = RequestMethod.GET)
	public ResponseEntity<?> findAllOrderByPriceAsc(Pageable pageable) {
		Page<Product> entities = productService.findAllOrderByPriceAsc(pageable);

		Page<ProductResponse> pageResponse = entities.map(new Function<Product, ProductResponse>() {

			@Override
			public ProductResponse apply(Product product) {
				List<ProductImage> listIMG = product.getListProductImage();
				List<ProductImagesRespone> listResponse = new ArrayList<>();
				for (ProductImage productImage : listIMG) {
					ProductImagesRespone imgresponse = new ProductImagesRespone(productImage.getImage_id(), 
							productImage.getProduct().getProductId(), productImage.getPath_image());
					listResponse.add(imgresponse);
				}
				ProductResponse response = new ProductResponse(product.getProductId(), product.getProductName(),
						product.getDescription(), product.getPrice(), product.getRam().getRamName(),
						product.getMemory().getMemoryName(), product.getBrand().getBrandName(), product.getCategory(),
						product.getQuantity(), product.getCamera(), product.getColor(), product.getScreenSize(),
						product.getOperatingSystem(), product.getChip(), product.getBattery(), product.getSim(),
						product.getPathImage(), product.getDiscount(),listResponse, product.getEnterDate());
				return response;
			}
		});
		return new ResponseEntity<>(pageResponse, HttpStatus.OK);
	}

	/**
	 * API getProduct by ProductID
	 * fix trả ra thêm list ProductImgResponse
	 */

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getProductByID(@PathVariable(name = "id") short id) {
		Product product = productService.getProductById(id);
		if (product == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		List<ProductImage> listIMG = product.getListProductImage();
		List<ProductImagesRespone> listResponse = new ArrayList<>();
		for (ProductImage productImage : listIMG) {
			ProductImagesRespone imgresponse = new ProductImagesRespone(productImage.getImage_id(), 
					productImage.getProduct().getProductId(), productImage.getPath_image());
			listResponse.add(imgresponse);
		}
		ProductResponse response = new ProductResponse(product.getProductId(), product.getProductName(),
				product.getDescription(), product.getPrice(), product.getRam().getRamName(),
				product.getMemory().getMemoryName(), product.getBrand().getBrandName(), product.getCategory(),
				product.getQuantity(), product.getCamera(), product.getColor(), product.getScreenSize(),
				product.getOperatingSystem(), product.getChip(), product.getBattery(), product.getSim(),
				product.getPathImage(), product.getDiscount(),listResponse, product.getEnterDate());

		return new ResponseEntity<ProductResponse>(response, HttpStatus.OK);
	}

	/**
	 * API deleteProduct by ProductID
	 */
	
	@PreAuthorize("hasRole('Admin')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") int id) {
		Product product = productService.getProductById(id);
		if (product == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		productService.deleteProduct(id);
		return new ResponseEntity<String>("Delete successfully!", HttpStatus.OK);
	}
	
	/**
	 * API create Product 
	 */
	@PreAuthorize("hasRole('Admin')")
	@PostMapping
	public ResponseEntity<?> createProduct(@RequestBody ProductRequest request){
		productService.createProduct(request);
		return new ResponseEntity<String>("Create successfully!", HttpStatus.OK);	
	}
	
	/**
	 * API update Product 
	 */
	@PreAuthorize("hasRole('Admin')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable(name = "id") int productId, @RequestBody ProductRequest request){
		productService.updateProduct(productId, request);
		return new ResponseEntity<String>("Update successfully!", HttpStatus.OK);		
	}

	/**
	 * API lấy listProductImage by ProductID
	 */

	@GetMapping(value = "/{id}/images")
	public ResponseEntity<?> ProductImgesDetail(@PathVariable(name = "id") Integer productId) {
		Product product = productRepository.findById(productId).orElse(null);
		if (product == null) {
			throw new NotFoundException(String.format("Product (ID = %s) is not found", productId));
		}
		List<ProductImage> productImages = product.getListProductImage();
		List<ProductImagesRespone> listRespone = new ArrayList<>();
		for (ProductImage productImage : productImages) {
			String pathRespone = productImage.getPath_image();
			ProductImagesRespone respone = new ProductImagesRespone(productImage.getImage_id(),
					productImage.getProduct().getProductId(), pathRespone);
			listRespone.add(respone);
		}
		return new ResponseEntity<>(listRespone, HttpStatus.OK);
	}

}
