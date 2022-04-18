package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.Withdraw;
import com.codegym.service.customer.ICustomerService;
import com.codegym.service.withdraw.IWithDrawService;
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

import java.util.List;
import java.util.Optional;

@Controller
public class WithDrawController {
    @Autowired
    private IWithDrawService withdrawService;

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/withdraw/{id}")
    private ModelAndView viewWithdraw(@PathVariable Long id) {

        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/transaction/withdraw");
            modelAndView.addObject("withdraw", new Withdraw());
            modelAndView.addObject("customer", customer.get());
            modelAndView.addObject("success", null);
            modelAndView.addObject("error", null);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error");
            return modelAndView;
        }
    }

    @PostMapping("/withdraw/{customerId}")
    private ModelAndView saveWithdraw(@PathVariable Long customerId
            ,@Validated @ModelAttribute("withdraw") Withdraw withdraw,
                                      BindingResult bindingResult) {
        Customer customer = customerService.findById(customerId).get();
        long money_withdraw = withdraw.getAmount();
        boolean isMoney = money_withdraw >= 1000;
        boolean isLimit = money_withdraw <= customer.getBalance();

        boolean isTrue = isMoney && isLimit;
        ModelAndView modelAndView = new ModelAndView("/transaction/withdraw");

        String error = null;
        if (bindingResult.hasFieldErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            error = "Edit customer error \n";
            for (int i = 0; i < errorList.size(); i++) {
                error += "***" + errorList.get(i).getDefaultMessage() + "\n";
            }
        }
        modelAndView.addObject("error", error);
        try {
            if (isTrue) {
                withdrawService.save(withdraw);
                customer.setBalance(customer.getBalance() - withdraw.getAmount());
                customerService.save(customer);
                modelAndView.addObject("success", "Withdraw successfully");
                modelAndView.addObject("withdraw", new Withdraw());
                modelAndView.addObject("customer", customer);
                return modelAndView;
            } else {
                modelAndView.addObject("error", "Withdraw not bigger balance");
                modelAndView.addObject("withdraw", new Withdraw());
                modelAndView.addObject("customer", customer);
                return modelAndView;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            modelAndView.addObject("error", error);
            modelAndView.addObject("withdraw", new Withdraw());
            modelAndView.addObject("customer", customer);
        }
        return modelAndView;
    }
}
