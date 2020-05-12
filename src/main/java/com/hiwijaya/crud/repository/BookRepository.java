package com.hiwijaya.crud.repository;

import com.hiwijaya.crud.entity.Book;
import java.util.List;


/**
 * @author Happy Indra Wijaya
 */
public interface BookRepository {

    Book save(Book book);

    boolean save(Book... books);

    boolean delete(Integer bookId);

    Book getBook(Integer bookId);

    List<Book> getAll();

}
