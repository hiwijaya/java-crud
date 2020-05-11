package com.hiwijaya.crud;


import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.repository.CustomerRepository;
import com.hiwijaya.crud.repository.impl.CustomerRepositoryImpl;
import com.hiwijaya.crud.util.Gender;

/**
 * @author Happy Indra Wijaya
 */
public class Main {

    public static void main(String[] args) {
        Customer customer = new Customer();
        customer.setName("Liam Abraham Wijaya");
        customer.setGender(Gender.MALE);

        CustomerRepository cr = new CustomerRepositoryImpl();
        Customer n = cr.save(customer);
        System.out.println("generated id: " + n.getId());
    }

}
