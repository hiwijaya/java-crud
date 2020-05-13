package com.hiwijaya.crud;

import com.hiwijaya.crud.entity.Book;
import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.service.BookService;
import com.hiwijaya.crud.service.CustomerService;
import com.hiwijaya.crud.service.RentalService;
import com.hiwijaya.crud.util.BookUnavailableException;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author Happy Indra Wijaya
 */
public class Main {

    private static void createBooks(BookService service){
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

        boolean succeed = service.save(book1, book2, book3, book4, book5, book6);
        System.out.println("All saved: " + succeed);

    }

    public static void rent(){

        RentalService rentalService = new RentalService();
        BookService bookService = new BookService();
        CustomerService customerService = new CustomerService();

        Customer customer = customerService.getCustomer(8);

        Book book6 = bookService.getBook(6);
        Book book2 = bookService.getBook(2);
        Book book5 = bookService.getBook(5);

        BigDecimal total = BigDecimal.ZERO;
        try {
            total = rentalService.rent(customer, book6, book2, book5);
        } catch (BookUnavailableException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Total: " + total.toPlainString());


    }


    public static void main(String[] args) {

        CustomerService customerService = new CustomerService();
        BookService bookService = new BookService();

        // create
//        Customer customer = new Customer(null, "Liam Abraham Wijaya", Gender.MALE);
//        customer = customerService.save(customer);
//        System.out.println("generated id: " + customer.getId());

        // update
//        Customer customer = new Customer(1, "John Wick", Gender.MALE);
//        Customer current = customerService.save(customer);
//        System.out.println(current);

        // delete
//        boolean deleted = customerService.delete(1);
//        System.out.println("deleted: " + deleted);

        // get customer by id
//        Customer customer = customerService.getCustomer(1);
//

        // get all customer
//        List<Customer> customers = customerService.getAll();
//        customers.forEach(System.out::println);

//        createBooks(bookService);

        rent();

    }

}
