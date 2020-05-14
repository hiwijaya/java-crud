package com.hiwijaya.crud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Happy Indra Wijaya
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentTransactionDetail {

    private Integer id;
    private RentTransaction rentTransaction;
    private Book book;


    public void setRentTransactionOnlyId(Integer trasactionId){
        this.rentTransaction = new RentTransaction(trasactionId);
    }

    public void setBookOnlyId(Integer bookId){
        this.book = new Book(bookId);
    }

}
