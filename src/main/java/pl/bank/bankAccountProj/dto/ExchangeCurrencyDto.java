package pl.bank.bankAccountProj.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;

@Data
public class ExchangeCurrencyDto {
    private String accountId;
    private HashMap<String, BigDecimal> finalBalanceMap;

    @Builder
    public ExchangeCurrencyDto(String accountId, HashMap<String, BigDecimal> finalBalanceMap) {
        this.accountId = accountId;
        this.finalBalanceMap = finalBalanceMap;
    }
}
