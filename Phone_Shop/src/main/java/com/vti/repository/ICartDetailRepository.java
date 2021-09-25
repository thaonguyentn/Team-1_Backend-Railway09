package com.vti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vti.entity.CartDetail;

@Repository
public interface ICartDetailRepository extends JpaRepository<CartDetail, Long>{
	@Query(value = "select * from cart_details where cart_id = ?", nativeQuery = true)
	List<CartDetail> getByCartId(Long id);
	
	@Query(value = "select COUNT(cart_id) from cart_details where cart_id = ?", nativeQuery = true)
	Long getCount(Long id);	
}
