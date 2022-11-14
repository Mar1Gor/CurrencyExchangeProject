package pl.bank.bankAccountProj.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

@Data
public class GetAccountInfoDto {
    private String userName;
    private Long pesel;
    private Date lastOperationDate;
    private HashMap<String, BigDecimal> balances;



    @Builder
    public GetAccountInfoDto(String userName, Long pesel, Date lastOperationDate, HashMap<String, BigDecimal> balances) {
        this.userName = userName;
        this.pesel = pesel;
        this.lastOperationDate = lastOperationDate;
        this.balances = balances;
    }



}
