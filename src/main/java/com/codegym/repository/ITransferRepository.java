package com.codegym.repository;

import com.codegym.model.Tranfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransferRepository extends JpaRepository<Tranfer, Long> {

}
