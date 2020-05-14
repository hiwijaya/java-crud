package com.hiwijaya.crud.repository.impl;

import com.hiwijaya.crud.datasource.DatabaseHelper;
import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.repository.CustomerRepository;
import com.hiwijaya.crud.util.Gender;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
public class CustomerRepositoryImpl implements CustomerRepository {


    @Override
    public Customer save(Customer customer) {

        Connection connection;

        try {
            connection = DatabaseHelper.getConnection();

            // if customer already exist, do update instead
            if(customer.getId() != null){
                boolean customerExisted = getCustomer(customer.getId()) != null;
                if(customerExisted){
                    updateExistingCustomer(connection, customer);
                    return customer;
                }
            }

            saveCustomer(connection, customer);

            return customer;

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private void updateExistingCustomer(Connection connection, Customer customer) throws SQLException {

        final String UPDATE_QUERY = "update customers set name = ?, gender = ? where id = ?";

        PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
        statement.setString(1, customer.getName());
        statement.setString(2, customer.getGenderSymbol());
        statement.setInt(3, customer.getId());
        statement.executeUpdate();

        statement.close();

    }

    private void saveCustomer(Connection connection, Customer customer) throws SQLException {

        final String INSERT_QUERY = "insert into customers(name, gender) values(?, ?)";

        PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, customer.getName());
        statement.setString(2, customer.getGenderSymbol());
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        int generatedId = rs.getInt("id");
        customer.setId(generatedId);        // passed by references

        rs.close();
        statement.close();

    }

    @Override
    public boolean delete(Integer customerId) {

        Connection connection;
        final String DELETE_QUERY = "delete from customers where id = ?";

        try{
            connection = DatabaseHelper.getConnection();

            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setInt(1, customerId);
            boolean deleted = statement.executeUpdate() > 0;
            statement.close();

            return deleted;
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public Customer getCustomer(Integer customerId) {

        Connection connection;
        final String GET_CUSTOMER_BY_ID = "select * from customers where id = ?";

        try{
            connection = DatabaseHelper.getConnection();

            PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_BY_ID);
            statement.setInt(1, customerId);

            ResultSet rs = statement.executeQuery();
            Customer customer = null;
            while (rs.next()){
                customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setGender(Gender.getGender(rs.getString("gender")));
            }

            rs.close();
            statement.close();

            return customer;
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Customer> getAll() {

        // TODO: implement pagination

        Connection connection;
        final String GET_ALL_QUERY = "select * from customers";
        List<Customer> customers = new ArrayList<>();

        try{
            connection = DatabaseHelper.getConnection();

            PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setGender(Gender.getGender(rs.getString("gender")));
                customers.add(c);
            }
            rs.close();
            statement.close();

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return customers;
    }
}
