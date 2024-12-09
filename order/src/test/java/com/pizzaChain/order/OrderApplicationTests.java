package com.pizzaChain.order;

import com.pizzaChain.order.enums.Status;
import com.pizzaChain.order.exception.InvalidOrderException;
import com.pizzaChain.order.model.Orders;
import com.pizzaChain.order.model.Pizza;
import com.pizzaChain.order.model.PizzaOrderItem;
import com.pizzaChain.order.repository.OrderRepository;
import com.pizzaChain.order.repository.PizzaOrderItemRepository;
import com.pizzaChain.order.service.feignClient.CustomerClient;
import com.pizzaChain.order.service.feignClient.PizzaClient;
import com.pizzaChain.order.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderApplicationTests {

	@Test
	void contextLoads() {
	}

	@InjectMocks
	private OrderServiceImpl orderService;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private PizzaOrderItemRepository pizzaOrderItemRepository;

	@Mock
	private CustomerClient customerClient;

	@Mock
	private PizzaClient pizzaClient;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createOrder_ShouldReturnOrder() {
		Orders order = new Orders();
		order.setCustomerId("customer1");
		PizzaOrderItem item = new PizzaOrderItem();
		item.setPizzaId("pizza1");
		item.setQuantity(2);
		order.setPizzaItems(Arrays.asList(item));

		Pizza pizza = new Pizza();
		pizza.setPrice(10L);
		when(pizzaClient.viewPizzaById("pizza1")).thenReturn(pizza);
		when(orderRepository.save(any(Orders.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

		Orders createdOrder = orderService.createOrder(order);

		assertNotNull(createdOrder);
		assertNotNull(createdOrder.getId());
		assertEquals(20L, createdOrder.getTotalPrice()); // 10 * 2
		assertEquals(Status.PENDING, createdOrder.getStatus().iterator().next());
		verify(orderRepository, times(1)).save(any(Orders.class));
	}

	@Test
	void getOrderById_ShouldReturnOrder() throws InvalidOrderException {
		String orderId = "order1";
		Orders order = new Orders();
		order.setId(orderId);

		when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

		Orders result = orderService.getOrderById(orderId);
		assertNotNull(result);
		assertEquals(orderId, result.getId());
		verify(orderRepository, times(1)).findById(orderId);
	}

	@Test
	void getOrderById_ShouldThrowExceptionIfOrderNotFound() {
		String orderId = "order1";

		when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

		assertThrows(InvalidOrderException.class, () -> orderService.getOrderById(orderId));
		verify(orderRepository, times(1)).findById(orderId);
	}

	@Test
	void updateOrderById_ShouldReturnUpdatedOrder() throws InvalidOrderException {
		String orderId = "order1";
		Orders existingOrder = new Orders();
		existingOrder.setId(orderId);
		existingOrder.setStatus(new HashSet<>(Arrays.asList(Status.PENDING)));

		Orders updatedOrder = new Orders();
		updatedOrder.setId(orderId);
		updatedOrder.setStatus(new HashSet<>(Arrays.asList(Status.DELIVERED)));

		when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
		when(orderRepository.save(existingOrder)).thenAnswer(invocation -> invocation.getArguments()[0]);

		Orders result = orderService.updateOrderById(orderId);
		assertNotNull(result);
		assertTrue(result.getStatus().contains(Status.DELIVERED));
		verify(orderRepository, times(1)).findById(orderId);
		verify(orderRepository, times(1)).save(existingOrder);
	}

	@Test
	void updateOrderById_ShouldThrowExceptionIfOrderNotFound() {
		String orderId = "order1";

		when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

		assertThrows(InvalidOrderException.class, () -> orderService.updateOrderById(orderId));
		verify(orderRepository, times(1)).findById(orderId);
		verify(orderRepository, times(0)).save(any(Orders.class));
	}

	@Test
	void getAllOrders_ShouldReturnOrderList() {
		Orders order1 = new Orders();
		Orders order2 = new Orders();
		List<Orders> orders = Arrays.asList(order1, order2);

		when(orderRepository.findAll()).thenReturn(orders);

		List<Orders> result = orderService.getAllOrders();
		assertNotNull(result);
		assertEquals(2, result.size());
		verify(orderRepository, times(1)).findAll();
	}

	@Test
	void getOrdersByCustomerId_ShouldReturnOrderList() {
		String customerId = "customer1";
		Orders order1 = new Orders();
		Orders order2 = new Orders();
		order1.setCustomerId(customerId);
		order2.setCustomerId(customerId);
		List<Orders> orders = Arrays.asList(order1, order2);

		when(orderRepository.findByCustomerId(customerId)).thenReturn(orders);

		List<Orders> result = orderService.getOrdersByCustomerId(customerId);
		assertNotNull(result);
		assertEquals(2, result.size());
		verify(orderRepository, times(1)).findByCustomerId(customerId);
	}
}
