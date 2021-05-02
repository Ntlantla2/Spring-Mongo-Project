package com.java.momentum.dailypoints.controller;

import com.java.momentum.dailypoints.exception.ResourceNotFoundException;
import com.java.momentum.dailypoints.model.Customer;
import com.java.momentum.dailypoints.model.MomentumProduct;
import com.java.momentum.dailypoints.repository.CustomerRepository;
import com.java.momentum.dailypoints.repository.MomentumProductRepository;
import com.java.momentum.dailypoints.request.PurchaseProductRequest;
import com.java.momentum.dailypoints.request.response.PurchaseProductResponse;
import com.java.momentum.dailypoints.service.CustomerSequenceGeneratorService;
import com.java.momentum.dailypoints.service.ProductSequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MomentumDigitalPlatformController, this class is used as a central controller to expose all the service endpoint need
 * for a customer and the momentum products basic CRUD operation web service methods are exposed bellow.
 *
 * @author  Ntlantla Mngenela
 * @version 1.0
 * @since  2021
 */

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class MomentumDigitalPlatformController {
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerSequenceGeneratorService customerSequenceGeneratorService;

	@Autowired
	private MomentumProductRepository momentumProductRepository;

	@Autowired
	private ProductSequenceGeneratorService momentumProductsequenceGeneratorServiceCustomer;


	/**
	 * Retrieve and return all the customers from the database
	 *
	 * @return
	 */
	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	/**
	 *
	 * @param customerId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/getCustomer/{id}")
	public ResponseEntity<Customer> getCustomerByCustomerId(@PathVariable(value = "id") Long customerId)
			throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));
		return ResponseEntity.ok().body(customer);
	}

	/**
	 * create a customer and generate a unique identifier for the customer id primary key
	 *
	 * @param customer
	 * @return
	 */
	@PostMapping("/addCustomer")
	public Customer createCustomer(@Valid @RequestBody Customer customer) {
		customer.setId(customerSequenceGeneratorService.generateSequence(Customer.SEQUENCE_NAME));
		return customerRepository.save(customer);
	}

	/**
	 * Updates the customer information of the passed customer Id, this function will be the responsible for updating
	 * the customer point balance after a successful purchase. its responsible for any other customer updates such as point updates,
	 *
	 * @param customerId
	 * @param customerDetails
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@PutMapping("/updateCustomerDetails/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long customerId,
												   @Valid @RequestBody Customer customerDetails) throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));

		customer.setLastName(customerDetails.getLastName());
		customer.setFirstName(customerDetails.getFirstName());
		customer.setPoints(customerDetails.getPoints());
		final Customer updatedCustomer = customerRepository.save(customer);
		return ResponseEntity.ok(updatedCustomer);
	}

	/**
	 * Responsible for removing a customer from the system and database
	 *
	 * @param customerId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@DeleteMapping("/deleteCustomer/{id}")
	public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long customerId)
			throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));

		customerRepository.delete(customer);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}


	/******************************************************************************************************************
	 *																												 **
	 * -----------------------------------Momentum Product Operations------------------------------------------------**
	 * 																												 **
	 ******************************************************************************************************************/

	/**
	 * Retrieve and return all the customers from the database
	 *
	 * @return
	 */
	@GetMapping("/products")
	public List<MomentumProduct> getAllProducts() {
		return momentumProductRepository.findAll();
	}

	/**
	 * retrieves a product that matches the supplied product Id, if the product is found it will be returned else a resource
	 * not found exception will be returned to the user
	 *
	 * @param productId used to retrieve a product from the db
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/getProduct/{id}")
	public ResponseEntity<MomentumProduct> getProductByProductNo(@PathVariable(value = "id") Long productId)
			throws ResourceNotFoundException {
		MomentumProduct product = momentumProductRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + productId));
		return ResponseEntity.ok().body(product);
	}

	/**
	 * create a product and generate a unique identifier for the product id primary key
	 *
	 * @param product new product to be added to the db/system
	 *
	 * @return The created MomentumProduct
	 *
	 */
	@PostMapping("/addProduct")
	public MomentumProduct createProduct(@Valid @RequestBody MomentumProduct product) {
		product.setId(momentumProductsequenceGeneratorServiceCustomer.generateSequence(MomentumProduct.SEQUENCE_NAME));
		return momentumProductRepository.save(product);
	}

	/**
	 * Updates the customer information of the passed customer Id, this function will be the responsible for updating
	 * the customer point balance after a successful purchase. its responsible for any other customer updates such as point updates,
	 *
	 * @param productId to be updated
	 * @param productDetails the details that will be used to update the existing record/document
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@PutMapping("/updateProductDetails/{id}")
	public ResponseEntity<MomentumProduct> updateProduct(@PathVariable(value = "id") Long productId,
												   @Valid @RequestBody MomentumProduct productDetails) throws ResourceNotFoundException {
		MomentumProduct product = momentumProductRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));

		product.setProductDescription(productDetails.getProductDescription());
		product.setProductPointCost(productDetails.getProductPointCost());
		final MomentumProduct updatedCustomer = momentumProductRepository.save(product);
		return ResponseEntity.ok(updatedCustomer);
	}

	/**
	 * Responsible for removing a customer from the system and database
	 *
	 * @param productId to be deleted
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@DeleteMapping("/deleteProduct/{id}")
	public Map<String, Boolean> deleteProduct(@PathVariable(value = "id") Long productId)
			throws ResourceNotFoundException {
		MomentumProduct product = momentumProductRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));

		momentumProductRepository.delete(product);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	/******************************************************************************************************************
	 *																												 **
	 * -----------------------------------Transactional Services-----------------------------------------------------**
	 * 																												 **
	 ******************************************************************************************************************/


	/**
	 * Purchase product, to purchase a product we need to know the client and the product they wish to purchase.
	 * The client is first retrieved, if found we make sure the funds are sufficient if so we subtract the product cost points
	 * and update the customer deeming the transaction successful else we generate a detailed error
	 *
	 * @param purchaseRequest purchase request
	 *
	 * @return The created MomentumProduct
	 *
	 */
	@PostMapping("/puchaseProduct")
	public PurchaseProductResponse puchaseproduct(@Valid @RequestBody PurchaseProductRequest purchaseRequest) throws Exception {

		PurchaseProductResponse purchaseProductResponse = new PurchaseProductResponse();
		ResponseEntity<Customer> customer = getCustomerByCustomerId(purchaseRequest.getCustomerId());
		ResponseEntity<MomentumProduct> product = getProductByProductNo(purchaseRequest.getProductId());

		Customer customerBody = customer.getBody();

		int customerPoints = customerBody.getPoints();
		int productPointCost = product.getBody().getProductPointCost();
		if(customerPoints >= productPointCost) {

			customerPoints = customerPoints - productPointCost;

			Customer customerDetails = new Customer(customerBody.getFirstName(), customerBody.getLastName(), customerPoints);
			updateCustomer(customerBody.getId(),customerDetails);

			purchaseProductResponse.setSuccessful(true);
			return purchaseProductResponse;
		}
		else {
			purchaseProductResponse.setSuccessful(false);
			purchaseProductResponse.setReason("Insufficient funds");

			return purchaseProductResponse;
		}
	}

}
