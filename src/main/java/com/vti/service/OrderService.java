package com.vti.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vti.entity.Account;
import com.vti.entity.Cart;
import com.vti.entity.CartDetail;
import com.vti.entity.Order;
import com.vti.entity.OrderDetail;
import com.vti.entity.Product;
import com.vti.enumerate.CartDetailStatus;
import com.vti.enumerate.OrderStatusEnum;
import com.vti.event.OnSendOrderConfirmEndViaEmailEvent;
import com.vti.event.OnSendOrderConfirmViaEmailEvent;
import com.vti.exception.CustomerException;
import com.vti.exception.NotFoundException;
import com.vti.repository.IAccountRepository;
import com.vti.repository.ICartDetailRepository;
import com.vti.repository.ICartRepository;
import com.vti.repository.IOrderDetailRepository;
import com.vti.repository.IOrderRepository;
import com.vti.repository.IProductRepository;
import com.vti.request.OrderRequest;

@Service
public class OrderService implements IOrderService{

	@Autowired
	private IOrderRepository orderRepo;
	
	@Autowired
	private IAccountRepository accountRepo;
	
	@Autowired
	private ICartRepository cartRepo;
	
	@Autowired
	private IOrderDetailRepository orderDetailRepo;
	
	@Autowired
	private ICartDetailRepository cartDetailRepo;
	
	@Autowired
	private IProductRepository productRepo;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Override
	public Order getOrderByID(int orderID) {
		Order order = orderRepo.getById(orderID);
		return order;
	}
	
	/**
	 * Function tạo order
	 */
	
	@Override
	@Transactional
	public void createOrder(int accountID, OrderRequest request) throws CustomerException {
		Cart cart = cartRepo.getById(accountID);
		List<CartDetail> listCartDetail = cart.getListCartDetail();
		
		List<CartDetail> listCartDetail2 = new ArrayList<>();
		String check = "Order";
		
		/**
		 * Duyệt qua list cartDetail được lấy ra từ cart ( do cartID = accountID )
		 * lọc những cartDetail có status = order cho vào 1 list riêng
		 */
		for (CartDetail cartDetail : listCartDetail) {
			if (cartDetail.getStatus().toString().equals(check)) {
				listCartDetail2.add(cartDetail);
			}
		}
		int quantity = 0;
		Double totalPrice = 0.0;
		
		/**
		 * Duyệt qua list cartDetail có status là order
		 * Tính tổng số cartDetail và tổng tiền để gán vào khi tạo order
		 */
		for (CartDetail cartDetail : listCartDetail2) {
			 quantity = quantity + cartDetail.getQuantity();
			 totalPrice = totalPrice + (cartDetail.getPrice()*cartDetail.getQuantity()-cartDetail.getPrice()*cartDetail.getQuantity()*cartDetail.getProduct().getDiscount()/100);
		}
		Account account = accountRepo.getById(accountID);		
		Order order = new Order((short) quantity, totalPrice,request.getFullname(), request.getAddress(),request.getPhone() ,account);
		if (request.getAddress() == null || request.getPhone() == null || request.getFullname() == null) {
			throw new CustomerException("Thông tin giao nhận hàng chưa đầy đủ");
		}
		orderRepo.save(order);
		
		/**
		 * Duyệt qua list cartDetail có status là order
		 * Tạo ra các orderDetail tương ứng với cartDetail
		 */
		for (CartDetail cartDetail : listCartDetail2) {
			Product product = cartDetail.getProduct();
			OrderDetail orderDetail = new OrderDetail(cartDetail.getPrice(), (short) cartDetail.getQuantity(),
					order, product);
			orderDetailRepo.save(orderDetail);
			int cartID = cartDetail.getCart().getCart_id();
			
			/**
			 * Đính kèm functin update lại giá trị của cart khi xóa cartDetail có status là order
			 * update lại tổng số hàng tồn của từng product khi khách đặt hàng
			 */
			updateCartDown(cartID, cartDetail);	
			product.setQuantity((short) (product.getQuantity() - cartDetail.getQuantity()));
			productRepo.save(product);
		}
		/**
		 * Xóa các cartDetail tương ứng với orderDetail được tạo
		 */
		cartDetailRepo.deleteCartDetailbyCartAndStatus(CartDetailStatus.Order, cart.getCart_id());
	}

	public void updateCartDown(int cartID, CartDetail cartDetail) {
		Cart cart = cartRepo.getById(cartID);
		cart.setQuantity(cart.getQuantity() - cartDetail.getQuantity());
		cart.setTotal_price(cart.getTotal_price() - (cartDetail.getPrice()*cartDetail.getQuantity()));
		cartRepo.save(cart);
	}
	
	/**
	 * Function show all order cho admin
	 */
	
	@Override
	public Page<Order> getAllOrder(Pageable pageable) {
		
		return orderRepo.findAll(pageable);
	}
	
	/**
	 * Function filter order theo status cho admin
	 */

	@Override
	public Page<Order> getAllOrderByStatus(OrderStatusEnum status, Pageable pageable) {
		
		return orderRepo.findByStatus(status, pageable);
	}
	
	/**
	 * Function duyệt đơn hàng và gửi email thông báo cho khách
	 */

	@Override
	public void updateOrder(int orderID) {
		Order order = orderRepo.getById(orderID);
		Account account = order.getAccount();
		if (order.getStatus().toString() == "Not_Active") {
			order.setStatus(OrderStatusEnum.Active);
			orderRepo.save(order);
			sendConfirmOrderViaEmail(account.getEmail());
		}else if (order.getStatus().toString() == "Active") {
			order.setStatus(OrderStatusEnum.End);
			orderRepo.save(order);
		}	
	}
	
	/**
	 * Function hủy đơn hàng và gửi email thông báo cho khách
	 */

	@Override
	public void endOrder(int orderID, OrderRequest request) throws CustomerException {
		Order order = orderRepo.getById(orderID);
		if (order == null) {
			throw new NotFoundException("Đơn hàng không tồn tại");		
		}
		Account account = order.getAccount();
		for (OrderDetail orderDetail : order.getListOrderDetail()) {
			/**
			 * Ở đây sẽ set lại số hàng cho product nếu đơn hàng bỉ hủy
			 */
			Product product = orderDetail.getProduct();
			product.setQuantity((short) (product.getQuantity() + orderDetail.getQuantity()));
			productRepo.save(product);
		}
		/**
		 * admin sẽ viết mô tả lí do hủy đơn hàng
		 * khách có thể vào phần chi tiết đơn hàng để check
		 */
		order.setDescription(request.getDescription());
		order.setStatus(OrderStatusEnum.Delete);
		if (order.getDescription() == null) {
			throw new CustomerException("Lý do hủy đơn hàng không thể để trống");
		}
		orderRepo.save(order);
		
		sendConfirmEndOrderViaEmail(account.getEmail());
	}

	@Override
	public void deleteOrder(int orderID) {
		orderRepo.deleteById(orderID);
		
	}
	
	@Override
	public void sendConfirmOrderViaEmail(String email) {
		eventPublisher.publishEvent(new OnSendOrderConfirmViaEmailEvent(email));
		
	}
	
	@Override
	public void sendConfirmEndOrderViaEmail(String email) {
		eventPublisher.publishEvent(new OnSendOrderConfirmEndViaEmailEvent(email));
		
	}

	@Override
	public Page<Order> findByUserId(int accountID, Pageable pageable) {
		
		return orderRepo.findByUserId(accountID, pageable);
	}
}
