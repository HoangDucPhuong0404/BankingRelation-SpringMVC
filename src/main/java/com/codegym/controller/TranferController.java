package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.Tranfer;
import com.codegym.service.customer.ICustomerService;
import com.codegym.service.transfer.ITransferService;
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
public class TranferController {

    @Autowired
    private ITransferService transferService;

    @Autowired
    private ICustomerService customerService;


    @PostMapping("/transfers/{senderId}")
    private ModelAndView doTransfer(@PathVariable Long senderId, @Validated @ModelAttribute Tranfer transfer,
                                    BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("/transaction/transfer");
        Optional<Customer> sender = customerService.findById(senderId);
        List<Customer> recipients = null;
        String error = null;

        if (sender.isPresent()) {

            recipients = (List<Customer>) customerService.findAllByIdIsSuspended(senderId);
            if (!transfer.getRecipient().getId().equals(sender.get().getId())) {
                Optional<Customer> recipientOptional = customerService.findById(transfer.getRecipient().getId());
                if (recipientOptional.isPresent()) {
                    long senderBalance = sender.get().getBalance();
                    long recipientBalance = recipientOptional.get().getBalance();
                    long transferAmount = transfer.getTransferAmount();
                    long fees = 10;
                    long feesAmount = transferAmount / fees;
                    long transactionAmount = transferAmount + feesAmount;

                    boolean isMoney = transferAmount >= 1000 && transferAmount < 10000000000L;
                    boolean isLimit = senderBalance - transactionAmount > 0;

                    try {
                        if (bindingResult.hasFieldErrors()) {
                            List<ObjectError> errorList = bindingResult.getAllErrors();
                            error = "Transfer error \n";
                            for (int i = 0; i < errorList.size(); i++) {
                                error += "***" + errorList.get(i).getDefaultMessage() + "\n";
                            }
                            modelAndView.addObject("error", error);
                        }
                        if (isMoney && isLimit) {
                            sender.get().setBalance(senderBalance - transactionAmount);
                            customerService.save(sender.get());

                            recipientOptional.get().setBalance(recipientBalance + transactionAmount);
                            customerService.save(recipientOptional.get());

                            transfer.setFees(fees);
                            transfer.setFeesAmount(feesAmount);
                            transfer.setTransactionAmount(transactionAmount);
                            transferService.save(transfer);
                            modelAndView.addObject("success", "Transfer successfully");
                            modelAndView.addObject("transfer", new Tranfer());
                            modelAndView.addObject("sender", sender.get());
                            modelAndView.addObject("recipients", recipients);
                            return modelAndView;
                        }
                    } catch (Exception e) {
                        modelAndView.addObject("error", error);
                        modelAndView.addObject("transfer", new Tranfer());
                        modelAndView.addObject("sender", sender.get());
                        modelAndView.addObject("recipients", recipients);
                        return modelAndView;

                    }
                }

            }
        } else {
            modelAndView.addObject("error", "Bug");
            modelAndView.addObject("transfer", new Tranfer());
            modelAndView.addObject("sender", sender.get());
            modelAndView.addObject("recipients", recipients);
            return modelAndView;
        }
        modelAndView.addObject("error", "Please you have to fill all");
        modelAndView.addObject("transfer", new Tranfer());
        modelAndView.addObject("sender", sender.get());
        modelAndView.addObject("recipients", recipients);
        return modelAndView;
    }

    @GetMapping("/transfers/{id}")
    public ModelAndView viewTransferCustom(@PathVariable Long id) {
        Optional<Customer> sender = customerService.findById(id);

        Iterable<Customer> recipients = customerService.findAllByIdIsSuspended(id);
//        Iterable<Customer> customers = customerService.findAll() ;

        if (sender.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/transaction/transfer");
            modelAndView.addObject("success", null);
            modelAndView.addObject("error", null);
            modelAndView.addObject("transfer", new Tranfer());
            modelAndView.addObject("sender", sender.get());
            modelAndView.addObject("recipients", recipients);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error");
            return modelAndView;
        }
    }

    @GetMapping("/history-transfers")
    public ModelAndView showListTransfer() {
        ModelAndView modelAndView = new ModelAndView("/transaction/transfer-list");
        Iterable<Tranfer> transfers = transferService.findAll();
        long total = 0;
        for (Tranfer transfer : transfers) {
            total += transfer.getFeesAmount();

        }
        modelAndView.addObject("transfers", transfers);
        modelAndView.addObject("total", total);
        return modelAndView;
    }

}