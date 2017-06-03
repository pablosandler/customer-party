package com.mycompany.inviter.customerInviter;


import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.mycompany.inviter.customerInviter.beans.Customer;
import com.mycompany.inviter.customerInviter.beans.Location;
import com.mycompany.inviter.customerInviter.exceptions.ApplicationException;
import com.mycompany.inviter.customerInviter.services.CustomerService;

import java.util.*;

public class CustomerInviter {

    public static final Double RADIUS = 6371.01;
    public static final double MAX_DISTANCE = 100.00;
    private CustomerService customerService;
    private Location nearbyLocation;


    public CustomerInviter(CustomerService customerService, Location location){
        if(customerService==null || location==null){
            throw new IllegalArgumentException("Constructor arguments cannot be null");
        }
        this.customerService = customerService;
        this.nearbyLocation = location;
    }


    public List<Customer> getCustomersToInvite() throws ApplicationException {
        List<Customer> customersToInvite = null;

        try {
            customersToInvite = getNearbyCustomers(customerService.getCustomers());

            Collections.sort(customersToInvite, getIdComparator());

        } catch (ApplicationException e) {
            throw new ApplicationException("Error retrieving customers to invite",e);
        }

        return customersToInvite;
    }


    private List<Customer> getNearbyCustomers(List<Customer> customers) {
        Collection<Customer> nearbyCustomers = (Collection<Customer>) Collections2.filter(customers, getPredicate());
        return new ArrayList<Customer>(nearbyCustomers);
    }

    private Predicate<Customer> getPredicate() {
        return new Predicate<Customer>() {
            public boolean apply(Customer customer) {
                if(calculateDistance(customer)<= MAX_DISTANCE){
                    return true;
                }
                return false;
            }
        };
    }

    private double calculateDistance(Customer customer) {
        double customerLatitude = customer.getLocation().getLatitude();
        double customerLongitude = customer.getLocation().getLongitude();

        double companyLatitude = nearbyLocation.getLatitude();
        double companyLongitude = nearbyLocation.getLongitude();

        double dLat = Math.toRadians(companyLatitude - customerLatitude);
        double dLon = Math.toRadians(companyLongitude - customerLongitude);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(customerLatitude)) * Math.cos(Math.toRadians(companyLatitude)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double resultInRadians = 2 * Math.asin(Math.sqrt(a));
        return  resultInRadians * RADIUS;
    }


    private Comparator<Customer> getIdComparator() {
        Comparator<Customer> comparator = new Comparator<Customer>() {
            public int compare(Customer c1, Customer c2) {
                return c1.getId().compareTo(c2.getId());
            }
        };
        return comparator;
    }
}
