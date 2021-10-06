package com.vti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vti.entity.Product;
import com.vti.entity.ProductBrand;
import com.vti.entity.ProductMemory;
import com.vti.entity.ProductRam;
import com.vti.repository.IProductBrandRepository;
import com.vti.repository.IProductMemoryRepository;
import com.vti.repository.IProductRamRepository;
import com.vti.repository.IProductRepository;
import com.vti.request.ProductFilterRequest;
import com.vti.request.ProductRequest;
import com.vti.specification.ProductSpecification;

@Service
public class ProductService implements IProductService {

	@Autowired
	private IProductRepository product_repo;
	
	@Autowired
	private IProductRamRepository ramRepo;
	
	@Autowired
	private IProductMemoryRepository memoryRepo;
	
	@Autowired
	private IProductBrandRepository brandRepo;

	@SuppressWarnings("deprecation")
	@Override
	public Page<Product> getAllProducts(Pageable pageable, String search, ProductFilterRequest filter) {
		Specification<Product> where = null;

		if (!StringUtils.isEmpty(search)) {
			ProductSpecification nameSpecification = new ProductSpecification("productName", "LIKE", search);
			where = Specification.where(nameSpecification);
		}

		if (filter != null && filter.getBrandName() != null) {
			ProductSpecification brandFilter = new ProductSpecification("brand.brandName", "=", filter.getBrandName());
			if (where == null) {
				where = Specification.where(brandFilter);
			} else {
				where = where.and(brandFilter);
			}
		}

		if (filter != null && filter.getMemoryName() != null) {
			ProductSpecification memoryFilter = new ProductSpecification("memory.memoryName", "=",
					filter.getMemoryName());
			if (where == null) {
				where = Specification.where(memoryFilter);
			} else {
				where = where.and(memoryFilter);
			}
		}

		if (filter != null && filter.getRamName() != null) {
			ProductSpecification memoryFilter = new ProductSpecification("ram.ramName", "=", filter.getRamName());
			if (where == null) {
				where = Specification.where(memoryFilter);
			} else {
				where = where.and(memoryFilter);
			}
		}
		return product_repo.findAll(where, pageable);
	}

	@Override
	public Product getProductById(int id) {

		return product_repo.getById(id);
	}

	@Override
	public void deleteProduct(int id) {
		product_repo.deleteById(id);

	}

	@Override
	public Page<Product> findAllOrderByPriceDesc(Pageable pageable) {
		
		return product_repo.findAllOrderByPriceDesc(pageable);
	}

	@Override
	public Page<Product> findAllOrderByPriceAsc(Pageable pageable) {
		
		return product_repo.findAllOrderByPriceAsc(pageable);
	}

	@Override
	public void createProduct(ProductRequest request) {
		Product product = new Product();
		ProductBrand productBrand = brandRepo.findByBrandName(request.getBrand());
		ProductMemory productMemory = memoryRepo.findByMemoryName(request.getMemory());
		ProductRam productRam = ramRepo.findByRamName(request.getRam());
		
		product.setProductName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setRam(productRam);
		product.setMemory(productMemory);
		product.setBrand(productBrand);
		product.setCategory(request.getCategory());
		product.setQuantity(request.getQuantity());
		product.setCamera(request.getCamera());
		product.setColor(request.getColor());
		product.setScreenSize(request.getScreenSize());
		product.setOperatingSystem(request.getOperatingSystem());
		product.setChip(request.getChip());
		product.setBattery(request.getBattery());
		product.setSim(request.getSim());
		product.setDiscount(request.getDiscount());
		product.setPathImage(request.getImage());
		
		product_repo.save(product);
	}

	@Override
	public void updateProduct(int productID, ProductRequest request) {
		Product product = product_repo.getById(productID);	
		ProductBrand productBrand = brandRepo.findByBrandName(request.getBrand());
		ProductMemory productMemory = memoryRepo.findByMemoryName(request.getMemory());
		ProductRam productRam = ramRepo.findByRamName(request.getRam());
		
		product.setProductName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setRam(productRam);
		product.setMemory(productMemory);
		product.setBrand(productBrand);
		product.setCategory(request.getCategory());
		product.setQuantity(request.getQuantity());
		product.setCamera(request.getCamera());
		product.setColor(request.getColor());
		product.setScreenSize(request.getScreenSize());
		product.setOperatingSystem(request.getOperatingSystem());
		product.setChip(request.getChip());
		product.setBattery(request.getBattery());
		product.setSim(request.getSim());
		product.setDiscount(request.getDiscount());
		product.setPathImage(request.getImage());
		
		product_repo.save(product);
	}

	@Override
	public Product findByProductName(String name) {
		Product product = product_repo.findByProductName(name);
		return product;
	}
}
