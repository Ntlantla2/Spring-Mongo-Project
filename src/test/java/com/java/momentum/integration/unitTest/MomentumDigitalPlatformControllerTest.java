package com.java.momentum.integration.unitTest;

import com.java.momentum.dailypoints.Application;
import com.java.momentum.dailypoints.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MomentumDigitalPlatformControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testGetAllCustomers() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "api/v1/customers",
                HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
    }

    @Test
    public void testGetCustomerById() {
        Customer Customer = restTemplate.getForObject(getRootUrl() + "api/v1/getCustomer/2/", Customer.class);
        System.out.println(Customer.getFirstName());
        assertNotNull(Customer.getFirstName());
    }

    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Austin");
        customer.setLastName("Powers");
        customer.setPoints(1000);

        ResponseEntity<Customer> postResponse = restTemplate.postForEntity(getRootUrl() + "api/v1/addCustomer", customer, Customer.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateCustomer() {
        int id = 2;
        Customer Customer = restTemplate.getForObject(getRootUrl() + "/api/v1/getCustomer/" + id, Customer.class);
        Customer.setFirstName("admin1");
        Customer.setLastName("admin2");

        restTemplate.put(getRootUrl() + "/api/v1/updateCustomerDetails/" + id, Customer);

        Customer updatedEmployee = restTemplate.getForObject(getRootUrl() + "/api/v1/getCustomer/" + id, Customer.class);
        assertNotNull(updatedEmployee);
    }

    @Test
    public void testDeleteCustomer() {
        int id = 2;
        Customer Customer = restTemplate.getForObject(getRootUrl() + "/api/v1/getCustomer/" + id, Customer.class);
        assertNotNull(Customer);

        restTemplate.delete(getRootUrl() + "/api/v1/deleteCustomer/" + id);

        try {
            Customer customer = restTemplate.getForObject(getRootUrl() + "/api/v1/getCustomer/" + id, Customer.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
