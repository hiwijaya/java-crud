package com.hiwijaya.crud.repository;

import com.hiwijaya.crud.entity.RentTransaction;
import com.hiwijaya.crud.util.RentStatus;

import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
public interface RentalRepository {

    RentTransaction save(RentTransaction transaction);

    boolean updateStatus(Integer transactionId, RentStatus status);

    RentTransaction getTransaction(Integer transactionId);

    List<RentTransaction> getAllTransaction();

}
