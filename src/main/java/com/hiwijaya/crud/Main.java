package com.hiwijaya.crud;

import com.hiwijaya.crud.entity.Book;
import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.entity.RentTransaction;
import com.hiwijaya.crud.service.BookService;
import com.hiwijaya.crud.service.CustomerService;
import com.hiwijaya.crud.service.RentalService;
import com.hiwijaya.crud.util.BookUnavailableException;
import com.hiwijaya.crud.util.Gender;
import com.hiwijaya.crud.util.RentOutdatedException;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author Happy Indra Wijaya
 */
public class Main {

    private static final CustomerService customerService = new CustomerService();
    private static final BookService bookService = new BookService();
    private static final RentalService rentalService = new RentalService();

    private static void createCustomers(){
        Customer customer1 = new Customer(null, "Liam Abraham Wijaya", Gender.MALE);
        customer1 = customerService.save(customer1);

        Customer customer2 = new Customer(null, "Emma Watson", Gender.FEMALE);
        customer2 = customerService.save(customer2);

        Customer customer3 = new Customer(null, "John Wick", Gender.MALE);
        customer3 = customerService.save(customer3);

        customerService.getAll().forEach(System.out::println);

    }


    private static void createBooks(){
        Book book1 = new Book(null,
                "The Fellowship of The Ring",
                "J. R. R. Tolkien",
                new BigDecimal(50000),
                false);

        Book book2 = new Book(null,
                "The Two Tower",
                "J. R. R. Tolkien",
                new BigDecimal(50000),
                false);

        Book book3 = new Book(null,
                "Return of The King",
                "J. R. R. Tolkien",
                new BigDecimal(60000),
                false);

        Book book4 = new Book(null,
                "The Hunger Games",
                "Suzanne Collins",
                new BigDecimal(30000),
                false);

        Book book5 = new Book(null,
                "Catching Fire",
                "Suzanne Collins",
                new BigDecimal(30000),
                false);

        Book book6 = new Book(null,
                "Mockingjay",
                "Suzanne Collins",
                new BigDecimal(45000),
                false);

        boolean succeed = bookService.save(book1, book2, book3, book4, book5, book6);
        System.out.println("All saved: " + succeed);

        bookService.getAll().forEach(System.out::println);

    }

    public static void rent(){

        List<Customer> customers = customerService.getAll();
        List<Book> books = bookService.getAll();

        Customer customer = customers.get(0);

        Book book1 = books.get(0);
        Book book2 = books.get(1);
        Book book3 = books.get(2);

        BigDecimal bill = BigDecimal.ZERO;
        try {
            bill = rentalService.rent(customer, book1, book2, book3);
        } catch (BookUnavailableException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Bill: " + bill.toPlainString());

    }

    public static void returnBooks(){

        List<RentTransaction> transactions = rentalService.getAll();
        transactions.forEach(System.out::println);

        RentTransaction transaction = transactions.get(0);

        try {
            boolean succeed = rentalService.returnBooks(transaction);
            System.out.println("Succeed returning all book: " + succeed);
        } catch (RentOutdatedException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {

        createCustomers();
        createBooks();

//        rent();

//        returnBooks();

    }

}
