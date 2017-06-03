package com.mycompany.inviter.customerInviter;

import com.mycompany.inviter.customerInviter.beans.Customer;
import com.mycompany.inviter.customerInviter.beans.Location;
import com.mycompany.inviter.customerInviter.exceptions.ApplicationException;
import com.mycompany.inviter.customerInviter.services.CustomerService;
import com.mycompany.inviter.customerInviter.services.CustomerServiceJsonImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CustomerInviterTest {

    private Location companyLocation;

    @Before
    public void setup(){
        companyLocation = new Location();
        companyLocation.setLatitude(53.3381985);
        companyLocation.setLongitude(-6.2592576);
    }

    @Test
    public void whenCustomerServiceIsNullShouldThrowAnException(){
        CustomerService customerService = null;

        try{
            CustomerInviter customerInviter = new CustomerInviter(customerService,companyLocation);
            fail();
        } catch(IllegalArgumentException e){
            assertNotNull(e);
        }
    }

    @Test
    public void whenLocationIsNullShouldThrowAnException(){
        CustomerService customerService = null;

        try{
            CustomerInviter customerInviter = new CustomerInviter(customerService,null);
            fail();
        } catch(IllegalArgumentException e){
            assertNotNull(e);
        }
    }

    @Test
    public void whenGetCustomersFailsCustomerInviterFailsToo(){
        CustomerService customerService = mock(CustomerService.class);

        CustomerInviter customerInviter = new CustomerInviter(customerService,companyLocation);

        try {
            when(customerService.getCustomers()).thenThrow(new ApplicationException("",new Exception()));
            customerInviter.getCustomersToInvite();
            fail();
        } catch (ApplicationException e) {
            assertNotNull(e);
            assertEquals("Error retrieving customers to invite",e.getMessage());
        }

    }


    @Test
     public void getCustomersToInviteShouldReturnAListOfCustomersOrderedById(){
        CustomerService customerService = mock(CustomerService.class);
        List<Customer> customerList = new ArrayList<Customer>();

        Customer customerOne = createCustomer(14L,getNearbyLocation());
        customerList.add(customerOne);

        Customer customerTwo = createCustomer(1L,getNearbyLocation());
        customerList.add(customerTwo);

        CustomerInviter customerInviter = new CustomerInviter(customerService,companyLocation);
        List<Customer> customersToInvite = null;
        try {
            when(customerService.getCustomers()).thenReturn(customerList);
            customersToInvite = customerInviter.getCustomersToInvite();
        } catch (ApplicationException e) {
            fail();
        }

        assertNotNull(customersToInvite);
        assertTrue(customersToInvite.size() == 2);
        assertArrayEquals(new Customer[]{customerTwo, customerOne}, customersToInvite.toArray());
    }

    @Test
    public void getCustomersToInviteShouldRemoveDistantCustomers(){
        CustomerService customerService = mock(CustomerService.class);
        List<Customer> customerList = new ArrayList<Customer>();

        Customer customerOne = createCustomer(14L,getDistantLocation());
        customerList.add(customerOne);

        Customer customerTwo = createCustomer(1l,getNearbyLocation());
        customerList.add(customerTwo);

        CustomerInviter customerInviter = new CustomerInviter(customerService,companyLocation);
        List<Customer> customersToInvite = null;

        try {
            when(customerService.getCustomers()).thenReturn(customerList);
            customersToInvite = customerInviter.getCustomersToInvite();
        } catch (ApplicationException e) {
            fail();
        }

        assertNotNull(customersToInvite);
        assertTrue(customersToInvite.size() == 1);
        assertArrayEquals(new Customer[]{customerTwo}, customersToInvite.toArray());
    }


    @Test
    public void testWithRealData(){
        CustomerService customerService = new CustomerServiceJsonImpl();

        CustomerInviter customerInviter = new CustomerInviter(customerService,companyLocation);

        try {
            List<Customer> customersToInvite = customerInviter.getCustomersToInvite();

            assertNotNull(customersToInvite);
            assertEquals(16, customersToInvite.size());

            for(Customer customer : customersToInvite){
                System.out.println(customer);
            }
        } catch (ApplicationException e) {
            fail();
        }

    }


    private Customer createCustomer(long id, Location location) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setLocation(location);
        return customer;
    }

    private Location getNearbyLocation() {
        Location location = new Location();
        location.setLatitude(53.0);
        location.setLongitude(-6.0);
        return location;
    }

    private Location getDistantLocation() {
        Location location = new Location();
        location.setLatitude(1.2);
        location.setLongitude(1.2);
        return location;
    }

}