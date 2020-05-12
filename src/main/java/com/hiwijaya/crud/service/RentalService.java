package com.hiwijaya.crud.service;

import com.hiwijaya.crud.entity.Book;
import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.entity.RentTransaction;
import com.hiwijaya.crud.entity.RentTransactionDetail;
import com.hiwijaya.crud.repository.RentalRepository;
import com.hiwijaya.crud.repository.impl.RentalRepositoryImpl;
import com.hiwijaya.crud.util.Lib;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 * @author Happy Indra Wijaya
 */
public class RentalService {

    RentalRepository repository = new RentalRepositoryImpl();

    public BigDecimal rent(Customer customer, Book... books){

        RentTransaction transaction = new RentTransaction();
        transaction.setCustomer(customer);

        BigDecimal total = BigDecimal.ZERO;
        total = Arrays.stream(books).map(book -> book.getRentPrice())
                .reduce(total, BigDecimal::add);

        transaction.setRentalDate(Lib.now());
        transaction.setReturnDate(Lib.nextWeek());
        transaction.setTotal(total);

        List<RentTransactionDetail> details = new ArrayList<>();
        for(Book book : books){
            RentTransactionDetail detail = new RentTransactionDetail();
            detail.setBook(book);
            details.add(detail);
        }
        transaction.setDetails(details);

        repository.save(transaction);

        return total;

    }
}
