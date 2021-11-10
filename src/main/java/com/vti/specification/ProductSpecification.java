	package com.vti.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.vti.entity.Product;

public class ProductSpecification implements Specification<Product>{

	private static final long serialVersionUID = 1L;
	
	private String field;
	private String operator;
	private Object value;
	
	public ProductSpecification(String field, String operator, Object value) {
		super();
		this.field = field;
		this.operator = operator;
		this.value = value;
	}

	@Override
	public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (operator.equalsIgnoreCase("LIKE")) {		
				return builder.like(root.get(field), "%" + value.toString() + "%");		
		}
		
		if (operator.equalsIgnoreCase("=")) {			
			if (field.equalsIgnoreCase("brand.brandName")) {
				return builder.equal(root.get("brand").get("brandName"), value.toString());
//				return builder.like(root.get("brand").get("brandName"), "%" + value.toString() + "%");
			}else if (field.equalsIgnoreCase("memory.memoryName")) {
				return builder.equal(root.get("memory").get("memoryName"),value.toString());
			}else if (field.equalsIgnoreCase("ram.ramName")) {
				return builder.equal(root.get("ram").get("ramName"),value.toString());
			}else if (field.equalsIgnoreCase("category")) {
				return builder.equal(root.get("category"),value.toString());
			}
		}
		
		return null;
	}

}
