package com.codegym.service.withdraw;

import com.codegym.model.Withdraw;
import com.codegym.repository.IWithDrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class WithDrawService implements IWithDrawService {

    @Autowired
    IWithDrawRepository withDrawRepository;


    @Override
    public Iterable<Withdraw> findAll() {
        return withDrawRepository.findAll();
    }

    @Override
    public Optional<Withdraw> findById(Long id) {
        return withDrawRepository.findById(id);
    }

    @Override
    public void save(Withdraw withdraw) {
        withDrawRepository.save(withdraw);
    }

    @Override
    public void remove(Long id) {
        Withdraw withdraw = withDrawRepository.findById(id).get();
        withdraw.setSuspended(true);
        withDrawRepository.save(withdraw);
    }
}
