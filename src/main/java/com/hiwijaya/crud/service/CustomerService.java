package com.hiwijaya.crud.service;

import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.repository.CustomerRepository;
import com.hiwijaya.crud.repository.impl.CustomerRepositoryImpl;

import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
public class CustomerService {

    private final CustomerRepository repository = new CustomerRepositoryImpl();


    public Customer save(Customer customer){

        // you can put some logic here

        return repository.save(customer);
    }

    public boolean delete(Integer customerId){
        return repository.delete(customerId);
    }

    public Customer getCustomer(Integer customerId){
        return repository.getCustomer(customerId);
    }

    public List<Customer> getAll(){
        return repository.getAll();
    }

}
