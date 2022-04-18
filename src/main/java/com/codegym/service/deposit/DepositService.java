package com.codegym.service.deposit;

import com.codegym.model.Deposit;
import com.codegym.repository.IDepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class DepositService implements IDepositService{

    @Autowired
    private IDepositRepository depositRepository;

    @Override
    public Iterable<Deposit> findAll() {
        return depositRepository.findAll();
    }

    @Override
    public Optional<Deposit> findById(Long id) {
        return depositRepository.findById(id);
    }

    @Override
    public void save(Deposit deposit) {
        depositRepository.save(deposit);
    }

    @Override
    public void remove(Long id) {
        Deposit deposit = depositRepository.findById(id).get();
        deposit.setSuspended(true);
        depositRepository.save(deposit);
    }


}
