package com.hiwijaya.crud.entity;

import com.hiwijaya.crud.util.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


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

    private List<RentTransaction> rentals;

    // obey to Law of Demeter
    public String getGenderSymbol(){
        return gender.getSymbol();
    }

}
