package com.hiwijaya.crud.repository;

import com.hiwijaya.crud.entity.Customer;
import java.util.List;


/**
 * @author Happy Indra Wijaya
 */
public interface CustomerRepository {

    Customer save(Customer customer);

    boolean delete(int customerId);

    Customer getCustomer(int customerId);

    List<Customer> getAll();

}
