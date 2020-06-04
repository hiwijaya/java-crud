package com.hiwijaya.crud.repository.impl;

import com.hiwijaya.crud.datasource.DatabaseHelper;
import com.hiwijaya.crud.entity.RentTransaction;
import com.hiwijaya.crud.entity.RentTransactionDetail;
import com.hiwijaya.crud.repository.RentalRepository;
import com.hiwijaya.crud.util.RentStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Happy Indra Wijaya
 */
public class RentalRepositoryImpl implements RentalRepository {


    @Override
    public RentTransaction save(RentTransaction transaction) {

        Connection connection = null;

        try{
            connection = DatabaseHelper.getConnection();

            connection.setAutoCommit(false);

            // save rent_transaction with all details
            saveTransaction(connection, transaction);

            // update book status as rented
            updateBookStatus(connection, transaction.getDetails(), true);

            connection.commit();
            connection.setAutoCommit(true);

            return transaction;

        }
        catch(SQLException ex){
            rollback(connection);
            ex.printStackTrace();
        }

        return null;
    }

    private void saveTransaction(Connection connection, RentTransaction transaction) throws SQLException {

        final String INSERT_QUERY = "insert into rent_transactions(customer_id, rental_date, return_date, total, status) " +
                "values(?, ?, ?, ?, ?)";

        final String INSERT_DETAIL_QUERY = "insert into rent_transaction_details(transaction_id, book_id) " +
                "values(?, ?)";

        PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, transaction.getCustomer().getId());
        statement.setDate(2, new Date(transaction.getRentalDate().getTime()));
        statement.setDate(3, new Date(transaction.getReturnDate().getTime()));
        statement.setBigDecimal(4, transaction.getTotal());
        statement.setInt(5, transaction.getStatus().getStatus());
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        while (rs.next()){
            int generatedId = rs.getInt("id");
            transaction.setId(generatedId);     // passed by reference
        }
        rs.close();
        statement.close();


        // add rent_transaction_details
        statement = connection.prepareStatement(INSERT_DETAIL_QUERY);
        for(RentTransactionDetail detail : transaction.getDetails()){
            statement.setInt(1, transaction.getId());
            statement.setInt(2, detail.getBook().getId());
            statement.addBatch();
        }
        statement.executeBatch();
        statement.close();

    }

    private void updateBookStatus(Connection connection, List<RentTransactionDetail> details, boolean rented) throws SQLException {

        final String UPDATE_BOOK_QUERY = "update books set rented = ? where id = ?";

        PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK_QUERY);
        for(RentTransactionDetail detail : details){
            statement.setString(1, rented ? "Y" : "N");
            statement.setInt(2, detail.getBook().getId());
            statement.addBatch();
        }
        statement.executeBatch();
        statement.close();
    }


    @Override
    public boolean updateStatus(Integer transactionId, RentStatus status) {

        Connection connection = null;
        final String UPDATE_STATUS_QUERY = "update rent_transactions set status = ? where id = ?";

        try {
            connection = DatabaseHelper.getConnection();

            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS_QUERY);
            statement.setInt(1, status.getStatus());
            statement.setInt(2, transactionId);
            statement.executeUpdate();
            statement.close();

            if(status.equals(RentStatus.RETURNED)){
                List<RentTransactionDetail> transactionDetails = getTransactionDetails(transactionId);
                updateBookStatus(connection, transactionDetails, false);
            }

            connection.commit();
            connection.setAutoCommit(true);

            return true;

        }
        catch (SQLException ex) {
            ex.printStackTrace();
            rollback(connection);
        }

        return false;
    }

    @Override
    public RentTransaction getTransaction(Integer transactionId) {

        Connection connection;
        final String GET_TRANSACTION_BY_ID_QUERY = "select * from rent_transactions where id = ?";

        try{
            connection = DatabaseHelper.getConnection();

            PreparedStatement statement = connection.prepareStatement(GET_TRANSACTION_BY_ID_QUERY);
            statement.setInt(1, transactionId);

            ResultSet rs = statement.executeQuery();
            RentTransaction transaction = null;
            while (rs.next()){
                transaction = new RentTransaction();
                transaction.setId(rs.getInt("id"));
                transaction.setCustomerOnlyId(rs.getInt("customer_id"));
                transaction.setRentalDate(rs.getDate("rental_date"));
                transaction.setReturnDate(rs.getDate("return_Date"));
                transaction.setTotal(rs.getBigDecimal("total"));
                transaction.setStatus(RentStatus.getStatus(rs.getInt("status")));
            }

            rs.close();
            statement.close();

            return transaction;
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public List<RentTransaction> getAll() {

        // TODO: implement pagination

        Connection connection;
        final String GET_ALL_QUERY = "select * from rent_transactions";
        List<RentTransaction> transactions = new ArrayList<>();

        try{
            connection = DatabaseHelper.getConnection();

            PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                RentTransaction transaction = new RentTransaction();
                transaction.setId(rs.getInt("id"));
                transaction.setCustomerOnlyId(rs.getInt("customer_id"));
                transaction.setRentalDate(rs.getDate("rental_date"));
                transaction.setReturnDate(rs.getDate("return_Date"));
                transaction.setTotal(rs.getBigDecimal("total"));
                transaction.setStatus(RentStatus.getStatus(rs.getInt("status")));
                transactions.add(transaction);
                // lazy load details
            }

            rs.close();
            statement.close();

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return transactions;
    }

    @Override
    public List<RentTransactionDetail> getTransactionDetails(Integer transactionId) {

        Connection connection;
        final String GET_ALL_QUERY = "select * from rent_transaction_details where transaction_id = ?";
        List<RentTransactionDetail> details = new ArrayList<>();

        try{
            connection = DatabaseHelper.getConnection();

            PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
            statement.setInt(1, transactionId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                RentTransactionDetail detail = new RentTransactionDetail();
                detail.setRentTransactionOnlyId(rs.getInt("transaction_id"));
                detail.setBookOnlyId(rs.getInt("book_id"));
                details.add(detail);
            }

            rs.close();
            statement.close();

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return details;
    }


    private void rollback(Connection connection){
        try {
            connection.rollback();
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        }
    }

}
