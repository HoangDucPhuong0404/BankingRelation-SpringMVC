package com.codegym.repository;

import com.codegym.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IDepositRepository extends JpaRepository<Deposit,Long> {

}
