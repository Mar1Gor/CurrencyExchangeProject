/*
package pl.bank.bankAccountProj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "transfer_log")
public class TransferLog {
    @Id
    @Column(name = "log_id")
    private Long logId;
    @Column(name = "account_id")
    private String accountId;
    @Column(name = "acc_id")
    private BigDecimal amount;
    @Column(name = "acc_id")
    private Date insertDate;

    public TransferLog(Long logId, String accountId, BigDecimal amount, Date insertDate) {
        this.logId = logId;
        this.accountId = accountId;
        this.amount = amount;
        this.insertDate = insertDate;
    }

    public TransferLog() {
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }
}
*/
