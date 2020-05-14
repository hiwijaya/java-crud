package com.hiwijaya.crud.entity;

import com.hiwijaya.crud.util.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @author Happy Indra Wijaya
 */
@Data
@NoArgsConstructor
public class Customer {

    private Integer id;
    private String name;
    private Gender gender;

    // one-to-many
    private List<RentTransaction> rentals;

    public Customer(Integer id, String name, Gender gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public Customer(Integer id){
        this.id = id;
    }

    // obey to Law of Demeter
    public String getGenderSymbol(){
        return gender.getSymbol();
    }

}
