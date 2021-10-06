package com.vti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.entity.CartDetail;
import com.vti.entity.OrderDetail;
import com.vti.entity.Product;
import com.vti.repository.ICartDetailRepository;
import com.vti.repository.IOrderDetailRepository;
import com.vti.repository.IProductRepository;

@Service
public class OrderDetailService implements IOrderDetailService{

	@Autowired
	private IOrderDetailRepository orderDetailRepo;
	
	@Autowired
	private ICartDetailRepository cartDetailRepo;
	
	@Autowired
	private IProductRepository productRepo;
	
	@Override
	public void createOrderDetail(int id) {
		CartDetail cartDetail = cartDetailRepo.getById(id);
		Product product = productRepo.getById(cartDetail.getProduct().getProduct_id()) ;
		OrderDetail orderDetail = new OrderDetail(cartDetail.getPrice(), (short) cartDetail.getQuantity(), null, product);
		
	}

}
