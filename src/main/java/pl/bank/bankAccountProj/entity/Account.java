package pl.bank.bankAccountProj.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "acc_id")
    private String accId;
    @Column(name = "balance_pln")
    private BigDecimal balancePln;
    @Column(name = "balance_usd")
    private BigDecimal balanceUsd;
    @Column(name = "insert_date")
    private Date insertDate;
    @Column(name = "modify_date")
    private Date modifyDate;

    @OneToOne(mappedBy = "account")
    private BankUser bankUser;

    @OneToMany(mappedBy = "account")
    private Collection<SubAccount> subAccountCollection;

    public Account( BigDecimal balancePln, Date insertDate, Date modifyDate, BankUser bankUser) {
        this.accId = String.valueOf(UUID.randomUUID());
        this.balancePln = balancePln;
        this.insertDate = insertDate;
        this.modifyDate = modifyDate;
        this.bankUser = bankUser;
    }

    public Account( BigDecimal balancePln, BigDecimal balanceUsd, Date insertDate, Date modifyDate, BankUser bankUser) {
        this.balancePln = balancePln;
        this.balanceUsd = balanceUsd;
        this.insertDate = insertDate;
        this.modifyDate = modifyDate;
        this.bankUser = bankUser;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public BigDecimal getBalancePln() {
        return balancePln;
    }

    public void setBalancePln(BigDecimal balancePln) {
        this.balancePln = balancePln;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public BankUser getUser() {
        return bankUser;
    }

    public void setUser(BankUser bankUser) {
        this.bankUser = bankUser;
    }

    public BigDecimal getBalanceUsd() {
        return balanceUsd;
    }

    public void setBalanceUsd(BigDecimal balanceUsd) {
        this.balanceUsd = balanceUsd;
    }

    public BankUser getBankUser() {
        return bankUser;
    }

    public void setBankUser(BankUser bankUser) {
        this.bankUser = bankUser;
    }

    public Collection<SubAccount> getSubAccountCollection() {
        return subAccountCollection;
    }

    public void setSubAccountCollection(Collection<SubAccount> subAccountCollection) {
        this.subAccountCollection = subAccountCollection;
    }
}
