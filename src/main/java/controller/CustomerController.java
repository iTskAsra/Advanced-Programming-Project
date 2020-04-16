package controller;

import model.Customer;

import java.util.ArrayList;

public class CustomerController {
    private  Customer customer;
    public CustomerController(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
