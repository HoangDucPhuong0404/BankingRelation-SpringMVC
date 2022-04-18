package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.Deposit;
import com.codegym.service.customer.ICustomerService;
import com.codegym.service.deposit.DepositService;
import com.codegym.service.deposit.IDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import java.util.List;
import java.util.Optional;

@Controller
public class DepositController {

    @Autowired
    private IDepositService depositService;

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/deposit/{id}")
    public ModelAndView showDepositForm(@PathVariable Long id){
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/transaction/deposit");
            modelAndView.addObject("deposit", new Deposit());
            modelAndView.addObject("customer", customer.get());
            modelAndView.addObject("success", null);
            modelAndView.addObject("error", null);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error");
            return modelAndView;
        }
    }
    @PostMapping("/deposit/{customerId}")
    public ModelAndView saveDeposits(@PathVariable Long customerId, @Validated @ModelAttribute("deposit") Deposit deposit, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("/transaction/deposit");
        Customer customer = customerService.findById(customerId).get();
        long money_deposits = deposit.getAmount();

        boolean isMoney = false;

        if ((money_deposits >= 1000)&&(money_deposits <= 1000000000)){
            isMoney = true;
        }
        String error = null;
        if (bindingResult.hasFieldErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            error = "Deposit error \n";
            for (int i = 0; i<errorList.size(); i ++){
                error +=  "***"+ errorList.get(i).getDefaultMessage() + "\n";
            }
            modelAndView.addObject("error", error);
        }try {
            deposit.setCustomer(customer);
            depositService.save(deposit);
            customer.setBalance(customer.getBalance() + deposit.getAmount());
            customerService.save(customer);
            modelAndView.addObject("message", "Deposits successfully");
            modelAndView.addObject("deposit", new Deposit());
            modelAndView.addObject("customer", customer);
        }catch (Exception e){
            modelAndView.addObject("error", error);
            modelAndView.addObject("deposit", new Deposit());
            modelAndView.addObject("customer", customer);
        }
        return modelAndView;
    }
}
