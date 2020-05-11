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

    private int id;
    private String title;
    private String author;
    private BigDecimal rentPrice;

}
