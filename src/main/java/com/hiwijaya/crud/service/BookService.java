package com.hiwijaya.crud.service;

import com.hiwijaya.crud.entity.Book;
import com.hiwijaya.crud.repository.BookRepository;
import com.hiwijaya.crud.repository.impl.BookRepositoryImpl;

import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
public class BookService {

    BookRepository repository = new BookRepositoryImpl();

    public Book save(Book book){

        // you can put some logic here

        return repository.save(book);
    }

    public boolean save(Book... books){
        return repository.save(books);
    }

    public boolean delete(Integer bookId){
        return repository.delete(bookId);
    }

    public Book getBook(Integer bookId){
        return repository.getBook(bookId);
    }

    public List<Book> getAll(){
        return repository.getAll();
    }

}
