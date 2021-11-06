package com.vti.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vti.entity.Rate;

public interface IRateRepository extends JpaRepository<Rate, Long>{
	@Query(value = " select *from rates where product_id = ?", nativeQuery = true)
	Page<Rate> findByProductId(Long id,Pageable pageable);
	
	@Query(value = " select AVG(star) from rates where product_id=?",nativeQuery = true)
	Double getAvgByProduct(Long id);
}
