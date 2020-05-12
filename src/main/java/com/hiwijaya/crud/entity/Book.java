package com.hiwijaya.crud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Happy Indra Wijaya
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Integer id;
    private String title;
    private String author;
    private BigDecimal rentPrice;
    private boolean rented;     // Y/N


    public void setRentedString(String rented){
        this.rented = rented.equals("Y");
    }

    public String isRentedString(){
        return rented ? "Y" : "N";
    }

}
