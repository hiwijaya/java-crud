package com.hiwijaya.crud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentTransaction {

    private int id;
    private Customer customer;
    private Date rentalDate;
    private Date returnDate;
    private BigDecimal total;

    // one-to-many
    private List<RentTransactionDetail> details;

}
