package com.codegym.service.transfer;

import com.codegym.model.Tranfer;
import com.codegym.repository.ITransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class TransferService implements ITransferService{

    @Autowired
    ITransferRepository transferRepository;

    @Override
    public Iterable<Tranfer> findAll() {
        return transferRepository.findAll();
    }

    @Override
    public Optional<Tranfer> findById(Long id) {
        return transferRepository.findById(id);
    }

    @Override
    public void save(Tranfer tranfer) {
        transferRepository.save(tranfer);
    }

    @Override
    public void remove(Long id) {
        Tranfer tranfer = transferRepository.findById(id).get();
        tranfer.setSuspended(true);
        transferRepository.save(tranfer);
    }

}
