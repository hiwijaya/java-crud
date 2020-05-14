package com.hiwijaya.crud.service;

import com.hiwijaya.crud.entity.Book;
import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.entity.RentTransaction;
import com.hiwijaya.crud.entity.RentTransactionDetail;
import com.hiwijaya.crud.repository.RentalRepository;
import com.hiwijaya.crud.repository.impl.RentalRepositoryImpl;
import com.hiwijaya.crud.util.BookUnavailableException;
import com.hiwijaya.crud.util.Lib;
import com.hiwijaya.crud.util.RentOutdatedException;
import com.hiwijaya.crud.util.RentStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author Happy Indra Wijaya
 */
public class RentalService {

    RentalRepository repository = new RentalRepositoryImpl();


    public BigDecimal rent(Customer customer, Book... books) throws BookUnavailableException {

        // check if one of the books already rented or not
        checkIfBooksAvailable(books);

        BigDecimal totalRentPrice = getTotalRentPrice(books);

        RentTransaction transaction = new RentTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(Lib.now());
        transaction.setReturnDate(Lib.nextWeek());
        transaction.setTotal(totalRentPrice);
        transaction.setStatus(RentStatus.RENT);

        List<RentTransactionDetail> details = new ArrayList<>();
        for(Book book : books){
            RentTransactionDetail detail = new RentTransactionDetail();
            detail.setRentTransaction(transaction);
            detail.setBook(book);
            details.add(detail);
        }
        transaction.setDetails(details);

        repository.save(transaction);

        return totalRentPrice;
    }

    private void checkIfBooksAvailable(Book[] books) throws BookUnavailableException {
        boolean rented = Arrays.stream(books).anyMatch(book -> book.isRented());
        if(rented){
            throw new BookUnavailableException("One of the selected books is already rented.");
        }
    }

    private BigDecimal getTotalRentPrice(Book[] books){
        BigDecimal total = BigDecimal.ZERO;
        total = Arrays.stream(books).map(book -> book.getRentPrice())
                .reduce(total, BigDecimal::add);

        return total;
    }


    public boolean returnBooks(RentTransaction transaction) throws RentOutdatedException {

        if(booksAlreadyReturned(transaction)){
            return true;
        }

        checkTransactionIfOutdated(transaction);

        return repository.updateStatus(transaction.getId(), RentStatus.RETURNED);
    }

    private boolean booksAlreadyReturned(RentTransaction transaction){
        return transaction.getStatus().equals(RentStatus.RETURNED);
    }

    private void checkTransactionIfOutdated(RentTransaction transaction) throws RentOutdatedException {
        if(transaction.getStatus().equals(RentStatus.RENT)){
            if(transaction.getReturnDate().before(Lib.now())){  // outdated
                repository.updateStatus(transaction.getId(), RentStatus.OUTDATED);
                throw new RentOutdatedException("You have to pay the late charges.");
            }
        }
    }


    public RentTransaction getTransaction(Integer transactionId){
        return repository.getTransaction(transactionId);
    }

    public List<RentTransaction> getAll(){
        return repository.getAll();
    }

}
