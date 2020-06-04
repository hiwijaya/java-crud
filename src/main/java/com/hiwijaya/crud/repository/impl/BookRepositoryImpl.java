package com.hiwijaya.crud.repository.impl;

import com.hiwijaya.crud.datasource.DatabaseHelper;
import com.hiwijaya.crud.entity.Book;
import com.hiwijaya.crud.repository.BookRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
public class BookRepositoryImpl implements BookRepository {


    @Override
    public Book save(Book book) {

        Connection connection;

        try {
            connection = DatabaseHelper.getConnection();

            // if book already exist, do update instead
            if(book.getId() != null){
                boolean bookExisted = getBook(book.getId()) != null;
                if(bookExisted){
                    updateExistingBook(connection, book);
                    return book;
                }
            }

            saveBook(connection, book);

            return book;

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private void updateExistingBook(Connection connection, Book book) throws SQLException {

        final String UPDATE_QUERY = "update books set title = ?, author = ?, rent_price = ?, rented = ? where id = ?";

        PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setBigDecimal(3, book.getRentPrice());
        statement.setString(4, book.isRentedString());
        statement.setInt(5, book.getId());
        statement.executeUpdate();

    }

    private void saveBook(Connection connection, Book book) throws SQLException {

        final String INSERT_QUERY = "insert into books(title, author, rent_price, rented) values(?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setBigDecimal(3, book.getRentPrice());
        statement.setString(4, book.isRentedString());
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        while (rs.next()){
            int generatedId = rs.getInt("id");
            book.setId(generatedId);
        }

        rs.close();
        statement.close();

    }

    @Override
    public boolean save(Book... books) {

        Connection connection = null;
        final String INSERT_QUERY = "insert into books(title, author, rent_price, rented) values(?, ?, ?, ?)";

        try {
            connection = DatabaseHelper.getConnection();

            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
            for (Book book : books) {
                statement.setString(1, book.getTitle());
                statement.setString(2, book.getAuthor());
                statement.setBigDecimal(3, book.getRentPrice());
                statement.setString(4, book.isRentedString());
                statement.addBatch();
                //statement.clearParameters();
            }
            statement.executeBatch();
            statement.close();

            connection.commit();
            connection.setAutoCommit(true);

            return true;
        }
        catch (SQLException ex1) {
            ex1.printStackTrace();
            rollback(connection);
        }

        return false;
    }

    private void rollback(Connection connection){
        try {
            connection.rollback();
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        }
    }

    @Override
    public boolean delete(Integer bookId) {

        Connection connection;
        final String DELETE_QUERY = "delete from books where id = ?";

        try{
            connection = DatabaseHelper.getConnection();

            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setInt(1, bookId);
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
    public Book getBook(Integer bookId) {

        Connection connection;
        final String GET_BOOK_BY_ID = "select * from books where id = ?";

        try{
            connection = DatabaseHelper.getConnection();

            PreparedStatement statement = connection.prepareStatement(GET_BOOK_BY_ID);
            statement.setInt(1, bookId);

            ResultSet rs = statement.executeQuery();
            Book book = null;
            while (rs.next()){
                book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setRentPrice(rs.getBigDecimal("rent_price"));
                book.setRentedString(rs.getString("rented"));
            }

            rs.close();
            statement.close();

            return book;
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return null;

    }

    @Override
    public List<Book> getAll() {
        // TODO: implement pagination

        Connection connection;
        final String GET_ALL_QUERY = "select * from books";
        List<Book> books = new ArrayList<>();

        try{
            connection = DatabaseHelper.getConnection();

            PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setRentPrice(rs.getBigDecimal("rent_price"));
                book.setRentedString(rs.getString("rented"));
                books.add(book);
            }
            rs.close();
            statement.close();

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return books;
    }
}
