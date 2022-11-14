package pl.bank.bankAccountProj.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "sub_account")
public class SubAccount {
    @Id
    @Column(name = "sa_id")
    private String saId;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "currency")
    private String currency;
    @Column(name = "modify_date")
    private Date modifyDate;

    @ManyToOne
    @JoinColumn(name="acc_id", nullable=false)
    private Account account;

    public SubAccount() {
    }

    public SubAccount(BigDecimal balance, String currency, Date modifyDate, Account account) {
        this.saId = this.saId = String.valueOf(UUID.randomUUID());
        this.balance = balance;
        this.currency = currency;
        this.modifyDate = modifyDate;
        this.account = account;
    }

    public String getSaId() {
        return saId;
    }

    public void setSaId(String saId) {
        this.saId = saId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
