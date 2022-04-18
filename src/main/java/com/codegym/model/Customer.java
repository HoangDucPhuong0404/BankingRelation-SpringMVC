package com.codegym.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name not emty pleasee !")
    @Size(min = 1, max = 50,message = "Full name description within 255 character")
    private String fullName;

    @Pattern(regexp = "(^[a-z][a-z0-9_\\.]{3,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,7}){1,7}$)" , message = "Mail not true, Ex: codegymhue2021@codegym.com ")
    @NotEmpty(message = "email not empty")
    @Column(unique = true)
    private String email;

    @Pattern(regexp ="(^$|[0][0-9]{9,10}$)",message = "Formatter not true, phone number is have 10-11 character ! " )
    @NotEmpty(message = "phone not empty")
    private String phone;

    @Size(min=1 , max=255 , message = "Address description within 255 characters ! ")
    @NotEmpty(message = "address not empty")
    private String address;

    @Min(value = 0, message = "balance not 0")
    private long balance = 0;

    private boolean suspended = false;
    private LocalDateTime create_at = LocalDateTime.now();

    @OneToMany(targetEntity = Deposit.class, mappedBy = "customer")
    private List<Deposit> deposits;

    @OneToMany(targetEntity = Withdraw.class, mappedBy = "customer")
    private List<Withdraw> withdraws;

    @OneToMany(targetEntity = Tranfer.class, mappedBy = "sender")
    private List<Tranfer> senders;

    @OneToMany(targetEntity = Tranfer.class, mappedBy = "recipient")
    private List<Tranfer> recipients;

    public Customer( @NotEmpty(message = "Name not empty")
//                     @Pattern(regexp = "\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+", message = "Name format not true, Ex example : Nguyễn Văn A")
                     @Size(min=1 , max=45 ,message = "Full name description within 255 characters ! ")
                             String fullName,
                     @Pattern(regexp = "^[a-z][a-z0-9_\\.]{3,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,7}){1,7}$" , message = "Mail not true, Ex: codegymhue2021@codegym.com")
                     @NotEmpty(message = "email not empty")
                             String email,
                     @Pattern(regexp ="(^$|[0][0-9]{9,10}$)",message = "Formatter not true, phone number is have 10-11 character !" )
                     @NotEmpty(message = "phone not empty")
                             String phone,
                     @Size(min=1 , max=255 , message = "Address description within 255 characters ! ")
                     @NotEmpty(message = "address not empty")
                             String address) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
    public Customer() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }

    public List<Withdraw> getWithdraws() {
        return withdraws;
    }

    public void setWithdraws(List<Withdraw> withdraws) {
        this.withdraws = withdraws;
    }

    public List<Tranfer> getSenders() {
        return senders;
    }

    public void setSenders(List<Tranfer> senders) {
        this.senders = senders;
    }

    public List<Tranfer> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Tranfer> recipients) {
        this.recipients = recipients;
    }
}
