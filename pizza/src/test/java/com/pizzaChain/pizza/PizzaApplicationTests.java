package com.pizzaChain.pizza;

import com.pizzaChain.pizza.enums.PizzaSize;
import com.pizzaChain.pizza.enums.PizzaType;
import com.pizzaChain.pizza.exceptions.InvalidPizzaException;
import com.pizzaChain.pizza.model.Pizza;
import com.pizzaChain.pizza.repository.PizzaRepository;
import com.pizzaChain.pizza.service.impl.PizzaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PizzaApplicationTests {

	@Test
	void contextLoads() {
	}
	@InjectMocks
	private PizzaServiceImpl pizzaService;

	@Mock
	private PizzaRepository pizzaRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createPizza_ShouldReturnPizza() {
		Pizza pizza = new Pizza();
		pizza.setId(UUID.randomUUID().toString());

		when(pizzaRepository.save(pizza)).thenReturn(pizza);

		Pizza createdPizza = pizzaService.createPizza(pizza);
		assertNotNull(createdPizza);
		assertEquals(pizza.getId(), createdPizza.getId());
		verify(pizzaRepository, times(1)).save(pizza);
	}

	@Test
	void updatePizzaById_ShouldReturnUpdatedPizza() throws InvalidPizzaException {
		String pizzaId = "123";
		Pizza existingPizza = new Pizza();
		existingPizza.setId(pizzaId);
		existingPizza.setName("Old Name");

		Pizza updatedPizza = new Pizza();
		updatedPizza.setName("New Name");
		updatedPizza.setPrice(20L);
		updatedPizza.setDescription("New Description");

		when(pizzaRepository.findById(pizzaId)).thenReturn(Optional.of(existingPizza));
		when(pizzaRepository.save(existingPizza)).thenReturn(existingPizza);

		Pizza result = pizzaService.updatePizzaById(pizzaId, updatedPizza);

		assertNotNull(result);
		assertEquals("New Name", result.getName());
		assertEquals(20.0, result.getPrice());
		assertEquals("New Description", result.getDescription());
		verify(pizzaRepository, times(1)).findById(pizzaId);
		verify(pizzaRepository, times(1)).save(existingPizza);
	}

	@Test
	void updatePizzaById_ShouldThrowExceptionIfPizzaNotFound() {
		String pizzaId = "123";
		Pizza updatedPizza = new Pizza();

		when(pizzaRepository.findById(pizzaId)).thenReturn(Optional.empty());

		assertThrows(InvalidPizzaException.class, () -> pizzaService.updatePizzaById(pizzaId, updatedPizza));
		verify(pizzaRepository, times(1)).findById(pizzaId);
		verify(pizzaRepository, times(0)).save(any());
	}

	@Test
	void viewAllPizzas_ShouldReturnPizzaList() {
		Pizza pizza1 = new Pizza();
		Pizza pizza2 = new Pizza();
		List<Pizza> pizzas = Arrays.asList(pizza1, pizza2);

		when(pizzaRepository.findAll()).thenReturn(pizzas);

		List<Pizza> result = pizzaService.viewAllPizzas();
		assertNotNull(result);
		assertEquals(2, result.size());
		verify(pizzaRepository, times(1)).findAll();
	}

	@Test
	void viewPizzaById_ShouldReturnPizza() throws InvalidPizzaException {
		String pizzaId = "123";
		Pizza pizza = new Pizza();
		pizza.setId(pizzaId);

		when(pizzaRepository.findById(pizzaId)).thenReturn(Optional.of(pizza));

		Pizza result = pizzaService.viewPizzaById(pizzaId);
		assertNotNull(result);
		assertEquals(pizzaId, result.getId());
		verify(pizzaRepository, times(1)).findById(pizzaId);
	}

	@Test
	void viewPizzaById_ShouldThrowExceptionIfPizzaNotFound() {
		String pizzaId = "123";

		when(pizzaRepository.findById(pizzaId)).thenReturn(Optional.empty());

		assertThrows(InvalidPizzaException.class, () -> pizzaService.viewPizzaById(pizzaId));
		verify(pizzaRepository, times(1)).findById(pizzaId);
	}

	@Test
	void deletePizza_ShouldReturnSuccessMessage() {
		String pizzaId = "123";

		doNothing().when(pizzaRepository).deleteById(pizzaId);

		String result = pizzaService.deletePizza(pizzaId);
		assertEquals("Pizza deleted successfully", result);
		verify(pizzaRepository, times(1)).deleteById(pizzaId);
	}

	@Test
	void getPizzasByType_ShouldReturnPizzaList() {
		Pizza pizza1 = new Pizza();
		Pizza pizza2 = new Pizza();
		PizzaType type = PizzaType.MARGHERITA;
		List<Pizza> pizzas = Arrays.asList(pizza1, pizza2);

		when(pizzaRepository.findByTypeContaining(type)).thenReturn(pizzas);

		List<Pizza> result = pizzaService.getPizzasByType(type);
		assertNotNull(result);
		assertEquals(2, result.size());
		verify(pizzaRepository, times(1)).findByTypeContaining(type);
	}

	@Test
	void getPizzasBySize_ShouldReturnPizzaList() {
		Pizza pizza1 = new Pizza();
		Pizza pizza2 = new Pizza();
		PizzaSize size = PizzaSize.MEDIUM;
		List<Pizza> pizzas = Arrays.asList(pizza1, pizza2);

		when(pizzaRepository.findBySizeContaining(size)).thenReturn(pizzas);

		List<Pizza> result = pizzaService.getPizzasBySize(size);
		assertNotNull(result);
		assertEquals(2, result.size());
		verify(pizzaRepository, times(1)).findBySizeContaining(size);
	}

}
