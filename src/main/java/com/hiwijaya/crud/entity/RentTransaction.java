package com.hiwijaya.crud.entity;

import com.hiwijaya.crud.util.RentStatus;
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

    private Integer id;
    private Customer customer;
    private Date rentalDate;
    private Date returnDate;
    private BigDecimal total;
    private RentStatus status;      // 0, 1, 2

    // one-to-many
    private List<RentTransactionDetail> details;


    public RentTransaction(Integer id){
        this.id = id;
    }


    public void setCustomerOnlyId(Integer customerId){
        this.customer = new Customer(customerId);
    }

}
