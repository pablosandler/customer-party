package com.mycompany.inviter.customerInviter.services;

import com.mycompany.inviter.customerInviter.beans.Customer;
import com.mycompany.inviter.customerInviter.exceptions.ApplicationException;

import java.util.List;

public interface CustomerService {

    public List<Customer> getCustomers() throws ApplicationException;
}
