package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;


    @GetMapping("")
    public ModelAndView showList(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
                           Pageable pageable){
        Page<Customer> customers = customerService.findAllNoSuspended(pageable);
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
    @GetMapping("/create-customer")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }
    @PostMapping("/create-customer")
    public ModelAndView saveCustomer(@Validated @ModelAttribute("customer")Customer customer,
                                     BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        String error =  null;
        if (bindingResult.hasFieldErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            error = "Customer created error \n";
            for (int i = 0; i < errorList.size(); i++){
                error += "***" + errorList.get(i).getDefaultMessage()+ "\n";

            }
            modelAndView.addObject("error", error);
        }
        try {
            customerService.save(customer);
            modelAndView.addObject("customer", new Customer());
//            modelAndView.addObject("error", error);
            modelAndView.addObject("message", "New customer created successfully");
            return modelAndView;
        }catch (Exception e){
            System.out.println(e.getMessage());
            modelAndView.addObject("customer", new Customer());
            modelAndView.addObject("error", error);
            return modelAndView;
        }
    }
    @GetMapping("/edit-customer/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/customer/edit");
            modelAndView.addObject("customer", customer.get());
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error");
            return modelAndView;
        }
    }
    @PostMapping("/edit-customer")
    public ModelAndView updateCustomer(@Validated @ModelAttribute("customer") Customer customer, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        String error = null;
        if (bindingResult.hasFieldErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            error = "Edit customer error \n";
            for (int i = 0; i < errorList.size(); i ++){
                error += "***" + errorList.get(i).getDefaultMessage() + "\n";
            }
            modelAndView.addObject("error", error);
        }try {
            customerService.save(customer);
            modelAndView.addObject("customer", customer);
            modelAndView.addObject("message", "Update Customer success");
            return modelAndView;
        }catch (Exception e){
            modelAndView.addObject("error", error);
            modelAndView.addObject("customer", customer);
            return modelAndView;
        }
    }
    @GetMapping("/delete-customer/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Optional<Customer> customerOptional = customerService.findById(id);
        if (customerOptional.isPresent()){
            ModelAndView modelAndView1 = new ModelAndView("/customer/delete");
            modelAndView1.addObject("customer", customerOptional.get());
            return modelAndView1;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error");
            return modelAndView;
        }
    }
    @PostMapping("/delete-customer")
    public String deleteCustomer(@ModelAttribute Customer customer){
        customerService.remove(customer.getId());
        return "redirect:/customer";
    }
}
