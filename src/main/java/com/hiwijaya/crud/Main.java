package com.hiwijaya.crud;

import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.service.CustomerService;
import java.util.List;


/**
 * @author Happy Indra Wijaya
 */
public class Main {

    public static void main(String[] args) {

        CustomerService customerService = new CustomerService();

        // create
//        Customer customer = new Customer(null, "Liam Abraham Wijaya", Gender.MALE);
//        customer = customerService.save(customer);
//        System.out.println("generated id: " + customer.getId());

        // update
//        Customer customer = new Customer(1, "John Wick", Gender.MALE);
//        Customer current = customerService.save(customer);
//        System.out.println(current);

        // delete
//        boolean deleted = customerService.delete(1);
//        System.out.println("deleted: " + deleted);

        // get customer by id
//        Customer customer = customerService.getCustomer(1);
//

        // get all customer
        List<Customer> customers = customerService.getAll();
        customers.forEach(System.out::println);


    }

}
