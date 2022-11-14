package pl.bank.bankAccountProj.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "bank_user")
public class BankUser implements Serializable {
    @Id
    @Column(name = "pesel")
    private Long pesel;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "insertDate")
    private Date insertDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "acc_id")
    private Account account;

    public BankUser() {
    }

    public BankUser(Long pesel, String name, String surname, Date insertDate) {
        this.pesel = pesel;
        this.name = name;
        this.surname = surname;
        this.insertDate = insertDate;
    }

    public BankUser(Long pesel, String name, String surname, Date insertDate, Account account) {
        this.pesel = pesel;
        this.name = name;
        this.surname = surname;
        this.insertDate = insertDate;
        this.account = account;
    }

    public Long getPesel() {
        return pesel;
    }

    public void setPesel(Long pesel) {
        this.pesel = pesel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
