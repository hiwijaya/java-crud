package com.hiwijaya.crud.repository;

import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.util.PersistentException;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
public interface CustomerRepository {

    public Customer save(Customer customer);

    public void delete(String customerId);

    public Customer getCustomer(String cusctomerId);

    public List<Customer> getAll();

}
