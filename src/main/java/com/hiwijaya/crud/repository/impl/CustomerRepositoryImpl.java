package com.hiwijaya.crud.repository.impl;

import com.hiwijaya.crud.datasource.DatabaseHelper;
import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.repository.CustomerRepository;

import java.sql.*;
import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
public class CustomerRepositoryImpl implements CustomerRepository {


    @Override
    public Customer save(Customer customer) {

        Connection connection;
        final String SAVE_QUERY = "insert into customers(name, gender) values(?, ?)";

        try {
            connection = DatabaseHelper.getConnection();

            PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getGenderSymbol());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int generatedId = rs.getInt(1);
            customer.setId(generatedId);

            return customer;

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(String customerId) {

    }

    @Override
    public Customer getCustomer(String cusctomerId) {
        return null;
    }

    @Override
    public List<Customer> getAll() {
        return null;
    }
}
