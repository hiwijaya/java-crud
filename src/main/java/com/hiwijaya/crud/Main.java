package com.hiwijaya.crud;

import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.repository.CustomerRepository;
import com.hiwijaya.crud.repository.impl.CustomerRepositoryImpl;
import java.util.List;


/**
 * @author Happy Indra Wijaya
 */
public class Main {

    public static void main(String[] args) {

        CustomerRepository customerRepository = new CustomerRepositoryImpl();

        // create
//        Customer customer = new Customer(null, "Liam Abraham Wijaya", Gender.MALE);
//        customer = customerRepository.save(customer);
//        System.out.println("generated id: " + customer.getId());

        // update
//        Customer customer = new Customer(1, "John Wick", Gender.MALE);
//        Customer current = customerRepository.save(customer);
//        System.out.println(current);

        // delete
//        boolean deleted = customerRepository.delete(1);
//        System.out.println("deleted: " + deleted);

        // get customer by id
//        Customer customer = customerRepository.getCustomer(1);
//

        // get all customer
        List<Customer> customers = customerRepository.getAll();
        customers.forEach(System.out::println);


    }

}
