package com.hcl.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.model.Cart;
import com.hcl.model.Order;
import com.hcl.model.OrderItem;
import com.hcl.model.User;
import com.hcl.repo.CartItemRepo;
import com.hcl.repo.OrderItemRepo;
import com.hcl.repo.OrderRepo;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private OrderItemRepo orderItemRepo;

	@Autowired
	private CartItemRepo cartItemRepo;

	@Override
	public boolean createOrder(Order order, User user) {

		if (user == null)
			return false;
		if (order == null)
			return false;

		order.setUser(user);
		order.getOrderItems().forEach(x -> x.setOrder(order));
		double sum = order.getOrderItems().stream().mapToInt(x -> x.getItemQuantity()).sum();
		// Can't create order if quantity less than zero or too many
		if (sum <= 0 || sum > Integer.MAX_VALUE)
			return false;
		order.setTotalNumItems((int) sum);
		double price = order.getOrderItems().stream().mapToDouble(x -> x.getItemQuantity() * x.getProduct().getPrice())
				.sum();
		if (price <= 0)
			return false;
		order.setTotalCost(price);
		orderRepo.save(order);
		return true;
	}

	@Override
	public Order generateOrder(Cart cart) {
		if (cart == null)
			return null;

		Order order = new Order();
		order.setUser(cart.getUser());
		order.setOrderStatus("Started/Printing shipping label");
		order.setTaxRate(0.725);
		order.setOrderItems(cartItemRepo.findAllByCart(cart).stream().map(x -> {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemQuantity(x.getItemQty());
			orderItem.setProduct(x.getProduct());
			orderItem.setOrder(order);
			return orderItem;
		}).collect(Collectors.toSet()));
		if (order.getOrderItems().isEmpty())
			return null;
		return order;
	}

	public Order getById(Long id) {

		return orderRepo.findById(id).orElse(null);

	}

	public boolean updateOrder(String status, Long orderId) {
		if (status == null || orderId == null)
			return false;

		Order order = orderRepo.findById(orderId).orElse(null);

		if (order == null)
			return false;

		order.setOrderStatus(status);

		orderRepo.save(order);

		return true;

	}

	public void deleteById(Long id) {
		if (id == null)
			return;
		orderRepo.deleteById(id);

	}

	@Override
	public Order findOrderById(User user, Long orderId) {

		if (user == null || orderId == null)
			return null;

		Order order = orderRepo.findById(orderId).orElse(null);

		if (order == null)
			return null;

		return order;
	}

	@Override
	public List<Order> findAllOrdersByUser(User user) {
		if (user == null)
			return null;
		return orderRepo.findAllByUser(user);

	}

}