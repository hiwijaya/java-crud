package com.hiwijaya.crud.repository.impl;

import com.hiwijaya.crud.datasource.DatabaseHelper;
import com.hiwijaya.crud.entity.RentTransaction;
import com.hiwijaya.crud.entity.RentTransactionDetail;
import com.hiwijaya.crud.repository.RentalRepository;

import java.sql.*;
import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
public class RentalRepositoryImpl implements RentalRepository {


    @Override
    public RentTransaction save(RentTransaction transaction) {

        Connection connection = null;
        final String INSERT_QUERY = "insert into rent_transactions(customer_id, rental_date, return_date, total) " +
                "values(?, ?, ?, ?)";
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
    public RentTransaction getTransaction(Integer transactionId) {
        return null;
    }

    @Override
    public List<RentTransaction> getAllTransaction() {
        return null;
    }
}
