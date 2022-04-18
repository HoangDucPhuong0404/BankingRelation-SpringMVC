package com.codegym.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "deposits")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Min(value = 1000, message = "amount not min 10000")
    @Max(value = 10000000000000L, message = "Amount not max 10000000000000")
    private Long amount;

    private boolean isSuspended = false;

    private LocalDateTime create_at = LocalDateTime.now();

    private Long create_by;

    private LocalDateTime update_at = LocalDateTime.now();

    private Long updated_by;
    public Deposit(Long id, Customer customer,
                   @Min(value = 1000, message = "Amount not min 1000$")
                   @Max(value = 10000000000L, message = "Amount not max 10000000000$") long amount, boolean isSuspended, LocalDateTime create_at) {
        this.id = id;
        this.customer = customer;
        this.amount = amount;
        this.isSuspended = isSuspended;
        this.create_at = create_at;
    }

    public Deposit(){}


    public Deposit(Long id, Customer customer,
                   @NotEmpty(message = "Amount not empty")
                   @Min(value = 1000, message = "Amount not min 1000$")
                   @Max(value = 10000000000L, message = "Amount not max 10000000000$") long amount, boolean isSuspended, LocalDateTime create_at, Long create_by, LocalDateTime update_at, Long updated_by) {
        this.id = id;
        this.customer = customer;
        this.amount = amount;
        this.isSuspended = isSuspended;
        this.create_at = create_at;
        this.create_by = create_by;
        this.update_at = update_at;
        this.updated_by = updated_by;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public Long getCreate_by() {
        return create_by;
    }

    public void setCreate_by(Long create_by) {
        this.create_by = create_by;
    }

    public LocalDateTime getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(LocalDateTime update_at) {
        this.update_at = update_at;
    }

    public Long getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(Long updated_by) {
        this.updated_by = updated_by;
    }
}
