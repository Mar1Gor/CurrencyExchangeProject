package pl.bank.bankAccountProj.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;

public class GetAccountInfoDto {
    private String userName;
    private Long pesel;
    private Date lastOperationDate;
    private BigDecimal balancePln;
    private BigDecimal balanceUsd;



    @Builder
    public GetAccountInfoDto(String userName, Long pesel, Date lastOperationDate, BigDecimal balancePln, BigDecimal balanceUsd) {
        this.userName = userName;
        this.pesel = pesel;
        this.lastOperationDate = lastOperationDate;
        this.balancePln = balancePln;
        this.balanceUsd = balanceUsd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getPesel() {
        return pesel;
    }

    public void setPesel(Long pesel) {
        this.pesel = pesel;
    }

    public Date getLastOperationDate() {
        return lastOperationDate;
    }

    public void setLastOperationDate(Date lastOperationDate) {
        this.lastOperationDate = lastOperationDate;
    }

    public BigDecimal getBalancePln() {
        return balancePln;
    }

    public void setBalancePln(BigDecimal balancePln) {
        this.balancePln = balancePln;
    }

    public BigDecimal getBalanceUsd() {
        return balanceUsd;
    }

    public void setBalanceUsd(BigDecimal balanceUsd) {
        this.balanceUsd = balanceUsd;
    }
}
