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
    @Column(name = "insert_date")
    private Date insertDate;
    @Column(name = "modify_date")
    private Date modifyDate;

    @OneToOne(mappedBy = "account")
    private BankUser bankUser;

    @OneToMany(mappedBy = "account")
    private Collection<SubAccount> subAccountCollection;
    public Account(Date insertDate, Date modifyDate, BankUser bankUser, Collection<SubAccount> subAccountCollection) {
        this.accId = String.valueOf(UUID.randomUUID());
        this.insertDate = insertDate;
        this.modifyDate = modifyDate;
        this.bankUser = bankUser;
        this.subAccountCollection = subAccountCollection;
    }

    public Account(Date insertDate, Date modifyDate, BankUser bankUser) {
        this.accId = String.valueOf(UUID.randomUUID());
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
