package com.codegym.repository;

import com.codegym.model.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWithDrawRepository extends JpaRepository<Withdraw, Long> {
}
