package com.hiwijaya.crud.repository;

import com.hiwijaya.crud.entity.Customer;
import java.util.List;


/**
 * @author Happy Indra Wijaya
 */
public interface CustomerRepository {

    Customer save(Customer customer);

    boolean delete(Integer customerId);

    Customer getCustomer(Integer customerId);

    List<Customer> getAll();

}
