package com.mph.services;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Optional;

import java.util.Date;
import java.util.Calendar;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.OrderService;
import com.mph.services.interfaces.SuborderService;
import com.mph.services.interfaces.SuborderStatusService;
import com.mph.services.interfaces.AddressService;
import com.mph.services.interfaces.CreditCardService;
import com.mph.services.interfaces.PromotionService;
import com.mph.services.interfaces.DeliveryOptionService;
import com.mph.services.interfaces.UserService;
import com.mph.services.interfaces.ShoppingCartService;
import com.mph.services.interfaces.ProductService;
import com.mph.services.interfaces.PaymentService;

import com.mph.services.exceptions.InsufficientQuantityAvailableException;
import com.mph.services.exceptions.ExpiredCreditCardException;
import com.mph.services.exceptions.ExpiredPromotionException;
import com.mph.services.exceptions.PaymentFailedException;
import com.mph.services.exceptions.OrderNotFoundException;
import com.mph.services.exceptions.NonCancellableOrderException;

import com.mph.repositories.interfaces.OrderRepository;

import com.mph.entities.Order;
import com.mph.entities.Suborder;
import com.mph.entities.SuborderStatus;
import com.mph.entities.Address;
import com.mph.entities.CreditCard;
import com.mph.entities.Promotion;
import com.mph.entities.DeliveryOption;
import com.mph.entities.User;
import com.mph.entities.ShoppingCart;
import com.mph.entities.ShoppingItem;
import com.mph.entities.Product;
import com.mph.entities.Payment;
import com.mph.entities.PaymentStatus;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private SuborderService suborderService;

	@Autowired
	private SuborderStatusService suborderStatusService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private CreditCardService creditCardService;

	@Autowired
	private PromotionService promotionService;

	@Autowired
	private DeliveryOptionService deliveryOptionService;

	@Autowired
	private UserService userService;

	@Autowired
	private ShoppingCartService cartService;

	@Autowired
	private ProductService productService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Order addOrder(long shippingAddressId, long billingAddressId, long creditCardId, long promotionId, long deliveryOptionId, String username) {

		Address shippingAddress = addressService.getAddressById(shippingAddressId);
		Address billingAddress = addressService.getAddressById(billingAddressId);

		CreditCard creditCard = creditCardService.getCreditCardById(creditCardId);

		checkCreditCard(creditCard);

		Optional<Promotion> optionalPromotion = promotionService.getPromotionById(promotionId);

		DeliveryOption deliveryOption = deliveryOptionService.getDeliveryOptionById(deliveryOptionId);

		User user = userService.getUserByUsername(username);

		ShoppingCart cart = cartService.getCartByUserUsername(username);

		Date orderDate = new Date();
		Date deliveryDate = addDaysToDate(orderDate, deliveryOption.getTime());

		List<ShoppingItem> items = cart.getItems();

		checkItems(items);

		Order order = Order.of(0F, orderDate, deliveryDate, deliveryOption, shippingAddress, billingAddress, 
			user, new ArrayList<Suborder>(), new ArrayList<Payment>());

		order = orderRepository.save(order);

		List<User> sellers = getSellersFromItems(items);

		for(User seller: sellers) {

			Suborder suborder = suborderService.addSuborder(order, seller, new ArrayList<ShoppingItem>());

			List<ShoppingItem> list = getItemsBySeller(seller, items);

			for(ShoppingItem item: list) {

				item.setSuborder(suborder);

				Product product = item.getProduct();

				productService.updateProductQuantityAvailable(product.getId(), product.getQuantityAvailable() - item.getQuantity());

			}

			suborder.addItems(list);

			order.addSuborder(suborder);

		}

		float amount = cart.getAmount();
		float deliveryOptionPrice = deliveryOption.getPrice();
		float total = 0;

		if(optionalPromotion.isPresent()) {

			Promotion promotion = optionalPromotion.get();

			checkPromotion(promotion);

			float percentage = promotion.getPercentage();

			total = (float) (amount - (amount * (percentage / 100)) + deliveryOptionPrice + (amount - (amount * (percentage / 100)) + deliveryOptionPrice) * (billingAddress.getCountry().getVAT() / 100));

			order.setPromotion(promotion);

		} else
			total = (float) (amount + deliveryOptionPrice + (amount + deliveryOptionPrice) * (billingAddress.getCountry().getVAT() / 100));

		order.setAmount(total);

		Payment payment = paymentService.addPayment(total, creditCard, order);

		order.addPayment(payment);

		order = orderRepository.save(order);

		cartService.resetCart(cart);

		if(payment.getStatus().getName().equals(PaymentStatus.PaymentStatusEnum.FAILED)) {

			cancelOrder(order.getId());

			throw new PaymentFailedException();

		}

		order.getSuborders().forEach(suborder -> suborder.setStatus(suborderStatusService.getSuborderStatusById(2)));

		order = orderRepository.save(order);

		return order;

	}

	@Override
	public Order cancelOrder(long orderId) {

		Order order = getOrderById(orderId);

		if(!isCancellable(order))
			throw new NonCancellableOrderException();

		for(Suborder suborder: order.getSuborders()) {

			suborder.setStatus(suborderStatusService.getSuborderStatusById(5));

			for(ShoppingItem item: suborder.getItems()) {
	
				Product product = item.getProduct();
	
				productService.updateProductQuantityAvailable(product.getId(), product.getQuantityAvailable() + item.getQuantity());

			}

		}

		order = orderRepository.save(order);

		return order;

	}

	@Override
	public Order getOrderById(long orderId) {

		Optional<Order> optionalOrder = orderRepository.findById(orderId);

		if(optionalOrder.isEmpty())
			throw new OrderNotFoundException();

		Order order = optionalOrder.get();

		return order;

	}

	@Override
	public List<Order> getOrdersByUserUsername(String username) {
		return orderRepository.findByUserUsernameOrderByIdDesc(username);
	}

	private List<User> getSellersFromItems(List<ShoppingItem> items) {

		List<User> sellers = new ArrayList<User>();

		for(ShoppingItem item: items) {

			User seller = item.getProduct().getSeller();

			if(!sellers.contains(seller))
				sellers.add(seller);

		}

		return sellers;

	}

	@Override
	public List<Order> getOrdersBySellerUsername(String username) {

		List<Order> orders = new ArrayList<Order>();

		for(Order order: orderRepository.findBySellerUsername(username)) {

			for(Suborder suborder: order.getSuborders()) {

				if(username.equals(suborder.getSeller().getUsername())) {

					orders.add(order);

					break;

				}

			}

		}

		return orders;

	}

	private List<ShoppingItem> getItemsBySeller(User seller, List<ShoppingItem> items) {

		List<ShoppingItem> list = new ArrayList<ShoppingItem>();

		for(ShoppingItem item: items) {

			if(seller.equals(item.getProduct().getSeller()))
				list.add(item);

		}

		return list;

	}

	private void checkCreditCard(CreditCard creditCard) {

		if(creditCard.getExpirationDate().compareTo(new Date()) < 0)
			throw new ExpiredCreditCardException();

	}

	private void checkPromotion(Promotion promotion) {

		if(promotion.getExpirationDate().compareTo(new Date()) < 0)
			throw new ExpiredPromotionException();

	}

	private void checkItems(List<ShoppingItem> items) {

		boolean error = false;

		for(ShoppingItem item: items) {

			Product product = item.getProduct();

			if(product.getQuantityAvailable() < item.getQuantity()) {

				error = true;

				cartService.deleteItem(item.getId());	

			}	

		}

		if(error)
			throw new InsufficientQuantityAvailableException();

	}

	private boolean isCancellable(Order order) {

		for(Suborder suborder: order.getSuborders()) {

			if(!Arrays.asList
				(
					SuborderStatus.SuborderStatusEnum.NEW, 
					SuborderStatus.SuborderStatusEnum.IN_PROGRESS
				)
				.contains(suborder.getStatus().getName())
			)
				return false;

		}

		return true;

	}

	private Date addDaysToDate(Date date, int days) {

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE, days);

		return calendar.getTime();

	}

}