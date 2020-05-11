package com.hiwijaya.crud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * @author Happy Indra Wijaya
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private int id;
    private String name;
    private Gender gender;

}


@Getter
enum Gender {

    MALE("M", "Male"),
    FEMALE("F", "Female");

    private final String symbol;
    private final String desc;

    Gender(String symbol, String desc) {
        this.symbol = symbol;
        this.desc = desc;
    }
}
