package com.codegym.service.customer;

import com.codegym.model.Customer;
import com.codegym.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService extends IGeneralService<Customer> {




    Page<Customer> findAll(Pageable pageable);

    Page<Customer> findAllNoSuspended(Pageable pageable);

    Iterable<Customer> findAllByIdIsSuspended(Long id);
}
