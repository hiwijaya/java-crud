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
        final String INSERT_QUERY = "insert into rent_transactions(customer_id, rental_date, return_date, total, status) " +
                "values(?, ?, ?, ?, ?)";
        final String INSERT_DETAIL_QUERY = "insert into rent_transaction_details(transaction_id, book_id) " +
                "values(?, ?)";
        final String UPDATE_BOOK_QUERY = "update books set rented = 'Y' where id = ?";

        try{
            connection = DatabaseHelper.getConnection();

            connection.setAutoCommit(false);

            // add rent_transaction
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, transaction.getCustomer().getId());
            statement.setDate(2, new java.sql.Date(transaction.getRentalDate().getTime()));
            statement.setDate(3, new java.sql.Date(transaction.getReturnDate().getTime()));
            statement.setBigDecimal(4, transaction.getTotal());
            statement.setInt(5, transaction.getStatus().getStatus());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int generatedId = rs.getInt("id");
            transaction.setId(generatedId);

            // add rent_transaction_details
            statement.clearParameters();
            statement = connection.prepareStatement(INSERT_DETAIL_QUERY);
            for(RentTransactionDetail detail : transaction.getDetails()){
                statement.setInt(1, transaction.getId());
                statement.setInt(2, detail.getBook().getId());
                statement.addBatch();
            }
            statement.executeBatch();

            // update book status as rented
            statement.clearParameters();
            statement = connection.prepareStatement(UPDATE_BOOK_QUERY);
            for(RentTransactionDetail detail : transaction.getDetails()){
                statement.setInt(1, detail.getBook().getId());
                statement.addBatch();
            }
            statement.executeBatch();

            statement.close();
            connection.commit();
            connection.setAutoCommit(true);

            return transaction;

        }
        catch(SQLException ex1){
            try {
                connection.rollback();
            } catch (SQLException ex2) {
                ex2.printStackTrace();
            }
        }

        return null;

    }

    @Override
    public boolean updateStatus(Integer transactionId, RentStatus status) {

        Connection connection;
        final String UPDATE_STATUS_QUERY = "update rent_transactions set status = ? where id = ?";

        try {
            connection = DatabaseHelper.getConnection();

            PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS_QUERY);
            statement.setInt(1, status.getStatus());
            statement.setInt(2, transactionId);
            boolean updated = statement.executeUpdate() > 0;

            return updated;

        } catch (SQLException ex) {
            ex.printStackTrace();
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

                // lazy load
            }

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return transactions;
    }
}
