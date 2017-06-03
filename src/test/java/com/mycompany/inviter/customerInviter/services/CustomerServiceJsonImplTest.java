package com.mycompany.inviter.customerInviter.services;

import com.mycompany.inviter.customerInviter.beans.Customer;
import com.mycompany.inviter.customerInviter.exceptions.ApplicationException;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CustomerServiceJsonImplTest {

    @Test
    public void testReadFileAndTransformLinesToCustomers(){
        CustomerService customerService = new CustomerServiceJsonImpl();
        try {
            List<Customer> customers = customerService.getCustomers();
            assertNotNull(customers);
            assertEquals(32,customers.size());
        } catch (ApplicationException e) {
            fail();
        }
    }

}