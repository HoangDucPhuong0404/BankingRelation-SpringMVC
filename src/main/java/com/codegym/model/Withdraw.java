package com.codegym.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "withdraws")
public class Withdraw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Min(value = 1000, message = "Withdraw not min 1000$")
    @Max(value = 100000000000L, message = "Withdraw not max 100000000000$")
    private long amount;

    private boolean isSuspended = false;

    private LocalDateTime create_at = LocalDateTime.now();

    private Long create_by;

    private LocalDateTime update_at = LocalDateTime.now();

    private Long update_by;

    public Withdraw() {}

    public Withdraw(Long id, Customer customer,
                    @Min(value = 1000, message = "Withdraw not min 1000$")
                    @Max(value = 100000000000L, message = "Withdraw not max 100000000000$")long amount, boolean isSuspended, LocalDateTime create_at, Long create_by, LocalDateTime update_at, Long update_by) {
        this.id = id;
        this.customer = customer;
        this.amount = amount;
        this.isSuspended = isSuspended;
        this.create_at = create_at;
        this.create_by = create_by;
        this.update_at = update_at;
        this.update_by = update_by;
    }

    public Withdraw(Long id, Customer customer,
                    @Min(value = 1000, message = "Withdraw not min 1000$")
                    @Max(value = 100000000000L, message = "Withdraw not max 100000000000$")long amount, boolean isSuspended, LocalDateTime create_at) {
        this.id = id;
        this.customer = customer;
        this.amount = amount;
        this.isSuspended = isSuspended;
        this.create_at = create_at;
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

    public Long getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(Long update_by) {
        this.update_by = update_by;
    }
}
