package com.vti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vti.entity.Cart;
import com.vti.entity.CartDetail;
import com.vti.entity.Product;
import com.vti.enumerate.CartDetailStatus;
import com.vti.exception.CustomerException;
import com.vti.repository.ICartDetailRepository;
import com.vti.repository.ICartRepository;
import com.vti.repository.IProductRepository;

@Service
public class CartDetailService implements ICartDetailService {

	@Autowired
	private ICartDetailRepository cartdetailRepo;

	@Autowired
	private IProductRepository productRepo;

	@Autowired
	private ICartRepository cartRepo;

	@Override
	public Page<CartDetail> getAllCartDetail(Pageable pageable) {

		return cartdetailRepo.findAll(pageable);
	}
	
	/**
	 * Function cho hàng vào giỏ
	 * từ accountID sẽ lấy được cartID vì cả 2 được tạo cùng lúc
	 */

	@Override
	public void createCartDetail(int producId, int accountId) throws CustomerException {
		Cart cart = cartRepo.getById(accountId);
		Product product = productRepo.getById(producId);
		/**
		 * Check product còn hàng tồn không
		 */
		if (product.getQuantity() == 0) {
			throw new CustomerException("Sản phẩm không tồn tại hoặc đã hết hàng");
		}
		
		List<CartDetail> listCartDetail = cart.getListCartDetail();
		for (CartDetail cartDetail : listCartDetail) {
			/**
			 * Lọc qua những sản phẩm trong giỏ hàng
			 * Nếu khách đã cho loại hàng tương tự vào giỏ, thì chuyển thành up số lượng sản phẩm
			 */
			if (cartDetail.getProduct().getProductId() == product.getProductId()){
				updateCartDetailUp(cartDetail.getCartdetail_id());
				return;
			}		
		}			
		/**
		 * Nếu phù hợp các điều kiện thì cho khách bỏ hàng vào giỏ
		 * Update lại giá trị của cart tương ứng với cartdetail được thêm vào
		 */
		CartDetail cartDetail2 = new CartDetail();

		cartDetail2.setPrice(product.getPrice());
		cartDetail2.setQuantity(1);
		cartDetail2.setCart(cartRepo.getById(accountId));
		cartDetail2.setProduct(product);

		cartdetailRepo.save(cartDetail2);

		updateCartUp(accountId, cartDetail2);
	}
	
	/**
	 * Function xóa hàng trong giỏ
	 * Update lại giá trị của cart tương ứng với cartdetail được xóa
	 */

	@Override
	public void deleteCartDetail(int id) {
		CartDetail cartDetail = cartdetailRepo.getById(id);
		int cartID = cartDetail.getCart().getCart_id();

		updateCartDown(cartID, cartDetail);

		cartdetailRepo.deleteById(id);

	}
	
	/**
	 * Function thêm số sản phẩm của hàng trong giỏ
	 * Update lại giá trị của cart tương ứng với cartdetail được thêm vào
	 */

	@Override
	public void updateCartDetailUp(int id) {
		CartDetail cartDetail = cartdetailRepo.getById(id);
		int cartID = cartDetail.getCart().getCart_id();
		cartDetail.setQuantity(cartDetail.getQuantity() + 1);
		cartdetailRepo.save(cartDetail);
		updateCartUp(cartID, cartDetail);
	}
	
	/**
	 * Function giảm số sản phẩm của hàng trong giỏ
	 * Update lại giá trị của cart tương ứng với cartdetail được giảm đi
	 * Nếu số sản phẩm = 1 thì khi bấm giảm số sp sẽ = xóa sản phẩm
	 */

	@Override
	public void updateCartDetailDown(int id) {
		CartDetail cartDetail = cartdetailRepo.getById(id);
		int cartID = cartDetail.getCart().getCart_id();
		int quantity = cartDetail.getQuantity() - 1;
		cartDetail.setQuantity(quantity);

		if (quantity == 0) {
			updateCartDown(cartID, cartDetail);
			cartdetailRepo.deleteById(cartDetail.getCartdetail_id());

		} else {
			cartdetailRepo.save(cartDetail);
			updateCartDown(cartID, cartDetail);
		}
	}
	
	/**
	 * Function chọn sản phẩm cho vào order
	 * Khi khách hàng tích chọn 1 lần thì sp sẽ thay đổi trạng thái từ not_order -> order
	 * Bấm lần thứ 2 sẽ chuyển lại từ order -> not_order
	 */
	
	@Override
	public void updateStatusCartDetail(int id) {
		CartDetail cartDetail = cartdetailRepo.getById(id);
		String check = "Order";
		if (cartDetail.getStatus().toString().equals(check)) {
			cartDetail.setStatus(CartDetailStatus.Not_Order);
			cartdetailRepo.save(cartDetail);
			return;
		}
		cartDetail.setStatus(CartDetailStatus.Order);
		cartdetailRepo.save(cartDetail);
	}
	
	/**
	 * Function update giá trị cart khi thêm 1 sản phẩm vào giỏ
	 */

	public void updateCartUp(int cartID, CartDetail cartDetail) {
		Cart cart = cartRepo.getById(cartID);
		cart.setQuantity(cart.getQuantity() + 1);
		cart.setTotal_price(cart.getTotal_price() + cartDetail.getPrice());
		cartRepo.save(cart);
	}
	
	/**
	 * Function update giá trị cart khi bỏ sản phẩm 1 khỏi giỏ
	 */
	
	public void updateCartDown(int cartID, CartDetail cartDetail) {
		Cart cart = cartRepo.getById(cartID);
		cart.setQuantity(cart.getQuantity() - 1);
		cart.setTotal_price(cart.getTotal_price() - (cartDetail.getPrice()));
		cartRepo.save(cart);
	}
	
	/**
	 * Function update giá trị cart khi xóa 1 sản phẩm khỏi giỏ
	 */
	
	public void updateCartDownAll(int cartID, CartDetail cartDetail) {
		Cart cart = cartRepo.getById(cartID);
		cart.setQuantity(cart.getQuantity() - cartDetail.getQuantity());
		cart.setTotal_price(cart.getTotal_price() - (cartDetail.getPrice()*cartDetail.getQuantity()));
		cartRepo.save(cart);
	}

	@Override
	public CartDetail getCartDetailById(int id) {
		
		return cartdetailRepo.getById(id);
	}
}
