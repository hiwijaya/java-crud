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

    private int id;
    private RentTransaction rentTransaction;
    private Book book;

}
