package com.hiwijaya.crud.repository;

import com.hiwijaya.crud.entity.RentTransaction;
import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
public interface RentalRepository {

    RentTransaction save(RentTransaction transaction);

    RentTransaction getTransaction(Integer transactionId);

    List<RentTransaction> getAllTransaction();

}
